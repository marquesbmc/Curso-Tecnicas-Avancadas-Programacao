package nucleo1;


import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * Representa uma parte exclusiva de um Pedido.
 *
 * O ItemPedido registra:
 * - qual produto foi escolhido;
 * - a quantidade solicitada;
 * - o preço utilizado naquela compra.
 *
 * Seu construtor não é público porque a criação dos itens
 * deve ser controlada por Pedido.
 */
public final class ItemPedido {

    // Associação com um Produto independente.
    private final Produto produto;

    private final int quantidade;

    /*
     * Registra o preço praticado no momento da compra.
     * Uma mudança futura no preço do produto não altera
     * os pedidos que já foram montados.
     */
    private final BigDecimal valorUnitario;

    /*
     * A ausência de public limita a criação ao próprio pacote.
     * Na aplicação, somente Pedido utilizará este construtor.
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

    // Calcula o valor correspondente a este item.
    public BigDecimal calcularSubtotal() {
        return valorUnitario.multiply(
            BigDecimal.valueOf(quantidade)
        );
    }

    // Produz uma descrição do item para apresentação.
    public String gerarResumo() {
        return "%dx %s — %s".formatted(
            quantidade,
            produto.consultarNome(),
            formatarMoeda(calcularSubtotal())
        );
    }

    private static String formatarMoeda(BigDecimal valor) {
        return NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("pt-BR")
        ).format(valor);
    }
}