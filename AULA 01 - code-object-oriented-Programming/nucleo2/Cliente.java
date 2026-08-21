package nucleo2;

/**
 * Representa uma pessoa que realiza pedidos.
 */
public final class Cliente {

    private final String nome;
    private String telefone;

    public Cliente(String nome, String telefone) {
        validarTexto(nome, "Nome");
        validarTexto(telefone, "Telefone");

        this.nome = nome;
        this.telefone = telefone;
    }

    public String consultarNome() {
        return nome;
    }

    public String consultarTelefone() {
        return telefone;
    }

    // O próprio Cliente controla a alteração de seu estado.
    public void atualizarTelefone(String novoTelefone) {
        validarTexto(novoTelefone, "Telefone");
        this.telefone = novoTelefone;
    }

    private static void validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(
                campo + " é obrigatório"
            );
        }
    }
}