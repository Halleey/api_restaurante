package card.cardapio.services;

import card.cardapio.dto.pedido.PedidoDto;
import card.cardapio.entitie.Pedido;
import card.cardapio.repositories.PedidoRepository;
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

    public List<PedidoDto> getAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PedidoDto convertToDTO(Pedido pedido) {
        String userName = pedido.getUser().getName();
        String userAddress = pedido.getUser().getAddresses().isEmpty() ? "" : pedido.getUser().getAddresses().get(0).toString();
        return new PedidoDto(pedido.getId(), pedido.getTitle(), pedido.getPrice(), userName, userAddress);
    }
}