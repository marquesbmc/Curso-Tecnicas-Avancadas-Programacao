public class Motorista extends Pessoa {
    private double avaliacao;
    private Veiculo veiculo;
    private boolean disponivel;

    public Motorista(String nome, String telefone, double avaliacao, Veiculo veiculo) {
        super(nome, telefone);
        this.avaliacao = avaliacao; this.veiculo = veiculo; this.disponivel = true;
    }
    public double getAvaliacao() { return avaliacao; }
    public Veiculo getVeiculo() { return veiculo; }
    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    @Override
    public String exibirResumo() {
        return nome + " - nota " + avaliacao + " - " + veiculo.getModelo();
    }
}
