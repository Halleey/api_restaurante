package card.cardapio.services;
import card.cardapio.dto.food.FoodResponseDTO;
import card.cardapio.entitie.Food;
import card.cardapio.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodRepository repository;

    public List<FoodResponseDTO> findFoodByCategoriaGeralAndCategoria(String categoriaGeral, String categoria) {
        if (categoriaGeral != null && categoria != null) {
            return repository.findByCategoriaGeralAndCategoria(categoriaGeral, categoria).stream()
                    .map(FoodResponseDTO::new)
                    .toList();
        } else if (categoriaGeral != null) {
            return repository.findByCategoriaGeral(categoriaGeral).stream()
                    .map(FoodResponseDTO::new)
                    .toList();
        } else {
            return repository.findAll().stream()
                    .map(FoodResponseDTO::new)
                    .toList();
        }
    }

    public void save(Food food) {
        repository.save(food);
    }

    public List<FoodResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(FoodResponseDTO::new)
                .toList();
    }

    public void removeFood(Long id) {
       repository.deleteById(id);
    }

}