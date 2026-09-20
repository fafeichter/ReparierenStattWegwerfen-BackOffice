package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller.BusinessPartnerActivityDto;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerActivity;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerAddress;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerActivityRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerActivityTypeRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerAddressRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.event.BusinessPartnerAddressChanged;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.event.BusinessPartnerCreated;
import at.reparierenstattwegwerfen.backoffice.event.DeviceBusinessPartnerSetAsBuyer;
import at.reparierenstattwegwerfen.backoffice.event.DeviceBusinessPartnerSetAsSeller;
import lombok.RequiredArgsConstructor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

/**
 * @author Fabian Feichter
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
		BusinessPartnerActivity businessPartnerActivity = new BusinessPartnerActivity(event.getTimestamp(), event.getActor());
		businessPartnerActivity.setName("#" + event.getBusinessPartnerId());
		businessPartnerActivity.setBusinessPartner(businessPartnerRepository.getReferenceById(event.getBusinessPartnerId()));
		businessPartnerActivity.setActivityType(businessPartnerActivityTypeRepository.getReferenceById(1));

		businessPartnerActivityRepository.save(businessPartnerActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(BusinessPartnerAddressChanged event) {
		BusinessPartnerActivity businessPartnerActivity = new BusinessPartnerActivity(event.getTimestamp(), event.getActor());
		BusinessPartnerAddress address = businessPartnerAddressRepository.getReferenceById(event.getBusinessPartnerAddressId());
		businessPartnerActivity.setName(address.toString());
		businessPartnerActivity.setBusinessPartner(businessPartnerRepository.getReferenceById(event.getBusinessPartnerId()));
		businessPartnerActivity.setActivityType(businessPartnerActivityTypeRepository.getReferenceById(2));

		businessPartnerActivityRepository.save(businessPartnerActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceBusinessPartnerSetAsSeller event) {
		BusinessPartnerActivity businessPartnerActivity = new BusinessPartnerActivity(event.getTimestamp(), event.getActor());
		businessPartnerActivity.setName("#" + event.getDeviceId());
		businessPartnerActivity.setBusinessPartner(businessPartnerRepository.getReferenceById(event.getSellerBusinessPartnerId()));
		businessPartnerActivity.setActivityType(businessPartnerActivityTypeRepository.getReferenceById(3));

		businessPartnerActivityRepository.save(businessPartnerActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceBusinessPartnerSetAsBuyer event) {
		BusinessPartnerActivity businessPartnerActivity = new BusinessPartnerActivity(event.getTimestamp(), event.getActor());
		businessPartnerActivity.setName("#" + event.getDeviceId());
		businessPartnerActivity.setBusinessPartner(businessPartnerRepository.getReferenceById(event.getBuyerBusinessPartnerId()));
		businessPartnerActivity.setActivityType(businessPartnerActivityTypeRepository.getReferenceById(4));

		businessPartnerActivityRepository.save(businessPartnerActivity);
	}

	public List<BusinessPartnerActivityDto> getActivitiesForBusinessPartner(Integer businessPartnerId) {
		return businessPartnerActivityRepository.getByIdWithRelations(businessPartnerId).stream().map(activity ->
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