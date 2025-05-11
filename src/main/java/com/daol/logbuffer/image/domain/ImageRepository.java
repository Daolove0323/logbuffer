package com.daol.logbuffer.image.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ImageRepository<T extends Image, ID extends ImageId> extends JpaRepository<T, ID> {

}