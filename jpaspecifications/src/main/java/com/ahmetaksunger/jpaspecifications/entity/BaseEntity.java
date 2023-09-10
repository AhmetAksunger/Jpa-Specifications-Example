package com.ahmetaksunger.jpaspecifications.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "CREATED_AT")
    protected Date createdAt;

    @Column(name = "UPDATED_AT")
    protected Date updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }
}