package card.cardapio.entitie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENTE;
    public enum Role {
        ROLE_ADMIN, ROLE_CLIENTE
    }

//    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
//    private List<Comments> comentarios;

    @ManyToOne
    @JoinColumn(name = "mesa_id") // Nome da coluna que faz referência à mesa na tabela de usuários
    private Mesa mesa;

    public Users(String name, String lastName, String password, String email) {
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

}
