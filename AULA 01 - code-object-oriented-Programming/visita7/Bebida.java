package visita7;

import java.math.BigDecimal;

/**
 * Bebida é uma especialização de Produto.
 *
 * A palavra extends representa a herança em Java.
 */
public final class Bebida extends Produto {

    // Característica específica de Bebida.
    private final int volumeEmMl;

    public Bebida(
            String nome,
            BigDecimal preco,
            int volumeEmMl) {

        /*
         * super(...) chama o construtor do tipo geral.
         * Produto inicializa nome, preço e disponibilidade.
         */
        super(nome, preco);

        if (volumeEmMl <= 0) {
            throw new IllegalArgumentException(
                "Volume deve ser positivo"
            );
        }

        // Bebida inicializa apenas sua característica específica.
        this.volumeEmMl = volumeEmMl;
    }

    /**
     * Comportamento pertencente somente a Bebida.
     */
    public int consultarVolumeEmMl() {
        return volumeEmMl;
    }
}