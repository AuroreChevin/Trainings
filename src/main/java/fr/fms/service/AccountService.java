package fr.fms.service;

import fr.fms.entities.AppRole;
import fr.fms.entities.AppUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    public AppUser saveUser(AppUser user);
    public AppRole saveRole(AppRole role);
    public void addRoleToUser(String username, String roleName);
    public AppUser findUserByUsername(String username);
    ResponseEntity<List<AppUser>> listUsers();
}
