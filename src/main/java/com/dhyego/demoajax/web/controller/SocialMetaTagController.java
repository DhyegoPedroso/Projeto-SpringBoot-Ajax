package com.dhyego.demoajax.web.controller;

import com.dhyego.demoajax.domain.SocialMetaTag;
import com.dhyego.demoajax.service.SocialMetaTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Dhyego Pedroso
 */
@Controller
@RequestMapping("/meta")
public class SocialMetaTagController {

    @Autowired
    private SocialMetaTagService socialMetaTagService;

    @PostMapping("/info")
    public ResponseEntity<SocialMetaTag> getDadosViaUrl(@RequestParam(value = "url") String url) {

        SocialMetaTag socialMetaTag = socialMetaTagService.getSocialMetaTagByUrl(url);

        return socialMetaTag != null
                ? ResponseEntity.ok(socialMetaTag)
                : ResponseEntity.notFound().build();

    }

}
