# Histórias de Usuário - EnAULA

As histórias estão organizadas por épico. Cada história tem critérios de aceitação que definem quando ela pode ser considerada concluída. Os critérios de aceitação incorporam, quando aplicável, os Requisitos Não Funcionais (RNF) levantados para o projeto.

## Autenticação

### HU001: Cadastro de usuário

**Como** aluno ou professor interessado no EnAULA, **quero** criar uma conta na plataforma, **para** poder buscar ou oferecer aulas.

**Critérios de aceitação:**

- O usuário informa nome, e-mail, senha e o perfil desejado (aluno e/ou monitor)

- A senha é armazenada com criptografia (nunca em texto puro)

- O usuário pode ser aluno, monitor, ou ambos simultaneamente

- Para se cadastrar como monitor, o usuário deve ser professor ou estudante de curso superior

- O cadastro coleta apenas os dados pessoais necessários, em conformidade com a LGPD

- O sistema exibe mensagem de sucesso após o cadastro

### HU002: Realização de login

**Como** usuário cadastrado, **quero** fazer login com e-mail e senha, **para** acessar minha conta no EnAULA.

**Critérios de aceitação:**

- O sistema valida e-mail e senha

- A comunicação entre app e servidor é feita via HTTPS

- O processamento do login ocorre em até 3 segundos

- Em caso de erro, o sistema exibe mensagem genérica (sem indicar qual campo está incorreto)

- Após login bem-sucedido, o usuário é redirecionado para a tela inicial de acordo com seu perfil

## Busca

### HU003: Busca de aulas por filtros

**Como** aluno, **quero** buscar aulas disponíveis usando filtros, **para** encontrar um monitor que se encaixe no meu orçamento e horário.

**Critérios de aceitação:**

- O aluno pode filtrar por matéria, professor, valor da aula e distância

- Para aulas presenciais, a busca considera um raio padrão de 5km

- Para aulas online, a busca considera um raio padrão de 100km

- Os resultados exibem: nome do monitor, matéria, valor da hora-aula e avaliação média

- A tela de resultados carrega em até 3 segundos

- Se nenhum resultado for encontrado, o sistema exibe uma mensagem informando a ausência de resultados para os filtros aplicados

## Aulas (Professor)

### HU004: Criação de aula

**Como** monitor, **quero** criar uma aula com meus horários disponíveis e valor cobrado, **para** que os alunos possam encontrá-la e se matricular.

**Critérios de aceitação:**

- O monitor define matéria (dentre as matérias vinculadas ao ENEM), data/horário, duração mínima de 1 hora e modalidade (online ou presencial)

- O monitor define se a aula é individual ou em grupo; no caso de aula em grupo, o valor por aluno é diluído entre os participantes

- O valor da aula respeita o valor mínimo definido pela tabela de preços da plataforma (por nível/titulação do monitor)

- O monitor estabelece previamente sua agenda de disponibilidade (semanal ou mensal)

- A aula criada fica visível na busca para os alunos

---

### HU005: Gerenciamento de materiais

**Como** monitor, **quero** enviar materiais de apoio (PDFs, exercícios, listas) vinculados às minhas aulas, **para** complementar o conteúdo ministrado.

**Critérios de aceitação:**

- O monitor pode anexar arquivos (PDF e formatos de documento comuns) a uma aula

- Os materiais enviados ficam disponíveis para os alunos matriculados na aula

- O monitor é o único responsável pelo conteúdo dos materiais disponibilizados

- O monitor pode remover um material previamente enviado

### HU006: Gerenciamento de matrículas de alunos

**Como** monitor, **quero** confirmar ou recusar solicitações de matrícula em minhas aulas, **para** controlar quem participa das aulas que ministro.

**Critérios de aceitação:**

- Toda solicitação de matrícula exige confirmação explícita do monitor (não é automática)

- O monitor visualiza a lista de alunos que solicitaram matrícula em uma aula

- Após confirmação, o aluno recebe notificação de que sua matrícula foi aceita

- O monitor pode recusar uma solicitação, informando o aluno

---

### HU007: Consultar histórico de aulas ministradas

**Como** monitor, **quero** consultar o histórico das aulas que já ministrei, **para** acompanhar meu desempenho e minha renda na plataforma.

**Critérios de aceitação:**

- O histórico lista data, matéria, aluno(s) e valor recebido por aula

- O monitor pode acessar gravações de aulas já ministradas, respeitando o prazo de retenção definido pela plataforma

- O histórico é ordenado da aula mais recente para a mais antiga

---

### HU008: Ministração de aula

**Como** monitor, **quero** ministrar a aula dentro da própria plataforma, **para** que ela seja gravada automaticamente por segurança.

**Critérios de aceitação:**

- A aula é realizada através de videochamada integrada ao EnAULA

- A aula é gravada automaticamente desde o início

- A gravação fica disponível para o aluno rever, respeitando o prazo de retenção do histórico

- Existe tolerância de atraso: se parte dos alunos de uma aula em grupo não estiver presente após o tempo de tolerância definido, a aula é iniciada e os atrasados perdem o acesso

