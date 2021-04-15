package com.dhyego.demoajax.web.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dhyego.demoajax.domain.Categoria;
import com.dhyego.demoajax.domain.Promocao;
import com.dhyego.demoajax.repository.CategoriaRepository;
import com.dhyego.demoajax.repository.PromocaoRepository;

/**
 *
 * @author Dhyego Pedroso
 */
@Controller
@RequestMapping("/promocao")
public class PromocaoController {

    private static final Logger log = LoggerFactory.getLogger(PromocaoController.class);

    @Autowired
    private PromocaoRepository promocaoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/add")
    public String abrirCadastro() {

        return "promo-add";
    }

    @ModelAttribute("categorias")
    public List<Categoria> getCategorias() {

        return categoriaRepository.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<Promocao> salvarPromocao(Promocao promocao) {

        log.info("Promocao {}", promocao.toString());
        promocao.setDtCadastro(LocalDateTime.now());
        promocaoRepository.save(promocao);
        return ResponseEntity.ok().build();
    }

}
