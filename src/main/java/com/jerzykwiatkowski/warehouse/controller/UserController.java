package com.jerzykwiatkowski.warehouse.controller;

import com.jerzykwiatkowski.warehouse.converter.UserResourceAssembler;
import com.jerzykwiatkowski.warehouse.entity.User;
import com.jerzykwiatkowski.warehouse.exception.UserNotFoundException;
import com.jerzykwiatkowski.warehouse.repository.UserRepository;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserRepository repository;

    private final UserResourceAssembler assembler;

    public UserController(UserRepository repository, UserResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public Resources<Resource<User>> all() {

        List<Resource<User>> users = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<?> newUser(@RequestBody User newUser) throws URISyntaxException {

        Resource<User> resource = assembler.toResource(repository.save(newUser));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping("/{id}")
    public Resource<User> one(@PathVariable Long id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return assembler.toResource(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id) throws URISyntaxException {

        User updatedUser = repository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setEmail(newUser.getEmail());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });

        Resource<User> resource = assembler.toResource(updatedUser);

        return ResponseEntity.created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
