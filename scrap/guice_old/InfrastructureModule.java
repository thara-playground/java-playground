package org.zetta1985;

import javax.validation.Configuration;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.engine.ResourceBundleMessageInterpolator;
import org.zetta1985.framework.transaction.Slim3SessionManager;
import org.zetta1985.framework.transaction.Slim3TransactionManager;
import org.zetta1985.framework.transaction.Slim3TransactionMethodInterceptor;
import org.zetta1985.framework.transaction.Transactional;
import org.zetta1985.framework.validation.ArgumentsValidationInterceptor;
import org.zetta1985.framework.validation.ParametarizedRessourceBundleMessageInterpolator;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * @author t_hara
 *
 */
public class InfrastructureModule extends AbstractModule {

	/**
	 * @inheritDoc
	 */
	@Override
	protected void configure() {
		configureValidation();
		configurePersistence();
	}

	protected void configurePersistence() {
		Slim3TransactionManager transactionManager = new Slim3TransactionManager();
		Slim3SessionManager sessionManager = new Slim3SessionManager();
		
		Slim3TransactionMethodInterceptor transactionMethodInterceptor = 
			new Slim3TransactionMethodInterceptor(transactionManager, sessionManager);
		
		bind(Slim3SessionManager.class).toInstance(sessionManager);
		bind(Slim3TransactionManager.class).toInstance(transactionManager);
		
		bindInterceptor(Matchers.annotatedWith(Transactional.class), Matchers.any(), transactionMethodInterceptor);
	}

	protected void configureValidation() {
		MessageInterpolator delegate = new ResourceBundleMessageInterpolator();
		MessageInterpolator messageInterpolator = new ParametarizedRessourceBundleMessageInterpolator(delegate);
		
		Configuration<?> configure = Validation.byDefaultProvider().configure();
		((HibernateValidatorConfiguration)configure).messageInterpolator(messageInterpolator);
		
		ValidatorFactory validatorFactory = configure.buildValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		
		bind(Validator.class).toInstance(validator);
		bind(ArgumentsValidationInterceptor.class).toInstance(new ArgumentsValidationInterceptor(validator));
	}

}
