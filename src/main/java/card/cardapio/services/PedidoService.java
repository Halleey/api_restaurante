package card.cardapio.services;
import card.cardapio.dto.pedido.PedidoDto;
import card.cardapio.entitie.Pedido;
import card.cardapio.repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
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


    // Buscar pedidos com pagamento aprovado
    public List<PedidoDto> getPedidosWithApprovedPayment() {
        return repository.findPedidosWithApprovedPayment().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private PedidoDto convertToDTO(Pedido pedido) {
        String userName = pedido.getUser().getName();
        String userAddress = pedido.getUser().getAddresses().isEmpty() ? "" : pedido.getUser().getAddresses().get(0).getAddress();
        String userNumber = pedido.getUser().getAddresses().isEmpty() ? "" : pedido.getUser().getAddresses().get(0).getNumero();
        return new PedidoDto(pedido.getId(), pedido.getTitle(), pedido.getPrice(), userName, userAddress, userNumber);
    }


    @Transactional
    public void removePedidoById(Long pedidoId) {
        repository.deleteById(pedidoId);
    }
}

