package visita10;

import java.math.BigDecimal;

// Representa um tipo concreto de Produto.
public final class Bebida extends Produto {

    // Característica específica das bebidas.
    private final int volumeEmMl;

    public Bebida(
            String nome,
            BigDecimal preco,
            int volumeEmMl) {

        // Inicializa a parte herdada de Produto.
        super(nome, preco);

        if (volumeEmMl <= 0) {
            throw new IllegalArgumentException(
                "O volume deve ser maior que zero."
            );
        }

        this.volumeEmMl = volumeEmMl;
    }

    // Permite consultar a característica específica.
    public int consultarVolumeEmMl() {
        return volumeEmMl;
    }

    // Sobrescreve a operação recebida de Produto.
    @Override
    public String preparar() {
        return "Extrair e servir %s em uma porção de %d ml."
            .formatted(consultarNome(), volumeEmMl);
    }
}