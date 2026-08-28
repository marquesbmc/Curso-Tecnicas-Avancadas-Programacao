# SOLID aplicado ao backend de um jogo RTS

<div style="background:#0A2240;color:#FFFFFF;padding:18px 22px;border-left:8px solid #E97824;border-radius:6px;">
<strong>MATERIAL DE ESTUDO — ARQUITETURA DE SOFTWARE</strong><br>
Da identificação do problema à melhoria progressiva do código Java.
</div>

## Estudo de caso para graduação

> Este material apresenta os cinco princípios SOLID por meio de código Java simples, questões para reflexão, diagnóstico do problema e refatorações progressivas.

### Legenda visual do material

<table>
<tr>
<td style="background:#F4F6F8;color:#263238;padding:10px 14px;border-left:5px solid #8FA3C0;"><strong>INVESTIGAÇÃO</strong><br>Código atual e questão para reflexão.</td>
<td style="background:#FDECEC;color:#7A1A12;padding:10px 14px;border-left:5px solid #B42318;"><strong>DIAGNÓSTICO</strong><br>Ponto focal do problema.</td>
<td style="background:#EAF2FF;color:#123A73;padding:10px 14px;border-left:5px solid #1D4ED8;"><strong>CORREÇÃO</strong><br>Nova decisão de implementação.</td>
</tr>
<tr>
<td style="background:#FDF3DC;color:#6B4B00;padding:10px 14px;border-left:5px solid #E97824;"><strong>TRADE-OFF</strong><br>Custo introduzido pela solução.</td>
<td style="background:#EAF7EF;color:#1F5B38;padding:10px 14px;border-left:5px solid #287A4B;"><strong>VERIFICAÇÃO</strong><br>Confirmação da aprendizagem.</td>
<td style="background:#0A2240;color:#FFFFFF;padding:10px 14px;border-left:5px solid #E97824;"><strong>CÓDIGO JAVA</strong><br>Trecho executável ou focal.</td>
</tr>
</table>

> As cores são reforçadas por títulos e símbolos para que o material continue compreensível mesmo quando visualizado sem estilos HTML.

---

## 1. Objetivos de aprendizagem

Ao concluir este material, você deverá ser capaz de:

- compreender o significado de cada princípio SOLID;
- identificar indícios de violação no código Java;
- diferenciar o problema técnico de sua consequência para a manutenção;
- propor mudanças pequenas e justificadas;
- reconhecer que princípios diferentes podem conduzir a decisões semelhantes;
- avaliar os benefícios e os custos de uma abstração;
- relacionar as decisões de GRASP à implementação orientada por SOLID.

### Limite didático

O código utiliza somente:

- classes e objetos;
- atributos e métodos;
- construtores;
- herança;
- classes abstratas;
- interfaces;
- condicionais;
- `System.out.println()`.

Não serão utilizados frameworks, banco de dados, coleções, `Map`, generics, anotações avançadas, mensageria ou algoritmos reais de cálculo de caminho.

---

## 2. Relação entre GRASP e SOLID

O modelo UML foi aprimorado com GRASP e entregue a uma fábrica de código. A implementação funciona, mas apresenta decisões que dificultam manutenção, testes e evolução.

| GRASP | SOLID |
|---|---|
| Ajuda a decidir quem deve receber uma responsabilidade | Ajuda a avaliar como essa responsabilidade foi implementada |
| Trabalha principalmente com a distribuição de responsabilidades | Trabalha principalmente com a qualidade das dependências, contratos e extensões |
| É utilizado durante a modelagem e o projeto | É observado principalmente na estrutura do código |

> GRASP e SOLID não são concorrentes. Os dois conjuntos de princípios podem conduzir à mesma solução por perspectivas diferentes.

---

## 3. Contexto do código

O código entregue permite:

1. representar uma partida;
2. realizar ataques entre unidades;
3. calcular danos diferentes por tipo de unidade;
4. salvar uma partida;
5. avisar os jogadores;
6. registrar ações importantes;
7. movimentar unidades;
8. calcular um caminho simples.

Entretanto, a implementação inicial possui cinco problemas:

| Evidência | Princípio relacionado |
|---|---|
| Uma classe ataca, salva, avisa e registra | SRP |
| O dano depende de condicionais por tipo | OCP |
| Uma torre herda uma operação de movimento que não consegue executar | LSP |
| Classes implementam operações de apoio que não utilizam | ISP |
| O controlador cria diretamente o calculador de caminho | DIP |

---

## 4. Código inicial propositalmente inadequado

### 4.1 Elementos principais

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Representa uma partida identificada pelo sistema.
class Partida {
    // Identificador simples utilizado para distinguir a partida.
    private int identificador;

    public Partida(int identificador) {
        this.identificador = identificador;
    }

    public int getIdentificador() {
        return identificador;
    }
}

// Representa uma unidade no modelo inicial.
class Unidade {
    // O tipo é armazenado como texto no modelo inicial.
    private String tipo;
    // Estado de vida protegido dentro da unidade.
    private int vida;

    public Unidade(String tipo, int vida) {
        this.tipo = tipo;
        this.vida = vida;
    }

    // Expõe o texto usado pelas decisões condicionais do código inicial.
    public String getTipo() {
        return tipo;
    }

    // Reduz a vida da unidade atingida.
    public void receberDano(int dano) {
        // Atualiza o estado do próprio objeto.
        vida = vida - dano;
    }

    // Executa o comportamento de movimento.
    public void mover(int x, int y) {
        System.out.println("Unidade movida para " + x + ", " + y);
    }
}

// Representa uma unidade fixa, capaz de atacar, mas incapaz de se mover.
class Torre extends Unidade {
    public Torre(int vida) {
        super("torre", vida);
    }

    @Override
    // Executa o comportamento de movimento.
    public void mover(int x, int y) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException("Uma torre não pode se mover");
    }
}
```

### 4.2 Operações de apoio

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Contrato amplo propositalmente inadequado: reúne capacidades diferentes.
interface AcoesApoio {
    // Capacidade de armazenar o estado de uma partida.
    void salvar(Partida partida);
    // Capacidade de enviar uma mensagem aos jogadores.
    void avisar(String mensagem);
    // Capacidade de registrar uma informação de auditoria.
    void registrar(String mensagem);
}

// Componente responsável pelo armazenamento, mas obrigado a cumprir um contrato amplo.
class ArmazenamentoPartida implements AcoesApoio {
    @Override
    // Método definido pelo contrato para salvar a partida.
    public void salvar(Partida partida) {
        System.out.println("Partida salva");
    }

    @Override
    // Método definido pelo contrato para enviar uma notificação.
    public void avisar(String mensagem) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }

    @Override
    // Método definido pelo contrato para registrar uma ação.
    public void registrar(String mensagem) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }
}

// Componente responsável por avisos, mas obrigado a cumprir um contrato amplo.
class NotificadorJogadores implements AcoesApoio {
    @Override
    // Método definido pelo contrato para salvar a partida.
    public void salvar(Partida partida) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }

    @Override
    // Método definido pelo contrato para enviar uma notificação.
    public void avisar(String mensagem) {
        System.out.println("Aviso: " + mensagem);
    }

    @Override
    // Método definido pelo contrato para registrar uma ação.
    public void registrar(String mensagem) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }
}

// Componente responsável por auditoria, mas obrigado a cumprir um contrato amplo.
class RegistroAuditoria implements AcoesApoio {
    @Override
    // Método definido pelo contrato para salvar a partida.
    public void salvar(Partida partida) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }

    @Override
    // Método definido pelo contrato para enviar uma notificação.
    public void avisar(String mensagem) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }

    @Override
    // Método definido pelo contrato para registrar uma ação.
    public void registrar(String mensagem) {
        System.out.println("Auditoria: " + mensagem);
    }
}
```

