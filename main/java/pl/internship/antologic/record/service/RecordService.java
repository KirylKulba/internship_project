package pl.internship.antologic.record.service;

import pl.internship.antologic.record.dto.RecordDto;
import pl.internship.antologic.record.form.CreateRecordForm;
import pl.internship.antologic.record.form.UpdateRecordForm;

import java.util.List;
import java.util.UUID;

public interface RecordService {
    List<RecordDto> findAll(final UUID currentUserUuid);
    RecordDto save(final UUID currentUserUuid, final CreateRecordForm userForm);
    RecordDto update(final UUID currentUserUuid, final UUID uuidForUpdating, final UpdateRecordForm userForm);
    void deleteByUuid(final UUID currentUserUuid, final UUID projectUuid);
}
