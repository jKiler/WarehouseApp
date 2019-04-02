package com.jerzykwiatkowski.warehouse.converter;

import com.jerzykwiatkowski.warehouse.controller.ItemController;
import com.jerzykwiatkowski.warehouse.entity.Item;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class ItemResourceAssembler implements ResourceAssembler<Item, Resource<Item>> {
    @Override
    public Resource<Item> toResource(Item item) {

        return new Resource<>(item,
                linkTo(methodOn(ItemController.class).one(item.getId())).withSelfRel(),
                linkTo(methodOn(ItemController.class).all()).withRel("items"));
    }
}
