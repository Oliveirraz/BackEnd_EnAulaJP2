# Histórias de Usuário - EnAULA

As histórias estão organizadas por épico. Cada história tem critérios de aceitação que definem quando ela pode ser considerada concluída. Os critérios de aceitação incorporam, quando aplicável, os Requisitos Não Funcionais (RNF) levantados para o projeto.

## Autenticação

### HU001: Cadastro de Aluno

**Como** interessado em aprender por meio da plataforma EnAULA, **quero** me cadastrar como aluno, **para** poder buscar, agendar e participar de aulas oferecidas pelos monitores.

**Critérios de aceitação:**

- O aluno deve informar nome, e-mail e senha para criar sua conta.
- O sistema deve validar se o e-mail informado possui um formato válido.
- O sistema não deve permitir o cadastro de dois usuários com o mesmo e-mail.
- A senha deve ser armazenada com criptografia, nunca em texto puro.
- Após o cadastro, o usuário deve possuir o perfil de **Aluno**.
- O aluno poderá buscar aulas e monitores disponíveis na plataforma.
- O cadastro deve coletar apenas os dados pessoais necessários, em conformidade com a LGPD.
- O sistema deve exibir uma mensagem de sucesso após a conclusão do cadastro.

### HU002: Cadastro de Monitor/Professor

**Como** professor ou estudante de curso superior, **quero** me cadastrar como monitor na plataforma EnAULA, **para** poder disponibilizar meus conhecimentos e oferecer aulas aos alunos.

**Critérios de aceitação:**

- O monitor deve informar nome, e-mail e senha para criar sua conta.

- O sistema deve permitir o cadastro apenas de usuários que atendam aos requisitos para atuar como monitor.
- O sistema deve validar se o e-mail informado possui um formato válido.
- O sistema não deve permitir o cadastro de dois usuários com o mesmo e-mail.
- A senha deve ser armazenada com criptografia, nunca em texto puro.
- Após o cadastro, o usuário deve possuir o perfil de **Monitor**.
- O monitor poderá cadastrar as disciplinas ou áreas de conhecimento que deseja ensinar.
- O monitor poderá informar sua disponibilidade de horários.
- O monitor poderá definir o valor cobrado por hora de aula.
- O cadastro deve coletar apenas os dados pessoais necessários, em conformidade com a LGPD.
- O sistema deve exibir uma mensagem de sucesso após a conclusão do cadastro.

### HU003 — Cadastro de Administrador

**Como** responsável pela administração da plataforma EnAULA, **quero** possuir uma conta de administrador, **para** gerenciar usuários, conteúdos e demais recursos do sistema.

**Critérios de aceitação:**

- O administrador deve possuir nome, e-mail e senha cadastrados.
- O e-mail do administrador deve ser único no sistema.
- A senha deve ser armazenada com criptografia, nunca em texto puro.
- O perfil de **Administrador** não deve estar disponível para cadastro público.
- A criação de uma conta de administrador deve ser realizada por um administrador autorizado ou por meio de um processo controlado pelo sistema.
- O administrador poderá visualizar e gerenciar os usuários cadastrados na plataforma.
- O administrador poderá gerenciar informações relacionadas às disciplinas e demais recursos administrativos do sistema.
- O sistema deve controlar o acesso às funcionalidades administrativas por meio de autenticação e autorização.
- O cadastro deve seguir os princípios de segurança e proteção de dados previstos pela LGPD.
- O sistema deve exibir uma mensagem de sucesso após a criação da conta de administrador.

### HU004: REALIZAÇÃO DE LOGIN

**Como** usuário, **quero** fazer login com e-mail e senha, **para** acessar minha conta no EnAula. 

**Critérios de aceitação**

- O sistema valida e-mail e senha

- A comunicação entre app e servidor é feita via HTTPS

- O processamento do login ocorre em até 3 segundos

- Em caso de erro, o sistema exibe mensagem genérica (sem indicar qual o campo está incorreto)

- Após login bem-sucedido, o usuário é redirecionado para a tela incial de acordo com seu perfil

## Busca

### HU005: Busca de aulas por filtros

**Como** aluno, **quero** buscar aulas disponíveis usando filtros, **para** encontrar um monitor que se encaixe no meu orçamento e horário.

