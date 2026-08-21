package visita9;

import java.math.BigDecimal;

// Representa um tipo concreto de Produto.
public final class Bebida extends Produto {

    // Característica específica de uma bebida.
    private final int volumeEmMl;

    // Inicializa os dados comuns e específicos da bebida.
    public Bebida(
            String nome,
            BigDecimal preco,
            int volumeEmMl) {

        // Inicializa nome e preço na classe Produto.
        super(nome, preco);

        if (volumeEmMl <= 0) {
            throw new IllegalArgumentException(
                "O volume deve ser maior que zero."
            );
        }

        this.volumeEmMl = volumeEmMl;
    }

    // Permite consultar o volume sem alterá-lo.
    public int consultarVolumeEmMl() {
        return volumeEmMl;
    }

    // Concretiza o comportamento exigido por Preparavel.
    @Override
    public String preparar() {
        return "Preparando a bebida %s de %d ml."
            .formatted(consultarNome(), volumeEmMl);
    }
}