# Trabalho Prático: Decisões Arquiteturais em Casos Reais

**Disciplina:** Arquitetura de Software

**Livro-Texto Base:** *Fundamentals of Software Architecture: An Engineering Approach* (Mark Richards & Neal Ford)

**Pré-requisito:** [Aula 06 – Estilos Arquiteturais e Análise de Trade-Offs](../AULA%2006%20-%20estilos-arquiteturais/01-estilos-arquiteturais.md)

**Objetivo Didático:** Aplicar o vocabulário da Aula 06 (características arquiteturais, *architectural quantum*, falácias da computação distribuída, estilos e matriz de estrelas) para **analisar o *trade-off* de decisões reais** (como era a arquitetura e o que a empresa fez) e, depois, **tomar uma decisão própria**, registrada num ADR.

---

> ### 💡 *"Tudo em arquitetura de software é um trade-off."*
> Toda empresa deste trabalho ganhou alguma coisa com a sua decisão, e pagou por ela. A sua tarefa é descobrir o quê.

---

## Sumário
1. **Formato e Regras**
2. **Cronograma da Aula (4h)**
3. **Parte 1: O *Trade-off* do Caso Real (Como Era × O Que Fizeram)**
4. **Parte 2: O Cartão de Mudança**
5. **Apresentação e Revisão**
6. **Critérios de Avaliação**

---

## 1. Formato e Regras

* **Duplas:** a turma trabalha em **3 duplas**. Cada dupla recebe um caso real diferente. Os três casos mudaram de arquitetura, cada um numa direção:
  * **Caso A: Amazon Prime Video.** Um serviço distribuído que foi **juntado** num processo só.
  * **Caso B: Shopify.** Um monólito que foi **organizado** por domínio, sem ser quebrado em serviços.
  * **Caso C: Netflix.** Um monólito que foi **quebrado** em centenas de microsserviços na nuvem.
* **Material base:** todo o material está **neste repositório**. Cada dupla abre o **dossiê** do seu caso, com o resumo técnico e os links para as fontes originais. O dossiê é suficiente para fazer o trabalho. Nada é impresso: tenha o dossiê, o [template](05-templates.md) e a matriz de estrelas da Aula 06 abertos no computador.
* **Pesquisa extra:** é permitida e valorizada, desde que **toda afirmação sobre a empresa tenha fonte** (link para blog de engenharia, palestra ou artigo). Não invente a arquitetura da empresa.
* **Pergunte ao CTO:** durante a Parte 2, cada dupla pode fazer **até 2 perguntas** ao professor, que faz o papel de CTO da empresa (ex.: "qual é o orçamento?", "qual é o prazo?"). Use as perguntas com sabedoria: um arquiteto levanta requisitos com o negócio, em vez de supor.
* **Entrega:** um arquivo `.md` por dupla, chamado `entrega-dupla-X.md` e seguindo o [template de entrega](05-templates.md), enviado ao professor **até o fim da aula** pelo canal combinado (ex.: AVA, e-mail ou *pull request* no repositório).

---

## 2. Cronograma da Aula (4h)

| Horário | Atividade | O que acontece |
| :--- | :--- | :--- |
| **0:00 – 0:10** | Abertura | Dinâmica explicada e sorteio dos casos. |
| **0:10 – 1:30** | **Parte 1: O *Trade-off* do Caso Real** (80 min) | Leitura do dossiê, análise do "como era" e do "o que fizeram", e a tabela de *trade-offs*. |
| **1:30 – 1:45** | Checkpoint | O professor passa 5 minutos com cada dupla. |
| **1:45 – 1:50** | **Sorteio do Cartão de Mudança** | Cada dupla escolhe o cartão "1" ou "2" do seu caso, sem ver, e recebe o cenário na tela ou no chat da turma. |
| **1:50 – 2:50** | **Parte 2: A Decisão da Dupla** (60 min) | A dupla decide o que fazer e registra a decisão num ADR. |
| **2:50 – 3:00** | Intervalo | |
| **3:00 – 3:45** | **Apresentações com Revisor** (3 × 15 min) | 8 min de apresentação + 7 min de revisão. |
| **3:45 – 4:00** | Fechamento | Os três *trade-offs* lado a lado na matriz de estrelas. |

---

## 3. Parte 1: O *Trade-off* do Caso Real (Como Era × O Que Fizeram)

**Pergunta central:** *o que a empresa ganhou com a mudança, o que ela pagou por isso e por que valeu a pena?* (Primeira Lei: "tudo é um *trade-off*"; Segunda Lei: "por que é mais importante do que como").

