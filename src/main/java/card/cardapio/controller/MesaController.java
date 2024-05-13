package card.cardapio.controller;
import card.cardapio.dto.MesaDto;
import card.cardapio.services.MesaService;
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

    @PatchMapping("/user/{userId}/mesa/{mesaId}")
    public ResponseEntity<String> removerMesaDoUsuario(@PathVariable Long userId, @PathVariable Long mesaId) {
        try {
            mesaService.removerMesa(userId, mesaId);
            return ResponseEntity.ok("Mesa removida do usuário com sucesso.");
        } catch (EntityNotFoundException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
