package com.crypto.Project.Crypto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "encripted_data")
@Table(name = "encripted_data")
@Getter
@Setter
public class EncriptedData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String data;
}
