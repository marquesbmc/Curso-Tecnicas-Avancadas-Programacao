package nucleo1;

/**
 * Representa uma pessoa que realiza pedidos na cafeteria.
 *
 * Cliente possui existência independente:
 * ele pode existir mesmo sem possuir pedidos.
 */
public final class Cliente {

    // Estado protegido do objeto.
    private final String nome;
    private String telefone;

    public Cliente(String nome, String telefone) {
        validarTexto(nome, "Nome");
        validarTexto(telefone, "Telefone");

        this.nome = nome;
        this.telefone = telefone;
    }

    // Permite consultar o nome sem expor o atributo diretamente.
    public String consultarNome() {
        return nome;
    }

    // Permite consultar o telefone atual.
    public String consultarTelefone() {
        return telefone;
    }

    /**
     * A alteração acontece por uma operação controlada.
     * O próprio objeto verifica se o novo telefone é válido.
     */
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