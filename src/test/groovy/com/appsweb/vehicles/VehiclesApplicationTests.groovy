package com.appsweb.vehicles

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(properties = ["eureka.client.enabled=false"])
class VehiclesApplicationTests {

    @Test
    void contextLoads() {
    }

}
