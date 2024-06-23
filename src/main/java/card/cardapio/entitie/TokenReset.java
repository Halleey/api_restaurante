package card.cardapio.entitie;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
public class TokenReset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity =  Users.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Users user;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public Long getId() {
        return id;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public Users getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
