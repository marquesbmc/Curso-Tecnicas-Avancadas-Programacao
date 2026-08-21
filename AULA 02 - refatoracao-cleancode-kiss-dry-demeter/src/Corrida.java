import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Corrida {
    private String codigo;
    private Passageiro passageiro;
    private Motorista motorista;
    private Localizacao origem;
    private Localizacao destino;
    private double distanciaKm;
    private int duracaoMinutos;
    private String horario;
    private String cupom;
    private String status = "SOLICITADA";
    private double valorFinal;
    private List<Parada> paradas = new ArrayList<>();
    private FormaPagamento pagamento;
    private CategoriaCorrida categoria;
    private CalculadoraUniversal x = new CalculadoraUniversal();
    private String resultadoPagamento;

    public Corrida(String codigo, Passageiro passageiro, Motorista motorista, Localizacao origem,
                   Localizacao destino, double distanciaKm, int duracaoMinutos, String horario,
                   FormaPagamento pagamento, CategoriaCorrida categoria) {
        this.codigo = codigo; this.passageiro = passageiro; this.motorista = motorista;
        this.origem = origem; this.destino = destino; this.distanciaKm = distanciaKm;
        this.duracaoMinutos = duracaoMinutos; this.horario = horario;
        this.pagamento = pagamento; this.categoria = categoria;
    }
    public String getCodigo() { return codigo; }
    public Passageiro getPassageiro() { return passageiro; }
    public Motorista getMotorista() { return motorista; }
    public Localizacao getOrigem() { return origem; }
    public Localizacao getDestino() { return destino; }
    public double getDistanciaKm() { return distanciaKm; }
    public int getDuracaoMinutos() { return duracaoMinutos; }
    public String getHorario() { return horario; }
    public String getCupom() { return cupom; }
    public void setCupom(String cupom) { this.cupom = cupom; }
    public FormaPagamento getPagamento() { return pagamento; }
    public CategoriaCorrida getCategoria() { return categoria; }
    public List<Parada> getParadas() { return paradas; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getValorFinal() { return valorFinal; }
    public void setValorFinal(double valorFinal) { this.valorFinal = valorFinal; }
    public String getResultadoPagamento() { return resultadoPagamento; }

    public void adicionarParada(Parada p) { paradas.add(p); }

    public double calcularEstimativa() {
        double v = categoria.calcular(distanciaKm, duracaoMinutos);
        if (horario.equals("PICO")) v = x.processar(v, "PERCENTUAL_MAIS", 25, false);
        if (passageiro.getCategoria().equals("FREQUENTE") && passageiro.getQuantidadeDeCorridas() >= 10) v = x.processar(v, "PERCENTUAL_MENOS", 10, false);
        if (cupom != null && cupom.equals("PRIMEIRA10")) v = x.processar(v, "SUBTRAIR", 10, false);
        if (v < 8) v = 8;
        return Math.round(v * 100.0) / 100.0;
    }

    public double calcularValorFinal() {
        // DRY proposital: a mesma regra acima foi copiada para cá.
        double v = categoria.calcular(distanciaKm, duracaoMinutos);
        if (horario.equals("PICO")) v = x.processar(v, "PERCENTUAL_MAIS", 25, false);
        if (passageiro.getCategoria().equals("FREQUENTE") && passageiro.getQuantidadeDeCorridas() >= 10) v = x.processar(v, "PERCENTUAL_MENOS", 10, false);
        if (cupom != null && cupom.equals("PRIMEIRA10")) v = x.processar(v, "SUBTRAIR", 10, false);
        if (v < 8) v = 8;
        return Math.round(v * 100.0) / 100.0;
    }

    public boolean validarTudo() {
        return passageiro != null && motorista != null && origem != null && destino != null && pagamento != null && categoria != null && distanciaKm > 0 && duracaoMinutos > 0;
    }

    public String iniciar() {
        if (!validarTudo()) throw new IllegalArgumentException("Dados inválidos");
        if (!status.equals("SOLICITADA")) throw new IllegalStateException("Corrida não está solicitada");
        if (!motorista.isDisponivel()) throw new IllegalStateException("Motorista indisponível");
        valorFinal = calcularValorFinal(); status = "EM_ANDAMENTO"; motorista.setDisponivel(false);
        return "Corrida " + codigo + " iniciada. " + motorista.getNome() + " chegará em um " + motorista.getVeiculo().getModelo() + " " + motorista.getVeiculo().getCor() + ", placa " + motorista.getVeiculo().getPlaca() + ". Estimativa R$ " + String.format(Locale.US, "%.2f", valorFinal);
    }

    public void finalizar() {
        if (!status.equals("EM_ANDAMENTO")) throw new IllegalStateException("Corrida não está em andamento");
        status = "FINALIZADA"; motorista.setDisponivel(true); passageiro.setQuantidadeDeCorridas(passageiro.getQuantidadeDeCorridas() + 1);
        resultadoPagamento = pagamento.pagar(valorFinal);
    }

    public void cancelar(String motivo) {
        if (status.equals("FINALIZADA")) throw new IllegalStateException("Corrida já finalizada");
        status = "CANCELADA: " + motivo; motorista.setDisponivel(true);
    }

    public void notificarTodoMundo() {
        System.out.println("SMS para " + passageiro.getTelefone() + " e " + motorista.getTelefone() + ": corrida " + status);
    }

    public String gerarReciboCompleto() {
        String s = "=== RECIBO DA CORRIDA ===\n";
        s += "Código: " + codigo + "\nPassageiro: " + passageiro.getNome() + " / " + passageiro.getTelefone() + "\n";
        s += "Motorista: " + motorista.getNome() + " - avaliação " + motorista.getAvaliacao() + "\n";
        s += "Veículo: " + motorista.getVeiculo().getModelo() + " / " + motorista.getVeiculo().getPlaca() + "\n";
        s += "Categoria: " + categoria.getNome() + "\nTrajeto: " + origem.getRua() + " - " + origem.getBairro();
        paradas.stream().sorted(Comparator.comparingInt(Parada::getOrdem)).forEach(p -> { });
        for (Parada p : paradas) s += " -> " + p.getLocal().getRua() + " - " + p.getLocal().getBairro();
        s += " -> " + destino.getRua() + " - " + destino.getBairro() + "\n";
        s += "Distância: " + distanciaKm + " km / " + duracaoMinutos + " min\nStatus: " + status + "\n";
        s += "Pagamento: " + resultadoPagamento + "\nTotal: R$ " + String.format(Locale.US, "%.2f", valorFinal) + "\n";
        return s;
    }
}
