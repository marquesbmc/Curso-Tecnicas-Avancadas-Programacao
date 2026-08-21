import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class TestesUnitarios {
    private static int executados;
    public static void main(String[] args) {
        testar("herança e sobrescrita", TestesUnitarios::herancaPessoa);
        testar("polimorfismo das categorias", TestesUnitarios::polimorfismoCategorias);
        testar("polimorfismo dos pagamentos", TestesUnitarios::polimorfismoPagamentos);
        testar("estado inicial da corrida", TestesUnitarios::estadoInicial);
        testar("tarifa normal sem ajustes", TestesUnitarios::tarifaNormal);
        testar("horário de pico isolado", TestesUnitarios::horarioDePico);
        testar("desconto de passageiro frequente", TestesUnitarios::descontoFrequente);
        testar("limite do desconto frequente", TestesUnitarios::limiteDescontoFrequente);
        testar("cupom isolado", TestesUnitarios::cupomIsolado);
        testar("cupom desconhecido não altera tarifa", TestesUnitarios::cupomDesconhecido);
        testar("tarifa dinâmica e descontos", TestesUnitarios::tarifaDinamicaEDescontos);
        testar("preço mínimo", TestesUnitarios::precoMinimo);
        testar("estimativa e valor final equivalentes", TestesUnitarios::estimativaEValorFinalEquivalentes);
        testar("iniciar altera estados", TestesUnitarios::iniciarAlteraEstado);
        testar("mensagem de início mantém dados públicos", TestesUnitarios::mensagemInicio);
        testar("impede iniciar duas vezes", TestesUnitarios::impedeIniciarDuasVezes);
        testar("finalizar processa pagamento", TestesUnitarios::finalizarProcessaPagamento);
        testar("pagamento ocorre somente ao finalizar", TestesUnitarios::pagamentoSomenteAoFinalizar);
        testar("impede finalizar antes de iniciar", TestesUnitarios::impedeFinalizarAntesDeIniciar);
        testar("impede motorista indisponível", TestesUnitarios::impedeIndisponivel);
        testar("rejeita dados obrigatórios inválidos", TestesUnitarios::rejeitaDadosInvalidos);
        testar("cancela corrida solicitada", TestesUnitarios::cancelaSolicitada);
        testar("cancela corrida em andamento", TestesUnitarios::cancelaEmAndamento);
        testar("impede cancelar corrida finalizada", TestesUnitarios::impedeCancelarFinalizada);
        testar("trajeto aceita zero paradas", TestesUnitarios::trajetoSemParadas);
        testar("composição e múltiplas paradas", TestesUnitarios::multiplasParadasNoRecibo);
        testar("recibo mantém comportamento", TestesUnitarios::reciboMantemComportamento);
        testar("notificação mantém destinatários e estado", TestesUnitarios::notificacaoMantemComportamento);
        System.out.println("\nOK - " + executados + " testes passaram.");
    }
    private static void herancaPessoa() {
        Pessoa p = new Passageiro("Marina", "9999", "FREQUENTE", 12);
        Pessoa m = new Motorista("Carlos", "8888", 4.9, new Veiculo("City", "RIO2A34", "prata"));
        contem(p.exibirResumo(), "FREQUENTE"); contem(m.exibirResumo(), "nota 4.9");
    }
    private static void polimorfismoCategorias() {
        CategoriaCorrida a = new CategoriaEconomica(), b = new CategoriaConforto(), c = new CategoriaMoto();
        igual(39.0, a.calcular(10, 20)); igual(57.0, b.calcular(10, 20)); igual(24.0, c.calcular(10, 20));
    }
    private static void polimorfismoPagamentos() {
        FormaPagamento a = new PagamentoPix("chave@pix");
        FormaPagamento b = new PagamentoCartao("5555444433331111", "123");
        FormaPagamento c = new PagamentoDinheiro(true, 50);
        FormaPagamento d = new PagamentoDinheiro(false, 0);
        igual("PIX de R$ 20.00 enviado para chave@pix", a.pagar(20));
        igual("CARTAO final 1111 cobrado em R$ 20.00", b.pagar(20));
        igual("DINHEIRO: troco para R$ 50.00", c.pagar(20));
        igual("DINHEIRO sem troco", d.pagar(20));
    }
    private static void estadoInicial() {
        CenarioCorrida dados = cenarioPadrao();
        Corrida c = dados.corrida;
        igual("SOLICITADA", c.getStatus());
        igual(0.0, c.getValorFinal());
        verdadeiro(dados.motorista.isDisponivel());
        verdadeiro(c.validarTudo());
        nulo(c.getResultadoPagamento());
    }
    private static void tarifaNormal() {
        Corrida c = corrida("NORMAL", "COMUM", 0, 10, 20, new CategoriaEconomica(), new PagamentoPix("x"));
        igual(39.0, c.calcularEstimativa());
        igual(39.0, c.calcularValorFinal());
    }
    private static void horarioDePico() {
        Corrida c = corrida("PICO", "COMUM", 0, 10, 20, new CategoriaEconomica(), new PagamentoPix("x"));
        igual(48.75, c.calcularEstimativa());
    }
    private static void descontoFrequente() {
        Corrida c = corrida("NORMAL", "FREQUENTE", 10, 10, 20, new CategoriaEconomica(), new PagamentoPix("x"));
        igual(35.10, c.calcularEstimativa());
    }
    private static void limiteDescontoFrequente() {
        Corrida c = corrida("NORMAL", "FREQUENTE", 9, 10, 20, new CategoriaEconomica(), new PagamentoPix("x"));
        igual(39.0, c.calcularEstimativa());
    }
    private static void cupomIsolado() {
        Corrida c = corrida("NORMAL", "COMUM", 0, 10, 20, new CategoriaEconomica(), new PagamentoPix("x"));
        c.setCupom("PRIMEIRA10");
        igual(29.0, c.calcularEstimativa());
    }
    private static void cupomDesconhecido() {
        Corrida c = corrida("NORMAL", "COMUM", 0, 10, 20, new CategoriaEconomica(), new PagamentoPix("x"));
        c.setCupom("OUTRO_CUPOM");
        igual(39.0, c.calcularEstimativa());
    }
    private static void tarifaDinamicaEDescontos() {
        Corrida c = corrida("PICO", "FREQUENTE", 12, 14.2, 38, new CategoriaEconomica(), new PagamentoPix("x"));
        c.setCupom("PRIMEIRA10"); igual(54.33, c.calcularEstimativa()); igual(54.33, c.calcularValorFinal());
    }
    private static void precoMinimo() {
        Corrida c = corrida("NORMAL", "FREQUENTE", 15, 0.5, 1, new CategoriaMoto(), new PagamentoPix("x"));
        c.setCupom("PRIMEIRA10");
        igual(8.0, c.calcularEstimativa());
        igual(8.0, c.calcularValorFinal());
    }
    private static void estimativaEValorFinalEquivalentes() {
        Corrida normal = corrida("NORMAL", "COMUM", 0, 7.3, 16, new CategoriaMoto(), new PagamentoPix("x"));
        Corrida pico = corrida("PICO", "COMUM", 0, 7.3, 16, new CategoriaConforto(), new PagamentoPix("x"));
        Corrida descontos = corridaPadrao();
        igual(normal.calcularEstimativa(), normal.calcularValorFinal());
        igual(pico.calcularEstimativa(), pico.calcularValorFinal());
        igual(descontos.calcularEstimativa(), descontos.calcularValorFinal());
    }
    private static void iniciarAlteraEstado() {
        CenarioCorrida dados = cenarioPadrao();
        String msg = dados.corrida.iniciar();
        igual("EM_ANDAMENTO", dados.corrida.getStatus());
        falso(dados.motorista.isDisponivel());
        contem(msg, "placa RIO2A34");
    }
    private static void mensagemInicio() {
        Corrida c = corridaPadrao();
        String mensagem = c.iniciar();
        contem(mensagem, "Corrida COR-1042 iniciada");
        contem(mensagem, "Carlos Souza chegará em um Honda City prata");
        contem(mensagem, "placa RIO2A34");
        contem(mensagem, "Estimativa R$ 54.33");
    }
    private static void impedeIniciarDuasVezes() {
        Corrida c = corridaPadrao();
        c.iniciar();
        lanca(IllegalStateException.class, c::iniciar);
        igual("EM_ANDAMENTO", c.getStatus());
    }
    private static void finalizarProcessaPagamento() {
        CenarioCorrida dados = cenarioPadrao();
        int antes = dados.passageiro.getQuantidadeDeCorridas();
        dados.corrida.iniciar();
        dados.corrida.finalizar();
        igual("FINALIZADA", dados.corrida.getStatus());
        verdadeiro(dados.motorista.isDisponivel());
        igual(antes + 1, dados.passageiro.getQuantidadeDeCorridas());
        contem(dados.corrida.getResultadoPagamento(), "PIX");
    }
    private static void pagamentoSomenteAoFinalizar() {
        PagamentoEspiao pagamento = new PagamentoEspiao();
        Corrida c = corrida("NORMAL", "COMUM", 0, 10, 20, new CategoriaEconomica(), pagamento);
        c.iniciar();
        igual(0, pagamento.chamadas);
        nulo(c.getResultadoPagamento());

        c.finalizar();
        igual(1, pagamento.chamadas);
        igual(39.0, pagamento.ultimoValor);
        igual("PAGAMENTO_CONFIRMADO", c.getResultadoPagamento());
    }
    private static void impedeFinalizarAntesDeIniciar() {
        CenarioCorrida dados = cenarioPadrao();
        lanca(IllegalStateException.class, dados.corrida::finalizar);
        igual("SOLICITADA", dados.corrida.getStatus());
        verdadeiro(dados.motorista.isDisponivel());
        nulo(dados.corrida.getResultadoPagamento());
    }
    private static void impedeIndisponivel() {
        CenarioCorrida dados = cenarioPadrao();
        dados.corrida.iniciar();
        Corrida segundaCorrida = novaCorridaComMotorista("COR-2042", dados.motorista);

        lanca(IllegalStateException.class, segundaCorrida::iniciar);
        igual("SOLICITADA", segundaCorrida.getStatus());
        igual(0.0, segundaCorrida.getValorFinal());
    }
    private static void rejeitaDadosInvalidos() {
        Corrida semPagamento = corrida("NORMAL", "COMUM", 0, 10, 20, new CategoriaEconomica(), null);
        Corrida semDistancia = corrida("NORMAL", "COMUM", 0, 0, 20, new CategoriaEconomica(), new PagamentoPix("x"));
        Corrida semDuracao = corrida("NORMAL", "COMUM", 0, 10, 0, new CategoriaEconomica(), new PagamentoPix("x"));

        falso(semPagamento.validarTudo());
        falso(semDistancia.validarTudo());
        falso(semDuracao.validarTudo());
        lanca(IllegalArgumentException.class, semPagamento::iniciar);
        lanca(IllegalArgumentException.class, semDistancia::iniciar);
        lanca(IllegalArgumentException.class, semDuracao::iniciar);
    }
    private static void cancelaSolicitada() {
        CenarioCorrida dados = cenarioPadrao();
        dados.corrida.cancelar("mudança de planos");
        igual("CANCELADA: mudança de planos", dados.corrida.getStatus());
        verdadeiro(dados.motorista.isDisponivel());
        nulo(dados.corrida.getResultadoPagamento());
    }
    private static void cancelaEmAndamento() {
        PagamentoEspiao pagamento = new PagamentoEspiao();
        CenarioCorrida dados = cenario("NORMAL", "COMUM", 3, 10, 20, new CategoriaEconomica(), pagamento);
        dados.corrida.iniciar();
        dados.corrida.cancelar("problema no trajeto");
        igual("CANCELADA: problema no trajeto", dados.corrida.getStatus());
        verdadeiro(dados.motorista.isDisponivel());
        igual(3, dados.passageiro.getQuantidadeDeCorridas());
        igual(0, pagamento.chamadas);
        nulo(dados.corrida.getResultadoPagamento());
    }
    private static void impedeCancelarFinalizada() {
        Corrida c = corridaPadrao();
        c.iniciar();
        c.finalizar();
        lanca(IllegalStateException.class, () -> c.cancelar("tarde demais"));
        igual("FINALIZADA", c.getStatus());
    }
    private static void trajetoSemParadas() {
        Corrida c = corridaPadrao();
        c.iniciar();
        c.finalizar();
        contem(c.gerarReciboCompleto(), "Av. Rio Branco - Centro -> Rua Dias Ferreira - Leblon");
    }
    private static void multiplasParadasNoRecibo() {
        Corrida c = corridaPadrao();
        c.adicionarParada(new Parada(1, new Localizacao("Praia de Botafogo", "Botafogo", 0, 0), "Buscar pasta"));
        c.adicionarParada(new Parada(2, new Localizacao("Rua Voluntários", "Botafogo", 0, 0), "Buscar passageiro"));
        c.iniciar(); c.finalizar();
        String recibo = c.gerarReciboCompleto();
        contem(recibo, "Praia de Botafogo - Botafogo");
        contem(recibo, "Rua Voluntários - Botafogo");
        antes(recibo, "Praia de Botafogo", "Rua Voluntários");
    }
    private static void reciboMantemComportamento() {
        Corrida c = corrida("NORMAL", "COMUM", 0, 10, 20, new CategoriaEconomica(), new PagamentoCartao("5555444433331111", "123"));
        c.iniciar(); c.finalizar(); String r = c.gerarReciboCompleto();
        contem(r, "=== RECIBO DA CORRIDA ==="); contem(r, "Código: COR-1042");
        contem(r, "Passageiro: Marina Costa"); contem(r, "Categoria: ECONOMICA");
        contem(r, "Av. Rio Branco - Centro -> Rua Dias Ferreira - Leblon"); contem(r, "Status: FINALIZADA"); contem(r, "Total: R$ 39.00");
        contem(r, "Motorista: Carlos Souza - avaliação 4.9");
        contem(r, "Veículo: Honda City / RIO2A34");
        contem(r, "Distância: 10.0 km / 20 min");
        contem(r, "Pagamento: CARTAO final 1111 cobrado em R$ 39.00");
    }
    private static void notificacaoMantemComportamento() {
        Corrida c = corridaPadrao();
        c.iniciar();
        c.finalizar();

        PrintStream saidaOriginal = System.out;
        ByteArrayOutputStream conteudo = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(conteudo, true, StandardCharsets.UTF_8));
            c.notificarTodoMundo();
        } finally {
            System.setOut(saidaOriginal);
        }

        igual("SMS para 99999-1111 e 98888-2222: corrida FINALIZADA",
                conteudo.toString(StandardCharsets.UTF_8).strip());
    }
    private static Corrida corridaPadrao() {
        return cenarioPadrao().corrida;
    }
    private static CenarioCorrida cenarioPadrao() {
        CenarioCorrida dados = cenario("PICO", "FREQUENTE", 12, 14.2, 38,
                new CategoriaEconomica(), new PagamentoPix("mobilidade@pix"));
        dados.corrida.setCupom("PRIMEIRA10");
        return dados;
    }
    private static Corrida corrida(String horario, String catPassageiro, int qtd, double km, int min, CategoriaCorrida categoria, FormaPagamento pagamento) {
        return cenario(horario, catPassageiro, qtd, km, min, categoria, pagamento).corrida;
    }
    private static CenarioCorrida cenario(String horario, String catPassageiro, int qtd, double km, int min,
                                           CategoriaCorrida categoria, FormaPagamento pagamento) {
        Passageiro passageiro = new Passageiro("Marina Costa", "99999-1111", catPassageiro, qtd);
        Motorista motorista = new Motorista("Carlos Souza", "98888-2222", 4.9,
                new Veiculo("Honda City", "RIO2A34", "prata"));
        Corrida corrida = new Corrida("COR-1042", passageiro, motorista,
                new Localizacao("Av. Rio Branco", "Centro", 0, 0), new Localizacao("Rua Dias Ferreira", "Leblon", 0, 0),
                km, min, horario, pagamento, categoria);
        return new CenarioCorrida(corrida, passageiro, motorista);
    }
    private static Corrida novaCorridaComMotorista(String codigo, Motorista motorista) {
        return new Corrida(codigo, new Passageiro("João Lima", "97777-3333", "COMUM", 0), motorista,
                new Localizacao("Rua A", "Centro", 0, 0), new Localizacao("Rua B", "Zona Sul", 0, 0),
                5, 10, "NORMAL", new PagamentoPix("x"), new CategoriaEconomica());
    }
    private static void testar(String nome, Runnable teste) { try { teste.run(); executados++; System.out.println("[PASSOU] " + nome); } catch (Throwable e) { throw new AssertionError("Falhou: " + nome, e); } }
    private static void igual(double e, double a) { if (Math.abs(e-a) > 0.001) throw new AssertionError("Esperado " + e + ", obtido " + a); }
    private static void igual(int e, int a) { if (e != a) throw new AssertionError("Esperado " + e + ", obtido " + a); }
    private static void igual(String e, String a) { if (!e.equals(a)) throw new AssertionError("Esperado " + e + ", obtido " + a); }
    private static void contem(String texto, String trecho) { if (!texto.contains(trecho)) throw new AssertionError("Trecho ausente: " + trecho); }
    private static void antes(String texto, String primeiro, String segundo) {
        int posicaoPrimeiro = texto.indexOf(primeiro), posicaoSegundo = texto.indexOf(segundo);
        if (posicaoPrimeiro < 0 || posicaoSegundo < 0 || posicaoPrimeiro >= posicaoSegundo)
            throw new AssertionError("Ordem esperada: " + primeiro + " antes de " + segundo);
    }
    private static void nulo(Object valor) { if (valor != null) throw new AssertionError("Esperado nulo, obtido " + valor); }
    private static void verdadeiro(boolean v) { if (!v) throw new AssertionError("Esperado verdadeiro"); }
    private static void falso(boolean v) { if (v) throw new AssertionError("Esperado falso"); }
    private static void lanca(Class<? extends Throwable> tipo, Runnable acao) { try { acao.run(); } catch (Throwable e) { if (tipo.isInstance(e)) return; throw new AssertionError("Exceção incorreta", e); } throw new AssertionError("Exceção não lançada"); }

    private static class CenarioCorrida {
        private final Corrida corrida;
        private final Passageiro passageiro;
        private final Motorista motorista;

        private CenarioCorrida(Corrida corrida, Passageiro passageiro, Motorista motorista) {
            this.corrida = corrida;
            this.passageiro = passageiro;
            this.motorista = motorista;
        }
    }

    private static class PagamentoEspiao implements FormaPagamento {
        private int chamadas;
        private double ultimoValor;

        @Override
        public String pagar(double valor) {
            chamadas++;
            ultimoValor = valor;
            return "PAGAMENTO_CONFIRMADO";
        }
    }
}
