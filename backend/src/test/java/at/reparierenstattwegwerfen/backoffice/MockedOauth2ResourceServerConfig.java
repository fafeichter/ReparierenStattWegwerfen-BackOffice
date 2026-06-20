package at.reparierenstattwegwerfen.backoffice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Fabian Feichter
 */
@TestConfiguration
public class MockedOauth2ResourceServerConfig {

	@Value("#{T(at.reparierenstattwegwerfen.backoffice.ResourceReader).readFileToString('classpath:jwt/public_key" +
		".txt')}")
	private String publicKeyPEM;

	@Bean
	public JwtDecoder jwtDecoder() throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);

		return NimbusJwtDecoder.withPublicKey(publicKey).build();
	}
}