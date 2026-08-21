public class Passageiro extends Pessoa {
    private String categoria;
    private int quantidadeDeCorridas;

    public Passageiro(String nome, String telefone, String categoria, int quantidadeDeCorridas) {
        super(nome, telefone);
        this.categoria = categoria;
        this.quantidadeDeCorridas = quantidadeDeCorridas;
    }

    public String getCategoria() { return categoria; }
    public int getQuantidadeDeCorridas() { return quantidadeDeCorridas; }

    // Encapsulamento apenas aparente: aceita até quantidade negativa.
    public void setQuantidadeDeCorridas(int valor) { quantidadeDeCorridas = valor; }

    @Override
    public String exibirResumo() {
        return nome + " - " + categoria + " - " + quantidadeDeCorridas + " corridas";
    }
}
