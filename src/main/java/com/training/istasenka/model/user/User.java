package com.training.istasenka.model.user;

import com.training.istasenka.util.UserRole;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@NaturalIdCache(region = "cache.users")
public class User implements Serializable {

    private static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    @NaturalId
    private String email;

    @Override
    public String toString() {
        return "User with " +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", userRole=" + role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User cachedUser = (User) o;
        return Objects.equals(email, cachedUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
