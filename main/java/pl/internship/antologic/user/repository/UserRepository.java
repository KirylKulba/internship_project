package pl.internship.antologic.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.internship.antologic.report.utils.ReportStats;
import pl.internship.antologic.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    boolean existsByLogin(String login);
    boolean existsByUuid(final UUID uuid);
    boolean existsByLoginAndUuidNot(String login, UUID uuid);
    Optional<User> findUserByUuid(final UUID uuid);
    void deleteUserByUuid(UUID uuid);

    @Query( "from User u " +
            "left join fetch u.records r " +
            "where u.uuid=:userUuid")
    Optional<User> findUserByUuidWithRecords(@Param("userUuid") final UUID uuid);


    @Query( value = "SELECT * from get_user_stats(:uuid, :beginDate, :endDate);",nativeQuery = true)
    List<ReportStats> findUserStats(
            final UUID uuid,
            final LocalDateTime beginDate,
            final LocalDateTime endDate);

}
