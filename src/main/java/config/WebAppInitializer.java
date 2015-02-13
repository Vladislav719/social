package config;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;


public class WebAppInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {


  //{!begin addToRootContext}
	@Override
    public Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { DataSourceConfig.class, PersistenceConfig.class, CoreConfig.class, AppSecurityConfig.class};
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
    protected Filter[] getServletFilters() {

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        return new Filter[]{
                characterEncodingFilter,
                new DelegatingFilterProxy("springSecurityFilterChain"),
                new OpenEntityManagerInViewFilter()
        };
    }


}