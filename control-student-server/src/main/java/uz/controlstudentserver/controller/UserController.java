package uz.controlstudentserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.controlstudentserver.dto.UserDto;
import uz.controlstudentserver.entity.Role;
import uz.controlstudentserver.entity.User;
import uz.controlstudentserver.entity.enums.RoleName;
import uz.controlstudentserver.payload.ApiResponse;
import uz.controlstudentserver.payload.CurrentUser;
import uz.controlstudentserver.servise.RoleService;
import uz.controlstudentserver.servise.UserServise;

import java.util.UUID;
@CrossOrigin
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserServise userServise;

    @Autowired
    RoleService roleService;

//    @PreAuthorize("hasAnyRole({'ROLE_DEAN','ROLE_DEPUTY_DEAN'})")
    @GetMapping("/allUsers")
    public HttpEntity<?> all() {
        ApiResponse all = userServise.getAll();
        return ResponseEntity.ok(all);
    }

//    @PreAuthorize("hasAnyRole({'ROLE_DEAN','ROLE_DEPUTY_DEAN','ROLE_TEACHER'})")
    @GetMapping("/findUserByGroup")
    public HttpEntity<?> findAllUsrByGroup(@RequestParam Integer groupId) {
        ApiResponse response = userServise.findByUserGroupId(groupId);
        return ResponseEntity.ok(response);
    }

//    @PreAuthorize("hasAnyRole({'ROLE_DEAN','ROLE_DEPUTY_DEAN'})")
    @GetMapping("/getByRoleName")
    public HttpEntity<?> getByRoleName(@RequestParam Role role) {
        ApiResponse allUsersByRole = userServise.findAllUsersByRole(role.getRoleName());
        return ResponseEntity.ok(allUsersByRole);
    }

//    @PreAuthorize("hasAnyRole({'ROLE_ADMIN','ROLE_DEAN','ROLE_DEPUTY_DEAN'})")
    @PostMapping("/saveOrEdit")
    public HttpEntity<?> saveOrEdit(@RequestBody UserDto dto) {
        ApiResponse response = userServise.saveOrEditUser(dto);
        return ResponseEntity.ok(response);
    }

//    @PreAuthorize("hasAnyRole({'ROLE_ADMIN','ROLE_DEAN','ROLE_DEPUTY_DEAN','ROLE_TEACHER','ROLE_STUDENT'})")
    @GetMapping("/findById/{id}")
    public HttpEntity<?> findById(@PathVariable UUID id) {
        ApiResponse one = userServise.getOne(id);
        return ResponseEntity.ok(one);
    }

    @GetMapping("/me")
    public HttpEntity<?> me(@CurrentUser User user) {
        return ResponseEntity.status(user != null ? 200 : 409).body(user);
    }
//    @PreAuthorize("hasAnyRole({'ROLE_ADMIN','ROLE_DEAN','ROLE_DEPUTY_DEAN','ROLE_TEACHER','ROLE_STUDENT'})")
    @GetMapping("/getRoleById/{id}")
    public HttpEntity<?> getRoleById(@PathVariable Integer id){
        return ResponseEntity.ok(roleService.findById(id));
    }

    @GetMapping("/allRoles")
    public HttpEntity<?> allRoles(){
        return ResponseEntity.ok(roleService.getAll());
    }

    @GetMapping("/getByRoleName/{roleName}")
    public HttpEntity<?> getByRoleName(@PathVariable RoleName roleName){
        return ResponseEntity.ok(roleService.getByRoleName(roleName));
    }
}
