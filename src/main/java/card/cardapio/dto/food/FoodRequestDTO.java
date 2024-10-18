package card.cardapio.dto.food;

import java.math.BigDecimal;

public record FoodRequestDTO(
        String title,
        BigDecimal price,
        String image,
        String description,
        String categoria,
        String categoriaGeral
) {}