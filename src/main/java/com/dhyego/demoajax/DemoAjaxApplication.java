package com.dhyego.demoajax;

import com.dhyego.demoajax.domain.SocialMetaTag;
import com.dhyego.demoajax.service.SocialMetaTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoAjaxApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoAjaxApplication.class, args);
    }

    @Autowired
    SocialMetaTagService service;

    @Override
    public void run(String... args) throws Exception {

        SocialMetaTag og = service
                .getOpenGraphByUrl("https://www.pichau.com.br/hardware/placa-m-e/placa-mae-asus-rog-strix-z490-f-gaming-ddr4-socket-lga1200-intel-z490");
        System.out.println(og);

        System.out.println("\n");
        
        SocialMetaTag twitter = service
                .getTwitterCardByUrl("https://www.pichau.com.br/hardware/placa-m-e/placa-mae-asus-rog-strix-z490-f-gaming-ddr4-socket-lga1200-intel-z490");
        System.out.println(twitter);

    }

}
