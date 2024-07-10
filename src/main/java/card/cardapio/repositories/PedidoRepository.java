package card.cardapio.repositories;

import card.cardapio.entitie.Pedido;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Pedido p WHERE p.id = :pedidoId")
    void deleteById(@Param("pedidoId") Long pedidoId);
}