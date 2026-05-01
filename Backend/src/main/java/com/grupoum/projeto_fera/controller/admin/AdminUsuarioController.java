package com.grupoum.projeto_fera.controller.admin;

import com.grupoum.projeto_fera.model.Role;
import com.grupoum.projeto_fera.model.Usuario;
import com.grupoum.projeto_fera.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/usuarios")
@RequiredArgsConstructor
public class AdminUsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "admin/usuarios/listar";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", Role.values());
        return "admin/usuarios/form";
    }

    @PostMapping("/novo")
    public String criar(@Valid @ModelAttribute Usuario usuario,
                        BindingResult result,
                        Model model,
                        RedirectAttributes attrs) {
        if (result.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "admin/usuarios/form";
        }
        try {
            usuarioService.criar(usuario);
            attrs.addFlashAttribute("sucesso", "Usuário criado com sucesso!");
        } catch (RuntimeException e) {
            attrs.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.buscarPorId(id));
        model.addAttribute("roles", Role.values());
        return "admin/usuarios/form";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute Usuario usuario,
                            BindingResult result,
                            Model model,
                            RedirectAttributes attrs) {
        if (result.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "admin/usuarios/form";
        }
        try {
            usuarioService.atualizar(id, usuario);
            attrs.addFlashAttribute("sucesso", "Usuário atualizado com sucesso!");
        } catch (RuntimeException e) {
            attrs.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes attrs) {
        try {
            usuarioService.deletar(id);
            attrs.addFlashAttribute("sucesso", "Usuário removido!");
        } catch (RuntimeException e) {
            attrs.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/desativar/{id}")
    public String desativar(@PathVariable Long id, RedirectAttributes attrs) {
        usuarioService.desativar(id);
        attrs.addFlashAttribute("sucesso", "Usuário desativado!");
        return "redirect:/admin/usuarios";
    }
}
