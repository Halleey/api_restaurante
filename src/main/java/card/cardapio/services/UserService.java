package card.cardapio.services;

import card.cardapio.dto.address.AddressDTO;
import card.cardapio.dto.user.UserRequestDto;
import card.cardapio.entitie.Address;
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
    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder, AddressRepository addressRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
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

    public Users.Role buscarPorRoleUser(String name) {
        return repository.findByRoleUser(name);
    }

    public Optional<Users> getId(Long id) {
        return repository.findById(id);
    }
}