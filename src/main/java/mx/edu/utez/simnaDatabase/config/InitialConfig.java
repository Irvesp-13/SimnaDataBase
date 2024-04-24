package mx.edu.utez.simnaDatabase.config;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.simnaDatabase.SimnaDatabaseApplication;
import mx.edu.utez.simnaDatabase.models.person.Person;
import mx.edu.utez.simnaDatabase.models.person.PersonRepository;
import mx.edu.utez.simnaDatabase.models.pozos.Pozos;
import mx.edu.utez.simnaDatabase.models.pozos.PozosRepository;
import mx.edu.utez.simnaDatabase.models.role.Role;
import mx.edu.utez.simnaDatabase.models.role.RoleRepository;
import mx.edu.utez.simnaDatabase.models.user.User;
import mx.edu.utez.simnaDatabase.models.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static mx.edu.utez.simnaDatabase.SimnaDatabaseApplication.getMensajeRecibido;
import static mx.edu.utez.simnaDatabase.SimnaDatabaseApplication.mensajeRecibido;

@Configuration
@RequiredArgsConstructor
public class InitialConfig implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final PozosRepository pozosRepository;
    private final SimnaDatabaseApplication simnaDatabaseApplication; // Inject SimnaDatabaseApplication



    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public void run(String... args) throws Exception {
        Role adminRole = getOrSaveRole(new Role("ADMIN_ROLE"));
        Role userRole = getOrSaveRole(new Role("USER_ROLE"));
        Role clientRole = getOrSaveRole(new Role("CLIENT_ROLE"));

        // Usuario existente
        Person person1 = getOrSavePerson(
                new Person("Irving", "Espinosa", "Hernandez",
                        LocalDate.of(2004, 6, 9), "MOVM980119HM")
        );
        User user1 = getOrSaveUser(
                new User("admin", encoder.encode("admin"), person1)
        );
        saveUserRoles(user1.getId(), adminRole.getId());

        // Usuario existente
        Person person4 = getOrSavePerson(
                new Person("Cecilia", "Victoriano", "Salgado",
                        LocalDate.of(2004, 12, 22), "ahsb1536byt145wteb")
        );
        User user4 = getOrSaveUser(
                new User("administrador", encoder.encode("administrador"), person4)
        );
        saveUserRoles(user4.getId(), adminRole.getId());

        // Usuario 1
        Person person2 = getOrSavePerson(
                new Person("Alejandro", "Hernandez", "Diaz",
                        LocalDate.of(2004, 6, 4), "DOEJ900101HM")
        );
        User user2 = getOrSaveUser(
                new User("client", encoder.encode("client"), person2)
        );
        saveUserRoles(user2.getId(), clientRole.getId());

        // Usuario 2
        Person person3 = getOrSavePerson(
                new Person("Antonio", "Diaz", "Garcia",
                        LocalDate.of(1992, 1, 1), "DOEJ920101HM")
        );
        User user3 = getOrSaveUser(
                new User("user", encoder.encode("user"), person3)
        );
        saveUserRoles(user3.getId(), userRole.getId());

        // Pozo 1
        Pozos pozo1 = getOrSavePozo(
                new Pozos(  "Pozo1", 1000.0, simnaDatabaseApplication.getMensaje(), "Zapata", "Palo", true)
        );

        // Pozo 2
        Pozos pozo2 = getOrSavePozo(
                new Pozos( "Pozo2", 2000.0, simnaDatabaseApplication.getMensaje2(), "Zapata", "Palo", true)
        );
    }

    @Transactional
    public Role getOrSaveRole(Role role) {
        Optional<Role> foundRole = roleRepository.findByName(role.getName());
        return foundRole.orElseGet(() -> roleRepository.saveAndFlush(role));
    }

    @Transactional
    public Person getOrSavePerson(Person person) {
        Optional<Person> foundPerson = personRepository.findByCurp(person.getCurp());
        return foundPerson.orElseGet(() -> personRepository.saveAndFlush(person));
    }

    @Transactional
    public Pozos getOrSavePozo(Pozos pozo) {
        Optional<Pozos> foundPozo = pozosRepository.findByNombre(pozo.getNombre());
        return foundPozo.orElseGet(() -> pozosRepository.saveAndFlush(pozo));
    }

    @Transactional
    public User getOrSaveUser(User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        return foundUser.orElseGet(() -> userRepository.saveAndFlush(user));
    }

    @Transactional
    public void saveUserRoles(Long id, Long roleId) {
        Long userRoleId = userRepository.getIdUserRoles(id, roleId);
        if (userRoleId == null)
            userRepository.saveUserRole(id, roleId);
    }

}