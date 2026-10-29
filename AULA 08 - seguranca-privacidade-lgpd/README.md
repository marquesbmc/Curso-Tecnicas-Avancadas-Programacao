# AULA 08 — Segurança, Privacidade e LGPD no Desenvolvimento de Software

## Conteúdo técnico da aula

**Tema:** como desenvolver aplicações que protejam os dados das pessoas<br>
**Duração:** aproximadamente 2 horas<br>
**Formato:** explicação teórica com exemplos simples em Java<br>
**Cenário:** um aplicativo de mobilidade urbana que possui passageiros, motoristas, corridas e pagamentos<br>
**Pré-requisitos:** conhecimentos básicos de classes, objetos, atributos e métodos em Java

> **Mensagem principal:** proteger dados não é apenas colocar uma senha no sistema. Também precisamos coletar somente o necessário, controlar quem pode ver cada informação, evitar vazamentos em logs e apagar dados quando eles não forem mais necessários.

> **Observação:** esta é uma aula técnica e introdutória. Ela não substitui orientação jurídica.

---

## 1. O que vamos aprender

Ao final da aula, o aluno deverá compreender:

1. o que são segurança, privacidade e proteção de dados;
2. o que é um dado pessoal;
3. por que a LGPD também afeta o trabalho do programador;
4. a diferença entre autenticação e autorização;
5. por que não devemos retornar todos os dados de um objeto;
6. como senhas e segredos devem ser protegidos;
7. por que logs podem causar vazamentos;
8. como organizar uma aplicação simples de maneira mais segura.

---

## 2. Segurança, privacidade e LGPD

Esses três assuntos estão relacionados, mas não significam exatamente a mesma coisa.

### 2.1 Segurança da informação

Segurança procura proteger a informação contra:

- acesso não autorizado;
- alteração indevida;
- destruição ou perda;
- indisponibilidade.

Exemplo:

> Um passageiro deve conseguir consultar suas próprias corridas, mas não pode consultar as corridas de outro passageiro.

### 2.2 Privacidade

Privacidade está relacionada ao uso das informações de uma pessoa.

Exemplo:

> O aplicativo utiliza a localização do passageiro para encontrar um motorista. Isso não significa que a empresa pode usar essa localização livremente para qualquer outra finalidade.

### 2.3 Proteção de dados e LGPD

A Lei Geral de Proteção de Dados Pessoais, conhecida como **LGPD**, estabelece regras para o tratamento de dados pessoais.

Para o programador, “tratamento” inclui praticamente tudo o que o sistema faz com um dado:

```text
receber -> consultar -> armazenar -> alterar -> compartilhar -> eliminar
```

Portanto, estas operações são tratamentos de dados:

- receber um nome por um formulário;
- salvar um telefone no banco;
- consultar o endereço de um passageiro;
- enviar dados para outra API;
- escrever informações em um log;
- apagar um cadastro.

### 2.4 Segurança não resolve tudo

Imagine que o banco de dados esteja criptografado e que somente funcionários autorizados possam acessá-lo. Mesmo assim, existe um problema se a empresa coletar informações sem necessidade ou utilizá-las para uma finalidade que não foi informada.

Por isso:

```text
segurança é necessária, mas segurança sozinha não garante privacidade
```

---

## 3. O que é um dado pessoal

Dado pessoal é uma informação relacionada a uma pessoa identificada ou que possa ser identificada.

### 3.1 Dados que identificam diretamente

Algumas informações identificam uma pessoa de forma bastante direta:

- nome completo;
- CPF;
- e-mail;
- telefone;
- fotografia.

### 3.2 Dados que também podem identificar

Outras informações parecem menos importantes, mas podem identificar alguém quando são combinadas:

- localização;
- data e horário de uma corrida;
- endereço de origem e destino;
- placa do veículo;
- identificador do celular;
- histórico de viagens.

Por exemplo, o nome do passageiro pode ter sido removido, mas uma sequência frequente de viagens entre uma casa e uma empresa pode revelar quem é a pessoa.

### 3.3 Dados pessoais sensíveis

A LGPD dá atenção especial aos dados pessoais sensíveis. Entre eles estão informações sobre:

- saúde;
- biometria;
- origem racial ou étnica;
- religião;
- opinião política;
- vida sexual;
- dados genéticos.

No aplicativo de mobilidade, uma informação de acessibilidade pode revelar uma condição de saúde. Por isso, ela não deve ser exibida para qualquer funcionário ou componente do sistema.

### 3.4 Exemplo no aplicativo

| Informação | É pessoal? | Por quê? |
| --- | :---: | --- |
| Nome do passageiro | Sim | Identifica diretamente |
| Telefone | Sim | Permite entrar em contato e identificar |
| Origem e destino | Sim | Podem revelar casa, trabalho e rotina |
| `passageiroId` | Sim | Permite ligar corridas a uma pessoa |
| Categoria do veículo | Depende | Sozinha, normalmente não identifica alguém |
| Preço-base da categoria | Normalmente não | Refere-se ao serviço, não a uma pessoa |

> **Regra prática:** não conclua que uma informação deixou de ser pessoal apenas porque o nome foi removido.

---

## 4. As ideias mais importantes da LGPD para o programador

A LGPD não determina que o programador seja advogado. Ela estabelece obrigações e princípios que precisam se transformar em comportamento do sistema.

Os princípios estão no **art. 6º da LGPD**. Para uma primeira aula, cinco deles são especialmente importantes.

### 4.1 Finalidade

O princípio da finalidade aparece no **art. 6º, inciso I**. Todo dado deve ser coletado por algum motivo legítimo, específico e informado.