**Critérios de aceitação:**

- O aluno pode filtrar por matéria, professor, valor da aula e distância
- Para aulas presenciais, a busca considera um raio padrão de 5km
- Para aulas online, a busca considera um raio padrão de 100km
- Os resultados exibem: nome do monitor, matéria, valor da hora-aula e avaliação média
- A tela de resultados carrega em até 3 segundos
- Se nenhum resultado for encontrado, o sistema exibe uma mensagem informando a ausência de resultados para os filtros aplicados

## Aulas (Professor)

### HU006: Cadastro de matéria pelo professor

**Como** professor/monitor da EnAULA, **quero** cadastrar uma matéria que desejo lecionar, **para** disponibilizar minha área de ensino aos alunos que procuram aulas na plataforma.

**Critérios de aceitação:**

- O professor deve estar autenticado na plataforma.
- O professor deve informar o nome da matéria que deseja cadastrar.
- O professor deve informar uma descrição da matéria.
- A matéria deve ficar vinculada ao professor que realizou o cadastro.
- Após o cadastro, a matéria deve ficar disponível na área do professor para que ele possa criar uma aula.
- Ao transformar uma matéria em aula, o professor deve informar a **data**, o **horário**, o **local** e o **valor da aula**.
- A aula criada deve ficar vinculada à matéria cadastrada e ao professor responsável.

- O sistema deve validar se os campos obrigatórios foram preenchidos.

### HU007: Criar aula

**Como** monitor, **quero** criar uma aula informando matéria, data, horário e duração, **para** disponibilizá-la aos alunos.

**Critérios de aceitação:**

- O monitor define matéria dentre as matérias vinculadas ao ENEM
- O monitor define data e horário
- A duração mínima da aula é de 1 hora
- A aula criada fica visível na busca para os alunos

### HU008: Definir modalidade da aula

**Como** monitor, **quero** definir a modalidade da aula, **para** informar aos alunos se ela será online ou presencial.

**Critérios de aceitação:**

- O monitor pode selecionar a modalidade online ou presencial
- A modalidade selecionada fica vinculada à aula
- A modalidade é exibida aos alunos na busca e nos detalhes da aula

### HU009: Definir formato e valor da aula

**Como** monitor, **quero** definir se a aula será individual ou em grupo e informar seu valor, **para** estabelecer as condições de participação.

**Critérios de aceitação:**

- O monitor define se a aula é individual ou em grupo
- No caso de aula em grupo, o valor por aluno é diluído entre os participantes
- O valor da aula respeita o valor mínimo definido pela tabela de preços da plataforma (por nível/titulação do monitor)

### HU010: Configurar disponibilidade

**Como** monitor, **quero** estabelecer previamente minha agenda de disponibilidade, **para** informar os horários em que posso ministrar aulas.

**Critérios de aceitação:**

- O monitor pode configurar sua disponibilidade semanal ou mensal
- Os horários configurados podem ser utilizados na criação das aulas
- A disponibilidade fica associada ao perfil do monitor

### HU011: Gerenciamento de materiais

**Como** monitor, **quero** enviar materiais de apoio (PDFs, exercícios, listas) vinculados às minhas aulas, **para** complementar o conteúdo ministrado.

**Critérios de aceitação:**

- O monitor pode anexar arquivos (PDF e formatos de documento comuns) a uma aula
- Os materiais enviados ficam disponíveis para os alunos matriculados na aula
- O monitor é o único responsável pelo conteúdo dos materiais disponibilizados
- O monitor pode remover um material previamente enviado

### HU012: Gerenciamento de matrículas de alunos

**Como** monitor, **quero** confirmar ou recusar solicitações de matrícula em minhas aulas, **para** controlar quem participa das aulas que ministro.

**Critérios de aceitação:**

- Toda solicitação de matrícula exige confirmação explícita do monitor (não é automática)
- O monitor visualiza a lista de alunos que solicitaram matrícula em uma aula
- Após confirmação, o aluno recebe notificação de que sua matrícula foi aceita
- O monitor pode recusar uma solicitação, informando o aluno

### HU013: Consultar histórico de aulas ministradas

**Como** monitor, **quero** consultar o histórico das aulas que já ministrei, **para** acompanhar meu desempenho e minha renda na plataforma.

