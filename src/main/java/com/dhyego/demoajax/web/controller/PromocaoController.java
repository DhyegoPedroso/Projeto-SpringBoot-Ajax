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
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
    public ResponseEntity<?> salvarPromocao(@Valid Promocao promocao, BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.unprocessableEntity().body(errors);

        }

        log.info("Promocao {}", promocao.toString());
        promocao.setDtCadastro(LocalDateTime.now());
        promocaoRepository.save(promocao);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public String listarOfertas(ModelMap model) {

        Sort sort = Sort.by(Sort.Direction.DESC, "dtCadastro");

        model.addAttribute("promocoes", promocaoRepository.findAll(sort));
        return "promo-list";
    }

}
