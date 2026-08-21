package nucleo1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa um conjunto de produtos disponíveis.
 *
 * Cardapio agrega objetos Produto:
 * - os produtos são criados independentemente;
 * - um produto pode participar de vários cardápios;
 * - retirar um produto não destrói seu cadastro.
 */
public final class Cardapio {

    private final String nome;

    // Agregação: coleção de referências para produtos independentes.
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

        // Impede a repetição da mesma instância no cardápio.
        if (produtos.contains(produto)) {
            throw new IllegalArgumentException(
                "Produto já pertence a este cardápio"
            );
        }

        /*
         * Cardapio não cria o Produto.
         * Apenas mantém uma referência para ele.
         */
        produtos.add(produto);
    }

    public void retirarProduto(Produto produto) {
        Objects.requireNonNull(
            produto,
            "Produto é obrigatório"
        );

        /*
         * Remove somente a associação.
         * O objeto Produto continua existindo.
         */
        produtos.remove(produto);
    }

    public List<Produto> consultarProdutos() {
        // Protege a coleção interna contra alterações externas.
        return List.copyOf(produtos);
    }
}