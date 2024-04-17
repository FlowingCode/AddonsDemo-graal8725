package com.flowingcode;

import com.vaadin.flow.component.page.AppShellConfigurator;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportRuntimeHints;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@ImportRuntimeHints(Application.ApplicationRuntimeHints.class)
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    static class ApplicationRuntimeHints implements RuntimeHintsRegistrar {
      @Override
      public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        // https://github.com/spring-projects/spring-framework/issues/30071
        hints.reflection().registerType(
            TypeReference.of("org.springframework.core.annotation.TypeMappedAnnotation[]"),
            MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);

        // com.github.javafaker
        hints.resources().registerPattern("en/address.yml");
        hints.resources().registerPattern("en/internet.yml");
        hints.resources().registerPattern("en/name.yml");
        hints.resources().registerPattern("en/phone_number.yml");
      }
    }

}
