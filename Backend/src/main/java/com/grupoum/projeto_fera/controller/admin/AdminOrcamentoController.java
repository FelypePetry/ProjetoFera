package com.grupoum.projeto_fera.controller.admin;

import com.grupoum.projeto_fera.model.Orcamento;
import com.grupoum.projeto_fera.service.OrcamentoService;
import com.grupoum.projeto_fera.service.ProdutoService;
import com.grupoum.projeto_fera.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/orcamentos")
@RequiredArgsConstructor
public class AdminOrcamentoController {

    private final OrcamentoService orcamentoService;
    private final UsuarioService usuarioService;
    private final ProdutoService produtoService;

    private void carregarDadosParaForm(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("produtos", produtoService.listarTodos());
    }

    @InitBinder("orcamento")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("imagem");
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("orcamentos", orcamentoService.listarTodos());
        return "admin/orcamentos/listar";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("orcamento", new Orcamento());
        carregarDadosParaForm(model);
        return "admin/orcamentos/form";
    }

    @PostMapping("/novo")
    public String criar(@Valid @ModelAttribute Orcamento orcamento,
                        BindingResult result,
                        @RequestParam("imagem") MultipartFile imagem,
                        RedirectAttributes attrs,
                        Model model) {
        if (result.hasErrors()) {
            carregarDadosParaForm(model);
            return "admin/orcamentos/form";
        }
        try {
            orcamentoService.criar(orcamento, imagem);
            attrs.addFlashAttribute("sucesso", "Orçamento criado com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro ao criar orçamento: " + e.getMessage());
        }
        return "redirect:/admin/orcamentos";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("orcamento", orcamentoService.buscarPorId(id));
        return "admin/orcamentos/detalhe";
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes attrs) {
        try {
            orcamentoService.deletar(id);
            attrs.addFlashAttribute("sucesso", "Orçamento removido com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro ao remover orçamento: " + e.getMessage());
        }
        return "redirect:/admin/orcamentos";
    }
}