Exemplo:

> A localização é coletada para solicitar e executar a corrida.

“Talvez seja útil no futuro” não é uma finalidade clara.

### 4.2 Necessidade

O princípio da necessidade aparece no **art. 6º, inciso III**. O sistema deve utilizar somente os dados necessários para realizar uma tarefa.

Considere um método que calcula o valor de uma corrida:

```java
public double calcularValor(
        double distancia,
        int minutos,
        String nome,
        String cpf,
        String email) {

    return distancia * 2.5 + minutos * 0.3;
}
```

O cálculo utiliza apenas `distancia` e `minutos`. Nome, CPF e e-mail não deveriam ter sido recebidos.

Uma versão melhor é:

```java
public double calcularValor(double distancia, int minutos) {
    return distancia * 2.5 + minutos * 0.3;
}
```

Essa mudança é simples, mas importante:

- o método fica mais fácil de entender;
- recebe menos dados pessoais;
- possui menos chance de vazar informações;
- fica mais fácil de testar.

### 4.3 Transparência

O princípio da transparência aparece no **art. 6º, inciso VI**. A organização deve conseguir explicar quais dados utiliza e por quê.

O programador ajuda quando:

- dá nomes claros às classes e métodos;
- documenta integrações;
- evita fluxos escondidos de dados;
- registra decisões importantes.

### 4.4 Segurança e prevenção

Segurança e prevenção aparecem no **art. 6º, incisos VII e VIII**. Não devemos esperar um vazamento acontecer para começar a proteger o sistema.

O **art. 46** determina que os agentes de tratamento adotem medidas técnicas e administrativas para proteger dados pessoais. O § 2º do mesmo artigo determina que essas medidas sejam observadas desde a fase de concepção do produto ou serviço.

Exemplos de prevenção:

- verificar permissões antes de retornar dados;
- não colocar senhas no código;
- não registrar dados desnecessários em logs;
- atualizar bibliotecas com vulnerabilidades conhecidas;
- limitar tentativas de login.

### 4.5 Responsabilização

O princípio da responsabilização e prestação de contas aparece no **art. 6º, inciso X**. A organização deve conseguir demonstrar que adotou medidas de proteção.

O código, os testes, os registros de acesso e a documentação ajudam a produzir essa evidência.

### 4.6 Direitos das pessoas

O **art. 18** apresenta direitos do titular. Uma pessoa pode solicitar, conforme o caso:

- confirmação de que seus dados são tratados;
- acesso aos seus dados;
- correção de informações incorretas;
- informação sobre compartilhamentos;
- bloqueio, anonimização ou eliminação em situações previstas pela lei.

Isso cria perguntas para quem desenvolve o sistema:

```text
Onde estão os dados deste passageiro?
Como corrigimos seu telefone?
Quais sistemas receberam seus dados?
Como apagamos ou bloqueamos o que não deve mais ser utilizado?
```

### 4.7 Conceitos do art. 5º

O **art. 5º** contém definições usadas em toda a lei. Algumas delas viram elementos do sistema:

| Conceito legal | Exemplo no aplicativo |
| --- | --- |
| **Dado pessoal** | Nome, telefone, localização e `passageiroId` |
| **Dado pessoal sensível** | Biometria ou informação de saúde e acessibilidade, conforme o contexto |
| **Titular** | Passageiro, motorista ou outra pessoa a quem o dado se refere |
| **Tratamento** | Receber, consultar, salvar, compartilhar, alterar ou eliminar dados |
| **Controlador** | Organização que decide a finalidade e os elementos essenciais do tratamento |
| **Operador** | Organização ou pessoa que trata dados em nome do controlador |

O programador realiza operações de tratamento quando escreve código que recebe, consulta, salva ou envia dados pessoais.

### 4.8 Base legal não significa somente consentimento

Os **arts. 7º e 11** apresentam hipóteses que podem autorizar o tratamento de dados pessoais e de dados pessoais sensíveis. Consentimento é uma dessas possibilidades, mas não é a única.

Exemplo:

> O endereço pode ser necessário para executar um serviço solicitado. Isso não autoriza automaticamente seu uso para qualquer outra finalidade.

A equipe de desenvolvimento não deve inventar a base legal. Essa definição envolve o controlador, o encarregado e a área jurídica. O papel técnico é registrar a finalidade e implementar os limites definidos.

### 4.9 Da lei para o código

Esta tabela mostra a conexão principal da aula:

| O que a LGPD exige | Artigo ou princípio | Decisão no software |
| --- | --- | --- |
| Utilizar somente dados necessários | Art. 6º, III — necessidade | Método recebe apenas os parâmetros que utiliza |
| Informar e tornar o tratamento compreensível | Art. 6º, VI — transparência | Nomes claros, documentação e respostas compreensíveis |
| Proteger contra acesso não autorizado | Arts. 6º, VII, e 46 | Autenticação, autorização e menor privilégio |
| Prevenir danos | Art. 6º, VIII — prevenção | Validar entradas e não expor campos internos |
| Demonstrar medidas eficazes | Art. 6º, X | Testes, logs de auditoria e documentação |
| Permitir acesso, correção e outras solicitações | Art. 18 | Localizar e corrigir dados em todos os lugares relevantes |
| Proteger desde a concepção | Art. 46, § 2º | Pensar em segurança antes de publicar a aplicação |
| Comunicar incidente com risco ou dano relevante | Art. 48 | Detectar alcance, dados e titulares afetados |

> **Relação central:** a lei define o dever; a arquitetura e o código implementam o comportamento necessário para cumprir esse dever.

