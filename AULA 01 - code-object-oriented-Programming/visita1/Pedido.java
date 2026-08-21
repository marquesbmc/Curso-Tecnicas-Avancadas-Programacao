package visita1;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

// Define o molde usado para criar objetos Pedido.
public class Pedido {

    // Cada objeto mantém seus próprios valores.
    private final int numero;
    private final String nomeRetirada;
    private BigDecimal total;

    // O construtor recebe os dados obrigatórios.
    public Pedido(int numero, String nomeRetirada) {
        this.numero = numero;
        this.nomeRetirada = nomeRetirada;

        // Todo novo pedido começa com total igual a zero.
        this.total = BigDecimal.ZERO;
    }

    // Os métodos continuam no próximo slide.
     // Recebe dados e altera o total do pedido.
    public void registrarProduto(
            String nome,
            BigDecimal valor) {

        // Impede o registro de um produto sem nome.
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }

        // Impede valores nulos, iguais a zero ou negativos.
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }

        // Altera somente o total deste objeto.
        total = total.add(valor);
    }

    // Retorna o total sem alterar o objeto.
    public BigDecimal consultarTotal() {
        return total;
    }

    // Produz uma representação textual do pedido.
    public String gerarResumo() {
        var moeda = NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("pt-BR")
        );

        return "Pedido %d - %s - Total: %s".formatted(
            numero, nomeRetirada, moeda.format(total)
        );
    }
}