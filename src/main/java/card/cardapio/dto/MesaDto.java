package card.cardapio.dto;

import card.cardapio.dto.user.UserRequestDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MesaDto {
    private Long id;
    @Getter
    @Setter
    private Long mesaId;
    @Getter
    @Setter
    private Long userId;

    private UserRequestDto userRequestDto;
}