---

## 5. Autenticação e autorização

Esses termos são frequentemente confundidos.

### 5.1 Autenticação

Autenticação responde:

> **Quem é você?**

Exemplos:

- entrar com e-mail e senha;
- usar código enviado pelo aplicativo;
- usar autenticação em dois fatores.

Depois do login, o sistema reconhece o usuário.

### 5.2 Autorização

Autorização responde:

> **O que você pode fazer?**

Estar autenticado não significa ter acesso a tudo.

Exemplo de código inseguro:

```java
public Corrida buscarCorrida(long corridaId) {
    return repositorio.buscar(corridaId);
}
```

Se o endereço da API for `/corridas/100`, um usuário pode tentar trocar `100` por `101`. O método busca a corrida, mas não verifica a quem ela pertence.

Uma versão melhor recebe também o usuário autenticado:

```java
public Corrida buscarCorrida(long corridaId, long usuarioId) {
    Corrida corrida = repositorio.buscar(corridaId);

    if (corrida.getPassageiroId() != usuarioId) {
        throw new SecurityException("Acesso não permitido");
    }

    return corrida;
}
```

Agora o sistema verifica duas coisas:

1. qual corrida foi solicitada;
2. se o usuário pode consultar aquela corrida.

### 5.3 Ocultar um botão não é autorização

Uma interface pode esconder o botão “Cancelar corrida”, mas um usuário ainda pode tentar chamar a API diretamente.

Por isso, a permissão sempre precisa ser verificada no servidor.

### 5.4 Negar por padrão

Quando o sistema não consegue confirmar uma permissão, a operação protegida não deve continuar.

```text
permissão confirmada     -> operação permitida
permissão não confirmada -> operação negada
```

> **Relação com a LGPD:** autenticação e autorização ajudam a aplicar os princípios da segurança e da prevenção do art. 6º e as medidas de proteção exigidas pelo art. 46. O código que verifica `usuarioId` impede acesso não autorizado a dados pessoais.

---

## 6. Não devolva o objeto inteiro

Considere a classe:

```java
public class Usuario {
    private long id;
    private String nome;
    private String email;
    private String cpf;
    private String senhaHash;
    private String observacaoInterna;

    // construtor, getters e outros métodos
}
```

Agora imagine que a aplicação retorne o objeto inteiro:

```java
public Usuario consultarUsuario(long id) {
    return repositorio.buscar(id);
}
```

Dependendo da forma como a resposta é criada, campos internos podem ser enviados ao cliente:

```json
{
  "id": 10,
  "nome": "Ana Souza",
  "email": "ana@exemplo.com",
  "cpf": "000.000.000-00",
  "senhaHash": "...",
  "observacaoInterna": "..."
}
```

Mesmo que a senha esteja transformada em hash, ela não deve ser enviada.

### 6.1 Criando um objeto somente para a resposta

Podemos criar uma classe pequena que contenha somente os campos que devem sair:

```java
public class PerfilPublico {
    private final long id;
    private final String primeiroNome;

    public PerfilPublico(long id, String primeiroNome) {
        this.id = id;
        this.primeiroNome = primeiroNome;
    }

    public long getId() {
        return id;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }
}
```

O método passa a devolver essa visão reduzida:

```java
public PerfilPublico consultarPerfil(long id) {
    Usuario usuario = repositorio.buscar(id);

    return new PerfilPublico(
            usuario.getId(),
            usuario.getPrimeiroNome());
}
```

A resposta agora contém somente:

```json
{
  "id": 10,
  "primeiroNome": "Ana"
}
```

Esse tipo de objeto criado para transportar dados entre partes do sistema é frequentemente chamado de **DTO**, sigla de *Data Transfer Object*.

> **DTO, em linguagem simples:** é uma classe usada como uma “caixa de transporte”. Colocamos nela somente as informações que precisam atravessar uma fronteira do sistema.

O nome DTO não é a parte mais importante. O importante é entender que a resposta da API não precisa ser igual ao objeto armazenado no banco.

### 6.2 Por que isso ajuda

- evita exposição acidental de campos internos;
- deixa claro o que a API devolve;
- permite respostas diferentes para usuários diferentes;
- reduz a quantidade de dados transmitidos;
- facilita mudanças futuras na classe `Usuario`.

> **Relação com a LGPD:** o DTO ajuda a aplicar o princípio da necessidade do art. 6º, III. Em vez de enviar todos os dados disponíveis, a aplicação envia apenas os dados necessários para aquela finalidade. Ele também reduz o risco de exposição, contribuindo para segurança e prevenção.

---

## 7. Senhas, criptografia e segredos

### 7.1 Senhas não devem ser armazenadas como texto

Nunca faça isto:

```text
email: ana@exemplo.com
senha: MinhaSenha123
```

Se o banco vazar, todas as senhas estarão imediatamente expostas.

O sistema deve armazenar um **verificador de senha**, normalmente produzido por uma função de hash apropriada para senhas.

```text
Senha digitada
      ↓
função própria para senhas
      ↓
valor armazenado no banco
```

Durante o login, a senha digitada passa novamente pelo processo de verificação. O sistema não precisa recuperar a senha original.

Bibliotecas de segurança oferecem algoritmos apropriados, como Argon2id, scrypt, bcrypt e PBKDF2. Não crie seu próprio algoritmo e não use MD5 ou SHA-256 puro para armazenar senhas.

#### Demonstração 1 — observando um hash no CMD do Windows

O Windows possui o comando `certutil`, que pode calcular o hash de um arquivo. Ele é útil para mostrar visualmente como um hash funciona.

