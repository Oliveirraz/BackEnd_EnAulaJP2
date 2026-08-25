Backlog do Projeto EnAULA – GitHub Projects

Este documento apresenta o backlog do projeto EnAULA, organizado para utilização no GitHub Projects.

As histórias de usuário abaixo são baseadas nas 32 histórias de usuário definidas para o projeto, mantendo seus respectivos épicos, objetivos e critérios de aceitação.

Cada história contém:

ID

História de usuário

Épico

MoSCoW

Size

Sprint

Critérios de aceitação

Tasks técnicas

Observação: MoSCoW, Size e Sprint são uma proposta de organização do backlog. Caso esses valores já tenham sido definidos pelo grupo no GitHub Projects, devem prevalecer os valores do grupo.

1. Visão geral do Backlog

ID

História

Épico

MoSCoW

Size

Sprint

HU001

Cadastro de Aluno

Autenticação

Must

M

Sprint 1

HU002

Cadastro de Monitor/Professor

Autenticação

Must

M

Sprint 1

HU003

Cadastro de Administrador

Autenticação

Must

M

Sprint 1

HU004

REALIZAÇÃO DE LOGIN

Autenticação

Must

P

Sprint 1

HU005

Busca de aulas por filtros

Busca

Must

M

Sprint 1

HU006

Cadastro de matéria pelo professor

Aulas (Professor)

Must

M

Sprint 1

HU007

Criar aula

Aulas (Professor)

Must

M

Sprint 1

HU008

Definir modalidade da aula

Aulas (Professor)

Must

P

Sprint 1

HU009

Definir formato e valor da aula

Aulas (Professor)

Must

M

Sprint 1

HU010

Configurar disponibilidade

Aulas (Professor)

Must

M

Sprint 1

HU011

Gerenciamento de materiais

Aulas (Professor)

Should

M

Sprint 2

HU012

Gerenciamento de matrículas de alunos

Aulas (Professor)

Must

M

Sprint 2

HU013

Consultar histórico de aulas ministradas

Aulas (Professor)

Should

M

Sprint 2

HU014

Entrar em aula por videochamada

Aulas (Professor)

Must

G

Sprint 3

HU015

Gravar aula automaticamente

Aulas (Professor)

Must

G

Sprint 3

HU016

Acessar gravação da aula

Aulas (Professor)

Should

M

Sprint 3

HU017

Controlar entrada de alunos atrasados

Aulas (Professor)

Should

M

Sprint 3

HU018

Utilizar chat durante a aula

Aulas (Professor)

Should

M

Sprint 3

HU019

Solicitação de matrícula em aula

Matrícula (Aluno)

Must

M

Sprint 2

HU020

Escolher e confirmar pagamento

Matrícula (Aluno)

Must

G

Sprint 2

HU021

Processar pagamento de matrícula

Matrícula (Aluno)

Must

G

Sprint 2

HU022

Cancelamento de matrícula

Matrícula (Aluno)

Should

M

Sprint 3

HU023

Processamento de reembolso por cancelamento

Matrícula (Aluno)

Should

M

Sprint 3

HU024

Consultar histórico de aulas assistidas

Matrícula (Aluno)

Should

M

Sprint 3

HU025

Avaliação de professor após a aula

Avaliações

Should

M

Sprint 4

HU026

Avaliação de aluno após aula

Avaliações

Should

M

Sprint 4

HU027

Denúncia de aula (aluno)

Denúncias e Moderação

Should

M

Sprint 4

HU028

Denúncia de aula (professor)

Denúncias e Moderação

Should

M

Sprint 4

HU029

Efetuar reembolso

Denúncias e Moderação

Should

M

Sprint 4

HU030

Visualizar denúncia

Denúncias e Moderação

Must

M

Sprint 4

HU031

Analisar evidências da denúncia

Denúncias e Moderação

Must

M

Sprint 4

HU032

Registrar decisão sobre denúncia

Denúncias e Moderação

Must

M

Sprint 4

2. Épico: Autenticação

HU001 – Cadastro de Aluno

Épico: Autenticação
MoSCoW: Must
Size: M
Sprint: Sprint 1

História de usuário

Como interessado em aprender por meio da plataforma EnAULA, quero me cadastrar como aluno, para poder buscar, agendar e participar de aulas oferecidas pelos monitores.

Critérios de aceitação

O aluno deve informar nome, e-mail e senha para criar sua conta.

