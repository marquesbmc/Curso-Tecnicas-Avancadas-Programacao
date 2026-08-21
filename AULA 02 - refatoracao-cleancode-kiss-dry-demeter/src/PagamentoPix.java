import java.util.Locale;

public class PagamentoPix implements FormaPagamento {
    private String chave;
    public PagamentoPix(String chave) { this.chave = chave; }
    @Override public String pagar(double valor) { return "PIX de R$ " + String.format(Locale.US, "%.2f", valor) + " enviado para " + chave; }
}
