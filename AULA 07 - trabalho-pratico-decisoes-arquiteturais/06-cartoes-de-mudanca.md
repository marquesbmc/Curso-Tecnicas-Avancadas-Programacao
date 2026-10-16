# Cartões de Mudança

**Trabalho Prático:** [01-enunciado-trabalho.md](01-enunciado-trabalho.md)

> 🎲 **Como funciona o sorteio:** cada caso tem **2 cartões**. Na hora do sorteio (1:45), cada dupla escolhe **"1" ou "2"** do seu caso, e o professor indica qual cartão ela recebeu. O cartão só vale a partir desse momento.
>
> ⚠️ Todos os cenários são **fictícios**, criados para o exercício. Eles **não** descrevem planos reais das empresas.

---

## Caso A: Prime Video

---

### 🃏 Cartão A1 · "Cinco Times, Um Processo"

O monitoramento deu tão certo que virou prioridade da empresa. Agora serão **40 tipos de detectores** em vez de poucos, mantidos por **5 times diferentes** (áudio, vídeo, legendas, anúncios e acessibilidade).

Cada time quer **implantar seus detectores várias vezes por dia**, sem esperar os outros. Na última semana, um detector de legendas com defeito **travou o processo inteiro** e derrubou o monitoramento de todas as transmissões por 40 minutos.

O orçamento continua apertado: a diretoria **não aceita perder** a economia de 90% conquistada.

**Decidam:** mantêm o processo único, voltam a distribuir, ou existe um caminho intermediário?

---

### 🃏 Cartão A2 · "A Final da Copa"

A empresa vai vender o monitoramento como **produto para outras emissoras**. O contrato mais importante é de uma emissora que transmite **futebol ao vivo**.

Num dia comum, a emissora tem 50 transmissões. No dia da **final da Copa**, terá **3.000 transmissões simultâneas por 3 horas**, e depois volta ao normal.

O contrato exige que **nenhum defeito fique mais de 10 segundos sem ser detectado** e que os dados de uma emissora **nunca** se misturem com os de outra.

**Decidam:** a arquitetura atual aguenta? O que precisa mudar para atender o pico e o isolamento entre clientes?

---

## Caso B: Shopify

---

### 🃏 Cartão B1 · "O Lançamento do Tênis"

Uma grande marca de calçados vai lançar um tênis de edição limitada **exclusivamente** numa loja da plataforma.

A previsão é de **2 milhões de pessoas** tentando comprar **no mesmo minuto**, para **20 mil pares**. Todas as compras são da **mesma loja**, que vive dentro de **um único *pod***.

Os outros lojistas desse *pod* já estão reclamando: em lançamentos menores, as lojas deles ficaram lentas.

**Decidam:** os *pods* resolvem esse problema? O que vocês mudariam e onde ficaria a mudança: no monólito, ao lado dele, ou fora dele?

---

### 🃏 Cartão B2 · "O Time de Pagamentos"

A empresa decidiu processar pagamentos com cartão **dentro de casa**. Isso traz três exigências novas:

1. Uma **certificação de segurança** que exige que o código e os dados de cartão fiquem **isolados** do resto do sistema, com acesso auditado.
2. O time de pagamentos (12 pessoas, contratadas agora) prefere usar **outra linguagem**, que domina, e quer **implantar de forma independente**.
3. Se o processamento de pagamentos cair, a diretoria quer que **o restante da loja continue funcionando** (catálogo, carrinho, painel do lojista).

**Decidam:** o componente de pagamentos continua dentro do monólito modular? Se sair, como ele se comunica com o resto?

---

## Caso C: Netflix

---

### 🃏 Cartão C1 · "A Startup que Quer Ser a Netflix"

A **StreamBR**, uma startup brasileira de streaming de filmes nacionais, contratou a dupla como arquitetos. Ela tem **6 desenvolvedores**, **20 mil assinantes** e dinheiro para **18 meses** de operação.

O fundador leu o caso da Netflix e quer **"fazer igual desde o começo"**: centenas de microsserviços, Chaos Monkey, vários data centers na nuvem. O argumento dele: "se a Netflix sofreu para migrar depois, é melhor já começar certo".

O time está dividido. Metade concorda com o fundador; a outra metade acha que a empresa vai quebrar antes de lançar.

**Decidam:** qual arquitetura a StreamBR deve adotar agora? O que vocês aproveitam da lição da Netflix e o que deixam para depois?

---

### 🃏 Cartão C2 · "Todo Mundo ao Mesmo Tempo"

A plataforma vai transmitir **ao vivo**, pela primeira vez, a final de um grande campeonato. A previsão é de **50 milhões de pessoas** abrindo o aplicativo **no mesmo minuto**.

A arquitetura foi feita para vídeo **sob demanda**, em que cada pessoa dá *play* num horário diferente. No teste de carga:
* o serviço de **recomendações** ficou lento;
* os serviços que montam a **tela inicial** ficaram esperando por ele;
* a tela inicial parou de carregar e **ninguém conseguia chegar ao botão do jogo**.

O evento **não pode ser adiado**, e um problema no meio do jogo vira notícia no mundo inteiro.

**Decidam:** o que muda na arquitetura (ou na forma de usá-la) para aguentar esse pico sem que uma parte derrube as outras?
