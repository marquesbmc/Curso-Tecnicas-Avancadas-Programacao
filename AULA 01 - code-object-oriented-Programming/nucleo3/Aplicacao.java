package nucleo3;

import java.math.BigDecimal;

/**
 * Aplicação de demonstração do modelo completo da cafeteria.
 *
 * Cada seção funciona como um pequeno teste executável dos conceitos de POO
 * representados no diagrama UML consolidado. As mensagens impressas também
 * explicam o resultado, permitindo acompanhar o código durante a aula.
 */
public final class Aplicacao {

    private Aplicacao() {
        // Esta classe possui somente o ponto de entrada da aplicação.
        // Por isso, não precisamos criar objetos do tipo Aplicacao.
    }

    public static void main(String[] args) {
        titulo("MODELO ORIENTADO A OBJETOS DA CAFETERIA");

        testarClassesObjetosEstadoEComportamento();
        testarAssociacaoENavegabilidade();
        testarAgregacaoEMultiplicidade();
        testarComposicaoESobrecarga();
        testarEncapsulamentoEInvariantes();
        testarHerancaAbstracaoESobrescrita();
        testarInterfaceEPolimorfismo();
        apresentarResumoFinal();
    }

    /**
     * POO — CLASSES, OBJETOS, ESTADO E COMPORTAMENTO
     *
     * Uma classe define a estrutura e os comportamentos possíveis.
     * Cada execução de new cria um objeto independente, com estado próprio.
     */
    private static void testarClassesObjetosEstadoEComportamento() {
        secao(1, "CLASSES, OBJETOS, ESTADO E COMPORTAMENTO");

        // Dois objetos diferentes, criados a partir da mesma classe Cliente.
        var ana = new Cliente("Ana", "2199999-0001");
        var bruno = new Cliente("Bruno", "2199999-0002");

        resultado("Ana", ana.consultarNome() + " — " + ana.consultarTelefone());
        resultado("Bruno", bruno.consultarNome() + " — " + bruno.consultarTelefone());
        explicacao("Ana e Bruno são objetos diferentes e mantêm valores próprios.");

        // O método expressa um comportamento que altera somente o objeto Ana.
        ana.atualizarTelefone("2198888-1111");

        resultado("Telefone atualizado de Ana", ana.consultarTelefone());
        resultado("Telefone de Bruno permanece", bruno.consultarTelefone());
        aprovado("O comportamento executado por Ana alterou apenas o estado de Ana.");
    }

    /**
     * POO — ASSOCIAÇÃO E NAVEGABILIDADE
     *
     * Pedido mantém uma referência para Cliente. Os dados do cliente não são
     * copiados para o pedido; o pedido navega até o objeto relacionado.
     */
    private static void testarAssociacaoENavegabilidade() {
        secao(2, "ASSOCIAÇÃO E NAVEGABILIDADE");

        var cliente = new Cliente("Carla", "2197777-2000");

        // Multiplicidade da associação: uma cliente pode realizar vários pedidos.
        var pedido201 = new Pedido(201, cliente);
        var pedido202 = new Pedido(202, cliente);

        resultado("Cliente do pedido 201", pedido201.consultarCliente().consultarNome());
        resultado("Cliente do pedido 202", pedido202.consultarCliente().consultarNome());
        resultado(
                "Os pedidos apontam para o mesmo objeto Cliente",
                pedido201.consultarCliente() == pedido202.consultarCliente()
        );

        // A alteração acontece no objeto compartilhado e pode ser vista pelos pedidos.
        cliente.atualizarTelefone("2197777-2999");
        resultado(
                "Telefone consultado pelo pedido 201",
                pedido201.consultarCliente().consultarTelefone()
        );

        aprovado("Pedido acessa Cliente por referência, sem copiar seus dados.");
    }

    /**
     * POO — AGREGAÇÃO E MULTIPLICIDADE
     *
     * Cardapio organiza objetos Produto que existem independentemente dele.
     * O mesmo Produto pode participar de mais de um Cardapio.
     */
    private static void testarAgregacaoEMultiplicidade() {
        secao(3, "AGREGAÇÃO E MULTIPLICIDADE");

        var cappuccino = novaBebida("Cappuccino", "13.50", 200);
        var brownie = novoAlimento("Brownie", "9.00", 90);

        var cardapioManha = new Cardapio("Cardápio da manhã");
        var cardapioTarde = new Cardapio("Cardápio da tarde");

        // O mesmo objeto cappuccino é agregado aos dois cardápios.
        cardapioManha.adicionarProduto(cappuccino);
        cardapioManha.adicionarProduto(brownie);
        cardapioTarde.adicionarProduto(cappuccino);

        resultado("Produtos no cardápio da manhã", cardapioManha.consultarProdutos().size());
        resultado("Produtos no cardápio da tarde", cardapioTarde.consultarProdutos().size());

        // Remover a parte de um agregado não destrói o objeto Produto.
        cardapioManha.retirarProduto(cappuccino);

        resultado(
                "Cappuccino permanece no cardápio da tarde",
                cardapioTarde.consultarProdutos().contains(cappuccino)
        );
        resultado("O objeto Produto continua existindo", cappuccino.consultarNome());

        aprovado("Na agregação, Cardapio organiza Produtos, mas não controla sua existência.");
    }

