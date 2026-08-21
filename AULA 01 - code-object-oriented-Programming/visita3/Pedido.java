package visita3;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

// Representa uma compra realizada por um cliente.
public final class Pedido {

    // Identifica individualmente o pedido.
    private final int numero;

    // Mantém a associação com o objeto Cliente.
    private final Cliente cliente;

    // Implementa a multiplicidade 0..*
    // A lista nasce vazia e pode receber vários itens.
    private final List<ItemPedido> itens =
        new ArrayList<>();

    // Indica se a montagem do pedido foi concluída.
    private boolean confirmado;

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

        // Todo novo pedido começa em montagem.
        this.confirmado = false;
    }

    public void adicionarItem(ItemPedido item) {

        // Um pedido confirmado não pode receber novos itens.
        validarEmMontagem();

        // Impede a inclusão de um item nulo.
        Objects.requireNonNull(
            item,
            "Item é obrigatório"
        );

        // Acrescenta o item sem substituir os anteriores.
        itens.add(item);
    }

    public List<ItemPedido> consultarItens() {

        // Retorna uma cópia não modificável da lista.
        // Assim, a coleção interna permanece protegida.
        return List.copyOf(itens);
    }

    public BigDecimal calcularTotal() {

        // Soma o subtotal de todos os itens.
        return itens.stream()
            .map(ItemPedido::calcularSubtotal)
            .reduce(
                BigDecimal.ZERO,
                BigDecimal::add
            );
    }

    public boolean podeConfirmar() {

        // O pedido precisa estar em montagem
        // e possuir pelo menos um item.
        return !confirmado && !itens.isEmpty();
    }

    public void confirmar() {

        // Impede a confirmação repetida.
        validarEmMontagem();

        // Embora a multiplicidade permita que o pedido
        // comece vazio, ele não pode ser confirmado vazio.
        if (itens.isEmpty()) {
            throw new IllegalStateException(
                "O pedido precisa ter ao menos um item"
            );
        }

        // Conclui a montagem do pedido.
        confirmado = true;
    }

    public String consultarTelefoneCliente() {

        // Navega de Pedido até o objeto Cliente.
        return cliente.consultarTelefone();
    }

    public String gerarResumo() {

        // Configura a apresentação dos valores em reais.
        var moeda = NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("pt-BR")
        );

        var situacao = confirmado
            ? "CONFIRMADO"
            : "EM MONTAGEM";

        return """
            Pedido %d
            Cliente: %s
            Quantidade de itens: %d
            Total: %s
            Situação: %s
            """.formatted(
                numero,
                cliente.consultarNome(),
                itens.size(),
                moeda.format(calcularTotal()),
                situacao
            );
    }

    private void validarEmMontagem() {

        // Centraliza a regra que protege pedidos confirmados.
        if (confirmado) {
            throw new IllegalStateException(
                "Pedido já foi confirmado"
            );
        }
    }
}