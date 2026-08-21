package nucleo3;

import java.util.Objects;

/**
 * Representa uma pessoa que realiza pedidos na cafeteria.
 */
public final class Cliente {

    // POO — ENCAPSULAMENTO:
    // O estado fica protegido e não pode ser alterado diretamente por outras classes.
    private final String nome;
    private String telefone;

    public Cliente(String nome, String telefone) {
        this.nome = validarTexto(nome, "nome");
        this.telefone = validarTexto(telefone, "telefone");
    }

    // A classe oferece consultas controladas sobre seu estado.
    public String consultarNome() {
        return nome;
    }

    public String consultarTelefone() {
        return telefone;
    }

    // POO — COMPORTAMENTO DO DOMÍNIO:
    // Em vez de expor um setTelefone genérico, o objeto oferece uma ação significativa.
    public void atualizarTelefone(String novoTelefone) {
        this.telefone = validarTexto(novoTelefone, "novo telefone");
    }

    private static String validarTexto(String valor, String campo) {
        Objects.requireNonNull(valor, campo + " não pode ser nulo");
        if (valor.isBlank()) {
            throw new IllegalArgumentException(campo + " não pode ficar vazio");
        }
        return valor.trim();
    }
}
