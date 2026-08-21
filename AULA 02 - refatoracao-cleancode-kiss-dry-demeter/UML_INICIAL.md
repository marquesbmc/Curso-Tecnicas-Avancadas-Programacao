# UML inicial — aplicativo de mobilidade urbana

Esta é a versão **antes da refatoração**. Ela utiliza os conceitos de POO estudados, mas contém deliberadamente problemas de Clean Code, DRY, KISS e Lei de Demeter.

## Diagrama de classes

```mermaid
classDiagram
    direction LR

    class Pessoa {
        <<abstract>>
        #String nome
        #String telefone
        +getNome() String
        +getTelefone() String
        +exibirResumo() String*
    }

    class Passageiro {
        -String categoria
        -int quantidadeDeCorridas
        +getCategoria() String
        +getQuantidadeDeCorridas() int
        +setQuantidadeDeCorridas(int valor)
        +exibirResumo() String
    }

    class Motorista {
        -double avaliacao
        -boolean disponivel
        +getAvaliacao() double
        +isDisponivel() boolean
        +setDisponivel(boolean valor)
        +getVeiculo() Veiculo
        +exibirResumo() String
    }

    class Veiculo {
        -String modelo
        -String placa
        -String cor
        +getModelo() String
        +getPlaca() String
        +getCor() String
    }

    class Localizacao {
        -String rua
        -String bairro
        -double latitude
        -double longitude
        +getRua() String
        +getBairro() String
        +getLatitude() double
        +getLongitude() double
    }

    class Corrida {
        -String codigo
        -String status
        -String horario
        -double distanciaKm
        -int duracaoMinutos
        -double valorFinal
        -Passageiro passageiro
        -Motorista motorista
        -Localizacao origem
        -Localizacao destino
        -List~Parada~ paradas
        -FormaPagamento pagamento
        -CategoriaCorrida categoria
        +calcularEstimativa() double
        +calcularValorFinal() double
        +validarTudo() boolean
        +iniciar() String
        +finalizar() void
        +cancelar(String motivo) void
        +gerarReciboCompleto() String
        +notificarTodoMundo() void
        +getPassageiro() Passageiro
        +getMotorista() Motorista
        +getOrigem() Localizacao
        +getDestino() Localizacao
        +getParadas() List~Parada~
        +getPagamento() FormaPagamento
        +setStatus(String status)
        +setValorFinal(double valor)
    }

    class Parada {
        -int ordem
        -Localizacao local
        -String observacao
        +getLocal() Localizacao
        +getOrdem() int
        +getObservacao() String
    }

    class CategoriaCorrida {
        <<abstract>>
        #double valorPorKm
        #double valorPorMinuto
        +calcular(double km, int minutos) double*
        +getNome() String*
    }

    class CategoriaEconomica {
        +calcular(double km, int minutos) double
        +getNome() String
    }

    class CategoriaConforto {
        +calcular(double km, int minutos) double
        +getNome() String
    }

    class CategoriaMoto {
        +calcular(double km, int minutos) double
        +getNome() String
    }

    class FormaPagamento {
        <<interface>>
        +pagar(double valor) String
    }

    class PagamentoPix {
        -String chave
        +pagar(double valor) String
    }

    class PagamentoCartao {
        -String numero
        -String cvv
        +pagar(double valor) String
    }

    class PagamentoDinheiro {
        -boolean precisaTroco
        -double trocoPara
        +pagar(double valor) String
    }

    class CalculadoraUniversal {
        +processar(double valor, String operacao, double numero, boolean arredondar) double
    }

    class Aplicacao {
        +main(String[] args) void
    }

    Pessoa <|-- Passageiro : generalização
    Pessoa <|-- Motorista : generalização

    CategoriaCorrida <|-- CategoriaEconomica : sobrescrita
    CategoriaCorrida <|-- CategoriaConforto : sobrescrita
    CategoriaCorrida <|-- CategoriaMoto : sobrescrita

    FormaPagamento <|.. PagamentoPix : realização
    FormaPagamento <|.. PagamentoCartao : realização
    FormaPagamento <|.. PagamentoDinheiro : realização

    Motorista "0..*" o-- "0..1" Veiculo : utiliza
    Corrida "1" --> "1" Passageiro : passageiro
    Corrida "1" --> "1" Motorista : motorista
    Corrida "1" *-- "1" Localizacao : origem
    Corrida "1" *-- "1" Localizacao : destino
    Corrida "1" *-- "0..*" Parada : controla
    Parada "0..*" --> "1" Localizacao : local
    Corrida "1" --> "1" FormaPagamento : paga com
    Corrida "1" --> "1" CategoriaCorrida : calcula como
    Corrida ..> CalculadoraUniversal : uso temporário
    Aplicacao ..> Corrida : cria e executa

    note for Corrida "[CLEAN CODE] Classe Deus: responsabilidades demais.\n[DRY] calcularEstimativa e calcularValorFinal repetem regras.\n[DEMETER] expõe toda a estrutura por getters.\n[CLEAN CODE] status e horário são strings mágicas."
    note for CalculadoraUniversal "[KISS] Generalização especulativa.\nRecebe operação por String e conhece operações não utilizadas."
    note for Motorista "[ENCAPSULAMENTO FRÁGIL] setDisponivel permite qualquer alteração externa.\nA presença de private não garante invariantes."
    note for Passageiro "[ENCAPSULAMENTO FRÁGIL] quantidade pode ficar negativa pelo setter público."
    note for PagamentoCartao "[CLEAN CODE / SEGURANÇA] Dados sensíveis modelados diretamente e sem proteção adequada."
    note for Localizacao "[DEMETER] Objetos clientes navegam por getOrigem().getRua() e getDestino().getBairro()."
```

