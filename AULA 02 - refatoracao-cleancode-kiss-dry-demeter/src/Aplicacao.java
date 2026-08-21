import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Aplicacao {
    private static final Locale PORTUGUES_BRASIL = Locale.forLanguageTag("pt-BR");
    private static final int MAXIMO_DE_PARADAS = 5;

    private final Scanner entrada;

    public Aplicacao(Scanner entrada) {
        this.entrada = entrada;
    }

    public static void main(String[] args) {
        Aplicacao aplicacao = new Aplicacao(new Scanner(System.in));
        aplicacao.executar();
    }

    public void executar() {
        exibirCabecalho();

        try {
            boolean continuar = true;
            while (continuar) {
                exibirMenuPrincipal();
                int opcao = lerInteiroEntre("Escolha uma opção: ", 0, 2);

                switch (opcao) {
                    case 1 -> solicitarCorrida();
                    case 2 -> exibirInformacoes();
                    case 0 -> continuar = false;
                    default -> throw new IllegalStateException("Opção não tratada: " + opcao);
                }
            }
        } catch (EntradaEncerradaException e) {
            System.out.println("\nEntrada encerrada.");
        }

        System.out.println("Obrigado por usar o Mobilidade Urbana!");
    }

    private void solicitarCorrida() {
        exibirTitulo("NOVA CORRIDA");

        Passageiro passageiro = cadastrarPassageiro();
        Localizacao origem = lerLocalizacao("ORIGEM");
        Localizacao destino = lerLocalizacao("DESTINO");
        double distanciaKm = lerDoublePositivo("Distância estimada (km): ");
        int duracaoMinutos = lerInteiroPositivo("Duração estimada (minutos): ");

        CategoriaCorrida categoria = escolherCategoria();
        String horario = escolherHorario();
        FormaPagamento pagamento = escolherPagamento();
        Motorista motorista = criarMotorista(categoria);

        Corrida corrida = new Corrida(
                gerarCodigo(),
                passageiro,
                motorista,
                origem,
                destino,
                distanciaKm,
                duracaoMinutos,
                horario,
                pagamento,
                categoria
        );

        adicionarCupom(corrida);
        adicionarParadas(corrida);
        exibirResumoDaSolicitacao(corrida);

        if (!lerSimNao("Confirmar a solicitação? (S/N): ")) {
            corrida.cancelar("Solicitação cancelada pelo passageiro");
            System.out.println("\nSolicitação cancelada. Nenhuma cobrança foi realizada.");
            return;
        }

        iniciarEConcluirCorrida(corrida);
    }

    private Passageiro cadastrarPassageiro() {
        System.out.println("\nDados do passageiro");
        String nome = lerTextoObrigatorio("Nome: ");
        String telefone = lerTextoObrigatorio("Telefone: ");
        int quantidadeDeCorridas = lerInteiroNaoNegativo("Quantidade de corridas já realizadas: ");
        String categoria = quantidadeDeCorridas >= 10 ? "FREQUENTE" : "COMUM";

        if (categoria.equals("FREQUENTE")) {
            System.out.println("Benefício identificado: passageiro frequente (10% de desconto).");
        }

        return new Passageiro(nome, telefone, categoria, quantidadeDeCorridas);
    }

    private Localizacao lerLocalizacao(String nome) {
        System.out.println("\n" + nome);
        String rua = lerTextoObrigatorio("Endereço: ");
        String bairro = lerTextoObrigatorio("Bairro: ");
        return new Localizacao(rua, bairro, 0, 0);
    }

    private CategoriaCorrida escolherCategoria() {
        System.out.println("\nCategoria da corrida");
        System.out.println("1 - Econômica (preço acessível)");
        System.out.println("2 - Conforto  (veículo premium)");
        System.out.println("3 - Moto      (rápida e econômica)");

        return switch (lerInteiroEntre("Categoria: ", 1, 3)) {
            case 1 -> new CategoriaEconomica();
            case 2 -> new CategoriaConforto();
            case 3 -> new CategoriaMoto();
            default -> throw new IllegalStateException("Categoria inválida");
        };
    }

    private String escolherHorario() {
        System.out.println("\nHorário da viagem");
        System.out.println("1 - Normal");
        System.out.println("2 - Pico (acréscimo de 25%)");
        return lerInteiroEntre("Horário: ", 1, 2) == 2 ? "PICO" : "NORMAL";
    }

    private FormaPagamento escolherPagamento() {
        System.out.println("\nForma de pagamento");
        System.out.println("1 - Pix");
        System.out.println("2 - Cartão");
        System.out.println("3 - Dinheiro");

        return switch (lerInteiroEntre("Pagamento: ", 1, 3)) {
            case 1 -> new PagamentoPix(lerTextoObrigatorio("Chave Pix de destino: "));
            case 2 -> criarPagamentoCartao();
            case 3 -> criarPagamentoDinheiro();
            default -> throw new IllegalStateException("Pagamento inválido");
        };
    }

    private FormaPagamento criarPagamentoCartao() {
        String numero;
        while (true) {
            numero = lerTextoObrigatorio("Número do cartão: ").replaceAll("[ .-]", "");
            if (numero.matches("\\d{4,19}")) {
                break;
            }
            System.out.println("Informe de 4 a 19 dígitos para o cartão.");
        }

        String cvv;
        while (true) {
            cvv = lerTextoObrigatorio("CVV: ");
            if (cvv.matches("\\d{3,4}")) {
                break;
            }
            System.out.println("O CVV deve conter 3 ou 4 dígitos.");
        }

        return new PagamentoCartao(numero, cvv);
    }

    private FormaPagamento criarPagamentoDinheiro() {
        boolean precisaTroco = lerSimNao("Precisa de troco? (S/N): ");
        double trocoPara = precisaTroco ? lerDoublePositivo("Troco para quanto? R$ ") : 0;
        return new PagamentoDinheiro(precisaTroco, trocoPara);
    }

    private void adicionarCupom(Corrida corrida) {
        if (lerSimNao("\nAplicar o cupom PRIMEIRA10? (S/N): ")) {
            corrida.setCupom("PRIMEIRA10");
            System.out.println("Cupom aplicado: R$ 10,00 de desconto.");
        }
    }

    private void adicionarParadas(Corrida corrida) {
        int quantidade = lerInteiroEntre(
                "Quantidade de paradas (0 a " + MAXIMO_DE_PARADAS + "): ",
                0,
                MAXIMO_DE_PARADAS
        );

        for (int ordem = 1; ordem <= quantidade; ordem++) {
            System.out.println("\nParada " + ordem);
            Localizacao local = lerLocalizacao("LOCAL DA PARADA");
            String observacao = lerTextoOpcional("Observação (opcional): ");
            corrida.adicionarParada(new Parada(ordem, local, observacao));
        }
    }

    private Motorista criarMotorista(CategoriaCorrida categoria) {
        Veiculo veiculo;
        if (categoria instanceof CategoriaMoto) {
            veiculo = new Veiculo("Honda CG 160", "MOB1A23", "vermelha");
        } else if (categoria instanceof CategoriaConforto) {
            veiculo = new Veiculo("Toyota Corolla", "MOB2B34", "preto");
        } else {
            veiculo = new Veiculo("Honda City", "MOB3C45", "prata");
        }
        return new Motorista("Carlos Souza", "(21) 98888-2222", 4.9, veiculo);
    }

    private void exibirResumoDaSolicitacao(Corrida corrida) {
        System.out.println("\n----------------------------------------");
        System.out.println("RESUMO DA SOLICITAÇÃO");
        System.out.println("Código: " + corrida.getCodigo());
        System.out.println("Passageiro: " + corrida.getPassageiro().getNome());
        System.out.println("Trajeto: " + formatarLocal(corrida.getOrigem()) + " -> " + formatarLocal(corrida.getDestino()));
        System.out.println("Distância e duração: " + corrida.getDistanciaKm() + " km / " + corrida.getDuracaoMinutos() + " min");
        System.out.println("Categoria: " + corrida.getCategoria().getNome());
        System.out.println("Motorista: " + corrida.getMotorista().getNome() + " (nota " + corrida.getMotorista().getAvaliacao() + ")");
        System.out.println("Veículo: " + corrida.getMotorista().getVeiculo().getModelo());
        System.out.println("Paradas: " + corrida.getParadas().size());
        System.out.println("Valor estimado: " + formatarMoeda(corrida.calcularEstimativa()));
        System.out.println("----------------------------------------");
    }

    private void iniciarEConcluirCorrida(Corrida corrida) {
        try {
            System.out.println("\n" + corrida.iniciar());
            System.out.println("Status atual: " + corrida.getStatus());
            System.out.println("\n1 - Finalizar corrida");
            System.out.println("2 - Cancelar corrida");
            int acao = lerInteiroEntre("Ação: ", 1, 2);

            if (acao == 2) {
                String motivo = lerTextoObrigatorio("Motivo do cancelamento: ");
                corrida.cancelar(motivo);
                System.out.println("Corrida cancelada. Status: " + corrida.getStatus());
                return;
            }

            corrida.finalizar();
            corrida.notificarTodoMundo();
            System.out.println("\n" + corrida.gerarReciboCompleto());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível processar a corrida: " + e.getMessage());
        }
    }

    private void exibirCabecalho() {
        System.out.println("========================================");
        System.out.println("       MOBILIDADE URBANA");
        System.out.println("       Sua viagem começa aqui");
        System.out.println("========================================");
    }

    private void exibirMenuPrincipal() {
        System.out.println("\nMENU PRINCIPAL");
        System.out.println("1 - Solicitar uma corrida");
        System.out.println("2 - Categorias e benefícios");
        System.out.println("0 - Sair");
    }

    private void exibirInformacoes() {
        exibirTitulo("CATEGORIAS E BENEFÍCIOS");
        System.out.println("Econômica: tarifa base de R$ 6,00.");
        System.out.println("Conforto: mais espaço e veículo premium; tarifa base de R$ 10,00.");
        System.out.println("Moto: opção ágil; tarifa base de R$ 4,00.");
        System.out.println("Passageiros com 10 ou mais corridas recebem 10% de desconto.");
        System.out.println("O cupom PRIMEIRA10 concede R$ 10,00 de desconto.");
        System.out.println("Em horário de pico há acréscimo de 25%. O preço mínimo é R$ 8,00.");
    }

    private void exibirTitulo(String titulo) {
        System.out.println("\n========================================");
        System.out.println(titulo);
        System.out.println("========================================");
    }

    private String gerarCodigo() {
        long parteNumerica = System.currentTimeMillis() % 100_000;
        return "COR-" + String.format(Locale.ROOT, "%05d", parteNumerica);
    }

    private String formatarLocal(Localizacao localizacao) {
        return localizacao.getRua() + " - " + localizacao.getBairro();
    }

    private String formatarMoeda(double valor) {
        return NumberFormat.getCurrencyInstance(PORTUGUES_BRASIL).format(valor);
    }

    private String lerTextoObrigatorio(String mensagem) {
        while (true) {
            String valor = lerLinha(mensagem).trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println("Este campo é obrigatório.");
        }
    }

    private String lerTextoOpcional(String mensagem) {
        return lerLinha(mensagem).trim();
    }

    private int lerInteiroPositivo(String mensagem) {
        while (true) {
            int valor = lerInteiro(mensagem);
            if (valor > 0) {
                return valor;
            }
            System.out.println("Informe um número inteiro maior que zero.");
        }
    }

    private int lerInteiroNaoNegativo(String mensagem) {
        while (true) {
            int valor = lerInteiro(mensagem);
            if (valor >= 0) {
                return valor;
            }
            System.out.println("Informe um número inteiro igual ou maior que zero.");
        }
    }

    private int lerInteiroEntre(String mensagem, int minimo, int maximo) {
        while (true) {
            int valor = lerInteiro(mensagem);
            if (valor >= minimo && valor <= maximo) {
                return valor;
            }
            System.out.println("Escolha um número entre " + minimo + " e " + maximo + ".");
        }
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            String valor = lerLinha(mensagem).trim();
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Informe um número inteiro válido.");
            }
        }
    }

    private double lerDoublePositivo(String mensagem) {
        while (true) {
            String valorInformado = lerLinha(mensagem).trim().replace(',', '.');
            try {
                double valor = Double.parseDouble(valorInformado);
                if (Double.isFinite(valor) && valor > 0) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // A mensagem comum de validação é exibida abaixo.
            }
            System.out.println("Informe um número maior que zero.");
        }
    }

    private boolean lerSimNao(String mensagem) {
        while (true) {
            String valor = lerLinha(mensagem).trim().toLowerCase(PORTUGUES_BRASIL);
            if (valor.equals("s") || valor.equals("sim")) {
                return true;
            }
            if (valor.equals("n") || valor.equals("nao") || valor.equals("não")) {
                return false;
            }
            System.out.println("Responda com S (sim) ou N (não).");
        }
    }

    private String lerLinha(String mensagem) {
        System.out.print(mensagem);
        if (!entrada.hasNextLine()) {
            throw new EntradaEncerradaException();
        }
        return entrada.nextLine();
    }

    private static class EntradaEncerradaException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
}