Abra o **Prompt de Comando — CMD**, não o PowerShell, e execute:

```bat
mkdir aula-hash
cd aula-hash
echo MinhaSenha123>senha.txt
type senha.txt
certutil -hashfile senha.txt SHA256
```

O comando exibirá um valor hexadecimal parecido com este formato:

```text
9f 86 d0 81 88 4c 7d 65 ...
```

O valor exato será diferente do trecho acima. O importante é observar que o resultado possui tamanho fixo.

Execute novamente sem alterar o arquivo:

```bat
certutil -hashfile senha.txt SHA256
```

O resultado será igual, porque a entrada é a mesma.

Agora altere apenas um caractere:

```bat
echo MinhaSenha124>senha.txt
certutil -hashfile senha.txt SHA256
```

O novo hash será completamente diferente. Isso demonstra três características:

1. a mesma entrada gera o mesmo resultado;
2. uma pequena mudança na entrada altera bastante o hash;
3. o resultado possui tamanho fixo, mesmo quando a entrada muda de tamanho.

Ao terminar a demonstração, apague o arquivo:

```bat
del senha.txt
cd ..
rmdir aula-hash
```

> **Importante:** use uma senha inventada na demonstração. O comando grava o texto em um arquivo. Não utilize uma senha real.

O `certutil` demonstra a ideia de hash, mas o SHA-256 puro **não é uma solução adequada para armazenar senhas**. Ele foi projetado para ser rápido. Um atacante pode testar uma grande quantidade de senhas em pouco tempo.

#### Demonstração 2 — SHA-256 com a biblioteca padrão do Java

O próprio JDK possui a classe:

```text
java.security.MessageDigest
```

Ela faz parte da biblioteca padrão do Java. Não é necessário baixar uma dependência.

Crie o arquivo `HashSimples.java`:

```java
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

public class HashSimples {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println(
                    "Uso: java HashSimples <texto>");
            return;
        }

        MessageDigest algoritmo =
                MessageDigest.getInstance("SHA-256");

        byte[] hash = algoritmo.digest(
                args[0].getBytes(StandardCharsets.UTF_8));

        String hexadecimal =
                HexFormat.of().formatHex(hash);

        System.out.println(hexadecimal);
    }
}
```

No CMD, na pasta em que o arquivo foi salvo, compile e execute:

```bat
javac HashSimples.java
java HashSimples MinhaSenha123
java HashSimples MinhaSenha123
java HashSimples MinhaSenha124
```

As duas primeiras execuções produzirão o mesmo resultado. A terceira produzirá outro hash.

O fluxo do programa é:

```text
texto informado
      ↓
conversão para bytes UTF-8
      ↓
MessageDigest com SHA-256
      ↓
32 bytes de resultado
      ↓
representação hexadecimal para exibição
```

Esse exemplo serve para ensinar o conceito e pode ser usado para verificar integridade de arquivos ou mensagens. Ele **não deve ser copiado como armazenamento de senha**, porque não utiliza uma função adaptativa própria para senhas nem gera um sal individual.

#### Biblioteca Java indicada para o exemplo de senha

Uma opção conhecida no ecossistema Java é:

```text
Spring Security Crypto
Maven: org.springframework.security:spring-security-crypto
Interface: org.springframework.security.crypto.password.PasswordEncoder
Implementação: org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
```

O `BCryptPasswordEncoder` utiliza bcrypt, que é deliberadamente mais lento que SHA-256 e inclui um **sal aleatório** na geração do resultado.

Exemplo:

```java
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SenhaComBCrypt {
    public static void main(String[] args) {
        PasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String senhaDigitada = "MinhaSenha123";
        String hash = encoder.encode(senhaDigitada);

        System.out.println("Hash: " + hash);
        System.out.println(
                "Senha correta? "
                + encoder.matches(senhaDigitada, hash));
        System.out.println(
                "Senha errada? "
                + encoder.matches("OutraSenha", hash));
    }
}
```

Saída esperada:

```text
Hash: $2a$10$...
Senha correta? true
Senha errada? false
```

Se o programa for executado novamente, o hash provavelmente será diferente, mesmo usando a mesma senha. Isso acontece por causa do sal aleatório. Ainda assim, `matches` consegue verificar a senha corretamente.

Por isso, não faça esta comparação:

```java
encoder.encode(senhaDigitada).equals(hashSalvo)
```

Use:

```java
encoder.matches(senhaDigitada, hashSalvo)
```

Em um projeto Maven administrado pelo Spring Boot, a dependência pode ser declarada sem fixar manualmente uma versão, pois o Spring Boot gerencia versões compatíveis:

```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
</dependency>
```

Em um projeto Maven sem Spring Boot, é necessário informar uma versão compatível com o JDK e com as outras bibliotecas utilizadas.

#### Resumo das duas demonstrações

| Exemplo | Ferramenta | Objetivo |
| --- | --- | --- |
| Hash de arquivo no CMD | `certutil` com SHA-256 | Visualizar o funcionamento de um hash |
| Hash simples em Java | `java.security.MessageDigest` | Aprender como o Java calcula SHA-256 |
| Armazenamento de senha | `spring-security-crypto` | Utilizar função própria para senhas |

> **Frase para reforçar em sala:** SHA-256 mostra como um hash funciona; bcrypt mostra como uma senha deve ser protegida.

### 7.2 Hash e criptografia não são a mesma coisa

| Técnica | Ideia principal | Exemplo de uso |
| --- | --- | --- |
| **Hash de senha** | Produz um verificador; não precisamos recuperar a senha | Senhas de usuários |
| **Criptografia** | Protege um conteúdo que poderá ser recuperado com uma chave | Documento que o sistema precisa consultar depois |

