package visita5;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

// Produto possui identidade e existência independentes.
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

        // Impede preço nulo.
        Objects.requireNonNull(
            preco,
            "Preço do produto é obrigatório"
        );

        // Impede preço igual a zero ou negativo.
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

    // Produz uma representação textual do produto.
    public String gerarResumo() {

        var moeda = NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("pt-BR")
        );

        return "%s — %s".formatted(
            nome,
            moeda.format(preco)
        );
    }
}