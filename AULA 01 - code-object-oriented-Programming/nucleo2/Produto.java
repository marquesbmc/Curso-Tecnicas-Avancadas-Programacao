package nucleo2;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa a definição comum a todos os produtos.
 *
 * Produto é abstrato porque não deve originar objetos
 * genéricos sem uma forma concreta de preparo.
 */
public abstract class Produto {

    private final String nome;
    private final BigDecimal preco;
    private final boolean disponivel;

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

    // Operação concreta compartilhada pelas especializações.
    public String consultarNome() {
        return nome;
    }

    // Operação concreta compartilhada pelas especializações.
    public BigDecimal consultarPreco() {
        return preco;
    }

    public boolean estaDisponivel() {
        return disponivel;
    }

    /**
     * Todo produto concreto deve definir como é preparado.
     */
    public abstract String preparar();
}