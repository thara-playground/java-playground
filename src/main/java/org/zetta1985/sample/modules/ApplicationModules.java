/**
 * 
 */
package org.zetta1985.sample.modules;

import java.util.ResourceBundle;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.bootstrap.GenericBootstrap;

import org.hibernate.validator.engine.ConfigurationImpl;
import org.hibernate.validator.engine.ResourceBundleMessageInterpolator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.zetta1985.core.jaxrs.ThymeleafTemplateWriter;

import com.google.inject.AbstractModule;

/**
 * @author t_hara
 *
 */
public class ApplicationModules extends AbstractModule {

	@Override
	protected void configure() {
		configureCoreModules();
		
		install(new IntranetModule());
	}

	/**
	 * 
	 */
	void configureCoreModules() {
		
		// for validation
		GenericBootstrap defaultProvider = Validation.byDefaultProvider();
		ConfigurationImpl configuration = (ConfigurationImpl) defaultProvider.configure();
		ResourceBundle validationMessages = ResourceBundle.getBundle("ValidationMessages");
		MessageInterpolator messageInterpolator = new ResourceBundleMessageInterpolator(validationMessages);
		configuration.messageInterpolator(messageInterpolator);
		
		ValidatorFactory validatorFactory = configuration.buildValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		bind(Validator.class).toInstance(validator);
		
		// for JAX-RS
		String encoding = "UTF-8";
		
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setTemplateMode(TemplateMode.XHTML);
		templateResolver.setCharacterEncoding(encoding);
		templateResolver.setCacheable(false);
		
		templateResolver.setPrefix("/");
//		templateResolver.setPrefix("/WEB-INF/templates/");
//		templateResolver.setSuffix(".html");
		
		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		
		bind(ThymeleafTemplateWriter.class);
	}
}
