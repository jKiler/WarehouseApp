package com.jerzykwiatkowski.warehouse.repository;

import com.jerzykwiatkowski.warehouse.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
