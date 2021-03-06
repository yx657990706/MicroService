package com.yx.appwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AppWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppWebServiceApplication.class, args);
    }

//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//
//    @RestController
//    public class TestController {
//
//        private final RestTemplate restTemplate;
//
//        @Autowired
//        public TestController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}
//
//        @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
//        public String echo(@PathVariable String str) {
//            return restTemplate.getForObject("http://app-game-service/echo/" + str, String.class);
//        }
//    }
}
