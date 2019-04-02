package com.jerzykwiatkowski.warehouse.controller;

import com.jerzykwiatkowski.warehouse.converter.ItemResourceAssembler;
import com.jerzykwiatkowski.warehouse.entity.Item;
import com.jerzykwiatkowski.warehouse.exception.ItemNotFoundException;
import com.jerzykwiatkowski.warehouse.repository.ItemRepository;
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
@RequestMapping(path = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    private final ItemRepository repository;
    private final ItemResourceAssembler assembler;

    public ItemController(ItemRepository repository,
                          ItemResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping
    public Resources<Resource<Item>> all() {

        List<Resource<Item>> items = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(items,
                linkTo(methodOn(ItemController.class).all()).withSelfRel());
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<?> newItem(@RequestBody Item newItem) throws URISyntaxException {

        Resource<Item> resource = assembler.toResource(repository.save(newItem));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public Resource<Item> one(@PathVariable Long id) {

        Item item = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));

        return assembler.toResource(item);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<?> replaceItem(@RequestBody Item newItem, @PathVariable Long id) throws URISyntaxException {

        Item updatedItem = repository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setType(newItem.getType());
                    item.setModel(newItem.getModel());
                    item.setCategory(newItem.getCategory());
                    return repository.save(item);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return repository.save(newItem);
                });

        Resource<Item> resource = assembler.toResource(updatedItem);

        return ResponseEntity.created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
