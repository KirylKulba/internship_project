package pl.internship.antologic.record.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class UpdateRecordForm {
    private LocalDateTime beginTimestamp;
    private LocalDateTime endTimestamp;
}
