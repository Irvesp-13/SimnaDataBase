package mx.edu.utez.simnaDatabase.services.person;

import mx.edu.utez.simnaDatabase.config.ApiResponse;
import mx.edu.utez.simnaDatabase.models.person.Person;
import mx.edu.utez.simnaDatabase.models.person.PersonRepository;
import mx.edu.utez.simnaDatabase.models.role.Role;
import mx.edu.utez.simnaDatabase.models.role.RoleRepository;
import mx.edu.utez.simnaDatabase.models.user.User;
import mx.edu.utez.simnaDatabase.models.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonService {
    private final PersonRepository repository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public PersonService(PersonRepository repository, UserRepository userRepository,
                         RoleRepository roleRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(Long id) {
        Optional<Person> foundPerson = repository.findById(id);
        if (foundPerson.isPresent())
            return new ResponseEntity<>(new ApiResponse(foundPerson.get(), HttpStatus.OK), HttpStatus.OK);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "RecordNotFound"), HttpStatus.NOT_FOUND);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findAll() {
        return new ResponseEntity<>(
                new ApiResponse(repository.findAll(), HttpStatus.OK),
                HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> update(Long id, Person person) {
        Optional<Person> foundPerson = repository.findById(id);
        if (foundPerson.isPresent()) {
            Person updatedPerson = foundPerson.get();
            updatedPerson.setName(person.getName());
            updatedPerson.setSurname(person.getSurname());
            updatedPerson.setLastname(person.getLastname());
            updatedPerson.setBirthdate(person.getBirthdate());
            updatedPerson.setCurp(person.getCurp());
            updatedPerson.setStatus(person.getStatus());
            if (person.getUser() != null) {
                Optional<User> foundUser = userRepository.findByUsername(person.getUser().getUsername());
                if (foundUser.isPresent())
                    return new ResponseEntity<>(
                            new ApiResponse(
                                    HttpStatus.BAD_REQUEST, true, "RecordAlreadyExist"
                            ), HttpStatus.BAD_REQUEST);
                updatedPerson.getUser().setUsername(person.getUser().getUsername());
                updatedPerson.getUser().setPassword(person.getUser().getPassword());
                updatedPerson.getUser().setAvatar(person.getUser().getAvatar());
                updatedPerson.getUser().setRoles(person.getUser().getRoles());
            }
            return new ResponseEntity<>(new ApiResponse(repository.saveAndFlush(updatedPerson), HttpStatus.OK), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "RecordNotFound"), HttpStatus.NOT_FOUND);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> delete(Long id) {
        Optional<Person> foundPerson = repository.findById(id);
        if (foundPerson.isPresent()) {
            Person person = foundPerson.get();
            // Obtener el usuario asociado a la persona
            User user = person.getUser();
            if (user != null) {
                // Poner los roles asignados al usuario como nulo
                Set<Role> roles = user.getRoles();
                for (Role role : roles) { // Eliminar los roles asignados al usuario
                     // Guardar un ID 2 por defecto:
                    Long roleId = 2L;
                    roleRepository.deleteById(roleId);
                    // Crear un nuevo rol con el mismo ID y nombre
                    Role newRole = new Role();
                    newRole.setId(roleId);
                    newRole.setName(role.getName()); // Usar el nombre del rol eliminado como nombre del nuevo rol
                    roleRepository.save(newRole); // Guardar el nuevo rol en la base de datos
                }
                // Eliminar el usuario
                userRepository.delete(user);
            }
            // Eliminar la persona
            repository.delete(person);
            return new ResponseEntity<>(new ApiResponse(id, HttpStatus.OK), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "RecordNotFound"), HttpStatus.NOT_FOUND);
    }





    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> save(Person person) {
        person.setStatus(true);
        Optional<Person> foundPerson = repository.findByCurp(person.getCurp());
        if (foundPerson.isPresent())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "RecordAlreadyExist"),
                    HttpStatus.BAD_REQUEST);
        if (person.getUser() != null) {
            Optional<User> foundUser = userRepository.findByUsername(person.getUser().getUsername());
            if (foundUser.isPresent())
                return new ResponseEntity<>(
                        new ApiResponse(
                                HttpStatus.BAD_REQUEST, true, "RecordAlreadyExist"
                        ), HttpStatus.BAD_REQUEST);
            person.getUser().setPerson(person);
            Set<Role> roles = person.getUser().getRoles();
            person.getUser().setRoles(null);
            person = repository.saveAndFlush(person);
            User savedUser = person.getUser();
            for (Role role : roles) {
                if (roleRepository.saveUserRole(role.getId(), savedUser.getId()) <= 0)
                    return new ResponseEntity<>(
                            new ApiResponse(
                                    HttpStatus.BAD_REQUEST, true, "RoleNotAttached"
                            ), HttpStatus.BAD_REQUEST);
            }
        } else {
            person = repository.saveAndFlush(person);
        }
        return new ResponseEntity<>(new ApiResponse(person, HttpStatus.OK), HttpStatus.OK);
    }

}
