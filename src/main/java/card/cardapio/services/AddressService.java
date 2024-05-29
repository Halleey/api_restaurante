package card.cardapio.services;

import card.cardapio.dto.address.AddressDTO;
import card.cardapio.entitie.Address;
import card.cardapio.entitie.Users;
import card.cardapio.repositories.AddressRepository;
import card.cardapio.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final UserRepository userRepository;
    private final AddressRepository repository;
    public AddressService(AddressRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public void saveAddress(Long userId, AddressDTO addressDTO) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Address addres = new Address();
        addres.setAddress(addressDTO.getAddress());
        addres.setNumero(addressDTO.getNumero());
        addres.setUsers(users);
        repository.save(addres);
    }

    public List<Address> getAddressAll() {
        return  repository.findAll();
    }

    public void removeAddress(Long userId, AddressDTO addressDTO) {
        Users users = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Address address = repository.findByUsersAndAddressAndNumero(users, addressDTO.getAddress(), addressDTO.getNumero());
        repository.delete(address);
    }
}

