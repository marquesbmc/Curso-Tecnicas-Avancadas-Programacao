package visita4;

// Representa a pessoa que realiza o pedido.
public final class Cliente {

    // O nome não muda depois da criação.
    private final String nome;

    // O telefone pode ser atualizado.
    private String telefone;

    public Cliente(String nome, String telefone) {

        // Impede a criação de um cliente sem nome.
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "Nome do cliente é obrigatório"
            );
        }

        // Impede a criação de um cliente sem telefone.
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException(
                "Telefone do cliente é obrigatório"
            );
        }

        this.nome = nome;
        this.telefone = telefone;
    }

    // Retorna o nome do cliente.
    public String consultarNome() {
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
                "Novo telefone é obrigatório"
            );
        }

        this.telefone = novoTelefone;
    }
}