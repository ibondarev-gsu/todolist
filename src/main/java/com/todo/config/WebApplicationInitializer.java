package com.todo.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootApplicationConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{};
    }

    @Override
    protected Filter[] getServletFilters() {
        //Encoding Filter
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("UTF-8", true);

//        //Security Filter
        DelegatingFilterProxy filterProxy = new DelegatingFilterProxy("springSecurityFilterChain");

        return new Filter[]{encodingFilter,
                filterProxy
        };
    }
}
