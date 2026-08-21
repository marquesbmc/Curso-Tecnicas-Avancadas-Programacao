package nucleo2;

import java.math.BigDecimal;

/**
 * Especialização concreta de Produto.
 */
public final class Bebida extends Produto {

    private final int volumeEmMl;

    public Bebida(
            String nome,
            BigDecimal preco,
            boolean disponivel,
            int volumeEmMl) {

        // Inicializa as características herdadas de Produto.
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

    // Implementação concreta exigida por Produto.
    @Override
    public String preparar() {
        return "Preparar a bebida "
            + consultarNome()
            + " e servir "
            + volumeEmMl
            + " ml.";
    }
}