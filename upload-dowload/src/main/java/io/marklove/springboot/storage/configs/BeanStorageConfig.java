package io.marklove.springboot.storage.configs;

import io.marklove.springboot.storage.services.StorageService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;

@Configuration
@ConfigurationProperties("app.storage")
public class BeanStorageConfig {
    private Class<StorageService> bean;

    public void setStorage(Class<StorageService> bean) {
        this.bean = bean;
    }

    @Bean
    public StorageService storageService() throws NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {

        return bean.getDeclaredConstructor().newInstance();
    }
}
