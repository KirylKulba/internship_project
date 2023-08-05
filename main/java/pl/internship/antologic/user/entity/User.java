package pl.internship.antologic.user.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import pl.internship.antologic.project.entity.Project;
import pl.internship.antologic.record.entity.Record;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name="user", schema = "public")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldNameConstants
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",
            unique = true,
            nullable = false)
    private Long id;

    @Column(nullable = false, name = "uuid")
    @EqualsAndHashCode.Include
    private UUID uuid = UUID.randomUUID();

    @Column(name="login",
            nullable = false,
            unique = true,
            length = 50)
    @EqualsAndHashCode.Include
    private String login;

    @Column(name="first_name",
            nullable = false,
            length = 50)
    private String firstName;

    @Column(name="last_name",
            nullable = false,
            length = 50)
    private String lastName;

    @Column(name="password",
            nullable = false,
            length = 50)
    private String password;

    @Column(name="email",
            nullable = false,
            length = 50)
    private String email;

    @Column(name="cost",
            nullable = false,
            precision = 5,
            scale = 2)
    private BigDecimal cost;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;


    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, fetch = FetchType.LAZY)
    private Set<Record> records = new HashSet<>();

    public void addRecord(final Record record) {
        records.add(record);
        record.setUser(this);
    }

    public void removeRecord(final Record record) {
        records.remove(record);
        record.setUser(null);
    }

    public void copy(final User userSource) {
        setEmail(userSource.getEmail()==null ? email : userSource.getEmail());
        setLogin(userSource.getLogin()==null ? login : userSource.getLogin());
        setPassword(userSource.getPassword()==null ? password : userSource.getPassword());
        setFirstName(userSource.getFirstName()==null ? firstName : userSource.getFirstName());
        setLastName(userSource.getLastName()==null ? lastName : userSource.getLastName());
        setCost(userSource.getCost()==null ? cost : userSource.getCost());
        setUserRole(userSource.getUserRole()==null ? userRole : userSource.getUserRole());
    }

}