O sistema deve validar se o e-mail informado possui um formato válido.

O sistema não deve permitir o cadastro de dois usuários com o mesmo e-mail.

A senha deve ser armazenada com criptografia, nunca em texto puro.

Após o cadastro, o usuário deve possuir o perfil de Aluno.

O aluno poderá buscar aulas e monitores disponíveis na plataforma.

O cadastro deve coletar apenas os dados pessoais necessários, em conformidade com a LGPD.

O sistema deve exibir uma mensagem de sucesso após a conclusão do cadastro.

Tasks

Criar tela de cadastro.

Criar modelo de usuário.

Validar nome, e-mail e senha.

Impedir e-mail duplicado.

Implementar armazenamento seguro da senha.

Atribuir perfil de Aluno.

Implementar regras de LGPD.

Implementar confirmação de cadastro.

Criar testes.

HU002 – Cadastro de Monitor/Professor

Épico: Autenticação
MoSCoW: Must
Size: M
Sprint: Sprint 1

História de usuário

Como professor ou estudante de curso superior, quero me cadastrar como monitor na plataforma EnAULA, para poder disponibilizar meus conhecimentos e oferecer aulas aos alunos.

Critérios de aceitação

O monitor deve informar nome, e-mail e senha para criar sua conta.

O sistema deve permitir o cadastro apenas de usuários que atendam aos requisitos para atuar como monitor.

O sistema deve validar se o e-mail informado possui um formato válido.

O sistema não deve permitir o cadastro de dois usuários com o mesmo e-mail.

A senha deve ser armazenada com criptografia, nunca em texto puro.

Após o cadastro, o usuário deve possuir o perfil de Monitor.

O monitor poderá cadastrar as disciplinas ou áreas de conhecimento que deseja ensinar.

O monitor poderá informar sua disponibilidade de horários.

O monitor poderá definir o valor cobrado por hora de aula.

O cadastro deve coletar apenas os dados pessoais necessários, em conformidade com a LGPD.

O sistema deve exibir uma mensagem de sucesso após a conclusão do cadastro.

Tasks

Criar tela de cadastro de monitor.

Validar requisitos para atuação como monitor.

Validar e-mail e duplicidade.

Implementar armazenamento seguro da senha.

Atribuir perfil de Monitor.

Implementar cadastro de disciplinas/áreas.

Implementar disponibilidade.

Implementar valor por hora.

Implementar regras de LGPD.

Criar testes.

HU003 – Cadastro de Administrador

Épico: Autenticação
MoSCoW: Must
Size: M
Sprint: Sprint 1

História de usuário

Como responsável pela administração da plataforma EnAULA, quero possuir uma conta de administrador, para gerenciar usuários, conteúdos e demais recursos do sistema.

Critérios de aceitação

O administrador deve possuir nome, e-mail e senha cadastrados.

O e-mail do administrador deve ser único no sistema.

A senha deve ser armazenada com criptografia, nunca em texto puro.

O perfil de Administrador não deve estar disponível para cadastro público.

A criação de uma conta de administrador deve ser realizada por um administrador autorizado ou por meio de um processo controlado pelo sistema.

O administrador poderá visualizar e gerenciar os usuários cadastrados na plataforma.

O administrador poderá gerenciar informações relacionadas às disciplinas e demais recursos administrativos do sistema.

O sistema deve controlar o acesso às funcionalidades administrativas por meio de autenticação e autorização.

O cadastro deve seguir os princípios de segurança e proteção de dados previstos pela LGPD.

O sistema deve exibir uma mensagem de sucesso após a criação da conta de administrador.

Tasks

Criar fluxo controlado de criação de administrador.

Validar e-mail único.

Implementar armazenamento seguro da senha.

Impedir cadastro público de administrador.

Implementar autorização administrativa.

Implementar gerenciamento de usuários.

Implementar gerenciamento de disciplinas e recursos.

Implementar regras de LGPD.

Criar testes.

HU004 – REALIZAÇÃO DE LOGIN

Épico: Autenticação
MoSCoW: Must
Size: P
Sprint: Sprint 1

História de usuário

Como usuário, quero fazer login com e-mail e senha, para acessar minha conta no EnAula.

Critérios de aceitação

O sistema valida e-mail e senha

A comunicação entre app e servidor é feita via HTTPS

O processamento do login ocorre em até 3 segundos

Em caso de erro, o sistema exibe mensagem genérica (sem indicar qual o campo está incorreto)

