package pl.internship.antologic.project.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsersForm {

    private List<UUID> usersUUID = new ArrayList<>();
    private UpdateProjectOperation operation;

    public List<UUID> getUsersUUID(){
        return usersUUID;
    }

}
