package mx.edu.utez.simnaDatabase.controllers.auth.dto;

import lombok.Value;
import mx.edu.utez.simnaDatabase.models.role.Role;
import mx.edu.utez.simnaDatabase.models.user.User;

import java.util.List;

@Value
public class SignedDto {
    String token;
    String tokenType;
    User user;
    List<Role> roles;
}
