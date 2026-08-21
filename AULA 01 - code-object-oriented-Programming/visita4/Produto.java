package visita4;

import java.math.BigDecimal;
import java.util.Objects;

// Produto existe independentemente de qualquer pedido.
public final class Produto {

    private final String nome;
    private final BigDecimal preco;

    public Produto(String nome, BigDecimal preco) {

        // Impede a criação de um produto sem nome.
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "Nome do produto é obrigatório"
            );
        }

        // Garante que o preço não seja nulo.
        Objects.requireNonNull(
            preco,
            "Preço do produto é obrigatório"
        );

        // Impede preços iguais a zero ou negativos.
        if (preco.signum() <= 0) {
            throw new IllegalArgumentException(
                "Preço deve ser positivo"
            );
        }

        this.nome = nome;
        this.preco = preco;
    }

    // Retorna o nome do produto.
    public String consultarNome() {
        return nome;
    }

    // Retorna o preço atual do produto.
    public BigDecimal consultarPreco() {
        return preco;
    }
}