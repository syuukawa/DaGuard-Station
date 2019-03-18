package com.xwings.coin.station.config;

import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.net.ssl.SSLContext;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

@Configuration
public class ApplicationConfiguration {
    private static final String CERTIFICATE_ALIAS = "daguard.station";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xwings.coin.station.controller"))
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        try {
            final SSLContext sslContext = SSLContexts
                    .custom()
                    .loadTrustMaterial(keyStore(), new TrustAllStrategy())
                    .build();
            final CloseableHttpClient client = HttpClients
                    .custom()
                    .setSSLContext(sslContext)
                    .build();

            return new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private KeyStore keyStore() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

        keyStore.load(null);

        final InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream("certificate/daguard-station.crt");
        final BufferedInputStream bis = new BufferedInputStream(fis);

        final CertificateFactory cf = CertificateFactory.getInstance("X.509");

        while (bis.available() > 0) {
            final Certificate cert = cf.generateCertificate(bis);
            keyStore.setCertificateEntry(CERTIFICATE_ALIAS, cert);
        }

        return keyStore;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("DaGuard Station Rest API")
                .version("1.0")
                .build();
    }

}