**Critérios de aceitação:**

- O histórico lista data, matéria, aluno(s) e valor recebido por aula
- O monitor pode acessar gravações de aulas já ministradas, respeitando o prazo de retenção definido pela plataforma
- O histórico é ordenado da aula mais recente para a mais antiga

### HU014: Entrar em aula por videochamada

**Como** monitor, **quero** entrar na aula por meio de uma videochamada integrada ao EnAULA, **para** ministrar a aula dentro da plataforma.

**Critérios de aceitação:**

- A aula é realizada através de videochamada integrada ao EnAULA
- O monitor consegue acessar a videochamada a partir da aula agendada

### HU015: Gravar aula automaticamente

**Como** monitor, **quero** que a aula seja gravada automaticamente, **para** permitir seu registro e posterior consulta.

**Critérios de aceitação:**

- A gravação inicia automaticamente desde o início da aula
- A gravação fica vinculada à aula realizada
- A gravação respeita o prazo de retenção definido pela plataforma

### HU016: Acessar gravação da aula

**Como** aluno ou monitor, **quero** acessar a gravação de uma aula realizada, **para** rever o conteúdo posteriormente.

**Critérios de aceitação:**

- A gravação fica disponível para o aluno rever
- O monitor pode acessar gravações de aulas que ministrou
- O acesso respeita o prazo de retenção definido pela plataforma

### HU017: Controlar entrada de alunos atrasados

**Como** monitor, **quero** controlar a entrada de alunos atrasados, **para** manter o funcionamento adequado da aula em grupo.

**Critérios de aceitação:**

- Existe tolerância de atraso definida pela plataforma
- Após o tempo de tolerância, a aula pode ser iniciada mesmo com alunos ausentes
- Os alunos que ultrapassarem o limite de atraso perdem o acesso à aula

### HU018: Utilizar chat durante a aula

**Como** aluno ou monitor, **quero** utilizar o chat durante a aula, **para** trocar mensagens relacionadas ao conteúdo ministrado.

**Critérios de aceitação:**

- A aula disponibiliza uma ferramenta de chat
- O chat pode ser utilizado durante a aula
- A ferramenta de chat é monitorada conforme os requisitos de segurança da plataforma

## Matrícula (Aluno)

### HU019: Solicitação de matrícula em aula

**Como** aluno, **quero** solicitar matrícula em uma aula disponível, **para** poder participar dela.

**Critérios de aceitação:**

- O aluno solicita matrícula a partir da tela de detalhes da aula
- A matrícula fica com status "pendente" até a confirmação do monitor
- O sistema confirma a matrícula em tempo real assim que o monitor aceita
- O aluno recebe notificação sobre o resultado da solicitação (aceita ou recusada)

### HU020: Escolher e confirmar pagamento

**Como** aluno, **quero** visualizar e confirmar o pagamento da matrícula, **para** garantir minha vaga na aula.

**Critérios de aceitação:**

- O aluno visualiza o valor total antes de confirmar o pagamento
- O aluno pode confirmar o pagamento dentro da plataforma
- A comunicação de dados de pagamento é feita via HTTPS

### HU021: Processar pagamento de matrícula

**Como** aluno, **quero** que o pagamento da matrícula seja processado pela plataforma, **para** efetivar minha participação na aula.

**Critérios de aceitação:**

- O pagamento é processado dentro da plataforma
- Após o pagamento confirmado, a matrícula do aluno é efetivada
- O sistema informa o resultado do processamento do pagamento

### HU022: Cancelamento de matrícula

**Como** aluno, **quero** cancelar minha matrícula em uma aula, **para** desistir de uma aula que não poderei mais assistir.

**Critérios de aceitação:**

- O aluno pode cancelar a matrícula respeitando o limite de horas de antecedência definido pela plataforma
- O sistema pede confirmação antes de efetivar o cancelamento
- Fora do prazo permitido, o sistema informa que o cancelamento não gera reembolso

### HU023: Processamento de reembolso por cancelamento

**Como** aluno, **quero** receber o reembolso quando o cancelamento ocorrer dentro das condições estabelecidas, **para** recuperar o valor correspondente à matrícula cancelada.

**Critérios de aceitação:**

