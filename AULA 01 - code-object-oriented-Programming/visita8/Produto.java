package visita8;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa o conceito geral Produto.
 *
 * A classe é abstrata porque não possui informação
 * suficiente para representar um produto concreto.
 */
public abstract class Produto {

    // Estado comum às especializações.
    private final String nome;
    private final BigDecimal preco;
    private final boolean disponivel;

    /**
     * Classes abstratas podem possuir construtores.
     *
     * O construtor será chamado pelas classes concretas
     * por meio de super(...).
     */
    protected Produto(
            String nome,
            BigDecimal preco,
            boolean disponivel) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "Nome do produto é obrigatório"
            );
        }

        Objects.requireNonNull(
            preco,
            "Preço do produto é obrigatório"
        );

        if (preco.signum() <= 0) {
            throw new IllegalArgumentException(
                "Preço deve ser positivo"
            );
        }

        this.nome = nome;
        this.preco = preco;
        this.disponivel = disponivel;
    }

    /**
     * Operação concreta compartilhada por todas
     * as especializações.
     */
    public String consultarNome() {
        return nome;
    }

    /**
     * Operação concreta compartilhada por todas
     * as especializações.
     */
    public BigDecimal consultarPreco() {
        return preco;
    }

    /**
     * Operação concreta compartilhada por todas
     * as especializações.
     */
    public boolean estaDisponivel() {
        return disponivel;
    }

    /**
     * Operação abstrata.
     *
     * Produto declara que todo tipo concreto deve informar
     * como é preparado, mas não possui uma implementação geral.
     */
    public abstract String preparar();
}