    /**
     * POO — COMPOSIÇÃO, MULTIPLICIDADE E SOBRECARGA
     *
     * Pedido cria e controla seus ItemPedido. A multiplicidade 0..* permite que
     * o pedido comece vazio e receba diversos itens durante a montagem.
     */
    private static void testarComposicaoESobrecarga() {
        secao(4, "COMPOSIÇÃO, MULTIPLICIDADE E SOBRECARGA");

        var cliente = new Cliente("Diego", "2196666-3000");
        var cafe = novaBebida("Café expresso", "8.00", 50);
        var paoDeQueijo = novoAlimento("Pão de queijo", "6.00", 80);
        var pedido301 = new Pedido(301, cliente);

        // Multiplicidade: o pedido acabou de nascer com zero itens.
        resultado("Quantidade inicial de itens", pedido301.consultarItens().size());

        // POO — SOBRECARGA:
        // O Java escolhe o método pela lista de argumentos fornecida.
        pedido301.adicionarItem(cafe);             // adicionarItem(Produto)
        pedido301.adicionarItem(paoDeQueijo, 2);  // adicionarItem(Produto, int)

        System.out.println("  Itens criados e controlados pelo Pedido:");
        pedido301.consultarItens()
                .forEach(item -> System.out.println("    - " + item.gerarResumo()));

        resultado("Quantidade final de itens", pedido301.consultarItens().size());
        resultado("Total calculado", "R$ " + pedido301.calcularTotal().toPlainString());
        aprovado("As duas sobrecargas expressam a mesma intenção com entradas diferentes.");

        // Demonstra o ciclo de vida forte da composição em outro pedido.
        var pedido302 = new Pedido(302, cliente);
        pedido302.adicionarItem(cafe, 2);
        pedido302.descartar();

        resultado("Status do pedido 302", pedido302.consultarStatus());
        resultado("Itens após descartar o pedido 302", pedido302.consultarItens().size());
        resultado("Produto preservado fora do pedido", cafe.consultarNome());
        aprovado("Ao descartar o Pedido, seus itens deixam de existir; o Produto permanece.");
    }

    /**
     * POO — ENCAPSULAMENTO E INVARIANTES
     *
     * O objeto protege seu estado e rejeita operações que violariam suas regras.
     */
    private static void testarEncapsulamentoEInvariantes() {
        secao(5, "ENCAPSULAMENTO E PROTEÇÃO DOS INVARIANTES");

        var cliente = new Cliente("Elisa", "2195555-4000");
        var pedidoVazio = new Pedido(401, cliente);

        // REGRA: um pedido vazio não pode ser confirmado.
        esperarErro(
                "Confirmar pedido vazio",
                pedidoVazio::confirmar
        );

        var produtoIndisponivel = novaBebida("Café especial", "18.00", 150);
        produtoIndisponivel.suspenderVenda();

        // REGRA: um produto indisponível não pode entrar no pedido.
        esperarErro(
                "Adicionar produto indisponível",
                () -> pedidoVazio.adicionarItem(produtoIndisponivel)
        );

        var cha = novaBebida("Chá", "7.00", 180);

        // REGRA: a quantidade precisa ser positiva.
        esperarErro(
                "Adicionar quantidade igual a zero",
                () -> pedidoVazio.adicionarItem(cha, 0)
        );

        pedidoVazio.adicionarItem(cha);
        pedidoVazio.confirmar();
        resultado("Status após confirmação válida", pedidoVazio.consultarStatus());

        // REGRA: pedido confirmado não volta a aceitar itens.
        esperarErro(
                "Adicionar item depois da confirmação",
                () -> pedidoVazio.adicionarItem(cha)
        );

        // ENCAPSULAMENTO DA COLEÇÃO:
        // consultarItens() devolve uma visão imutável, não a lista interna.
        esperarErro(
                "Limpar externamente a coleção de itens",
                () -> pedidoVazio.consultarItens().clear()
        );

        resultado("Itens preservados pelo Pedido", pedidoVazio.consultarItens().size());
        aprovado("Somente Pedido controla suas mudanças e mantém o estado consistente.");
    }

    /**
     * POO — HERANÇA, ABSTRAÇÃO E SOBRESCRITA
     *
     * Produto representa o conceito geral. Bebida e Alimento especializam esse
     * conceito e fornecem implementações próprias para preparar().
     */
    private static void testarHerancaAbstracaoESobrescrita() {
        secao(6, "HERANÇA, ABSTRAÇÃO E SOBRESCRITA");

        // As variáveis possuem o tipo geral Produto.
        Produto bebidaComoProduto = novaBebida("Latte", "14.00", 250);
        Produto alimentoComoProduto = novoAlimento("Croissant", "10.00", 100);

        resultado("Tipo declarado das referências", "Produto");
        resultado("Classe concreta da primeira referência", bebidaComoProduto.getClass().getSimpleName());
        resultado("Classe concreta da segunda referência", alimentoComoProduto.getClass().getSimpleName());

        // SOBRESCRITA E DESPACHO DINÂMICO:
        // Embora a referência seja Produto, executa-se o método do objeto concreto.
        resultado("preparar() da Bebida", bebidaComoProduto.preparar());
        resultado("preparar() do Alimento", alimentoComoProduto.preparar());

        explicacao("Produto é abstrata: organiza o que é comum, mas não pode ser instanciada.");
        aprovado("Bebida e Alimento herdam de Produto e concretizam preparar().");
    }

