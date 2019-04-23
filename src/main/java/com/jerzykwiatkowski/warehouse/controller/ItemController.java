package com.jerzykwiatkowski.warehouse.controller;

import com.jerzykwiatkowski.warehouse.converter.ItemResourceAssembler;
import com.jerzykwiatkowski.warehouse.entity.Item;
import com.jerzykwiatkowski.warehouse.exception.ItemNotFoundException;
import com.jerzykwiatkowski.warehouse.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path = "/items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ItemController {

    private final ItemRepository repository;
    private final ItemResourceAssembler assembler;
    private final PagedResourcesAssembler<Item> pagedAssembler;

    public ItemController(ItemRepository repository,
                          ItemResourceAssembler assembler,
                          PagedResourcesAssembler<Item> pagedAssembler) {

        this.repository = repository;
        this.assembler = assembler;
        this.pagedAssembler = pagedAssembler;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping
    public PagedResources<Resource<Item>> all(Pageable pageable) {

        Page<Item> items = repository.findAll(pageable);

        return pagedAssembler.toResource(items, assembler);
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