Após login bem-sucedido, o usuário é redirecionado para a tela incial de acordo com seu perfil

Tasks

Criar tela de login.

Implementar autenticação.

Validar credenciais.

Configurar HTTPS.

Implementar controle de tempo do processamento.

Implementar mensagem genérica de erro.

Redirecionar conforme perfil.

Criar testes.

3. Épico: Busca

HU005 – Busca de aulas por filtros

Épico: Busca
MoSCoW: Must
Size: M
Sprint: Sprint 1

História de usuário

Como aluno, quero buscar aulas disponíveis usando filtros, para encontrar um monitor que se encaixe no meu orçamento e horário.

Critérios de aceitação

O aluno pode filtrar por matéria, professor, valor da aula e distância

Para aulas presenciais, a busca considera um raio padrão de 5km

Para aulas online, a busca considera um raio padrão de 100km

Os resultados exibem: nome do monitor, matéria, valor da hora-aula e avaliação média

A tela de resultados carrega em até 3 segundos

Se nenhum resultado for encontrado, o sistema exibe uma mensagem informando a ausência de resultados para os filtros aplicados

Tasks

Criar tela de busca.

Criar filtros.

Implementar consulta das aulas.

Implementar filtros de matéria, professor, valor e distância.

Implementar regras de raio presencial e online.

Exibir resultados com informações do monitor.

Implementar mensagem para nenhum resultado.

Criar testes.

4. Épico: Aulas (Professor)

HU006 – Cadastro de matéria pelo professor

Épico: Aulas (Professor)
MoSCoW: Must
Size: M
Sprint: Sprint 1

História de usuário

Como professor/monitor da EnAULA, quero cadastrar uma matéria que desejo lecionar, para disponibilizar minha área de ensino aos alunos que procuram aulas na plataforma.

Critérios de aceitação

O professor deve estar autenticado na plataforma.

O professor deve informar o nome da matéria que deseja cadastrar.

O professor deve informar uma descrição da matéria.

A matéria deve ficar vinculada ao professor que realizou o cadastro.

Após o cadastro, a matéria deve ficar disponível na área do professor para que ele possa criar uma aula.

Ao transformar uma matéria em aula, o professor deve informar a data, o horário, o local e o valor da aula.

A aula criada deve ficar vinculada à matéria cadastrada e ao professor responsável.

O sistema deve validar se os campos obrigatórios foram preenchidos.

Tasks

Criar cadastro de matéria.

Validar autenticação do professor.

Validar campos obrigatórios.

Vincular matéria ao professor.

Disponibilizar matéria para criação de aula.

Implementar criação da aula a partir da matéria.

Validar data, horário, local e valor.

Criar testes.

HU007 – Criar aula

Épico: Aulas (Professor)
MoSCoW: Must
Size: M
Sprint: Sprint 1

História de usuário

Como monitor, quero criar uma aula informando matéria, data, horário e duração, para disponibilizá-la aos alunos.

Critérios de aceitação

O monitor define matéria dentre as matérias vinculadas ao ENEM

O monitor define data e horário

A duração mínima da aula é de 1 hora

A aula criada fica visível na busca para os alunos

Tasks

Criar formulário de aula.

Implementar seleção de matéria vinculada.

Definir data e horário.

Validar duração mínima de 1 hora.

Disponibilizar aula na busca.

Criar testes.

HU008 – Definir modalidade da aula

Épico: Aulas (Professor)
MoSCoW: Must
Size: P
Sprint: Sprint 1

História de usuário

Como monitor, quero definir a modalidade da aula, para informar aos alunos se ela será online ou presencial.

Critérios de aceitação

O monitor pode selecionar a modalidade online ou presencial

A modalidade selecionada fica vinculada à aula

A modalidade é exibida aos alunos na busca e nos detalhes da aula

Tasks

Criar seleção de modalidade.

Permitir modalidade online ou presencial.

Vincular modalidade à aula.

Exibir modalidade na busca.

Exibir modalidade nos detalhes.

Criar testes.

HU009 – Definir formato e valor da aula

Épico: Aulas (Professor)
MoSCoW: Must
Size: M
Sprint: Sprint 1

História de usuário

Como monitor, quero definir se a aula será individual ou em grupo e informar seu valor, para estabelecer as condições de participação.

Critérios de aceitação

O monitor define se a aula é individual ou em grupo

