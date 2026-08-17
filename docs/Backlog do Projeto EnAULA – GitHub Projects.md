# Backlog do Projeto EnAULA – GitHub Projects

Este documento apresenta o backlog do projeto **EnAULA**, organizado para utilização no GitHub Projects.

As histórias de usuário abaixo são baseadas nas **18 histórias de usuário definidas para o projeto**, mantendo seus respectivos épicos, objetivos e critérios de aceitação.

Cada história contém:

- **ID**
- **História de usuário**
- **Épico**
- **MoSCoW**
- **Size**
- **Sprint**
- **Critérios de aceitação**
- **Tasks técnicas**

> **Observação:** MoSCoW, Size e Sprint são uma proposta de organização do backlog. Caso esses valores já tenham sido definidos pelo grupo no GitHub Projects, devem prevalecer os valores do grupo.

---

# 1. Visão geral do Backlog

| ID    | História                                 | Épico                 | MoSCoW | Size | Sprint   |
| ----- | ---------------------------------------- | --------------------- | ------ | ---- | -------- |
| HU001 | Cadastro de usuário                      | Autenticação          | Must   | M    | Sprint 1 |
| HU002 | Realização de login                      | Autenticação          | Must   | P    | Sprint 1 |
| HU003 | Busca de aulas por filtros               | Busca                 | Must   | M    | Sprint 1 |
| HU004 | Criação de aula                          | Aulas                 | Must   | M    | Sprint 1 |
| HU005 | Gerenciamento de materiais               | Aulas                 | Should | M    | Sprint 2 |
| HU006 | Gerenciamento de matrículas de alunos    | Aulas                 | Must   | M    | Sprint 2 |
| HU007 | Consultar histórico de aulas ministradas | Aulas                 | Should | M    | Sprint 2 |
| HU008 | Ministração de aula                      | Aulas                 | Must   | G    | Sprint 3 |
| HU009 | Solicitação de matrícula em aula         | Matrícula             | Must   | M    | Sprint 1 |
| HU010 | Pagamento de matrícula                   | Matrícula             | Must   | G    | Sprint 2 |
| HU011 | Cancelamento de matrícula                | Matrícula             | Should | M    | Sprint 3 |
| HU012 | Consultar histórico de aulas assistidas  | Matrícula             | Should | M    | Sprint 3 |
| HU013 | Avaliação de professor após a aula       | Avaliações            | Should | M    | Sprint 4 |
| HU014 | Avaliação de aluno após aula             | Avaliações            | Should | M    | Sprint 4 |
| HU015 | Denúncia de aula — aluno                 | Denúncias e Moderação | Should | M    | Sprint 4 |
| HU016 | Denúncia de aula — professor             | Denúncias e Moderação | Should | M    | Sprint 4 |
| HU017 | Efetuar reembolso                        | Denúncias e Moderação | Should | M    | Sprint 4 |
| HU018 | Avaliação de denúncia                    | Denúncias e Moderação | Should | M    | Sprint 4 |

# 2. Épico: Autenticação

## HU001 – Cadastro de usuário

**Épico:** Autenticação  
**MoSCoW:** Must  
**Size:** M  
**Sprint:** Sprint 1

### História de usuário

> **Como** usuário, **quero** realizar meu cadastro no EnAULA, **para** poder acessar as funcionalidades disponíveis na plataforma.

### Critérios de aceitação

- [ ] O usuário consegue preencher os dados necessários para realizar o cadastro.
- [ ] O sistema valida os dados informados.
- [ ] A senha é armazenada de forma segura.
- [ ] O sistema impede o cadastro de dados inválidos.
- [ ] O usuário recebe confirmação após o cadastro.
- [ ] O cadastro respeita as regras de privacidade e proteção de dados definidas para o projeto.

A HU001 contempla cadastro de usuário, segurança da senha e aspectos relacionados à LGPD.

### Tasks

- [ ] Criar tela de cadastro.
- [ ] Criar modelo de usuário.
- [ ] Implementar validação dos campos.
- [ ] Implementar armazenamento seguro da senha.
- [ ] Implementar persistência do usuário.
- [ ] Implementar mensagens de erro.
- [ ] Implementar confirmação de cadastro.
- [ ] Criar testes do cadastro.

---

## HU002 – Realização de login

**Épico:** Autenticação  
**MoSCoW:** Must  
**Size:** P  
**Sprint:** Sprint 1

### História de usuário

> **Como** usuário cadastrado, **quero** realizar login no EnAULA, **para** acessar minha conta e utilizar as funcionalidades da plataforma.

### Critérios de aceitação

