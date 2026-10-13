package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.activity;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
public interface BusinessPartnerActivityRepository {
	List<BusinessPartnerActivity> getByIdWithRelations(Integer businessPartnerId);

	BusinessPartnerActivity save(BusinessPartnerActivity activity);
}