- A ferramenta de chat da aula é monitorada, conforme requisitos de segurança da plataforma

## Matrícula (Aluno)

### HU009: Solicitação de matrícula em aula

**Como** aluno, **quero** solicitar matrícula em uma aula disponível, **para** poder participar dela.

**Critérios de aceitação:**

- O aluno solicita matrícula a partir da tela de detalhes da aula

- A matrícula fica com status "pendente" até a confirmação do monitor

- O sistema confirma a matrícula em tempo real assim que o monitor aceita

- O aluno recebe notificação sobre o resultado da solicitação (aceita ou recusada)

### HU010: Pagamento de matrícula

**Como** aluno, **quero** pagar pela matrícula em uma aula, **para** garantir minha vaga.

**Critérios de aceitação:**

- O pagamento é processado dentro da plataforma

- O aluno visualiza o valor total antes de confirmar o pagamento

- A comunicação de dados de pagamento é feita via HTTPS

- Após o pagamento confirmado, a matrícula do aluno é efetivada

### HU011: Cancelamento de matrícula

**Como** aluno, **quero** cancelar minha matrícula em uma aula, **para** desistir de uma aula que não poderei mais assistir.

**Critérios de aceitação:**

- O aluno pode cancelar a matrícula respeitando o limite de horas de antecedência definido pela plataforma

- Ao cancelar dentro do prazo permitido, o aluno recebe reembolso proporcional ao tempo que ficou com a matrícula reservada

- Fora do prazo permitido, o sistema informa que o cancelamento não gera reembolso

- O sistema pede confirmação antes de efetivar o cancelamento

### HU012: Consultar histórico de aulas assistidas

**Como** aluno, **quero** consultar o histórico das aulas que já assisti, **para** acompanhar meu progresso de estudos.

**Critérios de aceitação:**

- O histórico lista data, matéria, monitor e status da aula (concluída, cancelada)

- O aluno pode acessar a gravação de aulas já assistidas, respeitando o prazo de retenção

- O histórico é ordenado da aula mais recente para a mais antiga

## Avaliações

### HU013: Avaliação de professor após a aula

**Como** aluno, **quero** avaliar o monitor após a aula, **para** contribuir com a reputação dele na plataforma.

**Critérios de aceitação:**

- A avaliação é feita em uma escala de estrelas (1 a 5)

- Se a nota for menor que 3 estrelas, o sistema exige uma justificativa por escrito

- A avaliação fica visível no perfil público do monitor

- A avaliação só pode ser feita após a conclusão da aula

### HU014: Avaliação de aluno após aula

**Como** monitor, **quero** avaliar o aluno após a aula, **para** contribuir com a reputação dele na plataforma.

**Critérios de aceitação:**

- A avaliação é feita em uma escala de estrelas (1 a 5)

- Se a nota for menor que 3 estrelas, o sistema exige uma justificativa por escrito

- A avaliação só pode ser feita após a conclusão da aula

- A avaliação recíproca (aluno ↔ monitor) é independente: um lado não vê a nota do outro antes de avaliar

## Denúncias e Moderação

### HU015: Denúncia de aula (aluno)

**Como** aluno, **quero** denunciar uma aula ou o comportamento de um monitor, **para** relatar situações inadequadas à administração da plataforma.

**Critérios de aceitação:**

- O botão de denúncia está disponível na tela da aula ou no histórico

- O aluno descreve o motivo da denúncia

- A denúncia é registrada e associada à aula e aos usuários envolvidos, com log da ação

- O aluno recebe confirmação de que a denúncia foi registrada

### HU016: Denúncia de aula (professor)

**Como** monitor, **quero** denunciar uma aula ou o comportamento de um aluno, **para** relatar situações inadequadas à administração da plataforma.

**Critérios de aceitação:**

- O botão de denúncia está disponível na tela da aula ou no histórico

- O monitor descreve o motivo da denúncia

- A denúncia é registrada e associada à aula e aos usuários envolvidos, com log da ação

- O monitor recebe confirmação de que a denúncia foi registrada

### HU017: Efetuar reembolso

**Como** monitor, **quero** efetuar o reembolso de uma matrícula em situações justificadas, **para** resolver problemas com um aluno sem depender exclusivamente da administração.

**Critérios de aceitação:**

- O monitor pode iniciar um reembolso para uma matrícula específica

- O sistema solicita confirmação antes de efetivar o reembolso

- O aluno é notificado quando o reembolso é processado

- A ação fica registrada em log, vinculada ao monitor que a realizou

### HU018: Avaliação de denúncia

**Como** administrador, **quero** avaliar as denúncias registradas na plataforma, **para** decidir as medidas cabíveis sobre usuários e aulas denunciadas.

**Critérios de aceitação:**

- O administrador visualiza a lista de denúncias pendentes, com detalhes da aula e dos usuários envolvidos

- O administrador pode acessar a gravação da aula denunciada como evidência, respeitando o prazo de retenção

- O administrador registra a decisão tomada (procedente ou improcedente) e uma justificativa

- Toda decisão do administrador é registrada em log
