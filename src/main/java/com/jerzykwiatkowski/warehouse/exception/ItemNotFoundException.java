package com.jerzykwiatkowski.warehouse.exception;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(Long id) {
        super("Could not find item" + id);
    }
}
