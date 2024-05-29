package card.cardapio.entitie;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue
    private long id;
    private String address;
    private String numero;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Users users;

    public void setId(long id) {
        this.id = id;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public Users getUsers() {
        return users;
    }

    public String getNumero() {
        return numero;
    }

    public String getAddress() {
        return address;
    }
}
