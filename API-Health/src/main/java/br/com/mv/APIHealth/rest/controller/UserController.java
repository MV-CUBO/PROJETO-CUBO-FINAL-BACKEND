package br.com.mv.APIHealth.rest.controller;

import br.com.mv.APIHealth.rest.dto.UserDTO;
import br.com.mv.APIHealth.service.UserService;
import br.com.mv.APIHealth.utils.Response;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private MessageSource messageSource;

    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable(name = "id") UUID id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<Response<UserDTO>> saveUser(@RequestBody @Valid UserDTO userDto,
                                                      BindingResult result) {
        Response<UserDTO> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        UserDTO newUserDto = userService.create(userDto);
        response.setData(newUserDto);
        response.getErrors().add("No content.");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<UserDTO>> updateUser(@PathVariable(name = "id") UUID id,
                                                        @RequestBody @Valid UserDTO userDto,
                                                        BindingResult result) {
        Response<UserDTO> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        UserDTO updatedDto = userService.update(id, userDto);
        response.setData(updatedDto);
        response.getErrors().add("No content.");

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable(name = "id") UUID id) {
        userService.delete(id);
    }
}
