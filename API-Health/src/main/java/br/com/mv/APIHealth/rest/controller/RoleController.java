package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.rest.dto.RoleDTO;
import br.com.mv.APIHealth.service.RoleService;
import br.com.mv.APIHealth.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/roles")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO getRoleById(@PathVariable(name = "id") UUID id) {
        return roleService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping
    public ResponseEntity<Response<RoleDTO>> saveUser(@RequestBody @Valid RoleDTO roleDto,
                                                   BindingResult result) {
        Response<RoleDTO> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        RoleDTO newRoleDto = roleService.create(roleDto);
        response.setData(newRoleDto);
        response.getErrors().add("No content.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<RoleDTO>> updateUser(@PathVariable(name = "id") UUID id,
                                                        @RequestBody @Valid RoleDTO roleDto,
                                                        BindingResult result) {
        Response<RoleDTO> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        RoleDTO updatedRole = roleService.update(id, roleDto);
        response.setData(updatedRole);
        response.getErrors().add("No content.");

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable(name = "id") UUID id) {
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
