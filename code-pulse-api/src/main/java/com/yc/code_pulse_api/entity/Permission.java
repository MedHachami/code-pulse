package com.yc.code_pulse_api.entity;

import com.yc.code_pulse_api.entity.enums.PermissionType;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PermissionType permissionType;

    @Column(nullable = false)
    private String target; // Can be Project or File

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
