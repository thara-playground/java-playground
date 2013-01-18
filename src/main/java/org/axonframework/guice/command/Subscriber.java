/**
 * 
 */
package org.axonframework.guice.command;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import com.google.inject.BindingAnnotation;

/**
 * @author t_hara
 *
 */
@Retention(RUNTIME) @BindingAnnotation
@interface Subscriber {
  String setName();
  int uniqueId();
}
