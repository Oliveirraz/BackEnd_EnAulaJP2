# Definition of Done – EnAULA

A Definition of Done (DoD) define os critérios que **toda história de usuário do projeto EnAULA** precisa cumprir para ser considerada concluída. Uma história só pode ser marcada como **"Done"** quando todos os critérios necessários tiverem sido atendidos.

A DoD complementa a Definition of Ready: enquanto a DoR verifica se a história está preparada para entrar em uma Sprint, a DoD verifica se ela foi efetivamente implementada, testada e integrada ao projeto.

---

## Critérios gerais

### Funcionalidade

- [ ] A funcionalidade foi implementada de acordo com a história de usuário.
- [ ] Todos os critérios de aceitação da história foram atendidos.
- [ ] O fluxo principal da funcionalidade foi implementado corretamente.
- [ ] Os fluxos alternativos previstos foram tratados.
- [ ] Os fluxos de exceção previstos foram tratados.
- [ ] As pré-condições da funcionalidade são respeitadas.
- [ ] As pós-condições esperadas são alcançadas.
- [ ] Os casos de erro previstos foram tratados.
- [ ] A funcionalidade está relacionada ao respectivo caso de uso ou requisito funcional do EnAULA.

### Qualidade do código

- [ ] O código implementado segue a arquitetura definida para o EnAULA.
- [ ] O código foi revisado por pelo menos outro membro da equipe.
- [ ] Não existem trechos de código desnecessários relacionados à implementação da história.
- [ ] As classes, métodos, funções e variáveis possuem nomes claros e coerentes.
- [ ] A implementação mantém a separação de responsabilidades definida na arquitetura.
- [ ] A alteração não introduz erros conhecidos em outras funcionalidades.
- [ ] O código está integrado aos componentes necessários do sistema.

A arquitetura definida para o EnAULA separa componentes como **Front-end, API REST, Controllers, Services, DAO e DTO**, além do banco de dados e integrações externas.

### Testes

- [ ] A funcionalidade foi testada após sua implementação.
- [ ] O fluxo principal foi testado.
- [ ] Os principais fluxos alternativos foram testados quando aplicável.
- [ ] Os principais cenários de erro foram testados.
- [ ] Os critérios de aceitação foram verificados.
- [ ] Nenhum teste existente relacionado à funcionalidade foi quebrado.
- [ ] Quando aplicável, foi realizado teste de aceitação da história.
- [ ] Os resultados dos testes estão de acordo com o comportamento esperado.

### Interface

Quando a história possuir interação com o usuário:

- [ ] A interface implementada corresponde ao fluxo definido para a funcionalidade.
- [ ] Os elementos previstos no protótipo foram considerados.
- [ ] Os campos necessários estão disponíveis.
- [ ] Os botões e ações necessários funcionam corretamente.
- [ ] As mensagens de sucesso e erro estão sendo apresentadas adequadamente.
- [ ] O fluxo de navegação está funcionando.
- [ ] A interface foi verificada quanto à consistência com os protótipos definidos para o projeto.

### Integração

- [ ] A funcionalidade está integrada aos componentes necessários do sistema.
- [ ] As comunicações entre os componentes envolvidos estão funcionando.
- [ ] Quando necessário, a funcionalidade está integrada ao banco de dados.
- [ ] Quando necessário, as integrações com serviços externos estão funcionando.
- [ ] A alteração foi integrada ao código principal após revisão da equipe.
- [ ] O Pull Request relacionado à história foi revisado e aprovado.
- [ ] O Pull Request está vinculado à história correspondente no GitHub Projects.

O projeto EnAULA prevê comunicação entre o Front-end, API REST, banco de dados e serviços externos, incluindo o Gateway de Pagamento e o Sistema de Gravação de Aulas.

### Rastreabilidade

- [ ] A história de usuário está vinculada ao respectivo caso de uso ou requisito funcional.
- [ ] Os critérios de aceitação foram verificados.
- [ ] Os testes realizados estão relacionados à funcionalidade implementada.
- [ ] O Pull Request está vinculado à história de usuário.
- [ ] O card da história no GitHub Projects foi atualizado.
- [ ] O card da história foi movido para a coluna **"Done"** após o cumprimento de todos os critérios.
- [ ] Os artefatos relacionados à funcionalidade foram atualizados quando necessário.

## O que NÃO faz parte da DoD

- Performance otimizada além do necessário para o funcionamento da funcionalidade.
- Documentação extensa de código que não seja necessária para a compreensão da implementação.
- Testes automatizados para todos os cenários possíveis.
- Implementação de funcionalidades que não fazem parte da história.
- Alterações não relacionadas à história apenas para "melhorar" outras partes do sistema.
- Aprovação externa que não esteja definida como requisito do projeto.

---

## Definition of Done

Uma história de usuário do **EnAULA** será considerada **Done** somente quando:

- [ ] A funcionalidade estiver implementada.
- [ ] Todos os critérios de aceitação forem atendidos.
- [ ] Os fluxos principal, alternativos e de exceção aplicáveis forem tratados.
- [ ] Os casos de erro relevantes forem tratados.
- [ ] O código tiver sido revisado por outro membro da equipe.
- [ ] A implementação estiver integrada à arquitetura do sistema.
- [ ] A interface estiver funcionando conforme o fluxo definido, quando aplicável.
- [ ] A funcionalidade tiver sido testada.
- [ ] Os critérios de aceitação tiverem sido verificados.
- [ ] Os testes não tiverem causado regressões conhecidas.
- [ ] As integrações necessárias estiverem funcionando.
- [ ] O Pull Request tiver sido aprovado.
- [ ] A história estiver vinculada ao respectivo Pull Request.
- [ ] A rastreabilidade com o requisito/caso de uso estiver mantida.
- [ ] O card no GitHub Projects estiver atualizado e movido para **Done**.

---

## Observação

A **Definition of Done aplica-se a todas as histórias do EnAULA**, independentemente do épico ou da Sprint.

Uma história **não será considerada Done apenas porque a funcionalidade foi implementada**. Ela também precisa atender aos critérios de aceitação, passar pelas verificações e testes definidos, estar integrada ao projeto e ter sua conclusão registrada no fluxo de desenvolvimento.

Dessa forma:

> **DoR = a história está pronta para começar.**  
> **DoD = a história está pronta para ser considerada concluída.**
