package visita8;

import java.math.BigDecimal;

/**
 * Representa um tipo concreto de Produto.
 */
public final class Bebida extends Produto {

    // Característica específica de Bebida.
    private final int volumeEmMl;

    public Bebida(
            String nome,
            BigDecimal preco,
            boolean disponivel,
            int volumeEmMl) {

        // Inicializa a parte definida em Produto.
        super(nome, preco, disponivel);

        if (volumeEmMl <= 0) {
            throw new IllegalArgumentException(
                "Volume deve ser positivo"
            );
        }

        this.volumeEmMl = volumeEmMl;
    }

    public int consultarVolumeEmMl() {
        return volumeEmMl;
    }

    /**
     * Bebida fornece uma implementação concreta
     * para a operação exigida por Produto.
     */
    @Override
    public String preparar() {
        return "Preparar a bebida "
            + consultarNome()
            + " e servir "
            + volumeEmMl
            + " ml.";
    }
}