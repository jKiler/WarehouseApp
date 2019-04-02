package com.jerzykwiatkowski.warehouse.service;

import com.jerzykwiatkowski.warehouse.entity.Item;
import com.jerzykwiatkowski.warehouse.entity.Report;
import com.jerzykwiatkowski.warehouse.entity.User;
import com.jerzykwiatkowski.warehouse.repository.ItemRepository;
import com.jerzykwiatkowski.warehouse.repository.ReportRepository;
import com.jerzykwiatkowski.warehouse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;

    public void generateReport(Map<Item, Integer> items, String receiver) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name);

        Report report = new Report();
        report.setCreated(LocalDateTime.now());

        items.keySet().forEach(item -> {
            item.setInStock(item.getInStock() - items.get(item));
            itemRepository.save(item);
        });

        report.setItems(items.keySet());
        report.setUser(user);
        report.setReceiver(receiver);

        reportRepository.save(report);
    }
}
