package br.com.fiap.cp02.controller;

import br.com.fiap.cp02.model.Produto;
import br.com.fiap.cp02.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Método para ir a página inicial
    @GetMapping("/inicio")
    public String inicio() {
        return "produto/inicio";
    }

    // Método para listar todos os produtos
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Produto> produtos = produtoService.listarTodos();
        model.addAttribute("produtos", produtos);
        return "produto/lista"; // Retorna a lista de produtos
    }

    // Exibe o formulário de cadastro de um novo produto
    @GetMapping("/cadastrar")
    public String cadastrar(Model model) {
        model.addAttribute("produto", new Produto()); // Inicializa um novo produto
        return "produto/cadastro"; // Retorna o formulário de cadastro
    }

    // Método para salvar o produto com validação
    @PostMapping("/cadastrar")
    @Transactional
    public String cadastrar(@Valid @ModelAttribute("produto") Produto produto, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "produto/cadastro"; // Retorna ao formulário se houver erros
        }
        produtoService.salvar(produto);
        redirectAttributes.addFlashAttribute("msg", "Produto registrado com sucesso!");
        return "redirect:/produto/listar"; // Redireciona para a lista de produtos
    }

    // Método para editar um produto existente
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Produto produto = produtoService.buscarPorId(id);
        if (produto == null) {
            return "redirect:/produto/listar"; // Redireciona se o produto não for encontrado
        }
        model.addAttribute("produto", produto);
        return "produto/editar"; // Retorna o formulário de edição
    }

    // Método para atualizar um produto existente
    @PostMapping("/editar")
    @Transactional
    public String editar(@Valid @ModelAttribute("produto") Produto produto, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "produto/editar"; // Retorna ao formulário se houver erros
        }
        produtoService.salvar(produto);
        redirectAttributes.addFlashAttribute("msg", "Produto atualizado com sucesso!");
        return "redirect:/produto/listar"; // Redireciona para a lista de produtos
    }

    // Método para excluir um produto
    @PostMapping("/excluir")
    @Transactional
    public String excluir(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        if (produtoService.buscarPorId(id) != null) {
            produtoService.excluir(id);
            redirectAttributes.addFlashAttribute("msg", "Produto excluído com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Produto não encontrado!");
        }
        return "redirect:/produto/listar"; // Redireciona para a lista de produtos
    }

    // Método para pesquisar produtos por nome (opcional)
    @GetMapping("/pesquisar")
    public String pesquisar(@RequestParam(required = false) String nome, Model model) {
        List<Produto> produtos = nome != null && !nome.trim().isEmpty() ? produtoService.buscarPorNome(nome) : List.of();
        model.addAttribute("produtos", produtos);
        if (produtos.isEmpty() && nome != null && !nome.trim().isEmpty()) {
            model.addAttribute("msg", "Nenhum produto encontrado com o nome: " + nome);
        }
        return "produto/pesquisar"; // Retorna a lista de produtos filtrados
    }
}
