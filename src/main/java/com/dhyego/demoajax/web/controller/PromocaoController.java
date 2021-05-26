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
import com.dhyego.demoajax.dto.PromocaoDTO;
import com.dhyego.demoajax.repository.CategoriaRepository;
import com.dhyego.demoajax.repository.PromocaoRepository;
import com.dhyego.demoajax.service.PromocaoDataTablesService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    // ======================================ADD OFERTAS=============================================
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

    // ======================================LISTAR OFERTAS==========================================
    @GetMapping("/list")
    public String listarOfertas(ModelMap model) {
        Sort sort = Sort.by(Sort.Direction.DESC, "dtCadastro");
        PageRequest pageRequest = PageRequest.of(0, 8, sort);
        model.addAttribute("promocoes", promocaoRepository.findAll(pageRequest));
        return "promo-list";
    }

    @GetMapping("/list/ajax")
    public String listarCards(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "site", defaultValue = "") String site,
            ModelMap model) {

        Sort sort = Sort.by(Sort.Direction.DESC, "dtCadastro");
        PageRequest pageRequest = PageRequest.of(page, 8, sort);

        if (site.isEmpty()) {
            model.addAttribute("promocoes", promocaoRepository.findAll(pageRequest));
        } else {
            model.addAttribute("promocoes", promocaoRepository.findBySite(site, pageRequest));
        }

        return "promo-card";
    }

    // ======================================ADD LIKES===============================================
    @PostMapping("/like/{id}")
    public ResponseEntity<?> adicionarLikes(@PathVariable("id") Long id) {
        promocaoRepository.updateSomarLikes(id);
        int likes = promocaoRepository.findLikesById(id);
        return ResponseEntity.ok(likes);
    }

    // ======================================AUTOCOMPLETE===============================================
    @GetMapping("/site")
    public ResponseEntity<?> autocompleteByTermo(@RequestParam(value = "termo") String termo) {
        List<String> sites = promocaoRepository.findSitesByTermo(termo);
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/site/list")
    public String listarPorSite(@RequestParam(value = "site") String site, ModelMap model) {
        Sort sort = Sort.by(Sort.Direction.DESC, "dtCadastro");
        PageRequest pageRequest = PageRequest.of(0, 8, sort);
        model.addAttribute("promocoes", promocaoRepository.findBySite(site, pageRequest));
        return "promo-card";
    }

    // ======================================DATATABLES===============================================
    @GetMapping("/tabela")
    public String showTabela() {
        return "promo-datatables";
    }

    @GetMapping("/datatable/server")
    public ResponseEntity<?> datatables(HttpServletRequest request) {

        Map<String, Object> data = new PromocaoDataTablesService().execute(promocaoRepository, request);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> excluirPromocao(@PathVariable(value = "id") Long id) {
        promocaoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<?> preEditarPromocao(@PathVariable(value = "id") Long id) {
        Promocao promo = promocaoRepository.findById(id).get();
        return ResponseEntity.ok(promo);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editarPromocao(@Valid PromocaoDTO dto, BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            Promocao promo = promocaoRepository.findById(dto.getId()).get();
            promo.setCategoria(dto.getCategoria());
            promo.setDescricao(dto.getDescricao());
            promo.setLinkImagem(dto.getLinkImagem());
            promo.setPreco(dto.getPreco());
            promo.setTitulo(dto.getTitulo());

            promocaoRepository.save(promo);

            return ResponseEntity.unprocessableEntity().body(errors);
        }

        return ResponseEntity.ok().build();
    }

}
