package nucleo3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Organiza os produtos oferecidos em determinado cardápio.
 */
public final class Cardapio {

    private final String nome;

    // POO — AGREGAÇÃO:
    // Cardapio reúne Produtos que continuam existindo independentemente dele.
    private final List<Produto> produtos = new ArrayList<>();

    public Cardapio(String nome) {
        Objects.requireNonNull(nome, "nome não pode ser nulo");
        if (nome.isBlank()) {
            throw new IllegalArgumentException("nome não pode ficar vazio");
        }
        this.nome = nome.trim();
    }

    public String consultarNome() {
        return nome;
    }

    public void adicionarProduto(Produto produto) {
        Objects.requireNonNull(produto, "produto não pode ser nulo");
        if (!produtos.contains(produto)) {
            produtos.add(produto);
        }
    }

    public void retirarProduto(Produto produto) {
        produtos.remove(produto);
    }

    // POO — ENCAPSULAMENTO:
    // Retorna uma cópia imutável para impedir alterações externas na coleção.
    public List<Produto> consultarProdutos() {
        return List.copyOf(produtos);
    }
}
