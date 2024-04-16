package mx.edu.utez.simnaDatabase.controllers.person.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.simnaDatabase.controllers.user.dto.UserDto;
import mx.edu.utez.simnaDatabase.models.person.Person;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class PersonDto {
    private Long id;
    private String name;
    private String surname;
    private String lastname;
    private LocalDate birthdate;
    private String curp;
    private UserDto user;

    public Person toEntity() {
        if (user == null)
            return new Person(name, surname, lastname, birthdate, curp);
        return new Person(id, name, surname, lastname, birthdate, curp, user.toEntity());
    }
}