Uma comparação simples:

- **hash de senha:** como conferir uma impressão digital sem guardar a mão;
- **criptografia:** como guardar um documento dentro de um cofre que possui chave.

### 7.3 Segredos não pertencem ao código

Código inseguro:

```java
String senhaBanco = "admin123";
String chaveApi = "abc-123-segredo";
```

Se o código for enviado ao Git, os segredos também serão enviados.

Devem ficar fora do código:

- senha do banco;
- chave de API;
- token de acesso;
- chave de criptografia;
- credencial de serviço.

Em ambiente de estudo, variáveis de ambiente já ajudam a separar configuração e código:

```java
String senhaBanco = System.getenv("SENHA_BANCO");
```

Em produção, normalmente são usados serviços próprios para guardar e controlar segredos.

### 7.4 Criptografia não substitui permissão

Um banco criptografado ainda precisa de controle de acesso. Quando a aplicação autorizada lê o dado, ele é descriptografado.

Se uma conta com permissão for roubada, o atacante poderá utilizar essa mesma permissão. Por isso, criptografia e autorização trabalham juntas.

> **Relação com a LGPD:** o art. 46 exige medidas técnicas aptas a proteger dados pessoais. Hash apropriado de senha, criptografia, proteção de chaves e controle de acesso são exemplos de medidas técnicas. A lei não determina uma única biblioteca; a escolha deve considerar o risco, o estado da técnica e o contexto da aplicação.

---

## 8. Cuidado com os logs

Logs ajudam a entender o que aconteceu no sistema.

Exemplos úteis:

- início e fim de uma operação;
- tentativa de acesso negada;
- erro de conexão;
- identificador de uma requisição;
- data e resultado de uma ação importante.

Mas logs também podem causar vazamentos.

### 8.1 Exemplo perigoso

```java
System.out.println(
        "Login: email=" + email + ", senha=" + senha);
```

A senha acabou de ser copiada para outro lugar.

Outro exemplo perigoso:

```java
System.out.println("Usuário recebido: " + usuario);
```

Se `usuario.toString()` incluir CPF, telefone ou senha, todas essas informações irão para o log.

### 8.2 Exemplo melhor

```java
System.out.println(
        "Tentativa de login. usuarioId=" + usuarioId
        + ", resultado=NEGADO");
```

O registro contém informações suficientes para investigar a tentativa sem copiar a senha.

### 8.3 O que não deve aparecer em logs

Evite registrar:

- senhas;
- tokens de acesso;
- número completo de cartão;
- chaves de API;
- documentos completos;
- conteúdo de objetos inteiros;
- dados pessoais que não ajudam na investigação.

### 8.4 Logs também precisam de proteção

Logs precisam de:

- acesso restrito;
- prazo de retenção;
- proteção contra alteração;
- descarte seguro;
- monitoramento.

> **Relação com a LGPD:** logs mínimos e protegidos ajudam na segurança do art. 46 e na prestação de contas do art. 6º, X. Entretanto, quando um log contém identificador, endereço, e-mail ou outro dado relacionado a uma pessoa, o próprio log também está sujeito às regras de proteção e retenção.

---

## 9. Organizando uma aplicação simples

Uma aplicação iniciante costuma misturar muitas tarefas na mesma classe:

```java
public void concluirCorrida(Corrida corrida) {
    // verifica usuário
    // calcula preço
    // altera estado
    // salva no banco
    // envia e-mail
    // escreve log
}
```

Quando tudo está junto, fica difícil verificar:

- onde a permissão é testada;
- quais dados são enviados;
- onde um dado pessoal é armazenado;
- qual parte deve ser corrigida;
- como testar uma regra isoladamente.

### 9.1 Uma separação inicial

Podemos começar com três partes:

```text
Controller -> Service -> Repository
```

#### Controller

Recebe a requisição e devolve a resposta.

```java
public class CorridaController {
    private CorridaService service;

    public PerfilCorrida consultar(long corridaId, long usuarioId) {
        return service.consultar(corridaId, usuarioId);
    }
}
```

#### Service

Executa as regras da aplicação, incluindo a verificação de permissão.

```java
public class CorridaService {
    private CorridaRepository repositorio;

    public PerfilCorrida consultar(long corridaId, long usuarioId) {
        Corrida corrida = repositorio.buscar(corridaId);

        if (corrida.getPassageiroId() != usuarioId) {
            throw new SecurityException("Acesso não permitido");
        }

        return new PerfilCorrida(
                corrida.getId(),
                corrida.getStatus(),
                corrida.getValor());
    }
}
```

#### Repository

Cuida do acesso aos dados.

```java
public class CorridaRepository {
    public Corrida buscar(long corridaId) {
        // consulta ao banco de dados
        return null;
    }
}
```

### 9.2 O fluxo completo

```text
1. Controller recebe o identificador da corrida
2. Service pede a corrida ao Repository
3. Service verifica se o usuário tem permissão
4. Service cria uma resposta com poucos campos
5. Controller devolve a resposta
```

Essa separação não torna a aplicação automaticamente segura. Ela apenas deixa as responsabilidades mais visíveis e facilita a aplicação dos controles.

> **Relação com a LGPD:** organizar as responsabilidades ajuda a cumprir o art. 46, § 2º, porque a proteção é considerada durante a concepção da aplicação. Também facilita localizar onde dados pessoais entram, são utilizados e saem do sistema.

### 9.3 Não comece com complexidade desnecessária

Uma aplicação pequena não precisa começar com dezenas de serviços, filas e bancos diferentes.

