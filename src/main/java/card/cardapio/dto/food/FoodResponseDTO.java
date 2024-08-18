package card.cardapio.dto.food;

import card.cardapio.entitie.Food;

public record FoodResponseDTO(Long id, String title, String image, Integer price, String description,String categoria, String categoriaGeral
) {
    public FoodResponseDTO(Food food){
        this(food.getId(), food.getTitle(), food.getImage(), food.getPrice(), food.getDescription(),food.getCategoria(), food.getCategoriaGeral());
    }
}
