package visita3;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

// Representa um produto registrado dentro de uma compra.
public final class ItemPedido {

    // Identifica o produto comprado.
    private final String produto;

    // Informa quantas unidades foram compradas.
    private final int quantidade;

    // Mantém o preço de uma unidade do produto.
    private final BigDecimal valorUnitario;

    public ItemPedido(
        String produto,
        int quantidade,
        BigDecimal valorUnitario
    ) {

        // Impede a criação de um item sem produto.
        if (produto == null || produto.isBlank()) {
            throw new IllegalArgumentException(
                "Produto é obrigatório"
            );
        }

        // A quantidade deve ser maior do que zero.
        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                "Quantidade deve ser maior do que zero"
            );
        }

        // O valor não pode ser nulo, zero ou negativo.
        if (valorUnitario == null
            || valorUnitario.signum() <= 0) {

            throw new IllegalArgumentException(
                "Valor unitário deve ser positivo"
            );
        }

        // Define o estado inicial do item.
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    // Calcula o valor correspondente a todas as unidades.
    public BigDecimal calcularSubtotal() {
        return valorUnitario.multiply(
            BigDecimal.valueOf(quantidade)
        );
    }

    // Produz uma representação textual do item.
    public String gerarResumo() {

        // Configura a apresentação de valores em reais.
        var moeda = NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("pt-BR")
        );

        return "%dx %s — %s".formatted(
            quantidade,
            produto,
            moeda.format(calcularSubtotal())
        );
    }
}