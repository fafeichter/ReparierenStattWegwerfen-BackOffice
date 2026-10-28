package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.activity;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartnerRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.BusinessPartnerAddress;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.BusinessPartnerAddressChanged;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.BusinessPartnerAddressRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.creation.BusinessPartnerCreated;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.interfaces.dtos.BusinessPartnerActivityDto;
import at.reparierenstattwegwerfen.backoffice.event.DeviceBusinessPartnerSetAsBuyer;
import at.reparierenstattwegwerfen.backoffice.event.DeviceBusinessPartnerSetAsSeller;
import lombok.RequiredArgsConstructor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 19.09.2026
 */
@Service
@RequiredArgsConstructor
@RegisterReflectionForBinding(BusinessPartnerCreated.class)
public class BusinessPartnerActivityService {

	private final BusinessPartnerActivityRepository businessPartnerActivityRepository;
	private final BusinessPartnerActivityTypeRepository businessPartnerActivityTypeRepository;
	private final BusinessPartnerAddressRepository businessPartnerAddressRepository;
	private final BusinessPartnerRepository businessPartnerRepository;

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(BusinessPartnerCreated event) {
		BusinessPartnerActivity businessPartnerActivity = new BusinessPartnerActivity(event.getExactTimestamp(), event.getActor());
		BusinessPartner businessPartner = businessPartnerRepository.getById(event.getBusinessPartnerId());
		businessPartnerActivity.setName(businessPartner.getName());
		businessPartnerActivity.setBusinessPartner(businessPartner);
		businessPartnerActivity.setActivityType(businessPartnerActivityTypeRepository.getById(1));

		businessPartnerActivityRepository.save(businessPartnerActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(BusinessPartnerAddressChanged event) {
		BusinessPartnerActivity businessPartnerActivity = new BusinessPartnerActivity(event.getExactTimestamp(), event.getActor());
		BusinessPartnerAddress address = businessPartnerAddressRepository.getById(event.getBusinessPartnerAddressId());
		businessPartnerActivity.setName(address.toString());
		businessPartnerActivity.setBusinessPartner(businessPartnerRepository.getById(event.getBusinessPartnerId()));
		businessPartnerActivity.setActivityType(businessPartnerActivityTypeRepository.getById(2));

		businessPartnerActivityRepository.save(businessPartnerActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceBusinessPartnerSetAsSeller event) {
		BusinessPartnerActivity businessPartnerActivity = new BusinessPartnerActivity(event.getExactTimestamp(), event.getActor());
		businessPartnerActivity.setName("#" + event.getDeviceId());
		businessPartnerActivity.setBusinessPartner(businessPartnerRepository.getById(event.getSellerBusinessPartnerId()));
		businessPartnerActivity.setActivityType(businessPartnerActivityTypeRepository.getById(3));

		businessPartnerActivityRepository.save(businessPartnerActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceBusinessPartnerSetAsBuyer event) {
		BusinessPartnerActivity businessPartnerActivity = new BusinessPartnerActivity(event.getExactTimestamp(), event.getActor());
		businessPartnerActivity.setName("#" + event.getDeviceId());
		businessPartnerActivity.setBusinessPartner(businessPartnerRepository.getById(event.getBuyerBusinessPartnerId()));
		businessPartnerActivity.setActivityType(businessPartnerActivityTypeRepository.getById(4));

		businessPartnerActivityRepository.save(businessPartnerActivity);
	}

	@Transactional(readOnly = true)
	public List<BusinessPartnerActivityDto> getActivitiesForBusinessPartner(Integer businessPartnerId) {
		return businessPartnerActivityRepository.getByIdWithRelations(businessPartnerId)
			.stream()
			.map(activity ->
				BusinessPartnerActivityDto.builder()
					.id(activity.getId())
					.action(activity.getActivityType().getName())
					.value(activity.getName())
					.actor(activity.getActor())
					.date(activity.getDate())
					.build())
			.toList();
	}
}