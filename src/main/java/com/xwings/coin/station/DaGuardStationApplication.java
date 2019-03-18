package com.xwings.coin.station;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class DaGuardStationApplication {

    public static final void main(String[] args) {
        SpringApplication.run(DaGuardStationApplication.class, args);
    }

}
