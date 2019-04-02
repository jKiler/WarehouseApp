//package com.jerzykwiatkowski.warehouse.fixture;
//
//import com.jerzykwiatkowski.warehouse.entity.Item;
//import com.jerzykwiatkowski.warehouse.repository.ItemRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@Slf4j
//public class LoadDatabase {
//
//    @Bean
//    CommandLineRunner initDatabase(ItemRepository itemRepository) {
//        return args -> {
//            log.info("Preloading " + itemRepository.save(new Item(
//                    "Microphone", "Condenser", "AKG C3000", "Sound")));
//            log.info("Preloading " + itemRepository.save(new Item(
//                    "Microphone", "Condenser", "Shure PG48", "Sound")));
//        };
//    }
//}
