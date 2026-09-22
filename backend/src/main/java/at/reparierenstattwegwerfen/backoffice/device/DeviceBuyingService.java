package at.reparierenstattwegwerfen.backoffice.device;

/**
 * @author Fabian Feichter
 * @since 13.07.2026
 */
public interface DeviceBuyingService {

	void setBuyerAddressForDevice(Integer deviceId, Integer buyerBusinessPartnerId);
}