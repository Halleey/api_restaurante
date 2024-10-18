package card.cardapio.repositories;
import card.cardapio.entitie.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByCategoriaGeralAndCategoria(String categoriaGeral, String categoria);

    List<Food> findByCategoriaGeral(String categoriaGeral);

}
