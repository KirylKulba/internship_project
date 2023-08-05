package pl.internship.antologic.record.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class RecordDto {
    private final UUID uuid;
    private final LocalDateTime beginTimestamp;
    private final LocalDateTime endTimestamp;
}
