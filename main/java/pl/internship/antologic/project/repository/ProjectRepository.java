package pl.internship.antologic.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.internship.antologic.project.entity.Project;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project,Long>, JpaSpecificationExecutor<Project> {
    boolean existsByName(final String name);
    boolean existsByNameAndUuidNot(String name, UUID uuid);
    boolean existsByUuid(UUID uuid);

    @Query( "from Project p " +
            "left join fetch p.users u " +
            "left join fetch p.records r " +
            "where p.uuid=:projectUuid")
    Optional<Project> findProjectByUuidWithRecordsAndUsers(@Param("projectUuid") final UUID uuid);
    Optional<Project> findProjectByUuid(final UUID uuid);

    void deleteByUuid(UUID projectUuid);


}
