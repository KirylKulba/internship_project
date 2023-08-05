package pl.internship.antologic.record.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.internship.antologic.project.entity.Project;
import pl.internship.antologic.user.entity.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="record", schema = "public")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",
            unique = true,
            nullable = false)
    private Long id;

    @Column(nullable = false, name = "uuid")
    @EqualsAndHashCode.Include
    private final UUID uuid = UUID.randomUUID();

    @Column(nullable = false, name = "begin_timestamp")
    @EqualsAndHashCode.Include
    private LocalDateTime beginTimestamp;

    @Column(nullable = false, name = "end_timestamp")
    @EqualsAndHashCode.Include
    private LocalDateTime endTimestamp;

    @Column(nullable = false,
            name = "work_cost",
            precision = 10,
            scale = 2)
    private BigDecimal workCost;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