Quanto mais partes o sistema tiver, mais lugares existirão para:

- copiar dados;
- configurar permissões;
- guardar segredos;
- gerar logs;
- cometer erros.

Primeiro construa limites claros em uma aplicação simples. Distribua o sistema somente quando existir uma necessidade real.

---

## 10. Outras técnicas úteis

Além do DTO, algumas técnicas simples ajudam a produzir código mais claro e seguro. Elas não precisam ser usadas em todas as classes. Devem ser aplicadas quando resolvem um problema real.

### 10.1 Mapper: transformar uma entidade em DTO

No exemplo anterior, o próprio `Service` criou o objeto de resposta:

```java
return new PerfilCorrida(
        corrida.getId(),
        corrida.getStatus(),
        corrida.getValor());
```

Se essa conversão começar a aparecer em vários lugares, podemos colocá-la em uma classe chamada **Mapper**.

> **Mapper, em linguagem simples:** é uma classe que transforma um tipo de objeto em outro.

```java
public class CorridaMapper {

    public PerfilCorrida paraPerfil(Corrida corrida) {
        return new PerfilCorrida(
                corrida.getId(),
                corrida.getStatus(),
                corrida.getValor());
    }
}
```

O `Service` passa a utilizar o Mapper:

```java
public PerfilCorrida consultar(long corridaId, long usuarioId) {
    Corrida corrida = repositorio.buscar(corridaId);

    if (corrida.getPassageiroId() != usuarioId) {
        throw new SecurityException("Acesso não permitido");
    }

    return mapper.paraPerfil(corrida);
}
```

Benefícios:

- a transformação fica em um lugar conhecido;
- reduz repetição;
- facilita controlar quais campos podem sair;
- uma mudança na resposta não precisa alterar a entidade.

Não é necessário criar um Mapper para toda classe. Em conversões muito pequenas e usadas uma única vez, isso pode gerar complexidade sem benefício.

### 10.2 Enum: limitar valores permitidos

Este código aceita qualquer texto:

```java
corrida.setStatus("CONCLUIDA");
```

Alguém pode escrever:

```java
corrida.setStatus("CONCLUIDAA");
```

O programa compila, mas o estado está errado. Podemos definir os valores permitidos com um `enum`:

```java
public enum StatusCorrida {
    SOLICITADA,
    ACEITA,
    EM_ANDAMENTO,
    CONCLUIDA,
    CANCELADA
}
```

A classe `Corrida` usa esse tipo:

```java
public class Corrida {
    private StatusCorrida status;

    public void iniciar() {
        status = StatusCorrida.EM_ANDAMENTO;
    }
}
```

Benefícios:

- impede textos inventados;
- facilita descobrir os estados possíveis;
- reduz comparações frágeis com `String`;
- torna o código mais fácil de ler.

### 10.3 Objeto de valor: proteger uma informação

Uma `String` pode conter qualquer texto. Se o e-mail for representado somente por `String`, valores inválidos podem circular pelo sistema:

```java
String email = "isto nao e um email";
```

Podemos criar uma classe que representa e valida o e-mail:

```java
public class Email {
    private final String valor;

    public Email(String valor) {
        if (valor == null || !valor.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido");
        }

        this.valor = valor.trim().toLowerCase();
    }

    public String getValor() {
        return valor;
    }
}
```

Uso:

```java
Email email = new Email("ana@exemplo.com");
```

Esse tipo de classe é frequentemente chamado de **objeto de valor** ou *Value Object*.

> **Objeto de valor, em linguagem simples:** é uma classe pequena que representa uma informação importante e protege suas regras.

Outros exemplos possíveis:

- `Telefone`;
- `Dinheiro`;
- `Distancia`;
- `Coordenada`;
- `Documento`.

O exemplo de e-mail foi simplificado para fins didáticos. Uma validação real não deve tentar reproduzir todas as regras de e-mail manualmente.

### 10.4 Validação na entrada

Dados recebidos de usuários ou de outros sistemas não devem ser considerados confiáveis.

Exemplo sem validação:

```java
public void solicitarCorrida(double distancia, String destino) {
    // continua a operação
}
```

O método aceitaria distância negativa ou destino vazio. Uma validação inicial evita que valores inválidos cheguem às regras internas:

```java
public void solicitarCorrida(double distancia, String destino) {
    if (distancia <= 0) {
        throw new IllegalArgumentException(
                "A distância deve ser maior que zero");
    }

    if (destino == null || destino.isBlank()) {
        throw new IllegalArgumentException(
                "O destino é obrigatório");
    }

    // continua a operação
}
```

É importante validar:

- campos obrigatórios;
- tamanho máximo de textos;
- números negativos ou fora do limite;
- valores permitidos;
- formatos esperados.

Validar a entrada ajuda na segurança, mas também melhora a qualidade geral do programa.

### 10.5 Injeção de dependência pelo construtor

Considere uma classe que cria sozinha o objeto de acesso ao banco:

```java
public class CorridaService {
    private CorridaRepository repositorio =
            new CorridaRepository();
}
```

O `Service` ficou preso a essa implementação. Uma alternativa é receber a dependência pelo construtor:

```java
public class CorridaService {
    private final CorridaRepository repositorio;

    public CorridaService(CorridaRepository repositorio) {
        this.repositorio = repositorio;
    }
}
```

Na criação da aplicação:

```java
CorridaRepository repositorio = new CorridaRepository();
CorridaService service = new CorridaService(repositorio);
```

> **Injeção de dependência, em linguagem simples:** em vez de uma classe criar tudo o que utiliza, ela recebe os objetos de que precisa.

