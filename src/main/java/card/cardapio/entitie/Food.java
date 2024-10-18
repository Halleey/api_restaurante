package card.cardapio.entitie;
import card.cardapio.dto.food.FoodRequestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "foods")
@Entity(name = "foods")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Food {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String image;
    private BigDecimal price;
    private String description;
    private String categoria;
    private String categoriaGeral;

    public String getCategoria() {
        return categoria;
    }

    public String getCategoriaGeral() {
        return categoriaGeral;
    }

    public void setCategoriaGeral(String categoriaGeral) {
        this.categoriaGeral = categoriaGeral;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Food(FoodRequestDTO data){
        this.image = data.image();
        this.price = data.price();
        this.title = data.title();
        this.description = data.description();
        this.categoria = data.categoria();
        this.categoriaGeral = data.categoriaGeral();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}