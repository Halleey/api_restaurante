package card.cardapio.controller;
import card.cardapio.dto.food.FoodRequestDTO;
import card.cardapio.dto.food.FoodResponseDTO;
import card.cardapio.entitie.Food;
import card.cardapio.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("food")
@CrossOrigin("http://localhost:5173")
public class FoodController {
    @Autowired
    private FoodRepository repository;
    @PostMapping("/save")
    public void saveFood(@RequestBody FoodRequestDTO data){
        Food foodData = new Food(data);
        repository.save(foodData);
    }

    @GetMapping
    public List<FoodResponseDTO> getAll(){

        return repository.findAll().stream().map(FoodResponseDTO::new).toList();
    }
}