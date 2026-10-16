# Templates do Trabalho Prático

**Trabalho Prático:** [01-enunciado-trabalho.md](01-enunciado-trabalho.md)

Este arquivo tem três partes:
1. **Template de Entrega:** copie e preencha. É o arquivo `.md` que a dupla entrega.
2. **Template de ADR:** o modelo do ADR que registra a decisão da Parte 2.
3. **Ficha do Revisor:** as perguntas que a dupla revisora usa na apresentação.

---

## 1. Template de Entrega

Copie tudo o que está dentro do bloco abaixo para um arquivo `entrega-dupla-X.md` e preencha.

````markdown
# Trabalho Prático – Dupla [A/B/C]: [Nome da Empresa]

**Integrantes:** [nome 1], [nome 2]
**Cartão sorteado:** [código e título do cartão, ex.: X1 · "Título do Cartão"]

---

## PARTE 1 – O Trade-off do Caso Real

### 1.1 Contexto de Negócio
[3 a 5 linhas: qual problema a empresa precisava resolver e quais eram as restrições.]

### 1.2 Como Era
```mermaid
graph TD
    %% cole aqui o diagrama "antes" do dossiê
    %% e marque os quanta com subgraph, ex.:
    %% subgraph Q1 ["Quantum 1"]
    %%     A --> B
    %% end
```
* **Estilo (Aula 06):**
* **Particionamento:** técnico / domínio

### 1.3 O Que Fizeram
```mermaid
graph TD
    %% cole aqui o diagrama "depois" do dossiê
    %% e marque os quanta com subgraph
```
* **Estilo (Aula 06):**
* **Particionamento:** técnico / domínio

### 1.4 Análise de Quanta (teste do "e se cair?")
| | Antes | Depois |
| :--- | :--- | :--- |
| **Componente testado** | | |
| **O que para junto** | | |
| **O que continua funcionando** | | |
| **Número de quanta** | | |

### 1.5 A Tabela de Trade-offs
| Característica | Antes | Depois | Melhorou / Piorou / Igual | Fato do dossiê que justifica |
| :--- | :---: | :---: | :---: | :--- |
| Simplicidade | | | | |
| Custo Geral | | | | |
| Modularidade | | | | |
| Implantabilidade | | | | |
| Testabilidade | | | | |
| Confiabilidade | | | | |
| Tolerância a Falhas | | | | |
| Desempenho | | | | |
| Escalabilidade | | | | |
| Elasticidade | | | | |
| Evolutividade | | | | |

> Use de ⭐ a ⭐⭐⭐⭐⭐, como na matriz da Aula 06. Não é preciso preencher a última coluna nas linhas que ficaram iguais.

### 1.6 O Veredito
* **Principal ganho:**
* **Preço pago:**
* **Por que valeu a pena para esta empresa, naquele momento:**

### 1.7 Falácias e Armadilhas
| Falácia ou armadilha | Como aparece no caso (antes ou depois) |
| :--- | :--- |
| | |
| | |

---

## PARTE 2 – A Decisão diante do Cartão de Mudança

### 2.1 O que o cartão muda
[Quais características ganharam ou perderam importância com o novo cenário?]

### 2.2 A Decisão
[Resumo em 2 ou 3 linhas: manter, ajustar ou mudar? O quê?]

```mermaid
graph TD
    %% diagrama da proposta (se a arquitetura mudar)
```

### 2.3 ADR
[Escreva aqui o ADR seguindo o template de ADR.]

### 2.4 Perguntas feitas ao CTO
1. **Pergunta:** ... **Resposta:** ...
2. **Pergunta:** ... **Resposta:** ...

---

## REVISÃO FEITA PELA DUPLA (preencher durante a apresentação da outra dupla)

**Dupla revisada:** [A/B/C]

| Pergunta feita | A dupla respondeu bem? | Ponto fraco encontrado |
| :--- | :---: | :--- |
| | | |
| | | |
| | | |

---

## Fontes
* [título](link)
````

---

## 2. Template de ADR

> Um ADR (*Architecture Decision Record*) registra **uma** decisão arquitetural e o **porquê** dela. É a Segunda Lei na prática: daqui a dois anos, alguém vai olhar o sistema e entender *como* ele funciona, mas só o ADR vai explicar *por que* ele é assim.

