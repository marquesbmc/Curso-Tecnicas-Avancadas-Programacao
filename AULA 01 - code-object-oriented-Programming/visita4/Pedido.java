package visita4;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

// Pedido representa o todo da composição.
public final class Pedido {

    private final int numero;

    // Mantém a associação com Cliente.
    private final Cliente cliente;

    // Pedido controla exclusivamente suas partes.
    private final List<ItemPedido> itens =
        new ArrayList<>();

    private StatusPedido status;

    public Pedido(int numero, Cliente cliente) {

        // Impede números inválidos.
        if (numero <= 0) {
            throw new IllegalArgumentException(
                "Número do pedido deve ser positivo"
            );
        }

        this.numero = numero;

        // Pedido mantém uma referência para Cliente.
        this.cliente = Objects.requireNonNull(
            cliente,
            "Cliente é obrigatório"
        );

        // Todo pedido começa em montagem.
        this.status = StatusPedido.EM_MONTAGEM;
    }

    public void adicionarItem(
        Produto produto,
        int quantidade
    ) {

        // Somente pedidos em montagem recebem itens.
        validarEmMontagem();

        // Impede a utilização de um produto nulo.
        Objects.requireNonNull(
            produto,
            "Produto é obrigatório"
        );

        // Impede quantidades inválidas.
        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                "Quantidade deve ser positiva"
            );
        }

        /*
         * Pedido cria seu próprio ItemPedido.
         * A aplicação não recebe nem controla esse objeto.
         */
        itens.add(new ItemPedido(
            produto,
            quantidade,
            produto.consultarPreco()
        ));
    }

    public List<String> consultarItens() {

        /*
         * Os objetos ItemPedido não são expostos.
         * A aplicação recebe somente suas representações.
         */
        return itens.stream()
            .map(ItemPedido::gerarResumo)
            .toList();
    }

    // Informa quantas partes o pedido mantém.
    public int consultarQuantidadeItens() {
        return itens.size();
    }

    public BigDecimal calcularTotal() {

        // Soma os subtotais de todos os itens.
        return itens.stream()
            .map(ItemPedido::calcularSubtotal)
            .reduce(
                BigDecimal.ZERO,
                BigDecimal::add
            );
    }

    public void confirmar() {

        // Impede a confirmação de um pedido finalizado.
        validarEmMontagem();

        // Um pedido vazio não pode ser confirmado.
        if (itens.isEmpty()) {
            throw new IllegalStateException(
                "Pedido precisa ter ao menos um item"
            );
        }

        status = StatusPedido.CONFIRMADO;
    }

    public void descartar() {

        // O exemplo permite descartar apenas durante a montagem.
        validarEmMontagem();

        /*
         * Ao descartar o todo, suas partes deixam
         * de ser mantidas pelo modelo.
         */
        itens.clear();

        status = StatusPedido.DESCARTADO;
    }

    public String consultarTelefoneCliente() {

        // Navega de Pedido até Cliente.
        return cliente.consultarTelefone();
    }

    public String gerarResumo() {

        var moeda = NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("pt-BR")
        );

        return """
            Pedido %d
            Cliente: %s
            Itens: %d
            Total: %s
            Situação: %s
            """.formatted(
                numero,
                cliente.consultarNome(),
                itens.size(),
                moeda.format(calcularTotal()),
                status
            );
    }

    private void validarEmMontagem() {

        // Protege pedidos confirmados ou descartados.
        if (status != StatusPedido.EM_MONTAGEM) {
            throw new IllegalStateException(
                "Pedido não está mais em montagem"
            );
        }
    }

    /*
     * A parte da composição é privada.
     * Somente Pedido pode criar e acessar ItemPedido.
     */
    private static final class ItemPedido {

        // Produto é apenas referenciado pelo item.
        private final Produto produto;

        private final int quantidade;

        /*
         * Registra o preço considerado no momento
         * em que o produto entrou na compra.
         */
        private final BigDecimal valorUnitario;

        private ItemPedido(
            Produto produto,
            int quantidade,
            BigDecimal valorUnitario
        ) {
            this.produto = produto;
            this.quantidade = quantidade;
            this.valorUnitario = valorUnitario;
        }

        private BigDecimal calcularSubtotal() {
            return valorUnitario.multiply(
                BigDecimal.valueOf(quantidade)
            );
        }

        private String gerarResumo() {

            var moeda = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("pt-BR")
            );

            return "%dx %s — %s".formatted(
                quantidade,
                produto.consultarNome(),
                moeda.format(calcularSubtotal())
            );
        }
    }

    // Define as situações possíveis do pedido.
    private enum StatusPedido {
        EM_MONTAGEM,
        CONFIRMADO,
        DESCARTADO
    }
}