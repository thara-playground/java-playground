package org.axonframework.guice.provider;

import org.axonframework.domain.AggregateRoot;
import org.axonframework.repository.Repository;

import com.google.inject.Provider;

/**
 * @author t_hara
 *
 */
public interface RepositoryProvider<T extends AggregateRoot> extends Provider<Repository<T>> {

}
