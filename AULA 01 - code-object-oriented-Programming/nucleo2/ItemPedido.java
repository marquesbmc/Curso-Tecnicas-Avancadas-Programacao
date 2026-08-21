package nucleo2;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * Representa uma parte exclusiva de Pedido.
 *
 * ItemPedido registra o produto, a quantidade e o preço
 * utilizado no momento da compra.
 */
public final class ItemPedido {

    // Associação com um Produto independente.
    private final Produto produto;

    private final int quantidade;
    private final BigDecimal valorUnitario;

    /**
     * O construtor não é público.
     * A criação dos itens é controlada por Pedido.
     */
    ItemPedido(
            Produto produto,
            int quantidade,
            BigDecimal valorUnitario) {

        this.produto = Objects.requireNonNull(
            produto,
            "Produto é obrigatório"
        );

        Objects.requireNonNull(
            valorUnitario,
            "Valor unitário é obrigatório"
        );

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                "Quantidade deve ser positiva"
            );
        }

        if (valorUnitario.signum() <= 0) {
            throw new IllegalArgumentException(
                "Valor unitário deve ser positivo"
            );
        }

        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal calcularSubtotal() {
        return valorUnitario.multiply(
            BigDecimal.valueOf(quantidade)
        );
    }

    public String gerarResumo() {
        return quantidade
            + "x "
            + produto.consultarNome()
            + " — "
            + formatarMoeda(calcularSubtotal());
    }

    private static String formatarMoeda(BigDecimal valor) {
        return NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("pt-BR")
        ).format(valor);
    }
}