Benefícios:

- deixa as dependências visíveis;
- facilita trocar implementações;
- permite utilizar uma versão falsa do repositório em testes;
- reduz o acoplamento entre as classes.

Não é necessário usar um framework para aplicar essa ideia. O próprio construtor já demonstra a técnica.

### 10.6 Exceções específicas

Usar apenas `Exception` ou `RuntimeException` não explica claramente o problema:

```java
throw new RuntimeException("Erro");
```

Podemos criar uma exceção com significado para a aplicação:

```java
public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException() {
        super("Acesso não permitido");
    }
}
```

Uso:

```java
if (corrida.getPassageiroId() != usuarioId) {
    throw new AcessoNegadoException();
}
```

Isso deixa o motivo da falha mais claro. A camada que recebe a exceção pode transformá-la em uma resposta segura, sem mostrar detalhes internos ao usuário.

### 10.7 Como essas técnicas se relacionam

```text
Entrada da API
    ↓ validação
Controller
    ↓
Service recebido pelo construtor
    ↓ verifica autorização e regras
Repository
    ↓
Entidade com enum e objetos de valor
    ↓
Mapper
    ↓
DTO com somente os campos permitidos
```

Cada técnica resolve uma parte diferente:

| Técnica | Problema que ajuda a resolver |
| --- | --- |
| **DTO** | Evitar exposição de campos desnecessários |
| **Mapper** | Centralizar a transformação entre entidade e DTO |
| **Enum** | Impedir valores de estado inválidos |
| **Objeto de valor** | Manter validação e regra junto da informação |
| **Validação de entrada** | Barrar dados inválidos antes que se espalhem |
| **Injeção de dependência** | Reduzir acoplamento e facilitar testes |
| **Exceção específica** | Representar falhas de forma clara |

> **Relação com a LGPD:** essas técnicas não aparecem pelo nome na lei. Elas são meios de engenharia que ajudam a implementar necessidade, segurança, prevenção e prestação de contas. Usar um `enum` ou um Mapper não cria conformidade sozinho; o valor está em reduzir erros e tornar o fluxo de dados mais controlável.

---

## 11. Retenção e eliminação

Guardar um dado para sempre parece fácil, mas aumenta o risco.

Os **arts. 15 e 16 da LGPD** tratam do término do tratamento e das situações em que os dados podem ser conservados. O **art. 18** prevê direitos do titular que podem envolver correção, bloqueio, anonimização ou eliminação, conforme o caso.

Isso não significa que todo dado deve ser apagado imediatamente quando alguém pedir. Pode existir uma obrigação legal ou outra hipótese de conservação. A decisão precisa ser analisada pelos responsáveis adequados; o sistema precisa ser capaz de executar e demonstrar o que foi decidido.

Para cada informação pessoal, a organização deve saber:

1. por que o dado é guardado;
2. onde ele está;
3. quem pode acessá-lo;
4. por quanto tempo ele será mantido;
5. o que acontece quando não for mais necessário.

### 11.1 Apagar uma linha pode não ser suficiente

Os dados de um passageiro podem existir em vários lugares:

```text
banco principal
cache
histórico de corridas
logs
arquivos exportados
backups
sistema de pagamento
```

Por isso, um pedido de eliminação não deve ser implementado apenas assim:

```sql
DELETE FROM usuarios WHERE id = 10;
```

Antes é preciso descobrir:

- quais dados podem ser eliminados;
- quais precisam ser mantidos por uma obrigação aplicável;
- quais cópias e sistemas foram afetados;
- como impedir que um backup restaure um acesso que já foi removido.

Essas decisões precisam envolver responsáveis pelo negócio, proteção de dados e área jurídica. O programador implementa e testa o comportamento definido.

### 11.2 Separar identidade do histórico

Uma ideia útil é não repetir nome, CPF e telefone em cada corrida.

```text
USUARIO
id | nome | telefone

CORRIDA
id | passageiroId | origem | destino | valor
```

A tabela de corridas utiliza `passageiroId` em vez de copiar todos os dados do passageiro. Isso reduz duplicação, embora o identificador ainda seja um dado pessoal.

---

## 12. Incidentes de segurança

Um incidente não é somente um ataque de hacker.

O **art. 48 da LGPD** determina que o controlador comunique à ANPD e aos titulares a ocorrência de incidente que possa acarretar risco ou dano relevante. A comunicação não é automática para qualquer erro: o controlador precisa avaliar se dados pessoais foram afetados e se existe risco ou dano relevante.

A **Resolução CD/ANPD nº 15/2024** regulamenta esse processo. Quando a comunicação for aplicável, o prazo é de **três dias úteis**, contado do conhecimento de que o incidente afetou dados pessoais, ressalvada a existência de prazo específico em outra legislação. O regulamento também exige que o controlador mantenha o registro dos incidentes por pelo menos cinco anos.

Também podem ser incidentes:

- enviar dados para a pessoa errada;
- deixar um arquivo público por engano;
- perder um computador com dados pessoais;
- registrar senhas em logs;
- permitir que um usuário veja dados de outro;
- ter dados alterados ou apagados indevidamente.

### 12.1 O que a equipe precisa fazer

Um fluxo básico é:

```text
detectar -> conter -> investigar -> corrigir -> evitar repetição
```

Exemplo:

```text
Problema: passageiros conseguiam consultar corridas de outras pessoas.

Conter: bloquear temporariamente o endpoint.
Investigar: descobrir quais corridas foram acessadas e por quem.
Corrigir: adicionar autorização por usuário e corrida.
Recuperar: publicar a correção e acompanhar os acessos.
Prevenir: criar teste para garantir que o problema não volte.
```

