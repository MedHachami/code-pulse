package com.yc.code_pulse_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String storageLocation;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;
}
