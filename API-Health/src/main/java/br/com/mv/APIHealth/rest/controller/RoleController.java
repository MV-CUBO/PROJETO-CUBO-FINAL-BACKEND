package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.rest.dto.RoleDTO;
import br.com.mv.APIHealth.service.RoleService;
import br.com.mv.APIHealth.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(tags = "Role", summary = "Get a role by ID")
    public RoleDTO getRoleById(@PathVariable(name = "id") UUID id) {
        return roleService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Role", summary = "Get all roles")
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping
    @Operation(tags = "Role", summary = "Create a new role")
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
    @Operation(tags = "Role", summary = "Update a role")
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
    @Operation(tags = "Role", summary = "Delete a role")
    public ResponseEntity<Void> deleteRole(@PathVariable(name = "id") UUID id) {
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