- [ ] O usuário consegue informar e-mail e senha.
- [ ] O sistema valida as credenciais.
- [ ] O acesso é permitido quando as credenciais são válidas.
- [ ] O acesso é negado quando as credenciais são inválidas.
- [ ] A comunicação ocorre utilizando HTTPS.
- [ ] O processamento do login atende ao tempo estabelecido na história.
- [ ] A mensagem de erro não expõe informações sensíveis.

Esses critérios incluem os requisitos de HTTPS, tempo de processamento e tratamento genérico de erro definidos na HU002.

### Tasks

- [ ] Criar tela de login.
- [ ] Implementar autenticação.
- [ ] Implementar validação de credenciais.
- [ ] Implementar controle de sessão.
- [ ] Implementar tratamento de credenciais inválidas.
- [ ] Configurar HTTPS.
- [ ] Criar testes de login.

---

# 3. Épico: Busca

## HU003 – Busca de aulas por filtros

**Épico:** Busca  
**MoSCoW:** Must  
**Size:** M  
**Sprint:** Sprint 1

### História de usuário

> **Como** aluno, **quero** buscar aulas utilizando filtros, **para** encontrar uma aula adequada às minhas necessidades.

### Critérios de aceitação

- [ ] O usuário consegue pesquisar aulas.
- [ ] É possível utilizar os filtros definidos na história.
- [ ] Os resultados correspondem aos filtros selecionados.
- [ ] A busca respeita as regras definidas para aulas presenciais e online.
- [ ] O sistema informa quando não existem resultados.
- [ ] Os resultados são apresentados de forma clara.

A HU003 possui regras específicas para filtros e raio de busca de aulas presenciais e online.

### Tasks

- [ ] Criar tela de busca.
- [ ] Criar filtros.
- [ ] Implementar consulta das aulas.
- [ ] Implementar regras de localização.
- [ ] Implementar resultados.
- [ ] Implementar mensagem para nenhum resultado.
- [ ] Criar testes de busca.

---

# 4. Épico: Aulas

## HU004 – Criação de aula

**Épico:** Aulas  
**MoSCoW:** Must  
**Size:** M  
**Sprint:** Sprint 1

### História de usuário

> **Como** professor, **quero** criar uma aula, **para** disponibilizá-la para os alunos.

### Tasks

- [ ] Criar formulário de aula.
- [ ] Implementar campos necessários.
- [ ] Validar informações.
- [ ] Associar aula ao professor.
- [ ] Salvar aula.
- [ ] Disponibilizar aula para busca.
- [ ] Criar testes.

---

## HU005 – Gerenciamento de materiais

**Épico:** Aulas  
**MoSCoW:** Should  
**Size:** M  
**Sprint:** Sprint 2

### História de usuário

> **Como** professor, **quero** gerenciar os materiais das minhas aulas, **para** disponibilizar conteúdo aos alunos.

### Tasks

- [ ] Criar área de materiais.
- [ ] Permitir adicionar materiais.
- [ ] Validar arquivos.
- [ ] Associar materiais às aulas.
- [ ] Permitir consultar materiais.
- [ ] Permitir alterações ou remoções quando aplicável.
- [ ] Criar testes.

---

## HU006 – Gerenciamento de matrículas de alunos

**Épico:** Aulas  
**MoSCoW:** Must  
**Size:** M  
**Sprint:** Sprint 2

### História de usuário

> **Como** professor, **quero** gerenciar as matrículas dos alunos nas minhas aulas, **para** controlar os participantes.

### Tasks

- [ ] Criar tela de matrículas.
- [ ] Exibir solicitações.
- [ ] Permitir aprovar matrícula.
- [ ] Permitir recusar matrícula.
- [ ] Atualizar status.
- [ ] Notificar aluno.
- [ ] Criar testes.

---

## HU007 – Consultar histórico de aulas ministradas

**Épico:** Aulas  
**MoSCoW:** Should  
**Size:** M  
**Sprint:** Sprint 2

### História de usuário

> **Como** professor, **quero** consultar o histórico das aulas ministradas, **para** acompanhar as aulas que já realizei.

### Tasks

- [ ] Criar tela de histórico.
- [ ] Recuperar aulas ministradas.
- [ ] Exibir informações das aulas.
- [ ] Permitir visualizar materiais relacionados.
- [ ] Permitir visualizar gravações quando disponíveis.
- [ ] Criar testes.

---

## HU008 – Ministração de aula

**Épico:** Aulas  
**MoSCoW:** Must  
**Size:** G  
**Sprint:** Sprint 3

### História de usuário

> **Como** professor, **quero** ministrar uma aula pelo EnAULA, **para** realizar a aula com os alunos matriculados.

### Critérios de aceitação

