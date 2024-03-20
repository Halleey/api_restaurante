package card.cardapio.repositories;
import card.cardapio.entitie.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