No caso de aula em grupo, o valor por aluno é diluído entre os participantes

O valor da aula respeita o valor mínimo definido pela tabela de preços da plataforma (por nível/titulação do monitor)

Tasks

Criar seleção individual ou em grupo.

Implementar cálculo do valor por aluno.

Aplicar valor mínimo da plataforma.

Criar testes.

HU010 – Configurar disponibilidade

Épico: Aulas (Professor)
MoSCoW: Must
Size: M
Sprint: Sprint 1

História de usuário

Como monitor, quero estabelecer previamente minha agenda de disponibilidade, para informar os horários em que posso ministrar aulas.

Critérios de aceitação

O monitor pode configurar sua disponibilidade semanal ou mensal

Os horários configurados podem ser utilizados na criação das aulas

A disponibilidade fica associada ao perfil do monitor

Tasks

Criar configuração de disponibilidade.

Permitir agenda semanal ou mensal.

Validar horários.

Utilizar disponibilidade na criação das aulas.

Vincular disponibilidade ao perfil do monitor.

Criar testes.

HU011 – Gerenciamento de materiais

Épico: Aulas (Professor)
MoSCoW: Should
Size: M
Sprint: Sprint 2

História de usuário

Como monitor, quero enviar materiais de apoio (PDFs, exercícios, listas) vinculados às minhas aulas, para complementar o conteúdo ministrado.

Critérios de aceitação

O monitor pode anexar arquivos (PDF e formatos de documento comuns) a uma aula

Os materiais enviados ficam disponíveis para os alunos matriculados na aula

O monitor é o único responsável pelo conteúdo dos materiais disponibilizados

O monitor pode remover um material previamente enviado

Tasks

Criar área de materiais.

Permitir anexar PDFs e documentos.

Validar arquivos.

Vincular materiais às aulas.

Disponibilizar materiais aos alunos matriculados.

Permitir remoção de materiais.

Criar testes.

HU012 – Gerenciamento de matrículas de alunos

Épico: Aulas (Professor)
MoSCoW: Must
Size: M
Sprint: Sprint 2

História de usuário

Como monitor, quero confirmar ou recusar solicitações de matrícula em minhas aulas, para controlar quem participa das aulas que ministro.

Critérios de aceitação

Toda solicitação de matrícula exige confirmação explícita do monitor (não é automática)

O monitor visualiza a lista de alunos que solicitaram matrícula em uma aula

Após confirmação, o aluno recebe notificação de que sua matrícula foi aceita

O monitor pode recusar uma solicitação, informando o aluno

Tasks

Criar tela de matrículas.

Exibir solicitações.

Exigir confirmação explícita.

Permitir aceitar solicitação.

Permitir recusar solicitação.

Atualizar status.

Notificar aluno.

Criar testes.

HU013 – Consultar histórico de aulas ministradas

Épico: Aulas (Professor)
MoSCoW: Should
Size: M
Sprint: Sprint 2

História de usuário

Como monitor, quero consultar o histórico das aulas que já ministrei, para acompanhar meu desempenho e minha renda na plataforma.

Critérios de aceitação

O histórico lista data, matéria, aluno(s) e valor recebido por aula

O monitor pode acessar gravações de aulas já ministradas, respeitando o prazo de retenção definido pela plataforma

O histórico é ordenado da aula mais recente para a mais antiga

Tasks

Criar tela de histórico.

Recuperar aulas ministradas.

Exibir data, matéria, alunos e valor.

Ordenar por data.

Permitir acesso às gravações dentro do prazo.

Criar testes.

HU014 – Entrar em aula por videochamada

Épico: Aulas (Professor)
MoSCoW: Must
Size: G
Sprint: Sprint 3

História de usuário

Como monitor, quero entrar na aula por meio de uma videochamada integrada ao EnAULA, para ministrar a aula dentro da plataforma.

Critérios de aceitação

A aula é realizada através de videochamada integrada ao EnAULA

O monitor consegue acessar a videochamada a partir da aula agendada

Tasks

Integrar videochamada à aula.

Criar acesso a partir da aula agendada.

Controlar acesso do monitor.

Criar testes.

HU015 – Gravar aula automaticamente

Épico: Aulas (Professor)
MoSCoW: Must
Size: G
Sprint: Sprint 3

História de usuário

Como monitor, quero que a aula seja gravada automaticamente, para permitir seu registro e posterior consulta.

Critérios de aceitação