## Conceitos de POO presentes

| Conceito | Onde aparece |
|---|---|
| Classe e objeto | Todas as classes; instanciação realizada por `Aplicacao` |
| Abstração | `Pessoa` e `CategoriaCorrida` representam conceitos essenciais do domínio |
| Encapsulamento | Atributos privados/protegidos com operações públicas — propositalmente incompleto em alguns pontos |
| Associação | `Corrida` conhece passageiro, motorista, pagamento e categoria |
| Agregação | `Motorista` utiliza um `Veiculo`, que pode existir e ser compartilhado independentemente |
| Composição | A corrida controla suas localizações e suas paradas |
| Herança | `Passageiro` e `Motorista` especializam `Pessoa` |
| Classe abstrata | `Pessoa` e `CategoriaCorrida` compartilham estado e comportamento-base |
| Interface | `FormaPagamento` define o contrato de pagamento |
| Realização | Pix, cartão e dinheiro implementam `FormaPagamento` |
| Sobrescrita | Cada categoria calcula a tarifa de modo diferente |
| Polimorfismo | `Corrida` usa os tipos comuns `FormaPagamento` e `CategoriaCorrida` |
| Multiplicidade | Uma corrida possui um passageiro, um motorista e zero ou muitas paradas |
| Dependência | `Aplicacao` cria corridas; `Corrida` usa temporariamente `CalculadoraUniversal` |

## Legenda dos problemas propositais

- **[CLEAN CODE]**: nomes, tamanho, responsabilidades, strings mágicas ou exposição desnecessária.
- **[DRY]**: uma mesma regra de negócio aparece em mais de um método.
- **[KISS]**: solução mais genérica e complexa do que a necessidade atual.
- **[DEMETER]**: uma classe precisa atravessar vários objetos para obter dados.
- **[ENCAPSULAMENTO FRÁGIL]**: o atributo é privado, mas o objeto não protege suas regras e invariantes.

## Perguntas para apresentar antes do código

1. Quais relações representam “é um”, “tem um” e “usa temporariamente”?
2. Onde ocorre polimorfismo por tipo comum e despacho dinâmico?
3. Por que apenas declarar atributos `private` não garante bom encapsulamento?
4. Quais responsabilidades não deveriam estar concentradas em `Corrida`?
5. Quais problemas são de modelagem POO e quais são de qualidade do código?

> O objetivo inicial não é corrigir o diagrama. Primeiro, os estudantes devem reconhecer os conceitos de POO e identificar os problemas intencionais. A implementação Java deve reproduzir esta versão problemática; as melhorias virão progressivamente nas aulas seguintes.