### 4.3 Ataque

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Coordena o caso de uso de ataque.
class ControladorAtaque {
    // Executa a sequência necessária para realizar um ataque.
    public void atacar(
        Unidade atacante,
        Unidade alvo,
        Partida partida
    ) {
        int dano;

        // A regra depende de uma comparação com o nome do tipo.
        if (atacante.getTipo().equals("espadachim")) {
            dano = 10;
        } else if (atacante.getTipo().equals("arqueiro")) {
            dano = 8;
        } else if (atacante.getTipo().equals("torre")) {
            dano = 15;
        } else {
            dano = 1;
        }

        // Solicita que o próprio alvo atualize sua vida.
        alvo.receberDano(dano);

        System.out.println("Partida " +
            partida.getIdentificador() + " salva");
        System.out.println("Jogadores avisados");
        System.out.println("Ataque registrado na auditoria");
    }
}
```

### 4.4 Movimento

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Implementação concreta de um cálculo simples de caminho.
class CaminhoSimples {
    public void calcular(int origemX, int origemY, int destinoX, int destinoY) {
        System.out.println("Caminho simples calculado");
    }
}

// Coordena o caso de uso de movimento.
class ControladorMovimento {
    // Dependência concreta criada dentro do controlador.
    private CaminhoSimples calculador = new CaminhoSimples();

    // Aceita qualquer Unidade, inclusive tipos que não conseguem se mover.
    public void mover(Unidade unidade, int x, int y) {
        // Primeiro, solicita o cálculo do caminho.
        calculador.calcular(0, 0, x, y);
        // Depois, solicita que a unidade realize o movimento.
        unidade.mover(x, y);
    }
}
```

### Pergunta de abertura

> O código funciona. Isso significa que ele está bem estruturado para receber mudanças?

### Análise inicial

Não. Compilar e executar corretamente não garante facilidade de manutenção, extensão ou substituição de componentes.

---

## 5. Como estudar este material

Em cada princípio, siga esta sequência:

1. leia a definição e o objetivo;
2. examine o código atual antes de consultar o diagnóstico;
3. tente identificar o problema apresentado na questão de reflexão;
4. compare sua análise com o diagnóstico em vermelho;
5. estude a implementação corrigida em azul;
6. observe quais elementos foram alterados;
7. avalie o benefício e o trade-off da decisão;
8. responda à questão de verificação antes de consultar a resposta.

### Regra de continuidade

O código corrigido em um princípio passa a integrar o estado inicial do princípio seguinte. Somente o problema focal da etapa deve ser corrigido.

As classes não relacionadas ao princípio atual podem ser omitidas nos trechos apresentados para facilitar a leitura. A omissão não representa remoção ou alteração no código.

### Como interpretar as cores

- **Preto e branco:** código atual apresentado para investigação.
- **Vermelho:** mesmas linhas do código atual que representam o problema.
- **Azul:** classes, métodos e dependências incluídos ou modificados pela correção.

---

## 6. SOLID 1 — Single Responsibility Principle

<div style="background:#0A2240;color:#FFFFFF;padding:10px 16px;border-left:6px solid #E97824;border-radius:5px;"><strong style="color:#E97824;">S</strong> — Foco da análise: razões diferentes para uma classe mudar.</div>

### 🧭 Conceito — Princípio da Responsabilidade Única

<div style="background:#F4F6F8;color:#263238;padding:12px 16px;border-left:5px solid #0A2240;border-radius:4px;"><strong>DEFINIÇÃO:</strong> uma classe deve possuir uma única razão relevante para mudar.</div>

### 🎯 Objetivo

Manter cada classe concentrada em uma responsabilidade bem definida, evitando que mudanças motivadas por assuntos diferentes afetem o mesmo componente.

### 📌 Quando utilizar

Utilize o princípio para analisar classes que:

- executam atividades de naturezas diferentes;
- são alteradas por necessidades de áreas diferentes;
- possuem métodos que poderiam formar responsabilidades independentes;
- exigem muitos preparativos para serem testadas;
- crescem sempre que uma nova funcionalidade é adicionada.

### ⚠️ Atenção

Responsabilidade única não significa possuir apenas um método. Uma classe pode ter vários métodos, desde que todos contribuam para o mesmo propósito.

### 1. 🔎 Investigação — código atual

<div style="background:#F4F6F8;color:#263238;padding:12px 16px;border-left:5px solid #8FA3C0;border-radius:4px;"><strong>QUESTÃO PARA REFLEXÃO:</strong> Quantas razões diferentes poderiam provocar alterações em <code>ControladorAtaque</code>?</div>

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Coordena o caso de uso de ataque.
class ControladorAtaque {
    // Executa a sequência necessária para realizar um ataque.
    public void atacar(
        Unidade atacante,
        Unidade alvo,
        Partida partida
    ) {
        int dano;

        // A regra depende de uma comparação com o nome do tipo.
        if (atacante.getTipo().equals("espadachim")) {
            dano = 10;
        } else if (atacante.getTipo().equals("arqueiro")) {
            dano = 8;
        } else if (atacante.getTipo().equals("torre")) {
            dano = 15;
        } else {
            dano = 1;
        }

        // Solicita que o próprio alvo atualize sua vida.
        alvo.receberDano(dano);

        System.out.println("Partida " +
            partida.getIdentificador() + " salva");
        System.out.println("Jogadores avisados");
        System.out.println("Ataque registrado na auditoria");
    }
}
```

### 2. 🔴 Diagnóstico — ponto focal do erro

<div style="background:#FDECEC;color:#7A1A12;padding:12px 16px;border-left:5px solid #B42318;border-radius:4px;"><strong>DIAGNÓSTICO:</strong> <code>ControladorAtaque</code> possui razões diferentes para mudar: regras de combate, armazenamento, notificação e auditoria.</div>

Trecho focal do problema:

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Coordena o caso de uso de ataque.
class ControladorAtaque {
    // Executa a sequência necessária para realizar um ataque.
    public void atacar(
        Unidade atacante,
        Unidade alvo,
        Partida partida
    ) {
        // Regra de cálculo de dano
        int dano;
        // A regra depende de uma comparação com o nome do tipo.
        if (atacante.getTipo().equals("espadachim")) {
            dano = 10;
        } else if (atacante.getTipo().equals("arqueiro")) {
            dano = 8;
        } else if (atacante.getTipo().equals("torre")) {
            dano = 15;
        } else {
            dano = 1;
        }

        // Solicita que o próprio alvo atualize sua vida.
        alvo.receberDano(dano);

        // Responsabilidade de armazenamento
        System.out.println("Partida salva");

        // Responsabilidade de notificação
        System.out.println("Jogadores avisados");

        // Responsabilidade de auditoria
        System.out.println("Ataque registrado na auditoria");
    }
}
```

