package com.grupoum.projeto_fera.controller.admin;

import com.grupoum.projeto_fera.model.ImagemProd;
import com.grupoum.projeto_fera.model.Produto;
import com.grupoum.projeto_fera.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/produtos")
@RequiredArgsConstructor
public class AdminProdutoController {

    private final ProdutoService produtoService;
    private final MaterialService materialService;
    private final CorService corService;
    private final CategoriaService categoriaService;
    private final ImageUploadService imageUploadService;

    @InitBinder("produto")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("imagens"); // evita conflito com MultipartFile
    }

    private void carregarAtributos(Model model) {
        model.addAttribute("materiais", materialService.findAll());
        model.addAttribute("cores", corService.findAll());
        model.addAttribute("categorias", categoriaService.findAll());
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "admin/produtos/listar";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("produto", produtoService.buscarPorId(id));
        return "admin/produtos/detalhe";
    }

    @GetMapping("/gerenciar-atributos")
    public String gerenciarAtributos() {
        return "admin/produtos/gerenciar-atributos";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("produto", new Produto());
        carregarAtributos(model);
        return "admin/produtos/form";
    }

    @PostMapping("/novo")
    public String criar(@Valid @ModelAttribute Produto produto,
                        BindingResult result,
                        @RequestParam("imagens") List<MultipartFile> imagens,
                        RedirectAttributes attrs,
                        Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> System.out.println("ERRO: " + e.getDefaultMessage()));
            carregarAtributos(model);
            return "admin/produtos/form";
        }
        try {
            produtoService.criar(produto, imagens); // delega tudo ao service
            attrs.addFlashAttribute("sucesso", "Produto criado com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro ao criar produto: " + e.getMessage());
        }
        return "redirect:/admin/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("produto", produtoService.buscarPorId(id));
        carregarAtributos(model);
        return "admin/produtos/form";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute Produto produto,
                            BindingResult result,
                            @RequestParam("imagens") List<MultipartFile> imagens,
                            RedirectAttributes attrs,
                            Model model) {
        if (result.hasErrors()) {
            carregarAtributos(model);
            return "admin/produtos/form";
        }
        try {
            List<ImagemProd> imagensParaSalvar = new ArrayList<>();
            for (MultipartFile imagem : imagens) {
                if (!imagem.isEmpty()) {
                    String urlImagem = imageUploadService.save(imagem, "produtos");
                    ImagemProd imagemProd = new ImagemProd();
                    imagemProd.setUrlImagem(urlImagem);
                    imagensParaSalvar.add(imagemProd);
                }
            }
            produto.setImagens(imagensParaSalvar);
            produtoService.atualizar(id, produto);
            attrs.addFlashAttribute("sucesso", "Produto atualizado com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro ao atualizar produto: " + e.getMessage());
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
