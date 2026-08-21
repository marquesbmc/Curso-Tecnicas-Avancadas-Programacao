package visita8;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Demonstra classes e métodos abstratos.
 */
public final class Aplicacao {

    private Aplicacao() {
        // Impede a criação da classe de inicialização.
    }

    public static void main(String[] args) {

        /*
         * Produto é abstrato.
         * A instrução abaixo não compilaria:
         *
         * var produto = new Produto(
         *     "Produto especial",
         *     new BigDecimal("10.00"),
         *     true
         * );
         */

        /*
         * Bebida é concreta e pode originar objetos.
         */
        var cappuccino = new Bebida(
            "Cappuccino",
            new BigDecimal("13.50"),
            true,
            200
        );

        /*
         * Alimento também é concreto.
         */
        var brownie = new Alimento(
            "Brownie",
            new BigDecimal("8.00"),
            true,
            90
        );

        System.out.println("BEBIDA");
        exibirDadosComuns(cappuccino);

        System.out.println(
            "Volume: "
                + cappuccino.consultarVolumeEmMl()
                + " ml"
        );

        // Executa a implementação fornecida por Bebida.
        System.out.println(cappuccino.preparar());

        System.out.println();
        System.out.println("ALIMENTO");
        exibirDadosComuns(brownie);

        System.out.println(
            "Peso: "
                + brownie.consultarPesoEmGramas()
                + " g"
        );

        // Executa a implementação fornecida por Alimento.
        System.out.println(brownie.preparar());
    }

    /*
     * Recebe Produto porque os dados comuns foram
     * definidos pelo tipo geral.
     *
     * O foco desta visita continua sendo a abstração.
     * O uso de diferentes objetos pelo mesmo tipo será
     * aprofundado posteriormente em polimorfismo.
     */
    private static void exibirDadosComuns(Produto produto) {
        System.out.println(
            "Nome: " + produto.consultarNome()
        );

        System.out.println(
            "Preço: "
                + formatarMoeda(produto.consultarPreco())
        );

        System.out.println(
            "Disponível: " + produto.estaDisponivel()
        );
    }

    private static String formatarMoeda(BigDecimal valor) {
        return NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("pt-BR")
        ).format(valor);
    }
}