package card.cardapio.services;

import card.cardapio.dto.MesaDto;
import card.cardapio.dto.user.UserRequestDto;
import card.cardapio.entitie.Mesa;
import card.cardapio.entitie.Users;
import card.cardapio.repositories.MesaRepository;
import card.cardapio.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MesaService {


    private UserService service;
    private final MesaRepository repository;
    private final UserRepository userRepository;
    public MesaService(MesaRepository repository, UserRepository userRepository) {
        this.repository  = repository;
        this.userRepository = userRepository;
    }
    public void salvarMesa(MesaDto mesaDto) {

        Users user = this.userRepository.findById(mesaDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + mesaDto.getUserId()));
        Mesa mesa = new Mesa();

        if (mesaDto.getMesaId() != null) {
            mesa.setId(mesaDto.getMesaId());
        }
        mesa.setCliente(user);
        this.repository.save(mesa);
    }

}