### 3. 🔵 Correção — nova implementação

<div style="background:#EAF2FF;color:#123A73;padding:12px 16px;border-left:5px solid #1D4ED8;border-radius:4px;"><strong>SOLUÇÃO:</strong> o controlador coordena o caso de uso e delega combate, armazenamento, notificação e auditoria para classes específicas.</div>

#### Combate

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Concentra as regras relacionadas à execução de um ataque.
class Combate {
    public void executarAtaque(Unidade atacante, Unidade alvo) {
        // Obtém o dano antes de aplicá-lo ao alvo.
        int dano = calcularDano(atacante);
        // Solicita que o próprio alvo atualize sua vida.
        alvo.receberDano(dano);
    }

    private int calcularDano(Unidade atacante) {
        // A regra depende de uma comparação com o nome do tipo.
        if (atacante.getTipo().equals("espadachim")) {
            return 10;
        }

        if (atacante.getTipo().equals("arqueiro")) {
            return 8;
        }

        if (atacante.getTipo().equals("torre")) {
            return 15;
        }

        return 1;
    }
}
```

#### Classes de apoio

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Componente responsável pelo armazenamento, mas obrigado a cumprir um contrato amplo.
class ArmazenamentoPartida implements AcoesApoio {
    @Override
    // Método definido pelo contrato para salvar a partida.
    public void salvar(Partida partida) {
        System.out.println("Partida salva");
    }

    @Override
    // Método definido pelo contrato para enviar uma notificação.
    public void avisar(String mensagem) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }

    @Override
    // Método definido pelo contrato para registrar uma ação.
    public void registrar(String mensagem) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }
}

// Componente responsável por avisos, mas obrigado a cumprir um contrato amplo.
class NotificadorJogadores implements AcoesApoio {
    @Override
    // Método definido pelo contrato para salvar a partida.
    public void salvar(Partida partida) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }

    @Override
    // Método definido pelo contrato para enviar uma notificação.
    public void avisar(String mensagem) {
        System.out.println("Aviso: " + mensagem);
    }

    @Override
    // Método definido pelo contrato para registrar uma ação.
    public void registrar(String mensagem) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }
}

// Componente responsável por auditoria, mas obrigado a cumprir um contrato amplo.
class RegistroAuditoria implements AcoesApoio {
    @Override
    // Método definido pelo contrato para salvar a partida.
    public void salvar(Partida partida) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }

    @Override
    // Método definido pelo contrato para enviar uma notificação.
    public void avisar(String mensagem) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }

    @Override
    // Método definido pelo contrato para registrar uma ação.
    public void registrar(String mensagem) {
        System.out.println("Auditoria: " + mensagem);
    }
}
```

#### Controlador

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Coordena o caso de uso de ataque.
class ControladorAtaque {
    private Combate combate;
    private ArmazenamentoPartida armazenamento;
    private NotificadorJogadores notificador;
    private RegistroAuditoria auditoria;

    // Recebe os colaboradores necessários sem executar suas responsabilidades.
    public ControladorAtaque(
        Combate combate,
        ArmazenamentoPartida armazenamento,
        NotificadorJogadores notificador,
        RegistroAuditoria auditoria
    ) {
        this.combate = combate;
        this.armazenamento = armazenamento;
        this.notificador = notificador;
        this.auditoria = auditoria;
    }

    // Executa a sequência necessária para realizar um ataque.
    public void atacar(
        Unidade atacante,
        Unidade alvo,
        Partida partida
    ) {
        combate.executarAtaque(atacante, alvo);
        armazenamento.salvar(partida);
        notificador.avisar("Uma unidade foi atacada");
        auditoria.registrar("Ataque realizado");
    }
}
```

### 🔧 O que foi alterado

- O cálculo e a aplicação do dano passaram para `Combate`.
- O armazenamento passou para `ArmazenamentoPartida`.
- A notificação passou para `NotificadorJogadores`.
- O registro passou para `RegistroAuditoria`.
- `ControladorAtaque` passou a coordenar a sequência.

### ⚖️ Trade-off

<div style="background:#FDF3DC;color:#6B4B00;padding:12px 16px;border-left:5px solid #E97824;border-radius:4px;"><strong>CUSTO DA DECISÃO:</strong> A solução possui mais classes e mais colaborações. Em compensação, cada classe apresenta uma razão mais clara para mudar.</div>

### ✅ Verificação da aprendizagem

<div style="background:#EAF7EF;color:#1F5B38;padding:12px 16px;border-left:5px solid #287A4B;border-radius:4px;"><strong>PERGUNTA DE VERIFICAÇÃO:</strong> Se a forma de salvar uma partida mudar, <code>ControladorAtaque</code> deverá ser responsável por implementar o novo armazenamento?</div>

<div style="background:#EAF7EF;color:#1F5B38;padding:10px 14px;border-left:5px solid #287A4B;border-radius:4px;"><strong>✓ RESPOSTA CORRETA:</strong> não. A mudança pertence a <code>ArmazenamentoPartida</code>. O controlador apenas solicita o salvamento.</div>

---

## 7. SOLID 2 — Open/Closed Principle

<div style="background:#0A2240;color:#FFFFFF;padding:10px 16px;border-left:6px solid #E97824;border-radius:5px;"><strong style="color:#E97824;">O</strong> — Foco da análise: extensão sem modificações recorrentes.</div>

### 🧭 Conceito — Princípio Aberto/Fechado

<div style="background:#F4F6F8;color:#263238;padding:12px 16px;border-left:5px solid #0A2240;border-radius:4px;"><strong>DEFINIÇÃO:</strong> uma unidade de software deve estar aberta para extensão e fechada para modificações recorrentes.</div>

### 🎯 Objetivo

Permitir a inclusão de novos comportamentos sem alterar repetidamente um componente que já está funcionando.

### 📌 Quando utilizar

Utilize o princípio quando:

- uma classe é modificada sempre que surge um novo tipo;
- existem condicionais baseadas em nomes ou tipos;
- um comportamento possui diferentes implementações;
- a extensão é esperada e relevante;
- uma abstração estável pode representar a variação.

### ⚠️ Atenção

Fechado para modificação não significa que o código nunca poderá ser alterado. O princípio procura evitar mudanças recorrentes causadas pela mesma espécie de extensão.

### 1. 🔎 Investigação — código atual

<div style="background:#F4F6F8;color:#263238;padding:12px 16px;border-left:5px solid #8FA3C0;border-radius:4px;"><strong>QUESTÃO PARA REFLEXÃO:</strong> O que precisa ser modificado em <code>Combate</code> quando uma nova unidade com dano diferente é adicionada?</div>

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Concentra as regras relacionadas à execução de um ataque.
class Combate {
    public void executarAtaque(Unidade atacante, Unidade alvo) {
        // Obtém o dano antes de aplicá-lo ao alvo.
        int dano = calcularDano(atacante);
        // Solicita que o próprio alvo atualize sua vida.
        alvo.receberDano(dano);
    }

    private int calcularDano(Unidade atacante) {
        // A regra depende de uma comparação com o nome do tipo.
        if (atacante.getTipo().equals("espadachim")) {
            return 10;
        }

        if (atacante.getTipo().equals("arqueiro")) {
            return 8;
        }

        if (atacante.getTipo().equals("torre")) {
            return 15;
        }

        return 1;
    }
}
```