A gravação inicia automaticamente desde o início da aula

A gravação fica vinculada à aula realizada

A gravação respeita o prazo de retenção definido pela plataforma

Tasks

Integrar gravação automática.

Iniciar gravação no início da aula.

Vincular gravação à aula.

Aplicar prazo de retenção.

Criar testes.

HU016 – Acessar gravação da aula

Épico: Aulas (Professor)
MoSCoW: Should
Size: M
Sprint: Sprint 3

História de usuário

Como aluno ou monitor, quero acessar a gravação de uma aula realizada, para rever o conteúdo posteriormente.

Critérios de aceitação

A gravação fica disponível para o aluno rever

O monitor pode acessar gravações de aulas que ministrou

O acesso respeita o prazo de retenção definido pela plataforma

Tasks

Criar acesso às gravações para alunos.

Criar acesso às gravações para monitores.

Validar permissões.

Aplicar prazo de retenção.

Criar testes.

HU017 – Controlar entrada de alunos atrasados

Épico: Aulas (Professor)
MoSCoW: Should
Size: M
Sprint: Sprint 3

História de usuário

Como monitor, quero controlar a entrada de alunos atrasados, para manter o funcionamento adequado da aula em grupo.

Critérios de aceitação

Existe tolerância de atraso definida pela plataforma

Após o tempo de tolerância, a aula pode ser iniciada mesmo com alunos ausentes

Os alunos que ultrapassarem o limite de atraso perdem o acesso à aula

Tasks

Configurar tolerância de atraso.

Controlar entrada dos alunos.

Permitir início após a tolerância.

Bloquear acesso após o limite.

Criar testes.

HU018 – Utilizar chat durante a aula

Épico: Aulas (Professor)
MoSCoW: Should
Size: M
Sprint: Sprint 3

História de usuário

Como aluno ou monitor, quero utilizar o chat durante a aula, para trocar mensagens relacionadas ao conteúdo ministrado.

Critérios de aceitação

A aula disponibiliza uma ferramenta de chat

O chat pode ser utilizado durante a aula

A ferramenta de chat é monitorada conforme os requisitos de segurança da plataforma

Tasks

Implementar chat da aula.

Permitir mensagens durante a aula.

Aplicar monitoramento e segurança.

Criar testes.

5. Épico: Matrícula (Aluno)

HU019 – Solicitação de matrícula em aula

Épico: Matrícula (Aluno)
MoSCoW: Must
Size: M
Sprint: Sprint 2

História de usuário

Como aluno, quero solicitar matrícula em uma aula disponível, para poder participar dela.

Critérios de aceitação

O aluno solicita matrícula a partir da tela de detalhes da aula

A matrícula fica com status "pendente" até a confirmação do monitor

O sistema confirma a matrícula em tempo real assim que o monitor aceita

O aluno recebe notificação sobre o resultado da solicitação (aceita ou recusada)

Tasks

Criar solicitação de matrícula.

Registrar status pendente.

Notificar monitor.

Atualizar status após decisão.

Notificar aluno.

Criar testes.

HU020 – Escolher e confirmar pagamento

Épico: Matrícula (Aluno)
MoSCoW: Must
Size: G
Sprint: Sprint 2

História de usuário

Como aluno, quero visualizar e confirmar o pagamento da matrícula, para garantir minha vaga na aula.

Critérios de aceitação

O aluno visualiza o valor total antes de confirmar o pagamento

O aluno pode confirmar o pagamento dentro da plataforma

A comunicação de dados de pagamento é feita via HTTPS

Tasks

Criar tela de pagamento.

Exibir valor total.

Implementar confirmação do pagamento.

Configurar comunicação HTTPS.

Criar testes.

HU021 – Processar pagamento de matrícula

Épico: Matrícula (Aluno)
MoSCoW: Must
Size: G
Sprint: Sprint 2

História de usuário

Como aluno, quero que o pagamento da matrícula seja processado pela plataforma, para efetivar minha participação na aula.

Critérios de aceitação

O pagamento é processado dentro da plataforma

Após o pagamento confirmado, a matrícula do aluno é efetivada

O sistema informa o resultado do processamento do pagamento

Tasks

Integrar processamento de pagamento.

Processar pagamento.

Efetivar matrícula após confirmação.

Informar resultado do processamento.

Criar testes.

HU022 – Cancelamento de matrícula

Épico: Matrícula (Aluno)
MoSCoW: Should
Size: M
Sprint: Sprint 3

