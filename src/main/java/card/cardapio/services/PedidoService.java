package card.cardapio.services;
import card.cardapio.dto.pedido.PedidoDto;
import card.cardapio.entitie.Pedido;
import card.cardapio.repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }


    public List<Pedido> savePedidos(List<Pedido> pedidos) {
        return repository.saveAll(pedidos);
    }

    // Buscar pedidos com pagamento aprovado e informações do usuário
    public List<PedidoDto> getPedidosWithApprovedPaymentAndUserDetails() {
        return repository.findPedidosWithApprovedPayment().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDto> getOptionalPedidos() {
        return repository.findPedidosWithOptionalAddressAndNumber().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    private PedidoDto convertToDTO(Pedido pedido) {
        String userName = pedido.getUser().getName();
        String userAddress = pedido.getUser().getAddress();
        String userNumber = pedido.getUser().getNumber();
        String userEmail = pedido.getUser().getEmail();
        return new PedidoDto(
                pedido.getId(),
                pedido.getTitle(),
                pedido.getPrice(),
                userName,
                userAddress,
                userNumber,
                pedido.getOptionalAddress(),
                pedido.getOptionalNumber(),
                userEmail,
                pedido.getLocalDateTime() // Inclui o horário do pedido no DTO
        );
    }

    @Transactional
    public void removePedidoById(Long pedidoId) {
        repository.deleteById(pedidoId);
    }
}