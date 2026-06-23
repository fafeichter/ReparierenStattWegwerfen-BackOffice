package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class AdDownloader {

	@Qualifier("adDownloaderRestClient")
	private final RestClient restClient;

	public AdPlainText downloadAd(String adUrl) {
		// 1. Fetch and clean data
		String rawHtml = restClient
			.get()
			.uri(adUrl)
			.retrieve()
			.body(String.class);

		return new AdPlainText(rawHtml);
	}

}
