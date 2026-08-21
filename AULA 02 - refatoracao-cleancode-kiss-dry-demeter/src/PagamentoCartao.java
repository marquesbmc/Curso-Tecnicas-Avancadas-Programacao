import java.util.Locale;

public class PagamentoCartao implements FormaPagamento {
    private String numero;
    private String cvv;
    public PagamentoCartao(String numero, String cvv) { this.numero = numero; this.cvv = cvv; }
    @Override public String pagar(double valor) { return "CARTAO final " + numero.substring(numero.length() - 4) + " cobrado em R$ " + String.format(Locale.US, "%.2f", valor); }
}
