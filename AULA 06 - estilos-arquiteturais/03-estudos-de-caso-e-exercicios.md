# Estudos de Caso e Exercícios: Estilos Arquiteturais

**Disciplina:** Arquitetura de Software
**Livro-Texto Base:** *Fundamentals of Software Architecture: An Engineering Approach* (Mark Richards & Neal Ford)
**Material de apoio:** [01-estilos-arquiteturais.md](01-estilos-arquiteturais.md)

---

## Estudo de Caso 1: Silicon Sandwiches (escolha entre monólitos)
* **Cenário:** rede nacional de sanduíches personalizados, com pedidos online, rotas de entrega considerando o trânsito e promoções diárias customizadas por loja.
* **Questão:** por que o livro escolheu uma arquitetura monolítica para este sistema? Qual a diferença entre resolvê-lo com um **monólito modular** e com um **Microkernel**?
* **Foco do debate:** o orçamento é limitado e as características exigidas cabem num **único quantum**, então um monólito reduz custo e complexidade. O **Microkernel** se destaca se a prioridade for isolar as customizações de cada loja em plug-ins independentes.

## Estudo de Caso 2: Going, Going, Gone (leilões online)
* **Cenário:** leilões em tempo real, com transmissão de vídeo, lances instantâneos e milhares de participantes simultâneos.
* **Questão:** por que a análise de *architectural quanta* levou os arquitetos a rejeitar uma solução monolítica?
* **Foco do debate:** as partes do sistema têm características operacionais muito diferentes. O leiloeiro precisa de confiabilidade e baixa latência; os compradores assistindo ao vídeo geram leitura massiva e precisam de escalabilidade e elasticidade. Características distintas exigem **quanta distintos**, o que leva a uma arquitetura distribuída com comunicação assíncrona.

## Estudo de Caso 3: Rede de Clínicas "VidaMais" (Service-Based ou Microsserviços?)
> *Estudo de caso elaborado para a disciplina (não consta no livro).*

* **Cenário:** uma rede regional com 15 clínicas usa um sistema monolítico em camadas para agendamento, prontuário eletrônico, faturamento de convênios e farmácia. A empresa vai abrir mais 20 unidades nos próximos dois anos. O time de TI tem **8 desenvolvedores** e o orçamento de infraestrutura é moderado. Hoje, qualquer correção no faturamento exige reimplantar o sistema inteiro, o que já derrubou o agendamento em horário comercial.
* **Questão:** a diretoria quer "migrar para microsserviços". Você recomendaria **Microsserviços** ou **Service-Based**? Quais serviços de domínio você criaria e quantos quanta o sistema teria?
* **Foco do debate:**
  * O problema real é **implantabilidade e tolerância a falhas** (um deploy no faturamento derruba o agendamento), não escalabilidade extrema.
  * Com 8 desenvolvedores, o custo operacional de microsserviços (dezenas de bancos, Kubernetes, tracing, Sagas) consumiria o time.
  * A **Service-Based** resolve o problema com 4 a 5 serviços de domínio (Agendamento, Prontuário, Faturamento, Farmácia), deploys independentes e transações ACID locais, como na cobrança de convênios.
  * Ponto de atenção: com banco compartilhado, o sistema pode continuar sendo **1 quantum**. Separar o banco do Agendamento é uma evolução natural se ele precisar de maior disponibilidade.

## Estudo de Caso 4: Exame Nacional de Admissão (picos extremos)
> *Estudo de caso elaborado para a disciplina (não consta no livro).*

* **Cenário:** um exame nacional fictício recebe **4 milhões de inscrições** num período de 10 dias. O tráfego fica baixo na maior parte do tempo, mas explode na primeira hora e no último dia, chegando a 200 mil acessos simultâneos. Depois de cada inscrição, o sistema precisa: gerar o boleto da taxa, enviar e-mail e SMS de confirmação, alocar o candidato num local de prova e atualizar os painéis do governo.
* **Questão:** qual estilo você usaria para o **fluxo de inscrição** e qual para as **tarefas após a inscrição**? Faz sentido usar o mesmo estilo para tudo?
* **Foco do debate:**
  * As duas partes têm características diferentes, portanto pedem **quanta diferentes** (mesmo raciocínio do Going, Going, Gone).
  * **Inscrição:** precisa de **elasticidade** e **desempenho** extremos durante poucas horas. A **Space-Based** tira o banco do caminho crítico e cria unidades de processamento sob demanda.
  * **Pós-inscrição:** boleto, e-mail, alocação e painéis são tarefas independentes que não precisam acontecer na hora. A **Event-Driven (Broker)** publica o evento `InscricaoConfirmada` e cada serviço reage no seu ritmo; se o envio de SMS cair, a inscrição não é afetada.
  * *Trade-off:* a solução híbrida é poderosa, mas cara e complexa. Vale discutir se, fora do período de inscrições, a infraestrutura pode ser reduzida (é aí que a elasticidade paga a conta).

## Exercícios de Fixação
1. *"Por que a arquitetura Service-Based costuma ser uma escolha mais prudente do que Microsserviços para empresas de médio porte?"*
2. *"Compare o exemplo do iFood (Broker) com o do Seguro Auto (Mediator): como cada um lida com uma falha no meio do fluxo?"*
3. *"Explique por que o exemplo da venda de ingressos tira o banco de dados do caminho principal da compra. Qual risco isso introduz?"*
4. *"No ERP Acadêmico (Camadas), o que você mudaria para suportar o pico de acessos ao boletim? Essa mudança altera o número de quanta?"*
