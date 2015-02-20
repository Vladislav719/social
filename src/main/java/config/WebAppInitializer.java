package config;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


public class WebAppInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {


  //{!begin addToRootContext}
	@Override
    public Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { DataSourceConfig.class, PersistenceConfig.class, CoreConfig.class,
                AppSecurityConfig.class, WebSocketConfig.class };
	}
  //{!end addToRootContext}

	@Override
    public Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	@Override
    public String[] getServletMappings() {
		return new String[] { "/" };
	}

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        String path = "/resources/tmp/";
        File dirPath = new File(path);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        registration.setInitParameter("dispatchOptionsRequest", "true");
        registration.setAsyncSupported(true);
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(dirPath.getAbsolutePath(),
                        10 * 1024 * 1024, 10 * 1024 * 1024 * 2, 10 * 1024 * 1024 / 2);

        registration.setMultipartConfig(multipartConfigElement);

    }

    @Override
    protected Filter[] getServletFilters() {

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        return new Filter[]{
                characterEncodingFilter,
                new DelegatingFilterProxy("springSecurityFilterChain"),
                new OpenEntityManagerInViewFilter(),
                new HttpPutFormContentFilter()
        };
    }


}