public class Parada {
    private int ordem;
    private Localizacao local;
    private String observacao;

    public Parada(int ordem, Localizacao local, String observacao) {
        this.ordem = ordem; this.local = local; this.observacao = observacao;
    }
    public int getOrdem() { return ordem; }
    public Localizacao getLocal() { return local; }
    public String getObservacao() { return observacao; }
}
