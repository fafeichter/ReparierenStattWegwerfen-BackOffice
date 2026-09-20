package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceNote;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceNoteRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.event.DeviceNoteAdded;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceNoteService {

	private final DeviceNoteRepository deviceNoteRepository;
	private final ApplicationEventPublisher events;

	public List<DeviceNoteDto> load(Integer deviceId) {
		return deviceNoteRepository.getNotesForDevice(deviceId).stream().map(note ->
				DeviceNoteDto.builder()
					.noteId(note.getId())
					.text(note.getText())
					.date(note.getDate())
					.build())
			.toList();
	}

	@Transactional
	public void add(Integer deviceId, String text, UserDetails actor) {
		DeviceNote note = new DeviceNote();
		note.setDeviceId(deviceId);
		note.setText(text);
		note.setDate(LocalDateTime.now());

		Integer newNoteId = deviceNoteRepository.save(note).getId();

		DeviceNoteAdded noteAddedEvent = DeviceNoteAdded.builder()
			.source(this)
			.actor(actor)
			.deviceId(deviceId)
			.newNoteId(newNoteId)
			.build();

		events.publishEvent(noteAddedEvent);
	}
}