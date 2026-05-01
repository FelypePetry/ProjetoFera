package com.grupoum.projeto_fera.controller.admin;

import com.grupoum.projeto_fera.model.StatusOrcamento;
import com.grupoum.projeto_fera.service.OrcamentoService;
import com.grupoum.projeto_fera.service.ProdutoService;
import com.grupoum.projeto_fera.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProdutoService produtoService;
    private final UsuarioService usuarioService;
    private final OrcamentoService orcamentoService;

    @GetMapping({"", "/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("totalProdutos", produtoService.contarTodos());
        model.addAttribute("totalUsuarios", usuarioService.contarTodos());
        model.addAttribute("totalOrcamentos", orcamentoService.listarTodos().size());
        model.addAttribute("emAnalise", orcamentoService.contarPorStatus(StatusOrcamento.EM_ANALISE));
        model.addAttribute("emProducao", orcamentoService.contarPorStatus(StatusOrcamento.EM_PRODUCAO));
        model.addAttribute("orcamentosRecentes", orcamentoService.listarTodos()
                .stream().limit(5).toList());
        return "admin/dashboard";
    }
}
