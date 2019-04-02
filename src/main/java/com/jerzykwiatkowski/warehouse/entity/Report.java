package com.jerzykwiatkowski.warehouse.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created;
    private String receiver;
    @ManyToOne
    private User user;
    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Item> items;

    public Report() {
    }
}
