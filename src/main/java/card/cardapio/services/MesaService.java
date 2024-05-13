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

    private final MesaRepository repository;
    private final UserRepository userRepository;

    public MesaService(MesaRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public void salvarMesa(MesaDto mesaDto) {
        Long userId = mesaDto.getUserId(); // Obtém o ID do usuário do DTO
        if (userId == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo.");
        }

        Users user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + userId));

        Mesa mesa = new Mesa();

        if (mesaDto.getMesaId() != null) {
            mesa.setId(mesaDto.getMesaId());
        }
        mesa.setCliente(user);
        this.repository.save(mesa);
    }

    public void removerMesa(Long userId, Long mesaId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + userId));

        Mesa mesa = repository.findById(mesaId)
                .orElseThrow(() -> new EntityNotFoundException("Mesa não encontrada com o ID: " + mesaId));

        mesa.setCliente(null);
        repository.save(mesa);
    }
}
