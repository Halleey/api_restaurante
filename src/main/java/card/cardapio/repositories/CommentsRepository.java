package card.cardapio.repositories;

import card.cardapio.entitie.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {


}
