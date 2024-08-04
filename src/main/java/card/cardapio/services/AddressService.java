package card.cardapio.services;

import card.cardapio.dto.address.AddressDTO;
import card.cardapio.entitie.Address;
import card.cardapio.entitie.Users;
import card.cardapio.repositories.AddressRepository;
import card.cardapio.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Address address = new Address();
        address.setAddress(addressDTO.getAddress());
        address.setNumero(addressDTO.getNumber());
        address.setUsers(users);
        repository.save(address);
    }

    public List<AddressDTO> getAddressesByUserId(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Address> addresses = repository.findByUsers(user);

        List<AddressDTO> addressDTOs = addresses.stream()
                .map(address -> new AddressDTO(address.getAddress(), address.getNumero(), userId))
                .collect(Collectors.toList());

        return addressDTOs;
    }


    public void removeAddress(Long userId, AddressDTO addressDTO) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Address address = repository.findByUsersAndAddressAndNumero(users, addressDTO.getAddress(), addressDTO.getNumber());
        repository.delete(address);
    }
}