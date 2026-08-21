package visita5;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Cardápio representa o todo da agregação.
public final class Cardapio {

    private final String nome;

    /*
     * Cardápio mantém referências para produtos
     * que foram criados independentemente.
     */
    private final List<Produto> produtos =
        new ArrayList<>();

    public Cardapio(String nome) {

        // Impede a criação de um cardápio sem nome.
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "Nome do cardápio é obrigatório"
            );
        }

        this.nome = nome;
    }

    public void adicionarProduto(Produto produto) {

        // Impede a inclusão de uma referência nula.
        Objects.requireNonNull(
            produto,
            "Produto é obrigatório"
        );

        /*
         * Evita repetir a mesma instância de Produto
         * dentro deste cardápio.
         */
        if (produtos.contains(produto)) {
            throw new IllegalArgumentException(
                "Produto já está neste cardápio"
            );
        }

        /*
         * O produto já existe.
         * Cardápio apenas mantém uma referência para ele.
         */
        produtos.add(produto);
    }

    public boolean retirarProduto(Produto produto) {

        // Impede uma tentativa de remoção com valor nulo.
        Objects.requireNonNull(
            produto,
            "Produto é obrigatório"
        );

        /*
         * Remove somente a referência deste cardápio.
         * O objeto Produto não é excluído.
         */
        return produtos.remove(produto);
    }

    public List<Produto> consultarProdutos() {

        /*
         * Retorna uma lista que não pode ser alterada
         * externamente, protegendo a coleção interna.
         */
        return List.copyOf(produtos);
    }

    public boolean contemProduto(Produto produto) {

        // Verifica se este cardápio mantém a referência.
        return produtos.contains(produto);
    }

    public int consultarQuantidadeProdutos() {
        return produtos.size();
    }

    public String consultarNome() {
        return nome;
    }

    public String gerarResumo() {

        var resumo = new StringBuilder();

        resumo.append("Cardápio: ")
            .append(nome)
            .append(System.lineSeparator());

        if (produtos.isEmpty()) {
            resumo.append("Nenhum produto disponível.");

            return resumo.toString();
        }

        // Percorre todos os produtos agregados.
        for (Produto produto : produtos) {
            resumo.append("- ")
                .append(produto.gerarResumo())
                .append(System.lineSeparator());
        }

        return resumo.toString();
    }
}