package visita2;

public final class Cliente {

    // Identifica a pessoa atendida.
    private final String nome;

    // Pode ser atualizado durante a existência do cliente.
    private String telefone;

    public Cliente(String nome, String telefone) {

        // Impede a criação de um cliente sem nome.
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "Nome obrigatório"
            );
        }

        // Impede a criação de um cliente sem telefone.
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException(
                "Telefone obrigatório"
            );
        }

        // Define o estado inicial do cliente.
        this.nome = nome;
        this.telefone = telefone;
    }

    // Informa o nome usado na retirada do pedido.
    public String nomeParaRetirada() {
        return nome;
    }

    // Retorna o telefone atualmente cadastrado.
    public String consultarTelefone() {
        return telefone;
    }

    public void atualizarTelefone(String novoTelefone) {

        // Impede a atualização com um valor inválido.
        if (novoTelefone == null || novoTelefone.isBlank()) {
            throw new IllegalArgumentException(
                "Telefone obrigatório"
            );
        }

        // Altera o estado do próprio objeto Cliente.
        this.telefone = novoTelefone;
    }
}