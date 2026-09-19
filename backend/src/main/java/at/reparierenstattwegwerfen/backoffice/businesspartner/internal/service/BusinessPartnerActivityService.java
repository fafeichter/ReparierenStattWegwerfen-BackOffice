package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartnerSetAsBuyer;
import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartnerSetAsSeller;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller.BusinessPartnerActivityDto;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerActivity;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerAddress;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerActivityRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerActivityTypeRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerAddressRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.event.BusinessPartnerAddressChanged;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.event.BusinessPartnerCreated;
import lombok.RequiredArgsConstructor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@EventListener
	@Transactional
	public void on(BusinessPartnerCreated event) {
		BusinessPartnerActivity businessPartnerActivity = new BusinessPartnerActivity(event.getTimestamp(), event.getActor());
		businessPartnerActivity.setName("#" + event.getBusinessPartnerId());
		businessPartnerActivity.setBusinessPartner(businessPartnerRepository.getReferenceById(event.getBusinessPartnerId()));
		businessPartnerActivity.setActivityType(businessPartnerActivityTypeRepository.getReferenceById(1));

		businessPartnerActivityRepository.save(businessPartnerActivity);
	}

	@EventListener
	@Transactional
	public void on(BusinessPartnerAddressChanged event) {
		BusinessPartnerActivity businessPartnerActivity = new BusinessPartnerActivity(event.getTimestamp(), event.getActor());
		BusinessPartnerAddress address = businessPartnerAddressRepository.getReferenceById(event.getBusinessPartnerAddressId());
		businessPartnerActivity.setName(address.toString());
		businessPartnerActivity.setBusinessPartner(businessPartnerRepository.getReferenceById(event.getBusinessPartnerId()));
		businessPartnerActivity.setActivityType(businessPartnerActivityTypeRepository.getReferenceById(2));

		businessPartnerActivityRepository.save(businessPartnerActivity);
	}

	@EventListener
	@Transactional
	public void on(BusinessPartnerSetAsSeller event) {
		BusinessPartnerActivity businessPartnerActivity = new BusinessPartnerActivity(event.getTimestamp(), event.getActor());
		businessPartnerActivity.setName("#" + event.getDeviceId());
		businessPartnerActivity.setBusinessPartner(businessPartnerRepository.getReferenceById(event.getBusinessPartnerId()));
		businessPartnerActivity.setActivityType(businessPartnerActivityTypeRepository.getReferenceById(3));

		businessPartnerActivityRepository.save(businessPartnerActivity);
	}

	@EventListener
	@Transactional
	public void on(BusinessPartnerSetAsBuyer event) {
		BusinessPartnerActivity businessPartnerActivity = new BusinessPartnerActivity(event.getTimestamp(), event.getActor());
		businessPartnerActivity.setName("#" + event.getDeviceId());
		businessPartnerActivity.setBusinessPartner(businessPartnerRepository.getReferenceById(event.getBusinessPartnerId()));
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