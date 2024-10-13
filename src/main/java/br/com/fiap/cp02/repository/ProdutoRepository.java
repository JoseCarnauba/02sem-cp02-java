package br.com.fiap.cp02.repository;

import br.com.fiap.cp02.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome); // Ignorar case na busca
    List<Produto> findByCategoria(String categoria);
}
