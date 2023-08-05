package pl.internship.antologic.record.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.internship.antologic.record.dto.RecordDto;
import pl.internship.antologic.record.form.CreateRecordForm;
import pl.internship.antologic.record.form.UpdateRecordForm;
import pl.internship.antologic.record.service.RecordService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @GetMapping
    public List<RecordDto> getProjects(@RequestParam final UUID currentUserUuid){
        return recordService.findAll(currentUserUuid);
    }

    @PostMapping
    public RecordDto createRecord(@RequestParam final UUID currentUserUuid, @Valid @RequestBody final CreateRecordForm createRecordForm){
        return recordService.save(currentUserUuid, createRecordForm);
    }

    @PutMapping("/{uuidForUpdating}")
    public RecordDto updateProject(@RequestParam final UUID currentUserUuid, @PathVariable final UUID uuidForUpdating, @Valid @RequestBody final UpdateRecordForm updateRecordForm){
        return recordService.update(currentUserUuid, uuidForUpdating, updateRecordForm);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void updateProject(@RequestParam final UUID currentUserUuid, @PathVariable final UUID uuid){
        recordService.deleteByUuid(currentUserUuid, uuid);
    }
}
