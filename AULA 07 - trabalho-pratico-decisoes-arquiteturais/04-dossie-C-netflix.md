# Dossiê C: Netflix, do Data Center para a Nuvem

**Trabalho Prático:** [01-enunciado-trabalho.md](01-enunciado-trabalho.md)

**Período do caso:** de 2008, quando uma falha grave motivou a mudança, até janeiro de 2016, quando a migração foi concluída. A empresa documentou a jornada no próprio site e em palestras técnicas.

---

## 1. O Contexto de Negócio

A Netflix começou em 1997 alugando **DVDs pelo correio**. Em 2007, lançou o serviço de **streaming**. Em 2008, os dois negócios conviviam: o cliente assinava, escolhia filmes no site e recebia os discos em casa, enquanto o streaming começava a crescer.

O sistema precisava controlar assinaturas, cobrança, a fila de filmes de cada cliente, o estoque de DVDs, os envios e, cada vez mais, a reprodução de vídeo pela internet.

---

## 2. Como Era (antes)

* **Uma aplicação monolítica:** o sistema era uma única unidade implantável.
* **Um banco de dados relacional central** (Oracle), usado pela aplicação inteira.
* **Data centers próprios:** a Netflix construiu seus data centers, o que era lento. Para atender mais carga, a empresa comprava **máquinas maiores** (escala vertical).
* **Operação tradicional:** pedir novos servidores exigia aprovação de orçamento e semanas de espera pelo hardware; as versões novas eram lançadas de forma **centralizada e coordenada**.

---

## 3. O Problema (agosto de 2008)

Uma **corrupção grave no banco de dados** impediu a Netflix de enviar DVDs aos clientes por **três dias**.

A conclusão da empresa foi que o problema não era só aquele banco: era a arquitetura inteira, baseada em **pontos únicos de falha que escalavam verticalmente**, como os bancos relacionais nos data centers. Além disso, o streaming prometia um crescimento que os data centers não acompanhariam.

---

## 4. A Decisão

A Netflix decidiu **sair dos data centers** e ir para a nuvem da **Amazon (AWS)**, escolhida pela escala e pela variedade de serviços.

**A escolha mais importante: não "levantar e mover".** O caminho mais fácil seria copiar o sistema como estava para a nuvem (*lift and shift*). A empresa rejeitou essa opção porque, nas palavras dela, isso levaria **todos os problemas do data center** junto. Em vez disso, decidiu **reconstruir** quase toda a tecnologia de forma nativa para a nuvem:

* **De monólito para centenas de microsserviços.**
* **Dados desnormalizados em bancos NoSQL**, em vez do banco relacional central.
* **Escala horizontal:** muitas máquinas comuns em vez de poucas máquinas grandes.
* **Nova forma de trabalhar:** implantação contínua, ferramentas de autoatendimento e **times pouco acoplados** que desenvolvem e operam os próprios serviços (DevOps), no lugar da aprovação centralizada.

---

## 5. Como Ficou (depois)

A migração levou **cerca de sete anos**. Primeiro foram os sistemas voltados ao cliente; por último, a cobrança e os dados de clientes e funcionários, que exigiam um caminho seguro e durável. Em **janeiro de 2016**, as últimas partes do data center que sustentavam o streaming foram desligadas.

**Resultados relatados pela empresa (2016):**
* O número de assinantes de streaming ficou cerca de **8 vezes maior** que em 2008.
* O volume total de horas assistidas cresceu cerca de **mil vezes** no período.
* Em janeiro de 2016, o serviço chegou a **mais de 130 novos países** de uma só vez.
* A empresa passou a conseguir adicionar **milhares de servidores e petabytes de armazenamento em minutos**, e a mover capacidade entre várias regiões da AWS.
* A disponibilidade melhorou de forma constante e se aproximou da meta de **99,99%**.
* **Custo:** economizar não era o objetivo principal, mas o custo de nuvem por início de reprodução acabou sendo uma **fração** do custo no data center.

---

## 6. O Preço da Mudança

A própria empresa descreveu a migração como **muito trabalhosa**:

* Reconstruir levou **muito mais tempo** do que simplesmente mover o sistema.
* Foi preciso criar **muitos sistemas novos** e desenvolver **novas habilidades** no time.
* Houve **momentos difíceis** de estabilidade no começo da vida na nuvem.

**Problemas novos que o mundo distribuído trouxe:**

* **Máquinas que somem a qualquer momento.** Na nuvem, um servidor pode falhar sem aviso. A resposta da Netflix foi criar o **Chaos Monkey**, uma ferramenta que **desliga servidores aleatoriamente em produção**, de propósito. A lógica: se o sistema não é testado contra falhas o tempo todo, ele não vai aguentar uma falha real.
* **Degradação graciosa.** Os serviços foram desenhados para **sempre responder**, mesmo sem entregar a experiência completa. Exemplo dado pela empresa: se o serviço de recomendações cair, a tela mostra **títulos populares** no lugar.
* **Falhas em cascata.** Com centenas de serviços chamando uns aos outros, um serviço lento pode travar todos os que dependem dele. A Netflix criou e publicou como código aberto o **Hystrix**, uma biblioteca que isola as chamadas entre serviços, interrompe chamadas a um serviço com problema (*circuit breaker*) e aciona uma resposta alternativa (*fallback*). Hoje o Hystrix está em modo de manutenção, e a Netflix passou a usar abordagens que se adaptam ao desempenho em tempo real.

---

## 7. Perguntas para Guiar a Análise

> Use estas perguntas como ponto de partida. A entrega segue o [template](05-templates.md).

* Aplique o **teste do "e se cair?"** à arquitetura **antes**: o que aconteceu em 2008 quando o banco central teve problema? Quantos quanta o sistema tinha? E **depois**?
* Monte a tabela **antes × depois** com as estrelas da matriz da Aula 06. O que a Netflix ganhou? O que ela **pagou** para ganhar isso?
* Por que a empresa rejeitou o *lift and shift*? Que alternativa mais barata ela descartou, e o que teria perdido?
* O Chaos Monkey e o Hystrix são respostas a quais **falácias da computação distribuída**?
* "Se recomendações cair, mostramos títulos populares": qual característica da Seção 1.5 esse exemplo ilustra?
* A Aula 06 diz que microsserviços "quebram" numa startup com 5 desenvolvedores. O que a Netflix tinha em 2008 que tornava essa mudança viável para ela?

---

## Fontes

* [Completing the Netflix Cloud Migration](https://about.netflix.com/en/news/completing-the-netflix-cloud-migration) – Yury Izrailevsky, Stevan Vlaovic e Ruslan Meshenberg, Netflix, fevereiro de 2016 (também disponível [em português](https://about.netflix.com/pt_br/news/completing-the-netflix-cloud-migration))
* [Netflix Cloud Migration](https://www.infoq.com/news/2016/03/Netflix-Cloud-Migration) – InfoQ, março de 2016
* [Netflix: Continually Test by Failing Servers with Chaos Monkey](https://highscalability.com/netflix-continually-test-by-failing-servers-with-chaos-monke/) – High Scalability, resumo do post da Netflix sobre as lições de usar a AWS
* [Hystrix](https://github.com/Netflix/Hystrix) – repositório oficial da Netflix no GitHub
* [Mastering Chaos – A Netflix Guide to Microservices](https://www.infoq.com/presentations/netflix-chaos-MicroServices/) – Josh Evans, QCon San Francisco, dezembro de 2016 (vídeo)
* [A Brief History of Scaling Netflix](https://blog.bytebytego.com/p/a-brief-history-of-scaling-netflix) – ByteByteGo, resumo da arquitetura original