    /**
     * POO — INTERFACE E POLIMORFISMO
     *
     * A fila trabalha somente com o tipo comum Preparavel. Ela não verifica se
     * recebeu Bebida, Alimento ou EncomendaEspecial.
     */
    private static void testarInterfaceEPolimorfismo() {
        secao(7, "INTERFACE E POLIMORFISMO");

        Preparavel bebida = novaBebida("Mocha", "15.00", 220);
        Preparavel alimento = novoAlimento("Sanduíche", "17.00", 180);
        Preparavel encomenda = new EncomendaEspecial(
                "Cesta com cafés, caneca e cartão de aniversário"
        );

        // Objetos sem uma única classe concreta comum ocupam o mesmo papel.
        var fila = new FilaPreparo();
        fila.adicionar(bebida);
        fila.adicionar(alimento);
        fila.adicionar(encomenda);

        resultado("Elementos recebidos pela fila", fila.consultarQuantidade());
        explicacao("A fila conhece somente Preparavel e solicita preparar() a todos.");
        System.out.println("  Respostas polimórficas:");

        // Cada objeto responde de acordo com sua classe concreta.
        fila.prepararTodos();

        resultado("Elementos após o preparo", fila.consultarQuantidade());
        aprovado("A mesma solicitação produziu comportamentos diferentes em tempo de execução.");
    }

    /**
     * Mostra ao final quais assuntos foram exercitados pela aplicação.
     */
    private static void apresentarResumoFinal() {
        titulo("RESUMO DOS CONCEITOS TESTADOS");

        System.out.println("  1. Classes e objetos");
        System.out.println("  2. Estado e comportamento");
        System.out.println("  3. Encapsulamento e invariantes");
        System.out.println("  4. Associação e navegabilidade");
        System.out.println("  5. Multiplicidade");
        System.out.println("  6. Composição");
        System.out.println("  7. Agregação");
        System.out.println("  8. Generalização e herança");
        System.out.println("  9. Classe e método abstratos");
        System.out.println(" 10. Interface e realização");
        System.out.println(" 11. Sobrecarga e sobrescrita");
        System.out.println(" 12. Polimorfismo");
        System.out.println();
        System.out.println("[CONCLUSÃO] Todos os testes previstos foram executados.");
    }

    // ---------------------------------------------------------------------
    // MÉTODOS AUXILIARES DE CRIAÇÃO
    // Centralizam detalhes repetitivos para manter os testes mais legíveis.
    // ---------------------------------------------------------------------

    private static Bebida novaBebida(String nome, String preco, int volumeEmMl) {
        return new Bebida(nome, new BigDecimal(preco), volumeEmMl);
    }

    private static Alimento novoAlimento(String nome, String preco, int pesoEmGramas) {
        return new Alimento(nome, new BigDecimal(preco), pesoEmGramas);
    }

    // ---------------------------------------------------------------------
    // MÉTODOS AUXILIARES DE APRESENTAÇÃO
    // Deixam a saída organizada e explicativa para acompanhamento em aula.
    // ---------------------------------------------------------------------

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("============================================================");
        System.out.println(texto);
        System.out.println("============================================================");
    }

    private static void secao(int numero, String texto) {
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.printf("TESTE %d — %s%n", numero, texto);
        System.out.println("------------------------------------------------------------");
    }

    private static void resultado(String descricao, Object valor) {
        System.out.printf("  [RESULTADO] %-43s : %s%n", descricao, valor);
    }

    private static void explicacao(String texto) {
        System.out.println("  [EXPLICAÇÃO] " + texto);
    }

    private static void aprovado(String texto) {
        System.out.println("  [APROVADO] " + texto);
    }

    /**
     * Executa uma operação que deve ser rejeitada por uma regra do domínio.
     * Se uma exceção for lançada, a saída explica que a proteção funcionou.
     */
    private static void esperarErro(String cenario, Runnable operacao) {
        try {
            operacao.run();
            System.out.println("  [FALHA NO TESTE] " + cenario + " foi aceito indevidamente.");
        } catch (RuntimeException erro) {
            var motivo = erro.getMessage();
            if (motivo == null || motivo.isBlank()) {
                motivo = erro.getClass().getSimpleName()
                        + " — a operação solicitada não é permitida";
            }

            System.out.println("  [REGRA PROTEGIDA] " + cenario);
            System.out.println("                     Motivo: " + motivo);
        }
    }
}
