package card.cardapio.repositories;

import card.cardapio.entitie.Pedido;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Pedido p WHERE p.id = :pedidoId")
    void deleteById(@Param("pedidoId") Long pedidoId);

    @Query("SELECT p FROM Pedido p JOIN p.paypal py WHERE py.state = 'approved'")
    List<Pedido> findPedidosWithApprovedPayment();

    @Query("SELECT p FROM Pedido p WHERE p.optionalAddress IS NOT NULL AND p.optionalNumber IS NOT NULL")
    List<Pedido> findPedidosWithOptionalAddressAndNumber();

}