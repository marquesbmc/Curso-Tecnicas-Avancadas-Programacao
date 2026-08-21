package visita11;

import java.math.BigDecimal;

// Demonstra o comportamento polimórfico.
public final class Aplicacao {

    public static void main(String[] args) {

        /*
         * O tipo das três referências é Preparavel.
         * Os objetos concretos, porém, são diferentes.
         *
         * Nenhum objeto é transformado em Preparavel.
         * Preparavel é apenas a forma comum utilizada
         * para referenciar esses objetos.
         */
        Preparavel cappuccino = new Bebida(
            "Cappuccino",
            new BigDecimal("13.50"),
            200
        );

        Preparavel paoDeQueijo = new Alimento(
            "Pão de queijo",
            new BigDecimal("8.00"),
            120
        );

        Preparavel kitPresente =
            new EncomendaEspecial(
                "Kit com café, caneca e embalagem"
            );

        // A fila conhece somente o contrato comum.
        var fila = new FilaPreparo();

        // O mesmo método aceita objetos concretos diferentes.
        fila.adicionar(cappuccino);
        fila.adicionar(paoDeQueijo);
        fila.adicionar(kitPresente);

        /*
         * A fila solicita preparar() para todos.
         * Cada objeto executa sua própria implementação.
         */
        fila.prepararTodos();
    }
}