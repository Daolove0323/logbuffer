package com.daol.logbuffer.image.application;

import com.daol.logbuffer.image.common.ImageType;
import com.daol.logbuffer.image.domain.Image;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceFactory {

    private final ConcurrentHashMap<ImageType, ImageService<? extends Image>> imageServiceMap;

    public ImageServiceFactory(List<ImageService<? extends Image>> imageServices) {
        imageServiceMap = new ConcurrentHashMap<>(imageServices.stream()
            .collect(Collectors.toConcurrentMap(ImageService::getImageType, Function.identity())));
    }

    public ImageService<? extends Image> get(ImageType imageType) {
        return imageServiceMap.get(imageType);
    }
}