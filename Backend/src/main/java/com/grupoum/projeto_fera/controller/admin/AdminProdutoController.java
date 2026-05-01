package com.grupoum.projeto_fera.controller.admin;

import com.grupoum.projeto_fera.model.Produto;
import com.grupoum.projeto_fera.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/produtos")
@RequiredArgsConstructor
public class AdminProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "admin/produtos/listar";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "admin/produtos/form";
    }

    @PostMapping("/novo")
    public String criar(@Valid @ModelAttribute Produto produto,
                        BindingResult result,
                        RedirectAttributes attrs) {
        if (result.hasErrors()) return "admin/produtos/form";
        try {
            produtoService.criar(produto);
            attrs.addFlashAttribute("sucesso", "Produto criado com sucesso!");
        } catch (RuntimeException e) {
            attrs.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/admin/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("produto", produtoService.buscarPorId(id));
        return "admin/produtos/form";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute Produto produto,
                            BindingResult result,
                            RedirectAttributes attrs) {
        if (result.hasErrors()) return "admin/produtos/form";
        try {
            produtoService.atualizar(id, produto);
            attrs.addFlashAttribute("sucesso", "Produto atualizado com sucesso!");
        } catch (RuntimeException e) {
            attrs.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/admin/produtos";
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes attrs) {
        try {
            produtoService.deletar(id);
            attrs.addFlashAttribute("sucesso", "Produto removido com sucesso!");
        } catch (RuntimeException e) {
            attrs.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/admin/produtos";
    }

    @PostMapping("/desativar/{id}")
    public String desativar(@PathVariable Long id, RedirectAttributes attrs) {
        produtoService.desativar(id);
        attrs.addFlashAttribute("sucesso", "Produto desativado!");
        return "redirect:/admin/produtos";
    }
}
