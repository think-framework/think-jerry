package io.github.thinkframework.container;

import javax.servlet.ServletContainerInitializer;
import java.util.Set;

public interface Context extends Container {

    void addServletContainerInitializer(
            ServletContainerInitializer servletContainerInitializer, Set<Class<?>> classes);
}
