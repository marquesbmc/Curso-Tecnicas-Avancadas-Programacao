package visita11;

import java.math.BigDecimal;

// Bebida é um tipo concreto de Produto.
public final class Bebida extends Produto {

    // Característica específica da bebida.
    private final int volumeEmMl;

    public Bebida(
            String nome,
            BigDecimal preco,
            int volumeEmMl) {

        // Inicializa nome e preço em Produto.
        super(nome, preco);

        if (volumeEmMl <= 0) {
            throw new IllegalArgumentException(
                "O volume deve ser maior que zero."
            );
        }

        this.volumeEmMl = volumeEmMl;
    }

    public int consultarVolumeEmMl() {
        return volumeEmMl;
    }

    // Implementação concreta de preparar().
    @Override
    public String preparar() {
        return "Extrair e servir a bebida %s de %d ml."
            .formatted(
                consultarNome(),
                volumeEmMl
            );
    }
}