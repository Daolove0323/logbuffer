package com.daol.logbuffer.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ImageRepository<T extends Image> extends JpaRepository<T, ImageId> {

}