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
import java.util.HashSet;

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

        createItems();

        createUsers(adminRole, userRole);
    }

    private void createRoleIfNotFound(String name) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
    }

    private void createItems() {

        Item item1 = new Item();
        Item item2 = new Item();

        item1.setName("Microphone");
        item1.setType("Condenser");
        item1.setModel("AKG C3000");
        item1.setCategory("Sound");
        item1.setQuantity(4);
        item1.setInStock(4);

        item2.setName("Microphone");
        item2.setType("Condenser");
        item2.setModel("Shure PG48");
        item2.setCategory("Sound");
        item2.setQuantity(8);
        item2.setInStock(8);

        itemRepository.save(item1);
        itemRepository.save(item2);
    }

    private void createUsers(Role adminRole, Role userRole) {
        User user = new User();
        user.setUsername("test");
        user.setFirstName("test");
        user.setLastName("test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@test.com");
        user.setRoles(new HashSet<>(Arrays.asList(adminRole, userRole)));

        userRepository.save(user);
    }
}