### 2. 🔴 Diagnóstico — ponto focal do erro

<div style="background:#FDECEC;color:#7A1A12;padding:12px 16px;border-left:5px solid #B42318;border-radius:4px;"><strong>DIAGNÓSTICO:</strong> cada novo tipo obriga a inclusão de outra condição em <code>Combate</code>.</div>

Trecho focal do problema:

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Método focal: toda a variação de dano está centralizada aqui.
private int calcularDano(Unidade atacante) {
    // Decisões baseadas no tipo da unidade
    if (atacante.getTipo().equals("espadachim")) {
        return 10;
    }

    if (atacante.getTipo().equals("arqueiro")) {
        return 8;
    }

    if (atacante.getTipo().equals("torre")) {
        return 15;
    }

    return 1;
}
```

### 3. 🔵 Correção — nova implementação

<div style="background:#EAF2FF;color:#123A73;padding:12px 16px;border-left:5px solid #1D4ED8;border-radius:4px;"><strong>SOLUÇÃO:</strong> <code>Unidade</code> define a operação comum e cada subtipo fornece sua forma de calcular o dano.</div>

#### Unidade

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Define o estado e o comportamento comuns a todas as unidades.
abstract class Unidade {
    // Estado de vida protegido dentro da unidade.
    private int vida;

    public Unidade(int vida) {
        this.vida = vida;
    }

    // Reduz a vida da unidade atingida.
    public void receberDano(int dano) {
        // Atualiza o estado do próprio objeto.
        vida = vida - dano;
    }

    // Executa o comportamento de movimento.
    public void mover(int x, int y) {
        System.out.println("Unidade movida para " + x + ", " + y);
    }

    // Cada subtipo deverá fornecer seu próprio cálculo de dano.
    public abstract int calcularDano();
}
```

#### Tipos de unidade

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Especializa Unidade com o dano próprio do espadachim.
class Espadachim extends Unidade {
    public Espadachim(int vida) {
        super(vida);
    }

    @Override
    // Fornece o dano específico deste subtipo.
    public int calcularDano() {
        return 10;
    }
}

// Especializa Unidade com o dano próprio do arqueiro.
class Arqueiro extends Unidade {
    public Arqueiro(int vida) {
        super(vida);
    }

    @Override
    // Fornece o dano específico deste subtipo.
    public int calcularDano() {
        return 8;
    }
}

// Representa uma unidade fixa, capaz de atacar, mas incapaz de se mover.
class Torre extends Unidade {
    public Torre(int vida) {
        super(vida);
    }

    @Override
    // Fornece o dano específico deste subtipo.
    public int calcularDano() {
        return 15;
    }

    @Override
    // Executa o comportamento de movimento.
    public void mover(int x, int y) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException(
            "Uma torre não pode se mover"
        );
    }
}
```

#### Combate

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Concentra as regras relacionadas à execução de um ataque.
class Combate {
    public void executarAtaque(Unidade atacante, Unidade alvo) {
        // A chamada polimórfica seleciona o comportamento do subtipo em execução.
        int dano = atacante.calcularDano();
        // Solicita que o próprio alvo atualize sua vida.
        alvo.receberDano(dano);
    }
}
```

### 🔧 O que foi alterado

- A decisão por `String` foi removida.
- `Unidade` passou a declarar `calcularDano()`.
- Cada subtipo passou a implementar seu próprio dano.
- `Combate` passou a depender do contrato comum de `Unidade`.

### ⚖️ Trade-off

<div style="background:#FDF3DC;color:#6B4B00;padding:12px 16px;border-left:5px solid #E97824;border-radius:4px;"><strong>CUSTO DA DECISÃO:</strong> A solução cria mais classes. Ela é justificável quando os tipos e seus comportamentos realmente variam; para uma única regra estável, a abstração poderia ser desnecessária.</div>

### ✅ Verificação da aprendizagem

<div style="background:#EAF7EF;color:#1F5B38;padding:12px 16px;border-left:5px solid #287A4B;border-radius:4px;"><strong>PERGUNTA DE VERIFICAÇÃO:</strong> Para adicionar <code>Catapulta</code>, seria necessário modificar <code>Combate</code>?</div>

<div style="background:#EAF7EF;color:#1F5B38;padding:10px 14px;border-left:5px solid #287A4B;border-radius:4px;"><strong>✓ RESPOSTA CORRETA:</strong> não. Basta criar <code>Catapulta</code> como subtipo de <code>Unidade</code> e implementar <code>calcularDano()</code>.</div>

---

## 8. SOLID 3 — Liskov Substitution Principle

<div style="background:#0A2240;color:#FFFFFF;padding:10px 16px;border-left:6px solid #E97824;border-radius:5px;"><strong style="color:#E97824;">L</strong> — Foco da análise: substituição segura entre tipos e subtipos.</div>

### 🧭 Conceito — Princípio da Substituição de Liskov

<div style="background:#F4F6F8;color:#263238;padding:12px 16px;border-left:5px solid #0A2240;border-radius:4px;"><strong>DEFINIÇÃO:</strong> um objeto de um subtipo deve poder substituir um objeto de seu tipo-base sem violar o comportamento esperado pelo cliente.</div>

### 🎯 Objetivo

Construir hierarquias nas quais os subtipos respeitem as expectativas e os contratos definidos pelos tipos mais gerais.

### 📌 Quando utilizar

Utilize o princípio para avaliar herança quando:

- um subtipo lança exceção em uma operação válida do tipo-base;
- um subtipo ignora um método herdado;
- o cliente precisa perguntar qual é o tipo real antes de usar o objeto;
- o subtipo exige condições especiais;
- a herança foi escolhida apenas para reaproveitar atributos ou métodos.

### ⚠️ Atenção

LSP não exige que todos os subtipos executem tudo da mesma maneira. Eles podem variar, desde que preservem o contrato esperado pelo cliente.

### 1. 🔎 Investigação — código atual

<div style="background:#F4F6F8;color:#263238;padding:12px 16px;border-left:5px solid #8FA3C0;border-radius:4px;"><strong>QUESTÃO PARA REFLEXÃO:</strong> Todo objeto declarado como <code>Unidade</code> pode realmente executar as operações prometidas por essa classe?</div>

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Define o estado e o comportamento comuns a todas as unidades.
abstract class Unidade {
    // Estado de vida protegido dentro da unidade.
    private int vida;

    public Unidade(int vida) {
        this.vida = vida;
    }

    // Reduz a vida da unidade atingida.
    public void receberDano(int dano) {
        // Atualiza o estado do próprio objeto.
        vida = vida - dano;
    }

    // Executa o comportamento de movimento.
    public void mover(int x, int y) {
        System.out.println("Unidade movida para " + x + ", " + y);
    }

    // Cada subtipo deverá fornecer seu próprio cálculo de dano.
    public abstract int calcularDano();
}

// Representa uma unidade fixa, capaz de atacar, mas incapaz de se mover.
class Torre extends Unidade {
    public Torre(int vida) {
        super(vida);
    }

    @Override
    // Fornece o dano específico deste subtipo.
    public int calcularDano() {
        return 15;
    }

    @Override
    // Executa o comportamento de movimento.
    public void mover(int x, int y) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException(
            "Uma torre não pode se mover"
        );
    }
}

// Coordena o caso de uso de movimento.
class ControladorMovimento {
    // Dependência concreta criada dentro do controlador.
    private CaminhoSimples calculador = new CaminhoSimples();

    // Aceita qualquer Unidade, inclusive tipos que não conseguem se mover.
    public void mover(Unidade unidade, int x, int y) {
        // Primeiro, solicita o cálculo do caminho.
        calculador.calcular(0, 0, x, y);
        // Depois, solicita que a unidade realize o movimento.
        unidade.mover(x, y);
    }
}
```

### 2. 🔴 Diagnóstico — ponto focal do erro

<div style="background:#FDECEC;color:#7A1A12;padding:12px 16px;border-left:5px solid #B42318;border-radius:4px;"><strong>DIAGNÓSTICO:</strong> <code>Torre</code> é aceita como <code>Unidade</code>, mas não consegue cumprir a operação <code>mover()</code> definida pelo tipo-base.</div>

Trecho focal do problema:

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Representa uma unidade fixa, capaz de atacar, mas incapaz de se mover.
class Torre extends Unidade {
    @Override
    // Executa o comportamento de movimento.
    public void mover(int x, int y) {
        // O subtipo rejeita uma operação prometida pelo tipo-base
        throw new UnsupportedOperationException(
            "Uma torre não pode se mover"
        );
    }
}

// Coordena o caso de uso de movimento.
class ControladorMovimento {
    // Aceita qualquer Unidade, inclusive tipos que não conseguem se mover.
    public void mover(Unidade unidade, int x, int y) {
        // O método aceita qualquer Unidade, inclusive Torre
        unidade.mover(x, y);
    }
}
```

### 3. 🔵 Correção — nova implementação

<div style="background:#EAF2FF;color:#123A73;padding:12px 16px;border-left:5px solid #1D4ED8;border-radius:4px;"><strong>SOLUÇÃO:</strong> o tipo geral mantém somente operações comuns, enquanto <code>UnidadeMovel</code> representa explicitamente os elementos que podem se mover.</div>

#### Hierarquia corrigida

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Define o estado e o comportamento comuns a todas as unidades.
abstract class Unidade {
    // Estado de vida protegido dentro da unidade.
    private int vida;

    public Unidade(int vida) {
        this.vida = vida;
    }

    // Reduz a vida da unidade atingida.
    public void receberDano(int dano) {
        // Atualiza o estado do próprio objeto.
        vida = vida - dano;
    }

    // Cada subtipo deverá fornecer seu próprio cálculo de dano.
    public abstract int calcularDano();
}

// Representa apenas as unidades que possuem capacidade de movimento.
abstract class UnidadeMovel extends Unidade {
    public UnidadeMovel(int vida) {
        super(vida);
    }

    // Executa o comportamento de movimento.
    public void mover(int x, int y) {
        System.out.println("Unidade movida para " + x + ", " + y);
    }
}
```

#### Subtipos

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Representa um espadachim que também pode se movimentar.
class Espadachim extends UnidadeMovel {
    public Espadachim(int vida) {
        super(vida);
    }

    @Override
    // Fornece o dano específico deste subtipo.
    public int calcularDano() {
        return 10;
    }
}

// Representa um arqueiro que também pode se movimentar.
class Arqueiro extends UnidadeMovel {
    public Arqueiro(int vida) {
        super(vida);
    }

    @Override
    // Fornece o dano específico deste subtipo.
    public int calcularDano() {
        return 8;
    }
}

// Representa uma unidade fixa, capaz de atacar, mas incapaz de se mover.
class Torre extends Unidade {
    public Torre(int vida) {
        super(vida);
    }

    @Override
    // Fornece o dano específico deste subtipo.
    public int calcularDano() {
        return 15;
    }
}
```

#### Controlador

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Coordena o caso de uso de movimento.
class ControladorMovimento {
    // Dependência concreta criada dentro do controlador.
    private CaminhoSimples calculador = new CaminhoSimples();

