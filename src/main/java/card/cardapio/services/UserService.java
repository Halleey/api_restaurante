package card.cardapio.services;

import card.cardapio.dto.user.UserRequestDto;
import card.cardapio.entitie.TokenReset;
import card.cardapio.entitie.Users;
import card.cardapio.repositories.AddressRepository;
import card.cardapio.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private final  EmailService emailService;
    private final TokenRecovyPass tokenService;
    @Autowired
    public UserService(UserRepository repository,
                       BCryptPasswordEncoder passwordEncoder,
                       AddressRepository addressRepository,
                       EmailService emailService,TokenRecovyPass tokenService)
    {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
        this.emailService = emailService;
        this.tokenService = tokenService;
    }
    public void saveUser(UserRequestDto requestDTO) {
        String encryptedPassword = passwordEncoder.encode(requestDTO.password());
        Users userData = new Users(
                requestDTO.name(),
                requestDTO.lastName(),
                encryptedPassword,
                requestDTO.email()
        );


        repository.save(userData);
        String destinatario = requestDTO.email();
        String assunto = "Bem-vindo ao nosso sistema!";
        String mensagem = "Olá " + requestDTO.name() + ",\n\nBem-vindo ao nosso sistema.";

        emailService.enviarEmail(destinatario, assunto, mensagem);
    }
    @Transactional(readOnly = true)
    public Users buscarPorNome(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Usuário não encontrado para o nome: " + name)));
    }

    public Users.Role buscarPorRoleUser(String name) {
        return repository.findByRoleUser(name);
    }

    public Optional<Users> getId(Long id) {
        return repository.findById(id);
    }
    public Users findByEmail(String email) {
        return repository.findByEmail(email);
    }
    public void iniciarRecuperacaoSenha(String email) {
        Users user = findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("Usuário não encontrado para o e-mail: " + email);
        }
        TokenReset tokenReset = tokenService.createToken(user);

        String destinatario = email;
        String assunto = "Recuperação de senha - Token de Verificação";
        String mensagem = "Olá " + user.getName() + ",\n\n"
                + "Você solicitou a recuperação de senha. Utilize o seguinte token para redefinir sua senha: "
                + tokenReset.getToken();

        emailService.enviarEmail(destinatario, assunto, mensagem);
    }
    public void alterPassword(String email, String token, String novaSenha) throws Exception {
        Users user = repository.findByEmail(email);
        if (user == null) {
            throw new Exception("Usuário não encontrado");
        }

        TokenReset tokenReset = tokenService.findByToken(token);
        if (tokenReset == null) {
            throw new IllegalArgumentException("Token não é válido");
        }

        if (tokenService.isTokenExpired(tokenReset)) {
            throw new IllegalArgumentException("Token inválido ou expirado.");
        }

        if (!tokenReset.getUser().getEmail().equals(email)) {
            throw new IllegalArgumentException("Token inválido para o e-mail fornecido.");
        }
        String encryptedPassword = passwordEncoder.encode(novaSenha);
        user.setPassword(encryptedPassword
        );
        repository.save(user);
        tokenService.deleteToken(tokenReset);
    }
}