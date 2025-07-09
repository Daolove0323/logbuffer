package com.daol.logbuffer._common.config;

import com.daol.logbuffer._common.event.Events;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EventConfig {
    
    @Bean
    public InitializingBean eventsInitializer(ApplicationEventPublisher eventPublisher) {
        return () -> Events.setPublisher(eventPublisher);
    }
}