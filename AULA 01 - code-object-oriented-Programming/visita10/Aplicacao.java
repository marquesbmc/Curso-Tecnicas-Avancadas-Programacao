package visita10;

import java.math.BigDecimal;

// Demonstra sobrecarga e sobrescrita.
public final class Aplicacao {

    public static void main(String[] args) {

        var cappuccino = new Bebida(
            "Cappuccino",
            new BigDecimal("13.50"),
            200
        );

        var paoDeQueijo = new Alimento(
            "Pão de queijo",
            new BigDecimal("8.00"),
            120
        );

        var pedido = new Pedido(210);

        /*
         * SOBRECARGA
         *
         * O compilador encontra um argumento e escolhe:
         * adicionarItem(Produto)
         */
        pedido.adicionarItem(cappuccino);

        /*
         * SOBRECARGA
         *
         * O compilador encontra dois argumentos e escolhe:
         * adicionarItem(Produto, int)
         */
        pedido.adicionarItem(paoDeQueijo, 2);

        System.out.println("ITENS REGISTRADOS");

        for (ItemPedido item : pedido.consultarItens()) {
            System.out.printf(
                "%d x %s — R$ %s%n",
                item.consultarQuantidade(),
                item.consultarProduto().consultarNome(),
                item.calcularSubtotal()
                    .setScale(2)
                    .toPlainString()
                    .replace(".", ",")
            );
        }

        System.out.println();
        System.out.println(pedido.gerarResumo());

        /*
         * SOBRESCRITA
         *
         * As variáveis possuem o tipo declarado Produto,
         * mas referenciam objetos concretos diferentes.
         */
        Produto primeiroProduto = cappuccino;
        Produto segundoProduto = paoDeQueijo;

        System.out.println();
        System.out.println("INSTRUÇÕES DE PREPARO");

        /*
         * A JVM consulta a classe concreta do objeto.
         * Como o primeiro objeto é uma Bebida,
         * executa Bebida.preparar().
         */
        System.out.println(primeiroProduto.preparar());

        /*
         * Como o segundo objeto é um Alimento,
         * executa Alimento.preparar().
         */
        System.out.println(segundoProduto.preparar());
    }
}