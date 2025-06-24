package com.daol.logbuffer.category;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class Category {

    @EmbeddedId
    private CategoryId id;

    @Column(name = "name", unique = true)
    @NotNull
    private String name;

    protected Category() {
        this.id = CategoryId.generate();
    }

    private Category(String name) {
        this.id = CategoryId.generate();
        this.name = name;
    }

    public static Category create(String name) {
        return new Category(name);
    }
}