package com.jerzykwiatkowski.warehouse.converter;

import com.jerzykwiatkowski.warehouse.controller.ItemController;
import com.jerzykwiatkowski.warehouse.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ItemResourceAssembler implements ResourceAssembler<Item, Resource<Item>> {

    @Override
    public Resource<Item> toResource(Item item) {
        return new Resource<>(item,
                linkTo(methodOn(ItemController.class).one(item.getId())).withSelfRel(),
                linkTo(methodOn(ItemController.class).all(Pageable.unpaged())).withRel("items"));
    }
}
