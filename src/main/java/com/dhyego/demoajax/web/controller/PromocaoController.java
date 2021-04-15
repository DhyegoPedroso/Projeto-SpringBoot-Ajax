package com.dhyego.demoajax.web.controller;

import com.dhyego.demoajax.domain.Categoria;
import com.dhyego.demoajax.repository.CategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Dhyego Pedroso
 */
@Controller
@RequestMapping("/promocao")
public class PromocaoController {

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

}