- Ao cancelar dentro do prazo permitido, o aluno recebe reembolso proporcional ao tempo que ficou com a matrícula reservada
- O sistema informa o valor do reembolso
- O reembolso é processado após a confirmação do cancelamento

### HU024: Consultar histórico de aulas assistidas

**Como** aluno, **quero** consultar o histórico das aulas que já assisti, **para** acompanhar meu progresso de estudos.

**Critérios de aceitação:**

- O histórico lista data, matéria, monitor e status da aula (concluída, cancelada)
- O aluno pode acessar a gravação de aulas já assistidas, respeitando o prazo de retenção
- O histórico é ordenado da aula mais recente para a mais antiga

## Avaliações

### HU025: Avaliação de professor após a aula

**Como** aluno, **quero** avaliar o monitor após a aula, **para** contribuir com a reputação dele na plataforma.

**Critérios de aceitação:**

- A avaliação é feita em uma escala de estrelas (1 a 5)
- Se a nota for menor que 3 estrelas, o sistema exige uma justificativa por escrito
- A avaliação fica visível no perfil público do monitor
- A avaliação só pode ser feita após a conclusão da aula

### HU026: Avaliação de aluno após aula

**Como** monitor, **quero** avaliar o aluno após a aula, **para** contribuir com a reputação dele na plataforma.

**Critérios de aceitação:**

- A avaliação é feita em uma escala de estrelas (1 a 5)
- Se a nota for menor que 3 estrelas, o sistema exige uma justificativa por escrito
- A avaliação só pode ser feita após a conclusão da aula
- A avaliação recíproca (aluno ↔ monitor) é independente: um lado não vê a nota do outro antes de avaliar

## Denúncias e Moderação

### HU027: Denúncia de aula (aluno)

**Como** aluno, **quero** denunciar uma aula ou o comportamento de um monitor, **para** relatar situações inadequadas à administração da plataforma.

**Critérios de aceitação:**

- O botão de denúncia está disponível na tela da aula ou no histórico
- O aluno descreve o motivo da denúncia
- A denúncia é registrada e associada à aula e aos usuários envolvidos, com log da ação
- O aluno recebe confirmação de que a denúncia foi registrada

### HU028: Denúncia de aula (professor)

**Como** monitor, **quero** denunciar uma aula ou o comportamento de um aluno, **para** relatar situações inadequadas à administração da plataforma.

**Critérios de aceitação:**

- O botão de denúncia está disponível na tela da aula ou no histórico
- O monitor descreve o motivo da denúncia
- A denúncia é registrada e associada à aula e aos usuários envolvidos, com log da ação
- O monitor recebe confirmação de que a denúncia foi registrada

### HU029: Efetuar reembolso

**Como** monitor, **quero** efetuar o reembolso de uma matrícula em situações justificadas, **para** resolver problemas com um aluno sem depender exclusivamente da administração.

**Critérios de aceitação:**

- O monitor pode iniciar um reembolso para uma matrícula específica
- O sistema solicita confirmação antes de efetivar o reembolso
- O aluno é notificado quando o reembolso é processado
- A ação fica registrada em log, vinculada ao monitor que a realizou

### HU030: Visualizar denúncia

**Como** administrador, **quero** visualizar as denúncias registradas na plataforma, **para** analisar as situações relatadas.

**Critérios de aceitação:**

- O administrador visualiza a lista de denúncias pendentes
- Cada denúncia apresenta detalhes da aula e dos usuários envolvidos
- O administrador consegue identificar a denúncia que precisa ser analisada

### HU031: Analisar evidências da denúncia

**Como** administrador, **quero** acessar as evidências de uma denúncia, **para** analisar a situação antes de tomar uma decisão.

**Critérios de aceitação:**

- O administrador pode acessar a gravação da aula denunciada como evidência
- O acesso à gravação respeita o prazo de retenção
- As evidências ficam vinculadas à denúncia analisada

### HU032: Registrar decisão sobre denúncia

**Como** administrador, **quero** registrar a decisão sobre uma denúncia, **para** definir as medidas cabíveis sobre usuários e aulas denunciadas.

**Critérios de aceitação:**

- O administrador registra a decisão como procedente ou improcedente
- O administrador registra uma justificativa
- Toda decisão do administrador é registrada em log
