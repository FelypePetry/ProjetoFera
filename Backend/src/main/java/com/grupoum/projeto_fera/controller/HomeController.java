package com.grupoum.projeto_fera.controller;

import com.grupoum.projeto_fera.model.Produto;
import com.grupoum.projeto_fera.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProdutoService produtoService;

    @GetMapping("/")
    public String home(Model model) {
        try {
            List<Produto> destaques = produtoService.listarAtivos()
                    .stream()
                    .filter(Produto::isMaisVendido)
                    .limit(4)
                    .collect(Collectors.toList());
            model.addAttribute("destaques", destaques);
        } catch (Exception e) {
            model.addAttribute("destaques", List.of());
        }
        return "index";
    }
}