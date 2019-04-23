package com.jerzykwiatkowski.warehouse.repository;

import com.jerzykwiatkowski.warehouse.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {

    Page<Item> findAll(Pageable pageable);
}
