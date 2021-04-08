package com.dhyego.demoajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dhyego.demoajax.domain.SocialMetaTag;
import com.dhyego.demoajax.service.SocialMetaTagService;

@SpringBootApplication
public class DemoAjaxApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoAjaxApplication.class, args);
    }

    @Autowired
    SocialMetaTagService service;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("");
        System.out.println("");
        SocialMetaTag tag = service.getSocialMetaTagByUrl(
                "https://www.terabyteshop.com.br/produto/15153/fonte-gamdias-kratos-e1-500w-add-rgb-80-plus-gd-z500zzz");
        System.out.println(tag.toString());

    }

}
