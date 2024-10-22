# Dynamic Scheduler Service - Prova de Conceito

Este projeto é uma Prova de Conceito (PoC) que demonstra como agendar tarefas automáticas em uma aplicação Java de forma dinâmica, utilizando expressões "cron" armazenadas em banco de dados e garantindo que apenas uma instância execute a tarefa de cada vez.

## Funcionalidades

- **Agendamento de Tarefas**: A aplicação agenda e executa tarefas automaticamente com base em uma expressão "cron" fornecida pelo banco de dados.
- **Execução Sincronizada**: Para evitar que a mesma tarefa seja executada simultaneamente por múltiplas instâncias da aplicação, a PoC utiliza o ShedLock para garantir que apenas uma instância realize a tarefa a qualquer momento.
- **Configuração Dinâmica**: As expressões "cron" podem ser alteradas diretamente no banco de dados e o sistema adapta a programação da tarefa sem a necessidade de reiniciar a aplicação.

## Tecnologias Utilizadas

- **Java 17+**
- **Spring Framework** para agendamento de tarefas.
- **ShedLock** para sincronização entre múltiplas instâncias.
- **ThreadPoolTaskScheduler** para gerenciamento de agendamentos.
- **Banco de dados** para armazenar e gerenciar as expressões cron de forma dinâmica.

## Como Funciona

1. **Agendamento de Tarefas**: Ao iniciar, o serviço busca a expressão "cron" do banco de dados usando o método `generalSettingsService.getCronExpressionFromDB()`.
2. **Expressão Cron**: A expressão "cron" define os intervalos de execução da tarefa. Se for uma expressão inválida, a aplicação lança uma exceção.
3. **Execução com Lock**: A tarefa é executada com um "lock" para evitar que múltiplas instâncias a rodem ao mesmo tempo. O lock dura até 2 minutos, garantindo que a tarefa tenha tempo suficiente para ser concluída antes de ser liberada para outra execução.
4. **Flexibilidade**: A expressão "cron" pode ser atualizada dinamicamente no banco de dados, e a próxima execução seguirá a nova programação sem a necessidade de reinicializar a aplicação.

## Estrutura do Código

- `DynamicSchedulerService`: Serviço principal que gerencia o agendamento e execução das tarefas.
- `ThreadPoolTaskScheduler`: Gerencia as execuções das tarefas em segundo plano.
- `ShedLock`: Garante que a tarefa seja executada por apenas uma instância de cada vez.
- `GeneralSettingsService`: Serviço responsável por buscar a expressão "cron" do banco de dados.

## Exemplo de Uso

Abaixo, um exemplo básico da tarefa executada:

```java
private void executeTask() {
    System.out.println("Executando tarefa em " + LocalDateTime.now() + " com a expressão cron: " + cronExpression);
}
