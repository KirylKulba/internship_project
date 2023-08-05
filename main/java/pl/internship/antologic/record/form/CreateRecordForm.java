package pl.internship.antologic.record.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
public class CreateRecordForm {
    @NotNull(message="Record must have begin time")
    private LocalDateTime beginTimestamp;

    @NotNull(message="Record must have end time")
    private LocalDateTime endTimestamp;

    @NotNull(message="Record must be assigned to project")
    private UUID projectUuid;
}
