package card.cardapio.controller;
import card.cardapio.dto.MesaDto;
import card.cardapio.services.MesaService;
import card.cardapio.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/mesa")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @PostMapping("/associar-usuario")
    public ResponseEntity<String> associarUsuarioAMesa(@RequestBody MesaDto mesaDto) {
        try {
            mesaService.salvarMesa(mesaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário associado à mesa com sucesso.");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
