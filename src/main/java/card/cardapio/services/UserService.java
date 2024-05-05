package card.cardapio.services;

import card.cardapio.dto.user.UserRequestDto;
import card.cardapio.entitie.Mesa;
import card.cardapio.entitie.Users;
import card.cardapio.repositories.MesaRepository;
import card.cardapio.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MesaRepository mesaRepository;
    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder, MesaRepository mesaRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        //terminar
        this.mesaRepository= mesaRepository;
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
    }
    @Transactional(readOnly = true)
    public Users buscarPorNome(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Usuário não encontrado para o nome: " + name)));
    }

    public List<Mesa> getMesas() {
        return mesaRepository.findAll();
    }


    public void associarUsuarioAMesa(Long userId, Long mesaId) {
        Users user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + userId));
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new EntityNotFoundException("Mesa não encontrada com o ID: " + mesaId));

        mesa.setCliente(user);
        mesaRepository.save(mesa);
    }


    public void desassociarUsuarioDeMesa(Long userId) {
        Users user = repository.findById(userId)
                .orElseThrow(()
                        -> new EntityNotFoundException("Usuário não encontrado com o ID: " + userId));
        user.setMesas(null);
        repository.save(user);
    }
    public Users.Role buscarPorRoleUser(String name) {
        return repository.findByRoleUser(name);
    }
}