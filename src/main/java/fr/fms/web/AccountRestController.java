package fr.fms.web;

import fr.fms.entities.AppRole;
import fr.fms.entities.AppUser;
import fr.fms.service.AccountServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AccountRestController {
    @Autowired
    AccountServiceImpl accountService;
    @GetMapping("/users")
    ResponseEntity<List<AppUser>> getUser(){
        return this.accountService.listUsers();
    }
    @PostMapping("/users")
    public AppUser postUser(@RequestBody AppUser user){return this.accountService.saveUser(user);
    }
    @GetMapping("/roles")
    ResponseEntity<List<AppRole>> getRole(){
        return this.accountService.listRoles();
    }
    @PostMapping("/role")
    public AppRole postRole(@RequestBody AppRole role){
        return this.accountService.saveRole(role);
    }
    @PostMapping("/roleUser")
    public void postRoleToUser(@RequestBody UserRoleForm userRoleForm){
        accountService.addRoleToUser(userRoleForm.getUsername(), userRoleForm.getRoleName());
    }
}
@Data
class UserRoleForm{
    private String username;
    private String roleName;
}