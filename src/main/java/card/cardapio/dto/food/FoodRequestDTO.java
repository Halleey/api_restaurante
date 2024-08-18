package card.cardapio.dto.food;
public record FoodRequestDTO(
        String title,
        Integer price,
        String image,
        String description,
        String categoria,
        String categoriaGeral
) {}
