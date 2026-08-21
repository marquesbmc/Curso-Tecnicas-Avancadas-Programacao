package visita5;

import java.math.BigDecimal;

public final class Aplicacao {

    public static void main(String[] args) {

        /*
         * Os produtos são criados antes e fora
         * de qualquer cardápio.
         */
        var cappuccino = new Produto(
            "Cappuccino",
            new BigDecimal("13.50")
        );

        var brownie = new Produto(
            "Brownie",
            new BigDecimal("8.00")
        );

        var cafeExpresso = new Produto(
            "Café expresso",
            new BigDecimal("7.00")
        );

        // Os cardápios também possuem identidade própria.
        var cardapioManha = new Cardapio(
            "Cardápio da manhã"
        );

        var cardapioTarde = new Cardapio(
            "Cardápio da tarde"
        );

        /*
         * O mesmo objeto Cappuccino participa
         * de dois cardápios diferentes.
         */
        cardapioManha.adicionarProduto(cappuccino);
        cardapioTarde.adicionarProduto(cappuccino);

        // Outros produtos participam de apenas um cardápio.
        cardapioManha.adicionarProduto(cafeExpresso);
        cardapioTarde.adicionarProduto(brownie);

        System.out.println("ANTES DA RETIRADA");
        System.out.println();

        System.out.println(cardapioManha.gerarResumo());
        System.out.println(cardapioTarde.gerarResumo());

        /*
         * O fornecedor suspende temporariamente
         * o cappuccino no período da manhã.
         */
        boolean retirado =
            cardapioManha.retirarProduto(cappuccino);

        System.out.println(
            "Cappuccino retirado da manhã? "
                + retirado
        );

        System.out.println();
        System.out.println("DEPOIS DA RETIRADA");
        System.out.println();

        /*
         * Cappuccino não aparece mais no cardápio
         * da manhã.
         */
        System.out.println(
            "Está no cardápio da manhã? "
                + cardapioManha.contemProduto(cappuccino)
        );

        /*
         * A mesma instância continua participando
         * do cardápio da tarde.
         */
        System.out.println(
            "Está no cardápio da tarde? "
                + cardapioTarde.contemProduto(cappuccino)
        );

        /*
         * O objeto Produto continua existindo
         * mesmo depois de uma relação ser removida.
         */
        System.out.println(
            "Produto ainda cadastrado: "
                + cappuccino.gerarResumo()
        );

        System.out.println();
        System.out.println(cardapioManha.gerarResumo());
        System.out.println(cardapioTarde.gerarResumo());
    }
}