    // Aceita somente unidades que pertencem ao contrato de movimento.
    public void mover(UnidadeMovel unidade, int x, int y) {
        // Primeiro, solicita o cálculo do caminho.
        calculador.calcular(0, 0, x, y);
        // Depois, solicita que a unidade realize o movimento.
        unidade.mover(x, y);
    }
}
```

### 🔧 O que foi alterado

- `mover()` saiu de `Unidade`.
- `UnidadeMovel` passou a representar o contrato de movimento.
- `Espadachim` e `Arqueiro` permanecem móveis.
- `Torre` continua sendo uma unidade de combate, mas não promete movimento.
- O controlador aceita apenas unidades móveis.

### ⚖️ Trade-off

<div style="background:#FDF3DC;color:#6B4B00;padding:12px 16px;border-left:5px solid #E97824;border-radius:4px;"><strong>CUSTO DA DECISÃO:</strong> A hierarquia possui mais um nível. Em compensação, o contrato de cada tipo torna-se explícito e evita exceções previsíveis.</div>

### ✅ Verificação da aprendizagem

<div style="background:#EAF7EF;color:#1F5B38;padding:12px 16px;border-left:5px solid #287A4B;border-radius:4px;"><strong>PERGUNTA DE VERIFICAÇÃO:</strong> Uma <code>Torre</code> pode ser enviada ao método <code>ControladorMovimento.mover()</code> depois da correção?</div>

<div style="background:#EAF7EF;color:#1F5B38;padding:10px 14px;border-left:5px solid #287A4B;border-radius:4px;"><strong>✓ RESPOSTA CORRETA:</strong> não. O método exige <code>UnidadeMovel</code>, e <code>Torre</code> não pertence a esse tipo. O problema passa a ser impedido pelo compilador.</div>

---

## 9. SOLID 4 — Interface Segregation Principle

<div style="background:#0A2240;color:#FFFFFF;padding:10px 16px;border-left:6px solid #E97824;border-radius:5px;"><strong style="color:#E97824;">I</strong> — Foco da análise: contratos pequenos e coerentes.</div>

### 🧭 Conceito — Princípio da Segregação de Interfaces

<div style="background:#F4F6F8;color:#263238;padding:12px 16px;border-left:5px solid #0A2240;border-radius:4px;"><strong>DEFINIÇÃO:</strong> um cliente não deve ser obrigado a depender de operações que não utiliza.</div>

### 🎯 Objetivo

Criar contratos pequenos e coerentes, evitando métodos sem significado, implementações vazias e exceções usadas apenas para satisfazer interfaces excessivamente amplas.

### 📌 Quando utilizar

Utilize o princípio quando:

- uma implementação deixa métodos vazios;
- métodos lançam `UnsupportedOperationException`;
- classes utilizam apenas uma parte pequena de uma interface;
- alterações em uma operação afetam clientes que não a utilizam;
- uma interface representa capacidades que poderiam variar separadamente.

### ⚠️ Atenção

Segregar não significa criar uma interface para cada método automaticamente. As operações devem ser agrupadas de acordo com necessidades reais dos clientes.

### 1. 🔎 Investigação — código atual

<div style="background:#F4F6F8;color:#263238;padding:12px 16px;border-left:5px solid #8FA3C0;border-radius:4px;"><strong>QUESTÃO PARA REFLEXÃO:</strong> Todas as classes de apoio precisam oferecer salvamento, notificação e auditoria?</div>

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Contrato amplo propositalmente inadequado: reúne capacidades diferentes.
interface AcoesApoio {
    // Capacidade de armazenar o estado de uma partida.
    void salvar(Partida partida);
    // Capacidade de enviar uma mensagem aos jogadores.
    void avisar(String mensagem);
    // Capacidade de registrar uma informação de auditoria.
    void registrar(String mensagem);
}

// Componente responsável pelo armazenamento, mas obrigado a cumprir um contrato amplo.
class ArmazenamentoPartida implements AcoesApoio {
    @Override
    // Método definido pelo contrato para salvar a partida.
    public void salvar(Partida partida) {
        System.out.println("Partida salva");
    }

    @Override
    // Método definido pelo contrato para enviar uma notificação.
    public void avisar(String mensagem) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }

    @Override
    // Método definido pelo contrato para registrar uma ação.
    public void registrar(String mensagem) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }
}

// Componente responsável por avisos, mas obrigado a cumprir um contrato amplo.
class NotificadorJogadores implements AcoesApoio {
    @Override
    // Método definido pelo contrato para salvar a partida.
    public void salvar(Partida partida) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }

    @Override
    // Método definido pelo contrato para enviar uma notificação.
    public void avisar(String mensagem) {
        System.out.println("Aviso: " + mensagem);
    }

    @Override
    // Método definido pelo contrato para registrar uma ação.
    public void registrar(String mensagem) {
        // A operação foi exigida, mas não faz sentido para esta classe.
        throw new UnsupportedOperationException();
    }
}
```

### 2. 🔴 Diagnóstico — ponto focal do erro

<div style="background:#FDECEC;color:#7A1A12;padding:12px 16px;border-left:5px solid #B42318;border-radius:4px;"><strong>DIAGNÓSTICO:</strong> as classes são obrigadas a implementar operações que não fazem parte de suas responsabilidades.</div>

Trecho focal do problema:

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Componente responsável pelo armazenamento, mas obrigado a cumprir um contrato amplo.
class ArmazenamentoPartida implements AcoesApoio {
    @Override
    // Método definido pelo contrato para enviar uma notificação.
    public void avisar(String mensagem) {
        // Armazenamento não realiza notificações
        throw new UnsupportedOperationException();
    }

    @Override
    // Método definido pelo contrato para registrar uma ação.
    public void registrar(String mensagem) {
        // Armazenamento não realiza auditoria
        throw new UnsupportedOperationException();
    }
}
```

### 3. 🔵 Correção — nova implementação

<div style="background:#EAF2FF;color:#123A73;padding:12px 16px;border-left:5px solid #1D4ED8;border-radius:4px;"><strong>SOLUÇÃO:</strong> o contrato amplo é dividido em interfaces pequenas, associadas às capacidades reais de cada classe.</div>

#### Interfaces específicas

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Contrato específico para componentes capazes de salvar partidas.
interface SalvamentoPartida {
    // Capacidade de armazenar o estado de uma partida.
    void salvar(Partida partida);
}

// Contrato específico para componentes capazes de enviar avisos.
interface NotificacaoJogadores {
    // Capacidade de enviar uma mensagem aos jogadores.
    void avisar(String mensagem);
}

// Contrato específico para componentes capazes de registrar auditoria.
interface Auditoria {
    // Capacidade de registrar uma informação de auditoria.
    void registrar(String mensagem);
}
```

#### Implementações

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Implementa somente a capacidade de salvar partidas.
class ArmazenamentoPartida implements SalvamentoPartida {
    @Override
    // Método definido pelo contrato para salvar a partida.
    public void salvar(Partida partida) {
        System.out.println("Partida salva");
    }
}

// Implementa somente a capacidade de avisar jogadores.
class NotificadorJogadores implements NotificacaoJogadores {
    @Override
    // Método definido pelo contrato para enviar uma notificação.
    public void avisar(String mensagem) {
        System.out.println("Aviso: " + mensagem);
    }
}

// Implementa somente a capacidade de registrar auditoria.
class RegistroAuditoria implements Auditoria {
    @Override
    // Método definido pelo contrato para registrar uma ação.
    public void registrar(String mensagem) {
        System.out.println("Auditoria: " + mensagem);
    }
}
```

#### Uso dos contratos específicos

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Coordena o caso de uso de ataque.
class ControladorAtaque {
    private Combate combate;
    private SalvamentoPartida armazenamento;
    private NotificacaoJogadores notificador;
    private Auditoria auditoria;

    // Recebe os colaboradores necessários sem executar suas responsabilidades.
    public ControladorAtaque(
        Combate combate,
        SalvamentoPartida armazenamento,
        NotificacaoJogadores notificador,
        Auditoria auditoria
    ) {
        this.combate = combate;
        this.armazenamento = armazenamento;
        this.notificador = notificador;
        this.auditoria = auditoria;
    }

    // Executa a sequência necessária para realizar um ataque.
    public void atacar(
        Unidade atacante,
        Unidade alvo,
        Partida partida
    ) {
        combate.executarAtaque(atacante, alvo);
        armazenamento.salvar(partida);
        notificador.avisar("Uma unidade foi atacada");
        auditoria.registrar("Ataque realizado");
    }
}
```

