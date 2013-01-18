package org.axonframework.guice.provider;

import org.axonframework.eventsourcing.Snapshotter;

import com.google.inject.Provider;

/**
 * @author t_hara
 */
public interface SnapshotterProvider extends Provider<Snapshotter> {

}
