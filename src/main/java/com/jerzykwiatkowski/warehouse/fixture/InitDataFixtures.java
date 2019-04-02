package com.jerzykwiatkowski.warehouse.fixture;

import com.jerzykwiatkowski.warehouse.entity.Item;
import com.jerzykwiatkowski.warehouse.entity.Role;
import com.jerzykwiatkowski.warehouse.entity.User;
import com.jerzykwiatkowski.warehouse.repository.ItemRepository;
import com.jerzykwiatkowski.warehouse.repository.RoleRepository;
import com.jerzykwiatkowski.warehouse.repository.UserRepository;
import com.jerzykwiatkowski.warehouse.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
public class InitDataFixtures {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ReportService reportService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        System.out.println("Init data");

        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Role userRole = roleRepository.findByName("ROLE_USER");

        User user = new User();
        user.setUsername("test");
        user.setFirstName("test");
        user.setLastName("test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@test.com");
        user.setRoles(new HashSet<>(Arrays.asList(adminRole, userRole)));
        userRepository.save(user);

        Item item1 = new Item("Microphone", "Condenser", "AKG C3000", "Sound", 4, 4);
        Item item2 = new Item("Microphone", "Condenser", "Shure PG48", "Sound", 8, 8);

        itemRepository.save(item1);
        itemRepository.save(item2);

        Map<Item, Integer> items = new HashMap<>();
        items.put(item1, 2);
        items.put(item2, 2);

//        reportService.generateReport(items, "Zajezdnia Kraków");
    }

    private void createRoleIfNotFound(String name) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
    }
}
