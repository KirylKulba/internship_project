package pl.internship.antologic.record.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.internship.antologic.common.error.NoRightsException;
import pl.internship.antologic.common.error.NotFoundException;
import pl.internship.antologic.common.utils.DateUtils;
import pl.internship.antologic.common.utils.ProjectUtils;
import pl.internship.antologic.common.utils.RecordUtils;
import pl.internship.antologic.project.entity.Project;
import pl.internship.antologic.project.repository.ProjectRepository;
import pl.internship.antologic.record.dto.RecordDto;
import pl.internship.antologic.record.entity.Record;
import pl.internship.antologic.record.form.CreateRecordForm;
import pl.internship.antologic.record.form.UpdateRecordForm;
import pl.internship.antologic.record.mapper.RecordMapper;
import pl.internship.antologic.record.repository.RecordRepository;
import pl.internship.antologic.user.entity.User;
import pl.internship.antologic.user.entity.UserRole;
import pl.internship.antologic.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RecordServiceImpl implements RecordService{

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public List<RecordDto> findAll(final UUID currentUserUuid) {
        final User user = findUserByUuid(currentUserUuid);

        checkIfUserHasPermissions(user, UserRole.recordRequiredRoles);

        return recordRepository.findAll().stream()
                .map(RecordMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RecordDto save(final UUID currentUserUuid, final CreateRecordForm createRecordForm) {
        final User user = findUserByUuid(currentUserUuid);
        checkIfUserHasPermissions(user, UserRole.recordRequiredRoles);

        final Project project = findProjectByUuid(createRecordForm.getProjectUuid());

        validateIfUserAssignedToProject(user, project);
        validateRecordDates(createRecordForm.getBeginTimestamp(), createRecordForm.getEndTimestamp());

        final Record record = RecordMapper.toEntity(createRecordForm);

        project.addRecord(record);
        user.addRecord(record);

        ProjectUtils.evaluateBudgetUsePercentage(project);
        RecordUtils.evaluateWorkCost(record);

        return RecordMapper.toDto(recordRepository.save(record));
    }

    @Override
    @Transactional
    public RecordDto update(final UUID currentUserUuid, final UUID uuidForUpdating, final UpdateRecordForm updateRecordForm) {
        final User user = findUserByUuid(currentUserUuid);

        checkIfUserHasPermissions(user, UserRole.recordRequiredRoles);

        validateRecordDates(updateRecordForm.getBeginTimestamp(), updateRecordForm.getEndTimestamp());

        final Record record = findRecordByUuid(uuidForUpdating);

        record.setBeginTimestamp(updateRecordForm.getBeginTimestamp());
        record.setEndTimestamp(updateRecordForm.getEndTimestamp());

        ProjectUtils.evaluateBudgetUsePercentage(record.getProject());
        RecordUtils.evaluateWorkCost(record);

        return RecordMapper.toDto(recordRepository.save(record));
    }


    @Override
    @Transactional
    public void deleteByUuid(final UUID currentUserUuid, final UUID recordUuid) {

        final User user = findUserByUuid(currentUserUuid);

        checkIfUserHasPermissions(user, UserRole.recordRequiredRoles);

        recordRepository.findByUuid(recordUuid)
                .ifPresent(this::deleteRecord);

    }

    private void deleteRecord(final Record record){
        final Project project = record.getProject();
        project.getRecords().remove(record);
        ProjectUtils.evaluateBudgetUsePercentage(project);
        recordRepository.delete(record);
    }

    private void validateRecordDates(final LocalDateTime begin, final LocalDateTime end){
        if(DateUtils.validateIfEndIsAfterBegin(begin, end)){
            throw new IllegalArgumentException("End time must be after begin time");
        }
    }

    private void validateIfUserAssignedToProject(final User user, final Project project){
        if(!user.getProjects().contains(project)){
            throw new IllegalArgumentException("User is not assigned to this project");
        }
    }

    private void checkIfUserHasPermissions(final User user, final Set<UserRole> requiredRoles){
        final UserRole userRole =  user.getUserRole();
        if(!requiredRoles.contains(userRole)) {throw new NoRightsException("User has no permissions");}
    }

    private Record findRecordByUuid(final UUID uuid){
        return recordRepository
                .findByUuid(uuid)
                .orElseThrow(()-> new NotFoundException("Record not found"));
    }

    private User findUserByUuid(final UUID uuid){
        return userRepository.
                findUserByUuidWithRecords(uuid).
                orElseThrow(()-> new NotFoundException("User not found"));
    }

    private Project findProjectByUuid(final UUID uuid){
        return projectRepository.
                findProjectByUuidWithRecordsAndUsers(uuid).
                orElseThrow(()-> new NotFoundException("Project not found"));
    }

}