História de usuário

Como aluno, quero cancelar minha matrícula em uma aula, para desistir de uma aula que não poderei mais assistir.

Critérios de aceitação

O aluno pode cancelar a matrícula respeitando o limite de horas de antecedência definido pela plataforma

O sistema pede confirmação antes de efetivar o cancelamento

Fora do prazo permitido, o sistema informa que o cancelamento não gera reembolso

Tasks

Criar opção de cancelamento.

Validar prazo de antecedência.

Solicitar confirmação.

Atualizar matrícula.

Informar condição de reembolso.

Criar testes.

HU023 – Processamento de reembolso por cancelamento

Épico: Matrícula (Aluno)
MoSCoW: Should
Size: M
Sprint: Sprint 3

História de usuário

Como aluno, quero receber o reembolso quando o cancelamento ocorrer dentro das condições estabelecidas, para recuperar o valor correspondente à matrícula cancelada.

Critérios de aceitação

Ao cancelar dentro do prazo permitido, o aluno recebe reembolso proporcional ao tempo que ficou com a matrícula reservada

O sistema informa o valor do reembolso

O reembolso é processado após a confirmação do cancelamento

Tasks

Calcular valor do reembolso.

Exibir valor ao aluno.

Processar reembolso após confirmação.

Criar testes.

HU024 – Consultar histórico de aulas assistidas

Épico: Matrícula (Aluno)
MoSCoW: Should
Size: M
Sprint: Sprint 3

História de usuário

Como aluno, quero consultar o histórico das aulas que já assisti, para acompanhar meu progresso de estudos.

Critérios de aceitação

O histórico lista data, matéria, monitor e status da aula (concluída, cancelada)

O aluno pode acessar a gravação de aulas já assistidas, respeitando o prazo de retenção

O histórico é ordenado da aula mais recente para a mais antiga

Tasks

Criar tela de histórico do aluno.

Recuperar aulas assistidas.

Exibir data, matéria, monitor e status.

Ordenar por data.

Permitir acesso às gravações dentro do prazo.

Criar testes.

6. Épico: Avaliações

HU025 – Avaliação de professor após a aula

Épico: Avaliações
MoSCoW: Should
Size: M
Sprint: Sprint 4

História de usuário

Como aluno, quero avaliar o monitor após a aula, para contribuir com a reputação dele na plataforma.

Critérios de aceitação

A avaliação é feita em uma escala de estrelas (1 a 5)

Se a nota for menor que 3 estrelas, o sistema exige uma justificativa por escrito

A avaliação fica visível no perfil público do monitor

A avaliação só pode ser feita após a conclusão da aula

Tasks

Criar formulário de avaliação.

Validar participação do aluno.

Implementar nota de 1 a 5.

Exigir justificativa abaixo de 3 estrelas.

Exibir avaliação no perfil público.

Restringir avaliação após conclusão da aula.

Criar testes.

HU026 – Avaliação de aluno após aula

Épico: Avaliações
MoSCoW: Should
Size: M
Sprint: Sprint 4

História de usuário

Como monitor, quero avaliar o aluno após a aula, para contribuir com a reputação dele na plataforma.

Critérios de aceitação

A avaliação é feita em uma escala de estrelas (1 a 5)

Se a nota for menor que 3 estrelas, o sistema exige uma justificativa por escrito

A avaliação só pode ser feita após a conclusão da aula

A avaliação recíproca (aluno ↔ monitor) é independente: um lado não vê a nota do outro antes de avaliar

Tasks

Criar formulário de avaliação.

Validar participação do monitor.

Implementar nota de 1 a 5.

Exigir justificativa abaixo de 3 estrelas.

Garantir independência entre avaliações.

Criar testes.

7. Épico: Denúncias e Moderação

HU027 – Denúncia de aula (aluno)

Épico: Denúncias e Moderação
MoSCoW: Should
Size: M
Sprint: Sprint 4

História de usuário

Como aluno, quero denunciar uma aula ou o comportamento de um monitor, para relatar situações inadequadas à administração da plataforma.

Critérios de aceitação

O botão de denúncia está disponível na tela da aula ou no histórico

O aluno descreve o motivo da denúncia

A denúncia é registrada e associada à aula e aos usuários envolvidos, com log da ação

O aluno recebe confirmação de que a denúncia foi registrada

Tasks

Criar formulário de denúncia.

