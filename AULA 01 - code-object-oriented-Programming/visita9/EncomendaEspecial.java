package visita9;

import java.util.Objects;

// Não é um Produto, mas também possui a capacidade de ser preparada.
public final class EncomendaEspecial implements Preparavel {

    // Informação própria da encomenda.
    private final String descricao;

    // Inicializa uma encomenda especial.
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

    // Permite consultar a descrição sem alterá-la.
    public String consultarDescricao() {
        return descricao;
    }

    // Realiza diretamente o contrato Preparavel.
    @Override
    public String preparar() {
        return "Montando a encomenda especial: %s."
            .formatted(descricao);
    }
}