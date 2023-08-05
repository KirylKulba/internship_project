package pl.internship.antologic.record.mapper;

import pl.internship.antologic.record.dto.RecordDto;
import pl.internship.antologic.record.entity.Record;
import pl.internship.antologic.record.form.CreateRecordForm;

public class RecordMapper {
    public static RecordDto toDto(final Record record){
        return RecordDto.builder()
                .uuid(record.getUuid())
                .beginTimestamp(record.getBeginTimestamp())
                .endTimestamp(record.getEndTimestamp())
                .build();
    }

    public static Record toEntity(final CreateRecordForm createRecordForm){
        return Record.builder()
                .beginTimestamp(createRecordForm.getBeginTimestamp())
                .endTimestamp(createRecordForm.getEndTimestamp())
                .build();
    }

}
