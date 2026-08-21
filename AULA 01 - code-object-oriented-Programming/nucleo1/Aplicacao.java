package nucleo1;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Demonstra as necessidades apresentadas nas cinco visitas.
 */
public final class Aplicacao {

    private Aplicacao() {
        // Esta classe é utilizada apenas para iniciar o programa.
    }

    public static void main(String[] args) {

        /*
         * =====================================================
         * VISITA 1 — CLASSES E OBJETOS
         * =====================================================
         */

        exibirTitulo("VISITA 1 — CLASSES E OBJETOS");

        // Cada new cria um objeto com estado próprio.
        var cappuccino = new Produto(
            "Cappuccino",
            new BigDecimal("13.50")
        );

        var brownie = new Produto(
            "Brownie",
            new BigDecimal("8.00")
        );

        var paoDeQueijo = new Produto(
            "Pão de queijo",
            new BigDecimal("5.50")
        );

        exibirProduto(cappuccino);
        exibirProduto(brownie);
        exibirProduto(paoDeQueijo);

        /*
         * =====================================================
         * VISITA 2 — ASSOCIAÇÃO E NAVEGABILIDADE
         * =====================================================
         */

        exibirTitulo("VISITA 2 — ASSOCIAÇÃO E NAVEGABILIDADE");

        // Existe apenas um objeto representando Ana.
        var ana = new Cliente(
            "Ana",
            "(21) 99999-1111"
        );

        /*
         * Os dois pedidos mantêm uma referência para
         * o mesmo objeto Cliente.
         */
        var pedido104 = new Pedido(104, ana);
        var pedido105 = new Pedido(105, ana);

        // Navegação: Pedido -> Cliente -> nome.
        System.out.println(
            "Cliente do pedido 104: "
                + pedido104
                    .consultarCliente()
                    .consultarNome()
        );

        // A alteração acontece no objeto Cliente.
        ana.atualizarTelefone("(21) 98888-2222");

        /*
         * Os dois pedidos acessam o mesmo objeto e,
         * portanto, consultam o telefone atualizado.
         */
        System.out.println(
            "Telefone pelo pedido 104: "
                + pedido104
                    .consultarCliente()
                    .consultarTelefone()
        );

        System.out.println(
            "Telefone pelo pedido 105: "
                + pedido105
                    .consultarCliente()
                    .consultarTelefone()
        );

        /*
         * =====================================================
         * VISITA 3 — MULTIPLICIDADE
         * =====================================================
         */

        exibirTitulo("VISITA 3 — MULTIPLICIDADE");

        // O pedido começa com zero itens.
        System.out.println(
            "Itens ao criar o pedido: "
                + pedido104.consultarItens().size()
        );

        // Os itens são acrescentados gradualmente.
        pedido104.adicionarItem(cappuccino, 1);
        pedido104.adicionarItem(paoDeQueijo, 2);
        pedido104.adicionarItem(brownie, 1);

        // Agora o mesmo pedido possui vários itens.
        pedido104.consultarItens()
            .forEach(item ->
                System.out.println(item.gerarResumo())
            );

        System.out.println(
            "Quantidade de itens: "
                + pedido104.consultarItens().size()
        );

        System.out.println(
            "Total: "
                + formatarMoeda(pedido104.calcularTotal())
        );

        // Como possui itens, o pedido pode ser confirmado.
        pedido104.confirmar();

        System.out.println(
            "Situação: " + pedido104.consultarStatus()
        );

        /*
         * =====================================================
         * VISITA 4 — COMPOSIÇÃO
         * =====================================================
         */

        exibirTitulo("VISITA 4 — COMPOSIÇÃO");

        // Pedido cria internamente seus ItemPedido.
        pedido105.adicionarItem(cappuccino, 1);
        pedido105.adicionarItem(brownie, 1);

        System.out.println(
            "Itens antes do descarte: "
                + pedido105.consultarItens().size()
        );

        /*
         * Ao descartar o todo, suas partes deixam de
         * fazer parte do modelo.
         */
        pedido105.descartar();

        System.out.println(
            "Itens depois do descarte: "
                + pedido105.consultarItens().size()
        );

        System.out.println(
            "Situação: " + pedido105.consultarStatus()
        );

        /*
         * Produto não faz parte da composição.
         * Portanto, continua existindo.
         */
        System.out.println(
            "Produto ainda existente: "
                + cappuccino.consultarNome()
        );

        /*
         * =====================================================
         * VISITA 5 — AGREGAÇÃO
         * =====================================================
         */

        exibirTitulo("VISITA 5 — AGREGAÇÃO");

        var cardapioManha = new Cardapio(
            "Cardápio da manhã"
        );

        var cardapioTarde = new Cardapio(
            "Cardápio da tarde"
        );

        /*
         * O mesmo objeto Produto participa de
         * dois objetos Cardapio.
         */
        cardapioManha.adicionarProduto(cappuccino);
        cardapioTarde.adicionarProduto(cappuccino);

        cardapioManha.adicionarProduto(paoDeQueijo);
        cardapioTarde.adicionarProduto(brownie);

        exibirCardapio(
            "Cardápio da manhã",
            cardapioManha
        );

        exibirCardapio(
            "Cardápio da tarde",
            cardapioTarde
        );

        /*
         * Retirar do cardápio da manhã desfaz somente
         * aquela relação.
         */
        cardapioManha.retirarProduto(cappuccino);

        System.out.println(
            "Está no cardápio da manhã? "
                + cardapioManha
                    .consultarProdutos()
                    .contains(cappuccino)
        );

        System.out.println(
            "Está no cardápio da tarde? "
                + cardapioTarde
                    .consultarProdutos()
                    .contains(cappuccino)
        );

        // O objeto Produto continua existindo.
        System.out.println(
            "Produto continua cadastrado: "
                + cappuccino.consultarNome()
        );
    }

    private static void exibirProduto(Produto produto) {
        System.out.println(
            produto.consultarNome()
                + " — "
                + formatarMoeda(produto.consultarPreco())
        );
    }

    private static void exibirCardapio(
            String nome,
            Cardapio cardapio) {

        System.out.println();
        System.out.println(nome);

        cardapio.consultarProdutos()
            .forEach(Aplicacao::exibirProduto);
    }

    private static String formatarMoeda(BigDecimal valor) {
        return NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("pt-BR")
        ).format(valor);
    }

    private static void exibirTitulo(String titulo) {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println(titulo);
        System.out.println("=".repeat(60));
    }
}