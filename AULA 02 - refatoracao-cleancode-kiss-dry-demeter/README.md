# Laboratório de Refatoração — Aplicativo de Mobilidade Urbana

Código Java propositalmente “sujo” para praticar **Clean Code, DRY, KISS e Lei de Demeter**. Refatoração é o método transversal: melhorar a estrutura sem mudar o comportamento.

## Material da aula

- [Slides — Aula de Refatoração, Clean Code, KISS, DRY e Lei de Demeter](slide-aula/Aula2-Refatoracao-CleanCode-KISS-DRY-Demeter.pptx)
- [Baixar o código completo da aula](https://github.com/marquesbmc/Curso-Tecnicas-Avancadas-Programacao/archive/refs/heads/main.zip)

## Cenário

O sistema simula um aplicativo de corridas urbanas. Ele calcula tarifas para categorias econômica, conforto e moto, oferece Pix, cartão e dinheiro, controla paradas, estados e disponibilidade e gera um recibo.

A modelagem retoma os conceitos da aula anterior: classe abstrata, interface, herança, sobrescrita, polimorfismo, associação, agregação, composição, dependência e multiplicidade. Apesar disso, o design contém deliberadamente problemas de qualidade.

O código funciona, mas foi escrito sob pressão e contém decisões difíceis de manter.

## Requisitos

- JDK 17 ou superior;
- nenhum Maven, Gradle ou biblioteca externa.

## Compilar e executar

No PowerShell, dentro desta pasta, use o JDK incluído no projeto:

```powershell
New-Item -ItemType Directory -Force out
.\java\bin\javac.exe -encoding UTF-8 -d out src\*.java testes\*.java
.\java\bin\java.exe -cp out TestesUnitarios
.\java\bin\java.exe -cp out Aplicacao
```

Ao executar `Aplicacao`, use o menu numérico exibido no terminal. A opção **Solicitar uma corrida** conduz por todo o processo:

1. cadastro do passageiro e identificação automática do benefício de cliente frequente;
2. preenchimento da origem, do destino, da distância e da duração;
3. escolha entre as categorias econômica, conforto e moto;
4. seleção do horário e da forma de pagamento (Pix, cartão ou dinheiro);
5. aplicação opcional do cupom `PRIMEIRA10` e inclusão de até cinco paradas;
6. conferência da estimativa e confirmação da solicitação;
7. finalização ou cancelamento da corrida e emissão do recibo.

Para encerrar a aplicação, volte ao menu principal e digite `0`.

Se o JDK 17 ou superior já estiver configurado no `PATH`, também é possível usar:

```powershell
New-Item -ItemType Directory -Force out
javac -encoding UTF-8 -d out src\*.java testes\*.java
java -cp out Aplicacao
java -cp out TestesUnitarios
```

Também é possível abrir a pasta na IDE e executar `Aplicacao.main()` e `TestesUnitarios.main()`.

## Regras do exercício

- Rode todos os testes antes da primeira mudança e depois de cada pequena refatoração.
- Não mude tarifas, mensagens, estados e resultados protegidos pelos testes.
- Os testes verificam comportamento público, não a estrutura interna: a implementação pode ser simplificada desde que os resultados continuem iguais.
- Faça alterações pequenas e justificadas.
- Não transforme deliberadamente o exercício em uma arquitetura complexa.

## Evolução nas aulas

1. **Clean Code:** nomes, funções, condicionais, comentários e formatação.
2. **DRY:** cálculo duplicado entre `calcularEstimativa()` e `calcularValorFinal()`.
3. **KISS:** `CalculadoraUniversal`, strings mágicas e generalizações especulativas.
4. **Lei de Demeter:** cadeias como `corrida.getMotorista().getVeiculo().getPlaca()`.
5. **Integração:** revisão final e justificativa das decisões, sempre com os testes verdes.

## Material de modelagem

Consulte [UML_INICIAL.md](UML_INICIAL.md) antes de abrir as classes. O diagrama identifica tanto os conceitos de POO quanto os problemas intencionais.
