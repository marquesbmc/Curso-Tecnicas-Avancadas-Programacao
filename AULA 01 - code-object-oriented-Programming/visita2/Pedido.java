package visita2;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public final class Pedido {

    // Identifica individualmente o pedido.
    private final int numero;

    // Mantém a referência para o objeto Cliente.
    private final Cliente cliente;

    // Representa o total próprio deste pedido.
    private BigDecimal total;

    public Pedido(int numero, Cliente cliente) {

        this.numero = numero;

        // Guarda a referência recebida.
        // Os dados do cliente não são copiados.
        this.cliente = Objects.requireNonNull(
            cliente,
            "Cliente obrigatório"
        );

        // Todo pedido começa com total igual a zero.
        this.total = BigDecimal.ZERO;
    }
        public void registrarProduto(
        String nome,
        BigDecimal valor
    ) {

        // Impede o registro de um produto sem nome.
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "Nome inválido"
            );
        }

        // Impede valores nulos, iguais a zero
        // ou negativos.
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException(
                "Valor inválido"
            );
        }

        // Altera somente o total deste pedido.
        total = total.add(valor);
    }

    // Consulta o total sem alterar o pedido.
    public BigDecimal consultarTotal() {
        return total;
    }
        public String consultarTelefoneCliente() {

        // Pedido acessa uma operação de Cliente.
        return cliente.consultarTelefone();
    }

    public String gerarResumo() {

        // Configura a apresentação do valor em reais.
        var moeda = NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("pt-BR")
        );

        // Obtém o nome navegando até Cliente.
        return "Pedido %d - %s - Total: %s"
            .formatted(
                numero,
                cliente.nomeParaRetirada(),
                moeda.format(total)
            );
    }
}