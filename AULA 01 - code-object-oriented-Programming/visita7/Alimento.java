package visita7;

import java.math.BigDecimal;

/**
 * Alimento é outra especialização de Produto.
 */
public final class Alimento extends Produto {

    // Característica específica de Alimento.
    private final int pesoEmGramas;

    public Alimento(
            String nome,
            BigDecimal preco,
            int pesoEmGramas) {

        /*
         * O construtor de Produto inicializa
         * as características comuns.
         */
        super(nome, preco);

        if (pesoEmGramas <= 0) {
            throw new IllegalArgumentException(
                "Peso deve ser positivo"
            );
        }

        // Alimento inicializa apenas sua característica específica.
        this.pesoEmGramas = pesoEmGramas;
    }

    /**
     * Comportamento pertencente somente a Alimento.
     */
    public int consultarPesoEmGramas() {
        return pesoEmGramas;
    }
}