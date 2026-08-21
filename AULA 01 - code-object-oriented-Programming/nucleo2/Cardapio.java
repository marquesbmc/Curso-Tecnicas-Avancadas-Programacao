package nucleo2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Agrega produtos que existem independentemente.
 *
 * Como Produto é abstrato, os objetos adicionados serão
 * instâncias concretas de Bebida ou Alimento.
 */
public final class Cardapio {

    private final String nome;

    // Agregação: referências para produtos independentes.
    private final List<Produto> produtos = new ArrayList<>();

    public Cardapio(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "Nome do cardápio é obrigatório"
            );
        }

        this.nome = nome;
    }

    public void adicionarProduto(Produto produto) {
        Objects.requireNonNull(
            produto,
            "Produto é obrigatório"
        );

        if (produtos.contains(produto)) {
            throw new IllegalArgumentException(
                "Produto já pertence ao cardápio"
            );
        }

        // Mantém uma referência para um produto já existente.
        produtos.add(produto);
    }

    public void retirarProduto(Produto produto) {
        Objects.requireNonNull(
            produto,
            "Produto é obrigatório"
        );

        /*
         * Remove somente a relação.
         * O objeto Produto continua existindo.
         */
        produtos.remove(produto);
    }

    public List<Produto> consultarProdutos() {
        return List.copyOf(produtos);
    }

    public String consultarNome() {
        return nome;
    }
}