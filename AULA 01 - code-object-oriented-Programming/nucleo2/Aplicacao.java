package nucleo2;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Executa o sistema consolidado dos Núcleos 1 e 2.
 */
public final class Aplicacao {

    private Aplicacao() {
        // Impede a criação da classe de inicialização.
    }

    public static void main(String[] args) {

        /*
         * =====================================================
         * CLASSES, OBJETOS E GENERALIZAÇÃO
         * =====================================================
         */

        var ana = new Cliente(
            "Ana",
            "(21) 99999-1111"
        );

        // Produto é abstrato; somente tipos concretos são criados.
        var cappuccino = new Bebida(
            "Cappuccino",
            new BigDecimal("13.50"),
            true,
            200
        );

        var cafeExpresso = new Bebida(
            "Café expresso",
            new BigDecimal("8.00"),
            true,
            50
        );

        var brownie = new Alimento(
            "Brownie",
            new BigDecimal("8.00"),
            true,
            90
        );

        var paoDeQueijo = new Alimento(
            "Pão de queijo",
            new BigDecimal("5.50"),
            true,
            60
        );

        /*
         * A instrução abaixo não compila porque Produto
         * é uma classe abstrata:
         *
         * var produto = new Produto(...);
         */

        /*
         * =====================================================
         * AGREGAÇÃO — CARDÁPIO E PRODUTO
         * =====================================================
         */

        var cardapioManha = new Cardapio(
            "Cardápio da manhã"
        );

        var cardapioTarde = new Cardapio(
            "Cardápio da tarde"
        );

        // O mesmo Produto pode participar de vários cardápios.
        cardapioManha.adicionarProduto(cappuccino);
        cardapioTarde.adicionarProduto(cappuccino);

        cardapioManha.adicionarProduto(cafeExpresso);
        cardapioManha.adicionarProduto(paoDeQueijo);
        cardapioTarde.adicionarProduto(brownie);

        exibirCardapio(cardapioManha);
        exibirCardapio(cardapioTarde);

        /*
         * =====================================================
         * ASSOCIAÇÃO — PEDIDO E CLIENTE
         * =====================================================
         */

        var pedido104 = new Pedido(104, ana);

        System.out.println();
        System.out.println(
            "Cliente do pedido: "
                + pedido104.consultarCliente().consultarNome()
        );

        // A alteração é feita no objeto Cliente.
        ana.atualizarTelefone("(21) 98888-2222");

        // Pedido navega até o mesmo objeto Cliente.
        System.out.println(
            "Telefone atualizado: "
                + pedido104.consultarCliente().consultarTelefone()
        );

        /*
         * =====================================================
         * MULTIPLICIDADE E COMPOSIÇÃO
         * =====================================================
         */

        System.out.println();
        System.out.println(
            "Itens antes da montagem: "
                + pedido104.consultarItens().size()
        );

        pedido104.adicionarItem(cappuccino, 1);
        pedido104.adicionarItem(paoDeQueijo, 2);
        pedido104.adicionarItem(brownie, 1);

        System.out.println("Itens do pedido:");

        pedido104.consultarItens()
            .forEach(item ->
                System.out.println(
                    "- " + item.gerarResumo()
                )
            );

        System.out.println(
            "Total: "
                + formatarMoeda(pedido104.calcularTotal())
        );

        /*
         * =====================================================
         * ENCAPSULAMENTO
         * =====================================================
         */

        pedido104.confirmar();

        System.out.println(
            "Situação: " + pedido104.consultarStatus()
        );

        // Pedido confirmado rejeita novos itens.
        try {
            pedido104.adicionarItem(cafeExpresso, 1);
        } catch (IllegalStateException erro) {
            System.out.println(
                "Operação rejeitada: "
                    + erro.getMessage()
            );
        }

        /*
         * =====================================================
         * CLASSES E MÉTODOS ABSTRATOS
         * =====================================================
         */

        System.out.println();
        System.out.println("Instruções de preparo:");

        // Cada tipo concreto fornece sua implementação.
        System.out.println(cappuccino.preparar());
        System.out.println(brownie.preparar());

        /*
         * =====================================================
         * CICLO DE VIDA DA COMPOSIÇÃO
         * =====================================================
         */

        var pedido105 = new Pedido(105, ana);

        pedido105.adicionarItem(cafeExpresso, 1);
        pedido105.adicionarItem(brownie, 1);

        System.out.println();
        System.out.println(
            "Itens antes do descarte: "
                + pedido105.consultarItens().size()
        );

        pedido105.descartar();

        System.out.println(
            "Itens depois do descarte: "
                + pedido105.consultarItens().size()
        );

        System.out.println(
            "Situação: " + pedido105.consultarStatus()
        );

        // Produtos continuam existindo após o descarte.
        System.out.println(
            "Produto ainda existente: "
                + cafeExpresso.consultarNome()
        );

        /*
         * =====================================================
         * INDEPENDÊNCIA DA AGREGAÇÃO
         * =====================================================
         */

        cardapioManha.retirarProduto(cappuccino);

        System.out.println();
        System.out.println(
            "Cappuccino no cardápio da manhã? "
                + cardapioManha
                    .consultarProdutos()
                    .contains(cappuccino)
        );

        System.out.println(
            "Cappuccino no cardápio da tarde? "
                + cardapioTarde
                    .consultarProdutos()
                    .contains(cappuccino)
        );

        System.out.println(
            "Produto continua existente: "
                + cappuccino.consultarNome()
        );
    }

    private static void exibirCardapio(Cardapio cardapio) {
        System.out.println();
        System.out.println(cardapio.consultarNome());

        cardapio.consultarProdutos()
            .forEach(produto ->
                System.out.println(
                    "- "
                        + produto.consultarNome()
                        + " — "
                        + formatarMoeda(
                            produto.consultarPreco()
                        )
                )
            );
    }

    private static String formatarMoeda(BigDecimal valor) {
        return NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("pt-BR")
        ).format(valor);
    }
}