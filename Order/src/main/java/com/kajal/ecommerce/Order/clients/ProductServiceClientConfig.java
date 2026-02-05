package com.kajal.ecommerce.Order.clients;

import com.kajal.ecommerce.Order.clients.ProductServiceClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

//import java.util.Optional;
//
//@Configuration
//public class ProductServiceClientConfig
//{
//
//    @Bean
//    @LoadBalanced
//    public RestClient.Builder restClientBuilder()
//    {
//        return RestClient.builder();
//    }
//
//    @Bean
//    public ProductServiceClient restClientInterface(RestClient.Builder builder)
//    {
//        RestClient restClient=builder
//                .baseUrl("http://product-service")
//                .defaultStatusHandler(HttpStatusCode::is4xxClientError, ((request, response) -> Optional.empty()))
//                .build();
//
//        RestClientAdapter restClientAdapter=RestClientAdapter.create(restClient);
//        HttpServiceProxyFactory factory=HttpServiceProxyFactory
//                .builderFor(restClientAdapter).build();
//
//        return factory.createClient(ProductServiceClient.class);
//    }
//}


@Configuration
public class ProductServiceClientConfig {

//    // 1️⃣ Plain RestClient (used by Eureka internally)
//    @Bean
//    @Primary
//    public RestClient.Builder defaultRestClientBuilder() {
//        return RestClient.builder();
//    }
//
//    // 2️⃣ LoadBalanced RestClient (ONLY for service-to-service calls)
//    @Bean
//    @LoadBalanced
//    @Qualifier("loadBalancedRestClientBuilder")
//    public RestClient.Builder loadBalancedRestClientBuilder() {
//        return RestClient.builder();
//    }

    // 3️⃣ Use ONLY the load-balanced one for product-service
    @Bean
    public ProductServiceClient productServiceClient(
            @Qualifier("loadBalancedRestClientBuilder") RestClient.Builder builder) {

        RestClient restClient = builder
                .baseUrl("http://product-service")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, ((request, response) -> Optional.empty()))
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(ProductServiceClient.class);
    }
}