- [ ] A aula pode ser iniciada no horário previsto.
- [ ] Apenas participantes autorizados conseguem acessar.
- [ ] A aula é gravada conforme definido.
- [ ] A gravação é armazenada.
- [ ] O sistema trata problemas relacionados à transmissão.
- [ ] O chat e os recursos previstos funcionam corretamente.

A HU008 possui requisitos específicos de gravação automática, tolerância de atraso e monitoramento do chat.

### Tasks

- [ ] Criar sala de aula online.
- [ ] Implementar controle de acesso.
- [ ] Integrar sistema de gravação.
- [ ] Implementar gravação automática.
- [ ] Implementar chat.
- [ ] Implementar encerramento da aula.
- [ ] Armazenar gravação.
- [ ] Criar testes.

---

# 5. Épico: Matrícula

## HU009 – Solicitação de matrícula em aula

**Épico:** Matrícula  
**MoSCoW:** Must  
**Size:** M  
**Sprint:** Sprint 1

### História de usuário

> **Como** aluno, **quero** solicitar matrícula em uma aula, **para** participar da aula escolhida.

### Tasks

- [ ] Permitir seleção da aula.
- [ ] Verificar disponibilidade.
- [ ] Criar solicitação.
- [ ] Registrar solicitação.
- [ ] Notificar professor.
- [ ] Exibir status da solicitação.
- [ ] Criar testes.

---

## HU010 – Pagamento de matrícula

**Épico:** Matrícula  
**MoSCoW:** Must  
**Size:** G  
**Sprint:** Sprint 2

### História de usuário

> **Como** aluno, **quero** pagar minha matrícula, **para** confirmar minha participação na aula.

### Tasks

- [ ] Criar tela de pagamento.
- [ ] Integrar gateway de pagamento.
- [ ] Processar pagamento.
- [ ] Registrar transação.
- [ ] Atualizar status da matrícula.
- [ ] Tratar pagamento recusado.
- [ ] Criar testes.

---

## HU011 – Cancelamento de matrícula

**Épico:** Matrícula  
**MoSCoW:** Should  
**Size:** M  
**Sprint:** Sprint 3

### História de usuário

> **Como** aluno, **quero** cancelar minha matrícula, **para** desistir da aula dentro das condições permitidas.

### Tasks

- [ ] Criar opção de cancelamento.
- [ ] Verificar prazo permitido.
- [ ] Atualizar status.
- [ ] Calcular condições de reembolso quando aplicável.
- [ ] Registrar cancelamento.
- [ ] Notificar usuário.
- [ ] Criar testes.

---

## HU012 – Consultar histórico de aulas assistidas

**Épico:** Matrícula  
**MoSCoW:** Should  
**Size:** M  
**Sprint:** Sprint 3

### História de usuário

> **Como** aluno, **quero** consultar meu histórico de aulas assistidas, **para** acompanhar as aulas das quais participei.

### Tasks

- [ ] Criar tela de histórico.
- [ ] Recuperar aulas assistidas.
- [ ] Exibir informações das aulas.
- [ ] Permitir acesso aos conteúdos disponíveis.
- [ ] Permitir acesso às gravações quando disponíveis.
- [ ] Criar testes.

---

# 6. Épico: Avaliações

## HU013 – Avaliação de professor após a aula

**Épico:** Avaliações  
**MoSCoW:** Should  
**Size:** M  
**Sprint:** Sprint 4

### História de usuário

> **Como** aluno, **quero** avaliar o professor após a aula, **para** registrar minha experiência e contribuir para sua avaliação.

### Critérios de aceitação

- [ ] Apenas alunos que participaram da aula podem avaliar.
- [ ] A avaliação ocorre após a conclusão da aula.
- [ ] O aluno pode informar a nota.
- [ ] O aluno pode registrar comentário quando previsto.
- [ ] Avaliações abaixo de 3 estrelas seguem as regras definidas.
- [ ] A avaliação é armazenada.

A HU013 estabelece regras específicas para avaliações inferiores a 3 estrelas.

### Tasks

- [ ] Criar formulário de avaliação.
- [ ] Validar participação do aluno.
- [ ] Implementar nota.
- [ ] Implementar comentário.
- [ ] Implementar regras para notas baixas.
- [ ] Salvar avaliação.
- [ ] Criar testes.

---

## HU014 – Avaliação de aluno após aula

**Épico:** Avaliações  
**MoSCoW:** Should  
**Size:** M  
**Sprint:** Sprint 4

### História de usuário

> **Como** professor, **quero** avaliar o aluno após a aula, **para** registrar minha experiência com o aluno.

### Tasks

