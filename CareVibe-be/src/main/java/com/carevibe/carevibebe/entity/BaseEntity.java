package com.carevibe.carevibebe.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Getter manual (Encapsulation)
    public Long getId() {
        return id;
    }

    // Setter manual (Encapsulation)
    public void setId(Long id) {
        this.id = id;
    }
}