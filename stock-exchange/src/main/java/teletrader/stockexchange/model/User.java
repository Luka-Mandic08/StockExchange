package teletrader.stockexchange.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="Users")
public class User implements UserDetails{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer id;

    @Column(name="name", nullable=false)
    @Getter @Setter
    private String name;

    @Column(name="surname", nullable=false)
    @Getter @Setter
    private String surname;

    @Column(name = "email", unique = true, nullable = false)
    @Getter @Setter
    private String email;

    @Column(name = "password", nullable = false)
    @Setter
    private String password;

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public User() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
