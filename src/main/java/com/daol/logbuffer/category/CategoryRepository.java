package com.daol.logbuffer.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, CategoryId> {

    @Modifying
    @Query("""
        delete from Category c
        where c.id = :categoryId
        and not exists (
        select 'x' from Post p
        where p.categoryId = :categoryId
        )
        """)
    void deleteCategoryIfNotUsed(CategoryId categoryId);
}