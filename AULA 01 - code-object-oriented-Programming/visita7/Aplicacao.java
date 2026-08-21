package visita7;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Demonstra generalização e herança.
 */
public final class Aplicacao {

    private Aplicacao() {
        // Impede a criação da classe de inicialização.
    }

    public static void main(String[] args) {

        /*
         * =====================================================
         * BEBIDA
         * =====================================================
         */

        var cappuccino = new Bebida(
            "Cappuccino",
            new BigDecimal("13.50"),
            200
        );

        /*
         * consultarNome(), consultarPreco() e estaDisponivel()
         * foram definidos em Produto e herdados por Bebida.
         */
        System.out.println("BEBIDA");
        System.out.println(
            "Nome: " + cappuccino.consultarNome()
        );
        System.out.println(
            "Preço: "
                + formatarMoeda(cappuccino.consultarPreco())
        );
        System.out.println(
            "Disponível: " + cappuccino.estaDisponivel()
        );

        /*
         * consultarVolumeEmMl() pertence somente a Bebida.
         */
        System.out.println(
            "Volume: "
                + cappuccino.consultarVolumeEmMl()
                + " ml"
        );

        /*
         * Bebida também herda o comportamento responsável
         * pela alteração da disponibilidade.
         */
        cappuccino.alterarDisponibilidade(false);

        System.out.println(
            "Disponível depois da alteração: "
                + cappuccino.estaDisponivel()
        );

        /*
         * =====================================================
         * ALIMENTO
         * =====================================================
         */

        var brownie = new Alimento(
            "Brownie",
            new BigDecimal("8.00"),
            90
        );

        /*
         * Os comportamentos comuns foram definidos uma vez
         * em Produto e também são herdados por Alimento.
         */
        System.out.println();
        System.out.println("ALIMENTO");
        System.out.println(
            "Nome: " + brownie.consultarNome()
        );
        System.out.println(
            "Preço: "
                + formatarMoeda(brownie.consultarPreco())
        );
        System.out.println(
            "Disponível: " + brownie.estaDisponivel()
        );

        /*
         * consultarPesoEmGramas() pertence somente a Alimento.
         */
        System.out.println(
            "Peso: "
                + brownie.consultarPesoEmGramas()
                + " g"
        );

        /*
         * =====================================================
         * LEITURA DA HERANÇA
         * =====================================================
         */

        System.out.println();
        System.out.println("RELAÇÕES REPRESENTADAS");
        System.out.println(
            "Cappuccino é Produto? "
                + (cappuccino instanceof Produto)
        );
        System.out.println(
            "Brownie é Produto? "
                + (brownie instanceof Produto)
        );
    }

    private static String formatarMoeda(BigDecimal valor) {
        return NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("pt-BR")
        ).format(valor);
    }
}