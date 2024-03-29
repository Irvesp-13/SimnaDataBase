package mx.edu.utez.simnaDatabase.services.user;

import mx.edu.utez.simnaDatabase.models.user.User;
import mx.edu.utez.simnaDatabase.models.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository repository;
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String username) {
        return repository.findByUsername(username);
    }
}
