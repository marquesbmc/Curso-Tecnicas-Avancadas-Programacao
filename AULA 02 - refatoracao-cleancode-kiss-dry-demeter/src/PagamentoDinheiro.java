import java.util.Locale;

public class PagamentoDinheiro implements FormaPagamento {
    private boolean precisaTroco;
    private double trocoPara;
    public PagamentoDinheiro(boolean precisaTroco, double trocoPara) { this.precisaTroco = precisaTroco; this.trocoPara = trocoPara; }
    @Override public String pagar(double valor) { return precisaTroco ? "DINHEIRO: troco para R$ " + String.format(Locale.US, "%.2f", trocoPara) : "DINHEIRO sem troco"; }
}
