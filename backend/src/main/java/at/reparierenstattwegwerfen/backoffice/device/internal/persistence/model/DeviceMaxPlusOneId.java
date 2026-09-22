package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model;

import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Fabian Feichter
 * @since 17.09.2026
 */
@IdGeneratorType(DeviceMaxPlusOnePKGenerator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface DeviceMaxPlusOneId {
}