A dupla deve produzir:

1. **Contexto de negócio:** em 3 a 5 linhas, qual problema a empresa precisava resolver e quais eram as restrições (time, prazo, custo, escala).
2. **Como era × o que fizeram:** o dossiê traz diagramas em Mermaid das duas arquiteturas. **Copie-os para a entrega e complete com a análise de vocês**: indique o estilo da Aula 06 que melhor descreve cada uma.
3. **Análise de quanta:** aplique o **teste do "e se cair?"** (Seção 1.3) **antes** e **depois**, e **marque os quanta nos diagramas** usando `subgraph`, como na Seção 1.3 da Aula 06. O número de quanta mudou? Por quê?
4. **A tabela de *trade-offs* (o coração do trabalho):** compare as duas arquiteturas, característica por característica, usando estrelas como na matriz da Aula 06 (Seção 4). Marque o que **melhorou**, o que **piorou** e o que ficou igual.
5. **O veredito:** em poucas linhas, responda:
   * Qual foi o principal **ganho**?
   * Qual foi o **preço pago**? (Se a dupla não encontrou nenhuma característica que piorou, ainda não encontrou o *trade-off*.)
   * Por que, **para esta empresa e naquele momento**, o ganho valeu o preço?
6. **Falácias e armadilhas:** quais **falácias da computação distribuída** (Seção 1.4) e quais **armadilhas comuns** da Aula 06 explicam o problema do "antes" ou aparecem no "depois"?

---

## 4. Parte 2: O Cartão de Mudança

Depois do checkpoint, cada dupla sorteia um **cartão de mudança**: um fato novo e fictício que altera o cenário.

Agora a decisão é **da dupla**. Usando o que aprendeu com o caso real, ela decide: **manter, ajustar ou mudar a arquitetura?**

A decisão é registrada em **1 ADR** (*Architecture Decision Record*), seguindo o [template](05-templates.md).

**Regras do ADR:**
* Precisa citar **pelo menos uma alternativa** que a dupla considerou e rejeitou, com o motivo.
* Precisa de uma seção **"O que estamos perdendo"**. Um ADR sem lado negativo é considerado incompleto (Primeira Lei).
* Use o vocabulário da aula: características, quanta, falácias, estrelas da matriz.
* Sempre que fizer sentido, **ligue a decisão ao caso real**: "a Netflix pagou X por isso", "o Prime Video mostrou que Y".

---

## 5. Apresentação e Revisão

Cada dupla apresenta por **8 minutos**:
* **5 minutos:** o caso real, com a tabela de *trade-offs* e o veredito (Parte 1).
* **3 minutos:** o cartão sorteado e a decisão da dupla (Parte 2).

Em seguida, a dupla revisora tem **7 minutos** para questionar, usando a [ficha do revisor](05-templates.md) aberta no computador. A dupla revisora registra as perguntas e os pontos fracos encontrados na seção **"Revisão feita pela dupla"**, no final do seu próprio arquivo de entrega. A rotação é:

| Apresenta | Revisa |
| :---: | :---: |
| Dupla A | Dupla B |
| Dupla B | Dupla C |
| Dupla C | Dupla A |

O papel do revisor **não é concordar**: é encontrar o *trade-off* que a dupla não enxergou. A dupla que apresenta deve defender a análise com argumentos, ou admitir o ponto fraco e dizer como o mitigaria. As duas atitudes são válidas; ignorar a pergunta não é.

---

## 6. Critérios de Avaliação

| Critério | Peso |
| :--- | :---: |
| Tabela de *trade-offs* e veredito do caso real (o que ganhou, o que pagou, por que valeu) | 35% |
| Uso correto dos conceitos (quanta, características, falácias, estilos) | 20% |
| Fidelidade ao caso real (fatos com fonte, sem inventar) | 15% |
| Decisão diante do cartão e qualidade do ADR | 20% |
| Defesa diante do revisor **e** qualidade da revisão feita | 10% |

> **Não existe resposta única.** Duas duplas poderiam montar estrelas um pouco diferentes para o mesmo caso e ambas tirarem nota máxima. O que se avalia é a qualidade do raciocínio: as estrelas precisam estar **justificadas por fatos do dossiê**.

---

📁 **Material do trabalho:**
* [02-dossie-A-prime-video.md](02-dossie-A-prime-video.md)
* [03-dossie-B-shopify.md](03-dossie-B-shopify.md)
* [04-dossie-C-netflix.md](04-dossie-C-netflix.md)
* [05-templates.md](05-templates.md) (template de entrega, template de ADR e ficha do revisor)
