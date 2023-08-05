package pl.internship.antologic.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import pl.internship.antologic.record.entity.Record;
import pl.internship.antologic.user.entity.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="project", schema = "public")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@FieldNameConstants
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",
            unique = true,
            nullable = false)
    private Long id;

    @Column(nullable = false, name = "uuid")
    @EqualsAndHashCode.Include
    private UUID uuid = UUID.randomUUID();

    @Column(name="name",
            nullable = false,
            unique = true,
            length = 50)
    @EqualsAndHashCode.Include
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="begin_date",
            nullable = false)
    private LocalDate beginDate;

    @Column(name="end_date",
            nullable = false)
    private LocalDate endDate;

    @Column(name="budget",
            nullable = false,
            precision = 10,
            scale = 2)
    private BigDecimal budget;

    @Column(name="budget_use",
            nullable = false,
            precision = 5,
            scale = 2)
    private BigDecimal budgetUse;


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "user_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @OneToMany(
            mappedBy = "project",
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    private Set<Record> records = new HashSet<>();

    public void addRecord(final Record record) {
        records.add(record);
        record.setProject(this);
    }

    public void removeRecord(final Record record) {
        records.remove(record);
        record.setProject(null);
    }

    public void addUser(final User user){
        users.add(user);
        user.getProjects().add(this);
    }

    public void removeUser(final User user){
        users.remove(user);
        user.getProjects().remove(this);
    }

}