### 🔧 O que foi alterado

- `AcoesApoio` foi removida.
- Cada capacidade recebeu um contrato específico.
- As classes deixaram de implementar operações sem significado.
- O controlador passou a depender apenas das operações que realmente utiliza.

### ⚖️ Trade-off

<div style="background:#FDF3DC;color:#6B4B00;padding:12px 16px;border-left:5px solid #E97824;border-radius:4px;"><strong>CUSTO DA DECISÃO:</strong> A solução aumenta a quantidade de interfaces. O benefício aparece quando as capacidades variam separadamente ou possuem clientes diferentes.</div>

### ✅ Verificação da aprendizagem

<div style="background:#EAF7EF;color:#1F5B38;padding:12px 16px;border-left:5px solid #287A4B;border-radius:4px;"><strong>PERGUNTA DE VERIFICAÇÃO:</strong> <code>NotificadorJogadores</code> ainda precisa implementar <code>salvar()</code>?</div>

<div style="background:#EAF7EF;color:#1F5B38;padding:10px 14px;border-left:5px solid #287A4B;border-radius:4px;"><strong>✓ RESPOSTA CORRETA:</strong> não. A classe implementa somente <code>NotificacaoJogadores</code>, que declara a operação <code>avisar()</code>.</div>

---

## 10. SOLID 5 — Dependency Inversion Principle

<div style="background:#0A2240;color:#FFFFFF;padding:10px 16px;border-left:6px solid #E97824;border-radius:5px;"><strong style="color:#E97824;">D</strong> — Foco da análise: dependência de abstrações estáveis.</div>

### 🧭 Conceito — Princípio da Inversão de Dependência

<div style="background:#F4F6F8;color:#263238;padding:12px 16px;border-left:5px solid #0A2240;border-radius:4px;"><strong>DEFINIÇÃO:</strong> módulos de alto nível não devem depender diretamente de detalhes concretos; ambos devem depender de abstrações estáveis.</div>

### 🎯 Objetivo

Permitir que detalhes de implementação sejam substituídos sem obrigar a alteração da classe que coordena a regra ou o caso de uso.

### 📌 Quando utilizar

Utilize o princípio quando:

- uma classe de coordenação instancia diretamente um colaborador concreto;
- trocar um detalhe exige alterar uma regra de alto nível;
- existe mais de uma implementação relevante;
- testes precisam substituir um colaborador;
- um contrato estável pode separar a política do detalhe.

### ⚠️ Atenção

DIP não significa criar interfaces para todas as classes. A abstração deve proteger uma dependência relevante ou um ponto real de variação.

### 1. 🔎 Investigação — código atual

<div style="background:#F4F6F8;color:#263238;padding:12px 16px;border-left:5px solid #8FA3C0;border-radius:4px;"><strong>QUESTÃO PARA REFLEXÃO:</strong> O controlador precisa conhecer e criar a forma concreta utilizada para calcular o caminho?</div>

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Implementação concreta de um cálculo simples de caminho.
class CaminhoSimples {
    // Realiza o cálculo conforme esta implementação.
    public void calcular(
        int origemX,
        int origemY,
        int destinoX,
        int destinoY
    ) {
        System.out.println("Caminho simples calculado");
    }
}

// Coordena o caso de uso de movimento.
class ControladorMovimento {
    // Dependência concreta criada dentro do controlador.
    private CaminhoSimples calculador = new CaminhoSimples();

    // Aceita somente unidades que pertencem ao contrato de movimento.
    public void mover(UnidadeMovel unidade, int x, int y) {
        // Primeiro, solicita o cálculo do caminho.
        calculador.calcular(0, 0, x, y);
        // Depois, solicita que a unidade realize o movimento.
        unidade.mover(x, y);
    }
}
```

### 2. 🔴 Diagnóstico — ponto focal do erro

<div style="background:#FDECEC;color:#7A1A12;padding:12px 16px;border-left:5px solid #B42318;border-radius:4px;"><strong>DIAGNÓSTICO:</strong> <code>ControladorMovimento</code> depende diretamente de <code>CaminhoSimples</code> e decide internamente qual implementação utilizar.</div>

Trecho focal do problema:

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Coordena o caso de uso de movimento.
class ControladorMovimento {
    // Dependência direta e criação da classe concreta
    private CaminhoSimples calculador = new CaminhoSimples();

    // Aceita somente unidades que pertencem ao contrato de movimento.
    public void mover(UnidadeMovel unidade, int x, int y) {
        // Primeiro, solicita o cálculo do caminho.
        calculador.calcular(0, 0, x, y);
        // Depois, solicita que a unidade realize o movimento.
        unidade.mover(x, y);
    }
}
```

### 3. 🔵 Correção — nova implementação

<div style="background:#EAF2FF;color:#123A73;padding:12px 16px;border-left:5px solid #1D4ED8;border-radius:4px;"><strong>SOLUÇÃO:</strong> o controlador depende do contrato <code>CalculadorCaminho</code> e recebe a implementação pelo construtor.</div>

#### Contrato

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Abstração que define o contrato para qualquer cálculo de caminho.
interface CalculadorCaminho {
    void calcular(
        int origemX,
        int origemY,
        int destinoX,
        int destinoY
    );
}
```

#### Implementações

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Implementação do contrato para um caminho simples.
class CaminhoSimples implements CalculadorCaminho {
    @Override
    // Realiza o cálculo conforme esta implementação.
    public void calcular(
        int origemX,
        int origemY,
        int destinoX,
        int destinoY
    ) {
        System.out.println("Caminho simples calculado");
    }
}

// Implementação alternativa para um caminho com obstáculos.
class CaminhoComObstaculos implements CalculadorCaminho {
    @Override
    // Realiza o cálculo conforme esta implementação.
    public void calcular(
        int origemX,
        int origemY,
        int destinoX,
        int destinoY
    ) {
        System.out.println("Caminho com obstáculos calculado");
    }
}
```

#### Controlador

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Coordena o caso de uso de movimento.
class ControladorMovimento {
    // O controlador conhece somente a abstração estável.
    private CalculadorCaminho calculador;

    // A implementação concreta é recebida de fora pelo construtor.
    public ControladorMovimento(CalculadorCaminho calculador) {
        // Armazena a dependência recebida para utilizá-la no movimento.
        this.calculador = calculador;
    }

