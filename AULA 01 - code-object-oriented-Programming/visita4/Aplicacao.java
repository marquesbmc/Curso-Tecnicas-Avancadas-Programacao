package visita4;

import java.math.BigDecimal;

public final class Aplicacao {

    public static void main(String[] args) {

        // Cliente existe independentemente do pedido.
        var ana = new Cliente(
            "Ana",
            "(21) 99999-1111"
        );

        /*
         * Produtos existem antes do pedido
         * e permanecem cadastrados depois dele.
         */
        var cappuccino = new Produto(
            "Cappuccino",
            new BigDecimal("13.50")
        );

        var brownie = new Produto(
            "Brownie",
            new BigDecimal("8.00")
        );

        // Pedido representa o todo da composição.
        var pedido104 = new Pedido(104, ana);

        /*
         * A aplicação não cria ItemPedido.
         * Ela solicita que Pedido crie suas partes.
         */
        pedido104.adicionarItem(cappuccino, 1);
        pedido104.adicionarItem(brownie, 1);

        System.out.println("Itens antes do descarte:");

        pedido104.consultarItens().forEach(
            System.out::println
        );

        System.out.println();
        System.out.println(pedido104.gerarResumo());

        /*
         * A cliente desiste antes da confirmação.
         * O pedido elimina as partes que controlava.
         */
        pedido104.descartar();

        System.out.println("Depois do descarte:");
        System.out.println(
            "Itens mantidos pelo pedido: "
                + pedido104.consultarQuantidadeItens()
        );

        System.out.println();
        System.out.println(pedido104.gerarResumo());

        /*
         * Os produtos continuam existindo,
         * porque não fazem parte da composição.
         */
        System.out.println(
            "Produto ainda cadastrado: "
                + cappuccino.consultarNome()
        );

        System.out.println(
            "Produto ainda cadastrado: "
                + brownie.consultarNome()
        );
    }
}