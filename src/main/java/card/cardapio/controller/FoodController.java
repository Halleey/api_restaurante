package card.cardapio.controller;
import java.util.List;

import card.cardapio.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import card.cardapio.dto.food.FoodRequestDTO;
import card.cardapio.dto.food.FoodResponseDTO;
import card.cardapio.entitie.Food;

@RestController
@RequestMapping("food")
@CrossOrigin("http://localhost:5173")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping("/save")
    public void saveFood(@RequestBody FoodRequestDTO data) {
        Food foodData = new Food(data);
        foodService.save(foodData);
    }

    @GetMapping
    public List<FoodResponseDTO> getAll(@RequestParam(required = false) String categoriaGeral,
                                        @RequestParam(required = false) String categoria) {
        if (categoriaGeral != null || categoria != null) {
            return foodService.findFoodByCategoriaGeralAndCategoria(categoriaGeral, categoria);
        } else {
            return foodService.findAll();
        }
    }
}