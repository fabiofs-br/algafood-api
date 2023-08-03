package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.core.storage.StorageProperties.TipoStorage;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.LocalFotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.S3FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "s3")
    public AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(
                storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getChaveAcessoSecreta());

        if (storageProperties.getS3().getServiceEndpoint() != null && storageProperties.getS3().getServiceEndpoint() != "") {
            var endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                    storageProperties.getS3().getServiceEndpoint(),
                    storageProperties.getS3().getRegiao());

            return AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withEndpointConfiguration(endpointConfiguration)
                    .build();
        } else {
            return AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(storageProperties.getS3().getRegiao())
                    .build();
        }


    }

    @Bean
    public FotoStorageService fotoStorageService() {
        if (TipoStorage.LOCAL.equals(storageProperties.getTipo())) {
            return new LocalFotoStorageService();
        } else {
            return new S3FotoStorageService();
        }
    }

}
