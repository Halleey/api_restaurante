package card.cardapio.controller;

import card.cardapio.dto.pedido.PedidoDto;
import card.cardapio.entitie.Pedido;
import card.cardapio.repositories.PedidoRepository;
import card.cardapio.services.PedidoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PedidoDto> getAll() {
        return service.getAll();
    }
}