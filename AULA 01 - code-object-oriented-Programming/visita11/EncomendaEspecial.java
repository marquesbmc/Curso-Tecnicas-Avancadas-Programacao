package visita11;

import java.util.Objects;

// EncomendaEspecial não herda de Produto.
// Mesmo assim, pode ocupar o papel de Preparavel
// porque realiza diretamente essa interface.
public final class EncomendaEspecial
        implements Preparavel {

    // Estado específico da encomenda.
    private final String descricao;

    public EncomendaEspecial(String descricao) {
        Objects.requireNonNull(
            descricao,
            "A descrição é obrigatória."
        );

        if (descricao.isBlank()) {
            throw new IllegalArgumentException(
                "A descrição não pode estar vazia."
            );
        }

        this.descricao = descricao;
    }

    public String consultarDescricao() {
        return descricao;
    }

    // Implementação própria de preparar().
    @Override
    public String preparar() {
        return "Montar a encomenda especial: %s."
            .formatted(descricao);
    }
}