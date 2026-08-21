package nucleo1;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa um produto cadastrado na cafeteria.
 *
 * Produto possui existência independente:
 * pode participar de cardápios e pedidos sem pertencer
 * exclusivamente a nenhum deles.
 */
public final class Produto {

    private final String nome;
    private final BigDecimal preco;

    public Produto(String nome, BigDecimal preco) {
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
                "Preço do produto deve ser positivo"
            );
        }

        this.nome = nome;
        this.preco = preco;
    }

    public String consultarNome() {
        return nome;
    }

    public BigDecimal consultarPreco() {
        return preco;
    }
}