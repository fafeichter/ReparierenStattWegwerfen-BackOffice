package at.reparierenstattwegwerfen.backoffice.device;

/**
 * @author Fabian Feichter
 */
public interface DeviceBuyingService {

	void setBuyerAddressForDevice(Integer deviceId, Integer buyerBusinessPartnerId);
}