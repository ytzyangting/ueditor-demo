package com.example.demo.configuration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by yangting on 2018/1/6.
 */
public class FmsAutoConfiguration implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        // Guard against calls for sub-classes
        Map<String, Object> enableConfiguration = annotationMetadata.getAnnotationAttributes(getAnnotation().getName());
        if (enableConfiguration == null) {
            return;
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(FmsContext.class);
        BeanDefinition beanDefinition = builder.getBeanDefinition();
        registry.registerBeanDefinition("fmsConfiguration", beanDefinition);
    }

    protected Class<? extends Annotation> getAnnotation() {
        return EnableFms.class;
    }

    @Configuration
    @ComponentScan("com.example.demo")
    @Import(SpringMVCConfig.class)
    public static class FmsContext {

    }

}
