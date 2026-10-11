package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @author Fabian Feichter
 * @since 17.09.2026
 */
public class DeviceMaxPlusOnePKGenerator implements IdentifierGenerator {

	@Override
	public synchronized Object generate(SharedSessionContractImplementor session, Object object) {
		return session
			.createSelectionQuery("SELECT COALESCE(MAX(d.id), 0) + 1 FROM DeviceEntity d", Integer.class)
			.getSingleResult();
	}
}