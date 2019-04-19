package com.jerzykwiatkowski.warehouse.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "persistent_logins")
public class PersistentLogin {

    @Column(nullable = false)
    private String username;
    @Id
    private String series;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private Timestamp lastUsed;
}
