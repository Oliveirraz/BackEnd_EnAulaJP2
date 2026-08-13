# Entrevistas - EnAULA

## Entrevista 1: Levantamento de requisitos com o idealizador do projeto

**Entrevistado:** Fágner de Oliveira Santos (Idealizador / Cliente Estrategista)

**Objetivo:** Levantar requisitos de negócio, funcionalidades e regras da plataforma a partir da visão do idealizador do EnAULA.

**Entrevistador**: Equipe EnAula

**Duração Aproximada**: 50 minutos

### Roteiro e respostas

**1. Todos os usuários cadastrados para lecionar serão professores, ou alunos de cursos superiores também podem se cadastrar como tutor?**

> A ideia inicial é que apenas professores possam se cadastrar na plataforma, ou pessoas que poderiam estar em sala de aula. Mas, caso o usuário seja aluno de um curso superior, ele também pode se candidatar a ministrar aula.

**2. Qual a duração mínima para as aulas?**

> A ideia é que a aula dure no mínimo 1 hora.

**3. Como o aplicativo gerará receita? Será porcentagem por aula? Será um valor fixo?**

> Uma tabela de preços dependendo do nível do tutor. Através disso, a plataforma ganha uma porcentagem sobre o valor da hora-aula.

**4. Quais aspectos dos aplicativos citados como exemplo (iFood, Uber, Tinder, Preply, Cambly) seriam do interesse a ser adotados na aplicação?**

> Que as matérias sejam divididas por áreas, e que o aluno consiga também filtrar por matéria, professor, valor da aula e distância.

**5. Qual é o principal diferencial do app em relação a outras plataformas de ensino online?**

> Conectar pessoas do interior do Brasil a ensino de qualidade.

**6. O foco será 100% ENEM ou também incluirá outros tipos de reforço escolar?**

> De início, a plataforma foca no ENEM, pois todo ano tem ENEM. Com o passar do tempo e o crescimento da plataforma, expandir para outras áreas.

**7. Qual será o raio padrão de busca por professores?**

> Caso a aula seja online, o raio é de 100km. Se a aula for presencial, o raio é de 5km.

**8. Quais informações obrigatórias o professor deve informar no perfil?**

> Poderia ser o Currículo Lattes, onde o professor carrega seu currículo, e também nome, formação, experiência, onde trabalha, diplomas, certificados e sua agenda.

**9. O professor poderá definir preço por aula livremente ou haverá padrão sugerido?**

> A plataforma já terá valores fixos, pelo menos em relação ao valor mínimo da aula.

**10. Como funcionará a agenda: o professor define horários fixos semanais ou horários flexíveis?**

> O professor precisa estabelecer previamente seus horários de aula, como uma agenda semanal ou mensal.

**11. O aluno poderá agendar automaticamente ou precisa de confirmação do professor?**

> O professor precisa confirmar que o aluno foi matriculado na aula.

**12. As aulas online serão feitas em algum aplicativo específico ou ficará a cargo do professor?**

> Seria interessante que a aula fosse dentro do próprio aplicativo, gravada automaticamente, e ficasse disponível por um tempo caso o aluno queira revê-la.

**13. Como funcionará a escolha entre aula individual e em grupo?**

> O professor pode criar uma aula individual por um valor X, e no caso de aula em grupo o valor diminui, pois será diluído entre mais pessoas.

**14. Quais medidas de segurança são essenciais?**

> Aulas gravadas por segurança, verificação de identidade, chat monitorado, bloqueio de usuários e botão de denúncia.

**15. Os alunos poderão avaliar os professores? Como funcionará o sistema de notas e comentários?**

> Será avaliação recíproca — todos avaliam. Caso a nota seja menor que 3 estrelas, será obrigatória uma justificativa do motivo da nota.

**16. Como funcionará o cancelamento de aulas e possíveis reembolsos?**

> Será estabelecido um prazo: caso o aluno cancele a aula, será descontado um valor proporcional ao tempo que ele ficou com a matrícula reservada. Haverá um limite de horas para que o aluno possa cancelar a matrícula e ainda receber uma porcentagem do valor pago.

**17. O sistema terá um painel administrativo? O que o administrador poderá gerenciar?**

> Sim, com permissões sobre usuários, denúncias, pagamentos e relatórios.

**18. O app deve recomendar professores com base no desempenho ou dificuldades do aluno?**

> Sim, um ranking com os melhores professores, dividido por matéria.

**19. Professores poderão enviar materiais (PDFs, exercícios, listas) dentro do app?**

> Sim. O professor é responsável por todas as informações que disponibiliza para o aluno.

**20. Quais regras devem existir para uso da plataforma (comportamento, atrasos, faltas, penalidades)?**

> As mesmas regras de uma escola. Haverá prazo para início da aula com uma tolerância de tempo — por exemplo, em uma aula em grupo com 5 alunos, se apenas 2 entrarem, será estipulado um tempo de 20 minutos; caso os demais não cheguem, a aula inicia e os atrasados não poderão mais entrar.

**21. O professor poderá criar a matéria que irá lecionar, ou serão as matérias que já existem no aplicativo?**

> Não. De início, serão apenas as matérias voltadas para o ENEM.

**22. O professor também poderá avaliar o aluno?**

> Sim, deve.

### Observações da equipe

- Confirmou o modelo de negócio: comissão da plataforma sobre uma tabela de preços definida por nível/titulação do tutor (não é valor livre nem porcentagem sobre valor livre)
- Confirmou que tanto professores quanto alunos de cursos superiores podem atuar como monitores — pendência: definir critérios mínimos de verificação para alunos de curso superior
- Levantou requisito importante de segurança: gravação obrigatória das aulas, com retenção temporária para revisão pelo aluno — isso tem implicação direta de custo de armazenamento, já identificado como risco na persona do idealizador
- Confirmou fluxo de agendamento com confirmação obrigatória do professor (não é agendamento automático)
- Confirmou avaliação recíproca (aluno ↔ monitor), com justificativa obrigatória para notas baixas (< 3 estrelas)
- Levantou regra de cancelamento com reembolso proporcional — precisa ser detalhada em critérios de aceitação nas histórias de usuário (prazos e percentuais ainda não definidos com exatidão)
- Confirmou necessidade de painel administrativo completo (usuários, denúncias, pagamentos, relatórios)
- Levantou funcionalidade de ranking de monitores por matéria — fora do escopo do MVP, mas relevante para o backlog
- Confirmou que, nesta fase, as matérias são fixas e vinculadas ao ENEM (o professor não cria matérias livremente)
- Diferencial estratégico do produto: levar ensino de qualidade a estudantes do interior do Brasil — reforça o público-alvo já mapeado nas personas (Lucas e Maria de Lourdes)
