package pl.internship.antologic.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.internship.antologic.record.entity.Record;

import java.util.Optional;
import java.util.UUID;

public interface RecordRepository extends JpaRepository<Record,Long> {
    Optional<Record> findByUuid(final UUID uuid);
    boolean existsByUuid(final UUID uuid);
    void deleteByUuid(final UUID uuid);
}
