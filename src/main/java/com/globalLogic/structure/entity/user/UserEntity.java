package com.globalLogic.structure.entity.user;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
@Table(name = "users",uniqueConstraints = {@UniqueConstraint(columnNames={"email"})})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String phones;
    @Column
    private Timestamp created;
    @Column
    private Timestamp modified;
    @Column
    private Timestamp lastLogin;
    @Column
    private boolean isActive;
    @Column
    private String token;

}
