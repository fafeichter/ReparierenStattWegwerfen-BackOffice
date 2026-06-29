package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceNoteService {

	private final DeviceNoteRepository deviceNoteRepository;

	public List<DeviceNoteDto> load(Integer deviceId) {
		return deviceNoteRepository.getNotesForDevice(deviceId).stream().map(note ->
				DeviceNoteDto.builder()
					.noteId(note.getId())
					.text(note.getText())
					.timestamp(note.getTimestamp())
					.build())
			.toList();
	}
}
