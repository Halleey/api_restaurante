package card.cardapio.controller;
import card.cardapio.dto.pedido.PedidoDto;
import card.cardapio.services.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }


    @GetMapping()
    public List<PedidoDto> getPedidosWithApprovedPayment() {
        return service.getPedidosWithApprovedPayment();
    }

    @GetMapping("/optional")
    public  List<PedidoDto>  getOptionalPedido() {
        return  service.getOptionalPedidos();
    }


    @DeleteMapping("/{pedidoId}")
    public void deletePedidoById(@PathVariable Long pedidoId) {
        service.removePedidoById(pedidoId);
    }
}