O controlador é responsável por avaliar o risco e realizar comunicações aplicáveis à ANPD e aos titulares. Para isso, a equipe técnica precisa conseguir explicar quais dados foram afetados, durante quanto tempo e quais medidas foram tomadas.

> **Relação com a LGPD:** logs protegidos, inventário de dados e controle de versões ajudam a responder às perguntas exigidas durante a avaliação de um incidente. O programador normalmente não decide sozinho se haverá comunicação, mas precisa fornecer evidências técnicas corretas e agir rapidamente.

---

## 13. Resumo para o aluno

As principais ideias da aula são:

1. **Dado pessoal não é somente nome e CPF.** Localização, histórico e identificadores também podem revelar uma pessoa.
2. **Colete somente o necessário.** Um método não deve receber informações que não utiliza.
3. **Login não é permissão.** Depois de autenticar o usuário, ainda é preciso verificar o que ele pode fazer.
4. **Não devolva a entidade inteira.** Crie uma resposta contendo somente os campos necessários.
5. **DTO é apenas uma caixa de transporte.** Ele leva dados específicos entre partes do sistema.
6. **Nunca salve senha como texto.** Utilize biblioteca e função próprias para senhas.
7. **Não coloque segredos no código ou no Git.**
8. **Logs também vazam dados.** Registre somente o necessário.
9. **Separar responsabilidades ajuda a enxergar os controles.**
10. **Dados possuem ciclo de vida.** Eles são coletados, usados, armazenados e, quando aplicável, eliminados.
11. **Mapper transforma entidades em DTOs.** Ele ajuda a centralizar quais campos podem sair.
12. **Enum limita valores possíveis.** Ele evita estados escritos incorretamente.
13. **Objetos de valor protegem regras de uma informação.**
14. **Valide dados na entrada.** Não permita que valores inválidos se espalhem pela aplicação.
15. **Receber dependências pelo construtor reduz o acoplamento.**

### Checklist simples antes de publicar

- [ ] Estou coletando somente os dados necessários?
- [ ] O sistema verifica a permissão para cada operação importante?
- [ ] A resposta da API possui apenas os campos necessários?
- [ ] Os dados recebidos são validados antes de entrar nas regras da aplicação?
- [ ] Estados importantes utilizam valores limitados, como um `enum`?
- [ ] Senhas e chaves estão fora do código?
- [ ] Logs estão livres de senhas, tokens e dados excessivos?
- [ ] Sei onde os dados pessoais são armazenados?
- [ ] Existe uma forma de corrigir, bloquear ou eliminar dados quando necessário?
- [ ] Um erro mostra mensagem segura, sem detalhes internos do sistema?

---

## 14. Glossário básico

| Termo | Explicação simples |
| --- | --- |
| **Autenticação** | Processo de verificar quem é o usuário |
| **Autorização** | Processo de verificar o que o usuário pode fazer |
| **Criptografia** | Proteção de um conteúdo por meio de uma chave |
| **Dado pessoal** | Informação relacionada a uma pessoa identificada ou identificável |
| **DTO** | Objeto simples usado para transportar somente os dados necessários |
| **Enum** | Tipo que limita uma informação a um conjunto de valores permitidos |
| **Hash de senha** | Verificador produzido por função apropriada, sem guardar a senha original |
| **Injeção de dependência** | Técnica em que uma classe recebe os objetos de que precisa |
| **LGPD** | Lei Geral de Proteção de Dados Pessoais |
| **Log** | Registro de acontecimentos do sistema |
| **Mapper** | Classe que transforma um tipo de objeto em outro |
| **Menor privilégio** | Dar a cada usuário ou componente somente o acesso necessário |
| **Objeto de valor** | Classe pequena que representa uma informação e protege suas regras |
| **Repository** | Classe responsável pelo acesso aos dados |
| **Service** | Classe que executa regras e casos de uso da aplicação |

---

## 15. Referências

### Legislação e orientação brasileira

1. BRASIL. **Lei nº 13.709/2018 — Lei Geral de Proteção de Dados Pessoais (LGPD)**: <https://www.planalto.gov.br/ccivil_03/_ato2015-2018/2018/lei/l13709.htm>
2. ANPD. **Comunicação de Incidente de Segurança**: <https://www.gov.br/anpd/pt-br/canais_atendimento/agente-de-tratamento/comunicado-de-incidente-de-seguranca-cis>
3. ANPD. **Guia Orientativo sobre Segurança da Informação para Agentes de Tratamento de Pequeno Porte**: <https://www.gov.br/anpd/pt-br/centrais-de-conteudo/materiais-educativos-e-publicacoes/processo-guia-orientativo-sobre-seguranca-da-informacao-para-agentes-de-tratamento-de-pequeno-porte.pdf>

### Segurança de aplicações

4. OWASP. **API Security Top 10**: <https://owasp.org/www-project-api-security/>
5. OWASP. **Password Storage Cheat Sheet**: <https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html>
6. OWASP. **Logging Cheat Sheet**: <https://cheatsheetseries.owasp.org/cheatsheets/Logging_Cheat_Sheet.html>
7. ORACLE. **Java Security Overview — MessageDigest e SHA-256**: <https://docs.oracle.com/en/java/javase/17/security/java-security-overview1.html>
8. SPRING SECURITY. **Password Storage**: <https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html>
9. SPRING SECURITY. **Crypto Module — Password Encoding**: <https://docs.spring.io/spring-security/reference/features/integrations/cryptography.html>
