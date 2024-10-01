package com.empresa.projetoapi.api;

import com.empresa.projetoapi.model.User;
import com.empresa.projetoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user", produces = { "application/json", "application/xml" })
    public ResponseEntity<?> getUser(@RequestParam Integer id) {
        try {
            if (id <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID inválido");
            }

            Optional<User> user = userService.getUser(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

    @GetMapping(value = "/users", produces = { "application/json", "application/xml" })
    public ResponseEntity<?> getUsers() {
        try {
            List<User> users = userService.getUserList();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

    @PostMapping(value = "/user", consumes = { "application/json", "application/xml" },
            produces = { "application/json", "application/xml" })
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            if (user.getName() == null || user.getEmail() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome ou e-mail não podem ser nulos");
            }

            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

    @PutMapping(value = "/user", consumes = { "application/json", "application/xml" },
            produces = { "application/json", "application/xml" })
    public ResponseEntity<?> updateUser(@RequestParam Integer id, @RequestBody User updatedUser) {
        try {
            if (id <= 0 || updatedUser.getName() == null || updatedUser.getEmail() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos fornecidos");
            }

            if (userService.updateUser(id, updatedUser)) {
                return ResponseEntity.ok("Usuário atualizado com sucesso");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/user", produces = { "application/json", "application/xml" })
    public ResponseEntity<?> deleteUser(@RequestParam Integer id) {
        try {
            if (id <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID inválido");
            }

            if (userService.deleteUser(id)) {
                return ResponseEntity.ok("Usuário deletado com sucesso");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }
}
