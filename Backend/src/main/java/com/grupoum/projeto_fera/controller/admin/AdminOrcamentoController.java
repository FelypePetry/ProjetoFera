package com.grupoum.projeto_fera.controller.admin;

import com.grupoum.projeto_fera.model.StatusOrcamento;
import com.grupoum.projeto_fera.service.OrcamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/orcamentos")
@RequiredArgsConstructor
public class AdminOrcamentoController {

    private final OrcamentoService orcamentoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("orcamentos", orcamentoService.listarTodos());
        model.addAttribute("statuses", StatusOrcamento.values());
        return "admin/orcamentos/listar";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("orcamento", orcamentoService.buscarPorId(id));
        model.addAttribute("statuses", StatusOrcamento.values());
        return "admin/orcamentos/detalhe";
    }

    @PostMapping("/{id}/status")
    public String atualizarStatus(@PathVariable Long id,
                                  @RequestParam StatusOrcamento status,
                                  RedirectAttributes attrs) {
        orcamentoService.atualizarStatus(id, status);
        attrs.addFlashAttribute("sucesso", "Status atualizado para: " + status);
        return "redirect:/admin/orcamentos/" + id;
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes attrs) {
        orcamentoService.deletar(id);
        attrs.addFlashAttribute("sucesso", "Orçamento removido!");
        return "redirect:/admin/orcamentos";
    }
}