    // Aceita somente unidades que pertencem ao contrato de movimento.
    public void mover(UnidadeMovel unidade, int x, int y) {
        // Primeiro, solicita o cálculo do caminho.
        calculador.calcular(0, 0, x, y);
        // Depois, solicita que a unidade realize o movimento.
        unidade.mover(x, y);
    }
}
```

#### Escolha da implementação

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Configuração simples das dependências da aplicação.
// A aplicação escolhe qual implementação concreta será utilizada.
CalculadorCaminho calculador = new CaminhoSimples();
ControladorMovimento controlador =
    // O controlador recebe a dependência já configurada.
    new ControladorMovimento(calculador);
```

### 🔧 O que foi alterado

- O controlador deixou de criar `CaminhoSimples`.
- Foi criado o contrato `CalculadorCaminho`.
- O construtor passou a receber a dependência.
- Diferentes implementações podem ser utilizadas sem modificar o controlador.

### ⚖️ Trade-off

<div style="background:#FDF3DC;color:#6B4B00;padding:12px 16px;border-left:5px solid #E97824;border-radius:4px;"><strong>CUSTO DA DECISÃO:</strong> A solução exige uma abstração e a configuração do objeto antes de seu uso. Esse custo é justificável quando a implementação pode variar ou precisa ser substituída em testes.</div>

### ✅ Verificação da aprendizagem

<div style="background:#EAF7EF;color:#1F5B38;padding:12px 16px;border-left:5px solid #287A4B;border-radius:4px;"><strong>PERGUNTA DE VERIFICAÇÃO:</strong> Para utilizar <code>CaminhoComObstaculos</code>, seria necessário modificar <code>ControladorMovimento</code>?</div>

<div style="background:#EAF7EF;color:#1F5B38;padding:10px 14px;border-left:5px solid #287A4B;border-radius:4px;"><strong>✓ RESPOSTA CORRETA:</strong> não. Basta fornecer uma instância diferente que implemente <code>CalculadorCaminho</code>.</div>

---

## 11. Síntese dos princípios

| Princípio | Problema observado | Decisão aplicada |
|---|---|---|
| SRP | Uma classe possuía várias razões para mudar | Separar combate, armazenamento, notificação e auditoria |
| OCP | Novos tipos exigiam novos condicionais | Estender `Unidade` por meio de subtipos |
| LSP | Um subtipo rejeitava uma operação do tipo-base | Separar unidades gerais de unidades móveis |
| ISP | Classes implementavam operações sem significado | Dividir a interface ampla em contratos específicos |
| DIP | O controlador dependia de uma implementação concreta | Depender de `CalculadorCaminho` e receber a implementação |

---

## 12. Trade-offs do SOLID

<div style="background:#FDF3DC;color:#6B4B00;padding:12px 16px;border-left:5px solid #E97824;border-radius:4px;"><strong>SOLID melhora a capacidade de manutenção, mas cada decisão possui custos.</strong></div>

- **SRP:** classes mais focadas podem aumentar a quantidade de objetos e delegações.
- **OCP:** extensibilidade exige abstrações e novos tipos.
- **LSP:** hierarquias mais precisas podem exigir reorganização do modelo.
- **ISP:** interfaces menores aumentam o número de contratos.
- **DIP:** abstrações e dependências recebidas tornam a criação dos objetos mais explícita.

<div style="background:#EAF7EF;color:#1F5B38;padding:12px 16px;border-left:5px solid #287A4B;border-radius:4px;"><strong>DECISÃO DE PROJETO:</strong> a melhor solução não é aquela que possui mais interfaces ou classes. É aquela que reduz um problema relevante sem introduzir complexidade desnecessária.</div>

---

## 13. Atividade prática

### Novo requisito

O sistema deverá permitir que uma unidade seja curada após um combate.

### Código inicial da atividade

<div style="background:#0A2240;color:#FFFFFF;padding:6px 10px;border-left:5px solid #E97824;border-radius:4px 4px 0 0;"><strong>CÓDIGO JAVA</strong></div>

```java
// Código da atividade: concentra o caso de uso de cura.
class ControladorCura {
    // Calcula a cura e também executa responsabilidades adicionais.
    public void curar(Unidade unidade, String tipoCurandeiro) {
        int pontos;

        // A inclusão de outro curandeiro exigirá mais uma condição.
        if (tipoCurandeiro.equals("monge")) {
            pontos = 10;
        } else if (tipoCurandeiro.equals("sacerdote")) {
            pontos = 20;
        } else {
            pontos = 1;
        }

        System.out.println("Unidade recuperou " + pontos + " pontos");
        System.out.println("Cura registrada");
        System.out.println("Jogador avisado");
    }
}
```

### Tarefa

1. Identifique pelo menos duas violações SOLID.
2. Explique a consequência de cada problema.
3. Proponha classes ou interfaces para melhorar o código.
4. Indique quais mudanças são realmente necessárias.
5. Registre um trade-off da solução proposta.

### Pistas para análise

- SRP: separar cura, registro e notificação.
- OCP: representar diferentes formas de cura por comportamento polimórfico.
- DIP: depender de contratos quando houver implementações que precisem variar.

---

## 14. Perguntas de encerramento

1. Uma classe com muitos métodos sempre viola SRP?
2. OCP significa que uma classe nunca poderá ser modificada?
3. Por que lançar `UnsupportedOperationException` pode indicar uma violação de LSP ou ISP?
4. Toda classe precisa possuir uma interface para atender ao DIP?
5. Qual é o risco de aplicar SOLID antes de existir um problema relevante?
6. Como High Cohesion e Low Coupling, do GRASP, se relacionam com SOLID?

### Respostas esperadas

1. Não. O problema é possuir razões diferentes para mudar.
2. Não. O objetivo é evitar modificações recorrentes provocadas por extensões previsíveis.
3. Porque o contrato promete uma operação que determinada implementação não consegue cumprir.
4. Não. A abstração deve proteger uma dependência ou variação relevante.
5. Criar complexidade, classes e contratos sem benefício proporcional.
6. Eles ajudam a avaliar a qualidade da distribuição de responsabilidades promovida pelas refatorações.

---

## 15. Resumo final

> **SRP:** uma razão relevante para mudar.

> **OCP:** estender sem modificar repetidamente.

> **LSP:** subtipos preservam o contrato do tipo-base.

> **ISP:** clientes dependem apenas das operações necessárias.

> **DIP:** regras de alto nível dependem de abstrações.

> **Síntese:** SOLID orienta decisões de implementação; não é uma obrigação de criar o maior número possível de classes e interfaces.

---

## 16. Referências principais

- Robert C. Martin. *Agile Software Development: Principles, Patterns, and Practices*.
- Robert C. Martin. *Clean Architecture: A Craftsman's Guide to Software Structure and Design*.
- Joshua Bloch. *Effective Java*.
