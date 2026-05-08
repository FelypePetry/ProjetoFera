package com.grupoum.projeto_fera.controller.cliente;

import com.grupoum.projeto_fera.service.FeedbackService;
import com.grupoum.projeto_fera.service.OrcamentoService;
import com.grupoum.projeto_fera.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/minha-conta")
@RequiredArgsConstructor
public class ClienteController {

    private final UsuarioService usuarioService;
    private final OrcamentoService orcamentoService;
    private final FeedbackService feedbackService;

    @GetMapping
    public String minhaConta(Model model, Principal principal) {
        model.addAttribute("usuario", usuarioService.buscarPorEmail(principal.getName()));
        return "cliente";
    }

    @GetMapping("/pedidos")
    public String meusPedidos(Model model, Principal principal) {
        model.addAttribute("orcamentos", orcamentoService.listarPorUsuario(principal.getName()));
        return "cliente/pedidos";
    }

    @GetMapping("/historico")
    public String historico(Model model, Principal principal) {
        model.addAttribute("orcamentos", orcamentoService.listarPorUsuario(principal.getName()));
        return "cliente/historico";
    }

    @GetMapping("/feedback")
    public String feedbackForm(Model model, Principal principal) {
        model.addAttribute("meusFeedbacks", feedbackService.listarPorUsuario(principal.getName()));
        return "cliente/feedback";
    }

    @PostMapping("/feedback")
    public String enviarFeedback(@RequestParam String mensagem,
                                 Principal principal,
                                 RedirectAttributes attrs) {
        feedbackService.criar(mensagem, principal.getName());
        attrs.addFlashAttribute("sucesso", "Feedback enviado! Obrigado.");
        return "redirect:/minha-conta/feedback";
    }

    @GetMapping("/orcamento")
    public String novoOrcamentoForm() {
        return "cliente/orcamento";
    }

    @PostMapping("/orcamento")
    public String enviarOrcamento(@RequestParam String tipoMovel,
                                  @RequestParam(required = false) String medidas,
                                  @RequestParam(required = false) String observacoes,
                                  @RequestParam(required = false) MultipartFile imagem,
                                  Principal principal,
                                  RedirectAttributes attrs) throws IOException {
        orcamentoService.criar(tipoMovel, medidas, observacoes, imagem, principal.getName());
        attrs.addFlashAttribute("sucesso", "Orçamento enviado com sucesso!");
        return "redirect:/minha-conta/pedidos";
    }
}
