package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceActivity;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.*;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.event.*;
import at.reparierenstattwegwerfen.backoffice.model.ModelDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * @author Fabian Feichter
 * @since 02.07.2026
 */
@Service
@RequiredArgsConstructor
@RegisterReflectionForBinding(DeviceCreated.class)
public class DeviceActivityService {

	private final DeviceActivityRepository deviceActivityRepository;
	private final DeviceActivityTypeRepository deviceActivityTypeRepository;
	private final DeviceRepository deviceRepository;
	private final DeviceStatusRepository deviceStatusRepository;
	private final DeviceBatteryStatusRepository deviceBatteryStatusRepository;
	private final DeviceGradeRepository deviceGradeRepository;
	private final DeviceTagRepository deviceTagRepository;
	private final DeviceNoteRepository deviceNoteRepository;
	private final ModelDetailsService modelDetailsService;

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceCreated event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName("#" + event.getDeviceId());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(1));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceStatusChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName(deviceStatusRepository.getReferenceById(event.getNewStatusId()).getName());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(2));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceBatteryStatusChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		Integer newBatteryStatusId = event.getNewBatteryStatusId();
		String activityValue;
		if (newBatteryStatusId != null) {
			activityValue = deviceBatteryStatusRepository.getReferenceById(newBatteryStatusId).getName();
		} else {
			activityValue = "-";
		}
		deviceActivity.setName(activityValue);
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(3));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceGradeChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());

		Integer newGradeId = event.getNewGradeId();
		String activityValue;
		if (newGradeId != null) {
			activityValue = deviceGradeRepository.getReferenceById(newGradeId).getName();
		} else {
			activityValue = "-";
		}
		deviceActivity.setName(activityValue);
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(4));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceTagAdded event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName(deviceTagRepository.getReferenceById(event.getNewDeviceTagId()).getName());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(5));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceTagRemoved event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName(deviceTagRepository.getReferenceById(event.getOldDeviceTagId()).getName());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(6));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceAppleSiliconChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName(modelDetailsService.getAppleSilicon(event.getModelAppleSiliconId()).getName());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(7));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceUnifiedMemoryChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName(modelDetailsService.getUnifiedMemory(event.getModelUnifiedMemoryId()).getName());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(8));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceStorageChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName(modelDetailsService.getStorage(event.getModelStorageId()).getName());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(9));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceColorChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName(modelDetailsService.getColor(event.getModelColorId()).getName());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(10));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceSerialNumberChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName(event.getSerialNumber());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(11));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceReportedDefectChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName(event.getReportedDefect());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(12));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceDiagnosedDefectChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName(event.getDiagnosedDefect());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(13));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceBuyingDateChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName(event.getBuyingDate().format(ofPattern("dd.MM.yyyy")));
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(14));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceSellingDateChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName(event.getSellingDate().format(ofPattern("dd.MM.yyyy")));
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(15));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceNoteAdded event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName(deviceNoteRepository.getReferenceById(event.getNewNoteId()).getName());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(16));

		deviceActivityRepository.save(deviceActivity);
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceNoteDeleted event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getExactTimestamp(), event.getActor());
		deviceActivity.setName(event.getOldNoteText());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(17));

		deviceActivityRepository.save(deviceActivity);
	}

	public List<DeviceActivityDto> getActivitiesForDevice(Integer deviceId) {
		return deviceActivityRepository.getByIdWithRelations(deviceId).stream().map(activity ->
				DeviceActivityDto.builder()
					.id(activity.getId())
					.action(activity.getActivityType().getName())
					.value(activity.getName())
					.actor(activity.getActor())
					.date(activity.getDate())
					.build())
			.toList();
	}
}