````markdown
### ADR: [Título curto da decisão, no imperativo]
*Ex.: "Extrair o processamento de pagamentos para um serviço independente"*

**Status:** Proposto | Aceito | Rejeitado

**Contexto**
[Qual é o problema? Quais forças estão em jogo? Cite o cartão de mudança e os fatos do caso.]

**Características envolvidas**
* Favorece: [ex.: Implantabilidade, Tolerância a Falhas]
* Prejudica: [ex.: Simplicidade, Custo]

**Decisão**
[O que foi decidido, de forma direta. "Vamos..."]

**Alternativas consideradas**
* [Alternativa 1]: em que ela era melhor e por que, mesmo assim, perdeu.
* [Alternativa 2, se houver]: por que perdeu.

**O que estamos perdendo** ⚠️ *(obrigatório)*
[O lado negativo desta decisão. Se esta seção estiver vazia, o ADR está incompleto.]

**Impacto nos quanta**
[A decisão aumenta, reduz ou mantém o número de quanta? Por quê?]

**Lição do caso real**
[O que a empresa do dossiê ensina sobre esta decisão? Ex.: "A Netflix levou 7 anos para migrar; não temos esse tempo."]
````

### Exemplo curto (caso da Aula 06, Rede de Clínicas "VidaMais")

> **ADR: Adotar arquitetura baseada em serviços com 4 serviços de domínio**
>
> **Status:** Aceito
>
> **Contexto:** um deploy no faturamento derrubou o agendamento em horário comercial. O time tem 8 desenvolvedores e orçamento moderado. Vamos abrir mais 20 unidades em dois anos.
>
> **Características envolvidas:** favorece Implantabilidade, Tolerância a Falhas e Testabilidade; prejudica Simplicidade e Custo (em relação ao monólito atual).
>
> **Decisão:** dividir o sistema em Agendamento, Prontuário, Faturamento e Farmácia, implantados de forma independente, inicialmente com banco compartilhado e esquemas separados.
>
> **Alternativas consideradas:** *Microsserviços*: seriam melhores em Elasticidade e dariam um banco por serviço, mas foram rejeitados porque o custo operacional consumiria o time de 8 pessoas. *Manter o monólito em camadas*: rejeitado porque não resolve o problema de implantação.
>
> **O que estamos perdendo:** passamos a operar 4 implantações em vez de 1; com o banco compartilhado, uma consulta pesada de um serviço ainda pode afetar os outros.
>
> **Impacto nos quanta:** com o banco compartilhado, o sistema continua sendo **1 quantum** do ponto de vista dos dados. Separar o banco do Agendamento é o próximo passo, se a disponibilidade dele virar prioridade.
>
> **Lição do caso real:** a Shopify mostrou que dá para ganhar fronteiras claras sem pagar o preço inteiro da distribuição.

---

## 3. Ficha do Revisor

> O papel do revisor **não é concordar**. É encontrar o *trade-off* que a dupla não enxergou. Escolha **3 ou 4 perguntas** e faça-as no tempo de revisão (7 min). Seja exigente com as ideias e respeitoso com as pessoas.

**Sobre o caso real (Parte 1)**
* "Que característica **piorou** com a mudança? Vocês deram a mesma nota antes e depois para ela?"
* "Essa estrela está justificada por qual fato do dossiê?"
* "Se a empresa tivesse feito o caminho oposto, o que teria ganhado?"
* "Façam o teste do 'e se cair?' com o componente [X], antes e depois. O que para junto?"

**Falácias da computação distribuída**
* "Qual falácia explica o problema do 'antes'?"
* "Vocês estão assumindo que **a rede é confiável**. O que acontece quando a chamada entre [X] e [Y] falha no meio?"
* "Quanto custa **transportar** esses dados entre as partes?"

**Sobre a decisão (Parte 2)**
* "Por que **não** a alternativa que vocês rejeitaram? Em que ela é melhor?"
* "Se o CTO dobrasse o orçamento (ou cortasse pela metade), a decisão mudaria?"
* "Isso não é um **monólito distribuído**?"
* "O time tem tamanho e conhecimento para operar essa solução?"
* "Essa decisão é reversível? Se der errado em seis meses, quanto custa voltar atrás?"

**Registro do revisor:** preencha a seção **"Revisão feita pela dupla"**, no final do seu próprio arquivo de entrega.