Permitir descrição do motivo.

Associar denúncia à aula e usuários.

Registrar log.

Exibir confirmação.

Criar testes.

HU028 – Denúncia de aula (professor)

Épico: Denúncias e Moderação
MoSCoW: Should
Size: M
Sprint: Sprint 4

História de usuário

Como monitor, quero denunciar uma aula ou o comportamento de um aluno, para relatar situações inadequadas à administração da plataforma.

Critérios de aceitação

O botão de denúncia está disponível na tela da aula ou no histórico

O monitor descreve o motivo da denúncia

A denúncia é registrada e associada à aula e aos usuários envolvidos, com log da ação

O monitor recebe confirmação de que a denúncia foi registrada

Tasks

Criar formulário de denúncia do monitor.

Permitir descrição do motivo.

Associar denúncia à aula e usuários.

Registrar log.

Exibir confirmação.

Criar testes.

HU029 – Efetuar reembolso

Épico: Denúncias e Moderação
MoSCoW: Should
Size: M
Sprint: Sprint 4

História de usuário

Como monitor, quero efetuar o reembolso de uma matrícula em situações justificadas, para resolver problemas com um aluno sem depender exclusivamente da administração.

Critérios de aceitação

O monitor pode iniciar um reembolso para uma matrícula específica

O sistema solicita confirmação antes de efetivar o reembolso

O aluno é notificado quando o reembolso é processado

A ação fica registrada em log, vinculada ao monitor que a realizou

Tasks

Criar fluxo de reembolso do monitor.

Selecionar matrícula.

Solicitar confirmação.

Processar reembolso.

Notificar aluno.

Registrar log do monitor.

Criar testes.

HU030 – Visualizar denúncia

Épico: Denúncias e Moderação
MoSCoW: Must
Size: M
Sprint: Sprint 4

História de usuário

Como administrador, quero visualizar as denúncias registradas na plataforma, para analisar as situações relatadas.

Critérios de aceitação

O administrador visualiza a lista de denúncias pendentes

Cada denúncia apresenta detalhes da aula e dos usuários envolvidos

O administrador consegue identificar a denúncia que precisa ser analisada

Tasks

Criar lista de denúncias pendentes.

Exibir detalhes da aula.

Exibir usuários envolvidos.

Permitir identificar denúncias para análise.

Criar testes.

HU031 – Analisar evidências da denúncia

Épico: Denúncias e Moderação
MoSCoW: Must
Size: M
Sprint: Sprint 4

História de usuário

Como administrador, quero acessar as evidências de uma denúncia, para analisar a situação antes de tomar uma decisão.

Critérios de aceitação

O administrador pode acessar a gravação da aula denunciada como evidência

O acesso à gravação respeita o prazo de retenção

As evidências ficam vinculadas à denúncia analisada

Tasks

Permitir acesso à gravação denunciada.

Validar prazo de retenção.

Vincular evidências à denúncia.

Controlar acesso administrativo.

Criar testes.

HU032 – Registrar decisão sobre denúncia

Épico: Denúncias e Moderação
MoSCoW: Must
Size: M
Sprint: Sprint 4

História de usuário

Como administrador, quero registrar a decisão sobre uma denúncia, para definir as medidas cabíveis sobre usuários e aulas denunciadas.

Critérios de aceitação

O administrador registra a decisão como procedente ou improcedente

O administrador registra uma justificativa

Toda decisão do administrador é registrada em log

Tasks

Criar tela de decisão.

Registrar procedente ou improcedente.

Registrar justificativa.

Registrar decisão em log.

Criar testes.

Dependências principais

Para o GitHub Projects, recomenda-se registrar as dependências entre as histórias.

Fluxo do aluno

HU001 / HU004

↓

HU005

↓

HU019

↓

HU012

↓

HU020 / HU021

↓

HU014

↓

HU024 / HU025

Fluxo do monitor

HU002 / HU004

↓

HU006

↓

HU007 / HU008 / HU009 / HU010

↓

HU012

↓

HU014 / HU015 / HU018

↓

HU013 / HU026

Fluxo de cancelamento e reembolso

HU022

↓

HU023 / HU029

Fluxo de denúncias e moderação

HU027 / HU028

↓

HU030

↓

HU031

↓

HU032

Essas dependências são uma proposta de planejamento baseada na relação funcional entre as histórias. O arquivo de Histórias de Usuário é a referência para IDs, títulos e critérios de aceitação