- [ ] Criar formulário de avaliação.
- [ ] Validar participação do aluno.
- [ ] Implementar nota.
- [ ] Implementar comentário.
- [ ] Salvar avaliação.
- [ ] Garantir independência entre as avaliações.
- [ ] Criar testes.

A HU014 estabelece que a avaliação do professor deve ser independente da avaliação feita pelo aluno.

---

# 7. Épico: Denúncias e Moderação

## HU015 – Denúncia de aula pelo aluno

**Épico:** Denúncias e Moderação  
**MoSCoW:** Should  
**Size:** M  
**Sprint:** Sprint 4

### História de usuário

> **Como** aluno, **quero** denunciar uma aula, **para** informar um comportamento inadequado ou uma irregularidade.

### Tasks

- [ ] Criar formulário de denúncia.
- [ ] Permitir selecionar o motivo.
- [ ] Permitir descrição.
- [ ] Registrar denúncia.
- [ ] Associar denúncia à aula.
- [ ] Disponibilizar denúncia para análise.
- [ ] Registrar log da denúncia.
- [ ] Criar testes.

---

## HU016 – Denúncia de aula pelo professor

**Épico:** Denúncias e Moderação  
**MoSCoW:** Should  
**Size:** M  
**Sprint:** Sprint 4

### História de usuário

> **Como** professor, **quero** denunciar uma aula ou comportamento inadequado de um aluno, **para** informar a administração do EnAULA.

### Tasks

- [ ] Criar formulário de denúncia para professor.
- [ ] Permitir selecionar o aluno/aula.
- [ ] Permitir informar motivo.
- [ ] Registrar denúncia.
- [ ] Disponibilizar denúncia para administração.
- [ ] Registrar log.
- [ ] Criar testes.

---

## HU017 – Efetuar reembolso

**Épico:** Denúncias e Moderação  
**MoSCoW:** Should  
**Size:** M  
**Sprint:** Sprint 4

### História de usuário

> **Como** usuário responsável pelo processo, **quero** efetuar o reembolso de uma matrícula quando aplicável, **para** devolver o valor devido ao aluno.

### Tasks

- [ ] Identificar pagamentos elegíveis para reembolso.
- [ ] Validar condições do reembolso.
- [ ] Integrar com o gateway.
- [ ] Solicitar reembolso.
- [ ] Registrar transação.
- [ ] Atualizar status.
- [ ] Notificar usuário.
- [ ] Criar testes.

---

## HU018 – Avaliação de denúncia

**Épico:** Denúncias e Moderação  
**MoSCoW:** Should  
**Size:** M  
**Sprint:** Sprint 4

### História de usuário

> **Como** administrador, **quero** avaliar uma denúncia, **para** decidir quais medidas devem ser tomadas.

### Critérios de aceitação

- [ ] O administrador consegue visualizar a denúncia.
- [ ] O administrador consegue consultar as informações relacionadas.
- [ ] Quando necessário, a gravação da aula pode ser utilizada como evidência.
- [ ] O administrador pode arquivar a denúncia.
- [ ] O administrador pode aplicar as medidas previstas.
- [ ] A decisão é registrada.
- [ ] As ações realizadas ficam registradas em log.

A HU018 prevê a análise da denúncia, utilização das informações disponíveis como evidência e registro das ações tomadas.

### Tasks

- [ ] Criar painel administrativo de denúncias.
- [ ] Listar denúncias.
- [ ] Criar tela de detalhes.
- [ ] Permitir consultar evidências.
- [ ] Implementar decisão administrativa.
- [ ] Implementar arquivamento.
- [ ] Registrar ações em log.
- [ ] Criar testes.

---

# Dependências principais

Para o GitHub Projects, recomendo também registrar as dependências entre as histórias.

HU001 Cadastro

      ↓

HU002 Login

      ↓

HU003 Busca de aulas

      ↓

HU009 Solicitação de matrícula

      ↓

HU006 Gerenciamento de matrículas

      ↓

HU010 Pagamento

      ↓

HU008 Ministração da aula

      ↓

HU012 Histórico / HU013 Avaliação

Para o professor:

HU001 Cadastro

      ↓

HU002 Login

      ↓

HU004 Criação de aula

      ↓

HU006 Gerenciamento de matrículas

      ↓

HU008 Ministração

      ↓

HU007 Histórico / HU014 Avaliação

Para moderação:

HU015 Denúncia do aluno

          ↓

HU016 Denúncia do professor

          ↓

HU018 Avaliação da denúncia

          ↓

HU017 Reembolso

Essas dependências são uma **proposta de planejamento baseada na relação funcional entre as histórias**; o arquivo das HUs não fornece explicitamente uma ordem de Sprint para elas.

---

# 


