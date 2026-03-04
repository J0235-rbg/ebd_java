# 📡 API EBD - Documentação de Endpoints

## 🔗 Base URL
```
http://localhost:8080/ebd
```

## 📑 Índice
- [Igrejas](#-igrejas)
- [Pessoas](#-pessoas)
- [Cargos](#-cargos)
- [Alunos Dependentes](#-alunos-dependentes)
- [Alunos Responsáveis](#-alunos-responsáveis)
- [Congregações](#-congregações)
- [Setores](#-setores)
- [Classes](#-classes)
- [Trimestres](#-trimestres)
- [Chamadas](#-chamadas)
- [Registro de Chamada](#-registro-de-chamada)
- [Dados Adicionais](#-dados-adicionais)
- [Relatórios](#-relatórios)

---

## ⛪ Igrejas
**Base Path:** `/ebd/igrejas`

### 1. Buscar Chave de Login da Igreja
```http
GET /ebd/igrejas/{id}/chave-login
```

**Path Parameters:**
- `id` (Integer) - ID da igreja

**Response:**
```json
"chave-de-acesso-unica"
```

**Descrição:** Retorna a chave de login/validação de uma igreja específica. Esta chave é usada como validação geral do sistema.

---

### 2. Validar Chave de Login
```http
GET /ebd/igrejas/validar-chave
```

**Query Parameters:**
- `chaveLogin` (String) - Chave de login a ser validada

**Response:**
```json
true
```

**Descrição:** Valida se uma chave de login existe no sistema. Retorna `true` se válida, `false` caso contrário.

---

### 3. Buscar Igreja por Chave
```http
GET /ebd/igrejas/buscar-por-chave
```

**Query Parameters:**
- `chaveLogin` (String) - Chave de login da igreja

**Response:**
```json
{
  "id": 1,
  "nome": "string",
  "endereco": "string",
  "chaveLogin": "string",
  "ativa": true
}
```

**Descrição:** Busca uma igreja pela chave de login.

---

### 4. Buscar Igreja por ID
```http
GET /ebd/igrejas/{id}
```

**Path Parameters:**
- `id` (Integer) - ID da igreja

**Response:**
```json
{
  "id": 1,
  "nome": "string",
  "endereco": "string",
  "chaveLogin": "string",
  "ativa": true
}
```

**Descrição:** Retorna uma igreja específica pelo ID.

---

### 5. Listar Todas as Igrejas
```http
GET /ebd/igrejas/listar
```

**Descrição:** Lista todas as igrejas cadastradas no sistema.

---

### 6. Listar Igrejas Ativas
```http
GET /ebd/igrejas/listar/ativas
```

**Descrição:** Lista apenas as igrejas ativas do sistema.

---

### 7. Criar Igreja
```http
POST /ebd/igrejas/criar
```

**Request Body:**
```json
{
  "nome": "string",
  "endereco": "string",
  "chaveLogin": "string",
  "ativa": true
}
```

**Descrição:** Cria uma nova igreja no sistema. A `chaveLogin` deve ser única.

---

### 8. Atualizar Igreja
```http
PUT /ebd/igrejas/{id}
```

**Path Parameters:**
- `id` (Integer) - ID da igreja

**Request Body:** Mesmo formato do criar igreja

**Descrição:** Atualiza os dados de uma igreja existente.

---

### 9. Ativar Igreja
```http
PUT /ebd/igrejas/{id}/ativar
```

**Path Parameters:**
- `id` (Integer) - ID da igreja

**Descrição:** Ativa uma igreja desativada.

---

### 10. Desativar Igreja
```http
PUT /ebd/igrejas/{id}/desativar
```

**Path Parameters:**
- `id` (Integer) - ID da igreja

**Descrição:** Desativa uma igreja.

---

## 👤 Pessoas
**Base Path:** `/ebd/pessoas`

### 1. Criar Pessoa
```http
POST /ebd/pessoas/criar
```

**Request Body:**
```json
{
  "nome": "string",
  "telefone": "string",
  "dt_nascimento": "2024-01-01",
  "endereco": "string",
  "numero": "string",
  "bairro": "string",
  "cargo": { "id": 1 },
  "congregacao": { "id": 1 },
  "igreja": { "id": 1 },
  "login": "string",
  "keyApp": "string",
  "admin": false,
  "podeRelatorio": false,
  "ativo": true
}
```

**Descrição:** Cria uma nova pessoa no sistema (membro, professor, administrador).

---

### 2. Atualizar Pessoa
```http
PUT /ebd/pessoas/{id}
```

**Path Parameters:**
- `id` (Integer) - ID da pessoa

**Request Body:** Mesmo formato do criar pessoa

**Descrição:** Atualiza os dados de uma pessoa existente.

---

### 3. Buscar Pessoa por ID
```http
GET /ebd/pessoas/{id}
```

**Path Parameters:**
- `id` (Integer) - ID da pessoa

**Descrição:** Retorna uma pessoa específica pelo ID.

---

### 4. Listar Todas as Pessoas
```http
GET /ebd/pessoas/listar
```

**Descrição:** Lista todas as pessoas do sistema.

---

### 5. Listar Pessoas Ativas
```http
GET /ebd/pessoas/ativos/listar
```

**Descrição:** Lista apenas as pessoas ativas do sistema.

---

### 6. Listar Pessoas por Congregação
```http
GET /ebd/pessoas/congregacao
```

**Query Parameters:**
- `congregacaoId` (Integer) - ID da congregação

**Descrição:** Lista todas as pessoas de uma congregação específica.

---

### 7. Listar Pessoas por Cargo
```http
GET /ebd/pessoas/cargo
```

**Query Parameters:**
- `cargoId` (Integer) - ID do cargo

**Descrição:** Lista todas as pessoas que possuem um cargo específico.

---

### 8. Listar Pessoas por Classe
```http
GET /ebd/pessoas/classe/{classeId}
```

**Path Parameters:**
- `classeId` (Integer) - ID da classe

**Descrição:** Lista todas as pessoas vinculadas a uma classe específica.

---

### 9. Listar Pessoas por Igreja
```http
GET /ebd/pessoas/igreja
```

**Query Parameters:**
- `igrejaId` (Integer) - ID da igreja

**Descrição:** Lista todas as pessoas de uma igreja específica.

---

### 10. Login (Autenticação)
```http
POST /ebd/pessoas/login
```

**Request Body:**
```json
{
  "login": "string",
  "keyApp": "string"
}
```

**Response:**
```json
"Bem vindo, [Nome]! Login realizado"
```

**Descrição:** Realiza o login de um usuário usando login e keyApp.

---

### 11. Atualizar Cargo
```http
PUT /ebd/pessoas/{id}/cargo
```

**Path Parameters:**
- `id` (Integer) - ID da pessoa

**Request Body:**
```json
{
  "cargoId": 1
}
```

**Descrição:** Atualiza o cargo de uma pessoa específica.

---

### 12. Obter Cargo
```http
GET /ebd/pessoas/{id}/cargo
```

**Path Parameters:**
- `id` (Integer) - ID da pessoa

**Response:** Integer (ID do cargo)

**Descrição:** Obtém o ID do cargo de uma pessoa específica.

---

### 13. Ativar Pessoa
```http
PUT /ebd/pessoas/{id}/ativar
```

**Path Parameters:**
- `id` (Integer) - ID da pessoa

**Descrição:** Ativa uma pessoa desativada.

---

### 14. Desativar Pessoa
```http
PUT /ebd/pessoas/{id}/desativar
```

**Path Parameters:**
- `id` (Integer) - ID da pessoa

**Descrição:** Desativa uma pessoa.

---

### 15. Deletar Pessoa
```http
DELETE /ebd/pessoas/{id}
```

**Path Parameters:**
- `id` (Integer) - ID da pessoa

**Descrição:** Remove uma pessoa do sistema.

---

## � Cargos
**Base Path:** `/ebd/cargos`

### 1. Criar Cargo
```http
POST /ebd/cargos/criar
```

**Request Body:**
```json
{
  "nome": "Professor"
}
```

**Response:**
```json
{
  "id": 1,
  "nome": "Professor"
}
```

**Descrição:** Cria um novo cargo no sistema (ex: Professor, Coordenador, Secretário).

---

### 2. Atualizar Cargo
```http
PUT /ebd/cargos/{id}
```

**Path Parameters:**
- `id` (Integer) - ID do cargo

**Request Body:**
```json
{
  "nome": "Professor Auxiliar"
}
```

**Response:**
```json
{
  "id": 1,
  "nome": "Professor Auxiliar"
}
```

**Descrição:** Atualiza os dados de um cargo existente.

---

### 3. Buscar Cargo por ID
```http
GET /ebd/cargos/{id}
```

**Path Parameters:**
- `id` (Integer) - ID do cargo

**Response:**
```json
{
  "id": 1,
  "nome": "Professor"
}
```

**Descrição:** Retorna um cargo específico pelo ID.

---

### 4. Listar Todos os Cargos
```http
GET /ebd/cargos/listar
```

**Response:**
```json
[
  {
    "id": 1,
    "nome": "Professor"
  },
  {
    "id": 2,
    "nome": "Coordenador"
  },
  {
    "id": 3,
    "nome": "Secretário"
  }
]
```

**Descrição:** Lista todos os cargos cadastrados no sistema.

---

### 5. Deletar Cargo
```http
DELETE /ebd/cargos/{id}
```

**Path Parameters:**
- `id` (Integer) - ID do cargo

**Response:** 204 No Content

**Descrição:** Remove um cargo do sistema.

---

## �👨‍🎓 Alunos Dependentes
**Base Path:** `/ebd/alunos/dependentes`

### 1. Criar Aluno Dependente
```http
POST /ebd/alunos/dependentes/criar
```

**Request Body:**
```json
{
  "nome": "string",
  "alunoResponsavel": 1,
  "igreja": 1,
  "setor": 1,
  "congregacao": 1,
  "classe": 1,
  "status": 1,
  "dt_nascimento": "2015-05-10"
}
```

**Descrição:** Cria um novo aluno dependente (menor de idade) no sistema.

---

### 2. Buscar Aluno Dependente por ID
```http
GET /ebd/alunos/dependentes/{id}
```

**Path Parameters:**
- `id` (Integer) - ID do aluno dependente

**Descrição:** Retorna um aluno dependente específico pelo ID.

---

### 3. Buscar Aluno Dependente por ID e Classe
```http
GET /ebd/alunos/dependentes/{alunoId}/classe/{classeId}
```

**Path Parameters:**
- `alunoId` (Integer) - ID do aluno
- `classeId` (Integer) - ID da classe

**Descrição:** Retorna um aluno dependente específico, validando se pertence à classe.

---

### 4. Listar Alunos Dependentes por Classe
```http
GET /ebd/alunos/dependentes/classe/{classeId}
```

**Path Parameters:**
- `classeId` (Integer) - ID da classe

**Descrição:** Lista todos os alunos dependentes de uma classe específica.

---

### 5. Listar Alunos Dependentes por Congregação
```http
GET /ebd/alunos/dependentes/congregacao/{congregacaoId}
```

**Path Parameters:**
- `congregacaoId` (Integer) - ID da congregação

**Descrição:** Lista todos os alunos dependentes de uma congregação específica.

---

### 6. Listar Alunos Dependentes por Igreja
```http
GET /ebd/alunos/dependentes/igreja/{igrejaId}
```

**Path Parameters:**
- `igrejaId` (Integer) - ID da igreja

**Descrição:** Lista todos os alunos dependentes de uma igreja específica.

---

### 7. Listar Todos os Alunos Dependentes
```http
GET /ebd/alunos/dependentes/listar
```

**Descrição:** Lista todos os alunos dependentes cadastrados no sistema.

---

### 8. Listar Alunos Dependentes Ativos
```http
GET /ebd/alunos/dependentes/listar/ativos
```

**Descrição:** Lista apenas os alunos dependentes ativos do sistema.

---

### 9. Listar Alunos Dependentes Ativos por Classe
```http
GET /ebd/alunos/dependentes/classe/{classeId}/ativos
```

**Path Parameters:**
- `classeId` (Integer) - ID da classe

**Response:**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "alunoResponsavel": 5,
    "igreja": 1,
    "setor": 1,
    "congregacao": 1,
    "classe": 1,
    "status": 1,
    "dt_nascimento": "2015-05-10"
  }
]
```

**Descrição:** Lista apenas os alunos dependentes ATIVOS (status = 1) de uma classe específica, ordenados alfabeticamente por nome. **Este é o endpoint principal para carregar alunos ativos para registro de presença.**

---

### 10. Atualizar Aluno Dependente
```http
PUT /ebd/alunos/dependentes/{id}
```

**Path Parameters:**
- `id` (Integer) - ID do aluno dependente

**Request Body:** Mesmo formato do criar aluno dependente

**Descrição:** Atualiza os dados de um aluno dependente existente.

---

### 11. Ativar Aluno Dependente
```http
PUT /ebd/alunos/dependentes/{id}/ativar
```

**Path Parameters:**
- `id` (Integer) - ID do aluno dependente

**Descrição:** Ativa um aluno dependente desativado.

---

### 12. Desativar Aluno Dependente
```http
PUT /ebd/alunos/dependentes/{id}/desativar
```

**Path Parameters:**
- `id` (Integer) - ID do aluno dependente

**Descrição:** Desativa um aluno dependente.

---

### 12. Deletar Aluno Dependente
```http
DELETE /ebd/alunos/dependentes/{id}
```

**Path Parameters:**
- `id` (Integer) - ID do aluno dependente

**Descrição:** Remove um aluno dependente do sistema.

---

## 👨‍🎓 Alunos Responsáveis
**Base Path:** `/ebd/alunos/responsaveis`

### 1. Criar Aluno Responsável
```http
POST /ebd/alunos/responsaveis/criar
```

**Request Body:**
```json
{
  "nome": "string",
  "igreja": 1,
  "setor": 1,
  "congregacao": 1,
  "classe": 1,
  "sexo": 1,
  "email": "string@email.com",
  "observacao": "string",
  "status": 1,
  "dt_nascimento": "1990-03-15"
}
```

**Descrição:** Cria um novo aluno responsável (adulto) no sistema.

---

### 2. Buscar Aluno Responsável por ID
```http
GET /ebd/alunos/responsaveis/{id}
```

**Path Parameters:**
- `id` (Integer) - ID do aluno responsável

**Descrição:** Retorna um aluno responsável específico pelo ID.

---

### 3. Buscar Aluno Responsável por ID e Classe
```http
GET /ebd/alunos/responsaveis/{alunoId}/classe/{classeId}
```

**Path Parameters:**
- `alunoId` (Integer) - ID do aluno
- `classeId` (Integer) - ID da classe

**Descrição:** Retorna um aluno responsável específico, validando se pertence à classe.

---

### 4. Listar Alunos Responsáveis por Classe
```http
GET /ebd/alunos/responsaveis/classe/{classeId}
```

**Path Parameters:**
- `classeId` (Integer) - ID da classe

**Descrição:** Lista todos os alunos responsáveis de uma classe específica.

---

### 5. Listar Alunos Responsáveis por Congregação
```http
GET /ebd/alunos/responsaveis/congregacao/{congregacaoId}
```

**Path Parameters:**
- `congregacaoId` (Integer) - ID da congregação

**Descrição:** Lista todos os alunos responsáveis de uma congregação específica.

---

### 6. Listar Alunos Responsáveis por Igreja
```http
GET /ebd/alunos/responsaveis/igreja/{igrejaId}
```

**Path Parameters:**
- `igrejaId` (Integer) - ID da igreja

**Descrição:** Lista todos os alunos responsáveis de uma igreja específica.

---

### 7. Listar Todos os Alunos Responsáveis
```http
GET /ebd/alunos/responsaveis/listar
```

**Descrição:** Lista todos os alunos responsáveis cadastrados no sistema.

---

### 8. Listar Alunos Responsáveis Ativos
```http
GET /ebd/alunos/responsaveis/listar/ativos
```

**Descrição:** Lista apenas os alunos responsáveis ativos do sistema.

---

### 9. Listar Alunos Responsáveis Ativos por Classe
```http
GET /ebd/alunos/responsaveis/classe/{classeId}/ativos
```

**Path Parameters:**
- `classeId` (Integer) - ID da classe

**Response:**
```json
[
  {
    "id": 1,
    "nome": "João da Silva",
    "email": "joao@email.com",
    "sexo": 1,
    "status": 1,
    "classe": 1,
    "dt_nascimento": "1985-08-20"
  },
  {
    "id": 2,
    "nome": "Maria Santos",
    "email": "maria@email.com",
    "sexo": 2,
    "status": 1,
    "classe": 1,
    "dt_nascimento": "1990-12-05"
  }
]
```

**Descrição:** Lista apenas os alunos responsáveis ATIVOS (status = 1) de uma classe específica, ordenados alfabeticamente por nome.

---

### 10. Atualizar Aluno Responsável
```http
PUT /ebd/alunos/responsaveis/{id}
```

**Path Parameters:**
- `id` (Integer) - ID do aluno responsável

**Request Body:** Mesmo formato do criar aluno responsável

**Descrição:** Atualiza os dados de um aluno responsável existente.

---

### 11. Ativar Aluno Responsável
```http
PUT /ebd/alunos/responsaveis/{id}/ativar
```

**Path Parameters:**
- `id` (Integer) - ID do aluno responsável

**Descrição:** Ativa um aluno responsável desativado.

---

### 12. Desativar Aluno Responsável
```http
PUT /ebd/alunos/responsaveis/{id}/desativar
```

**Path Parameters:**
- `id` (Integer) - ID do aluno responsável

**Descrição:** Desativa um aluno responsável.

---

### 13. Deletar Aluno Responsável
```http
DELETE /ebd/alunos/responsaveis/{id}
```

**Path Parameters:**
- `id` (Integer) - ID do aluno responsável

**Descrição:** Remove um aluno responsável do sistema.

---

## 🏛️ Congregações
**Base Path:** `/ebd/congregacoes`

### 1. Criar Congregação
```http
POST /ebd/congregacoes/criar
```

**Request Body:**
```json
{
  "nome": "string",
  "endereco": "string",
  "setor": { "id": 1 },
  "igreja": { "id": 1 }
}
```

**Descrição:** Cria uma nova congregação.

---

### 2. Atualizar Congregação
```http
PUT /ebd/congregacoes/{id}
```

**Path Parameters:**
- `id` (Integer) - ID da congregação

**Request Body:** Mesmo formato do criar congregação

**Descrição:** Atualiza os dados de uma congregação existente.

---

### 3. Listar por Igreja
```http
GET /ebd/congregacoes/listar
```

**Query Parameters:**
- `igrejaId` (Integer) - ID da igreja

**Descrição:** Lista todas as congregações de uma igreja.

---

### 4. Listar por Setor
```http
GET /ebd/congregacoes/listar-por-setor
```

**Query Parameters:**
- `setorId` (Integer) - ID do setor

**Descrição:** Lista todas as congregações de um setor.

---

### 5. Buscar por ID
```http
GET /ebd/congregacoes/{id}
```

**Path Parameters:**
- `id` (Integer) - ID da congregação

**Descrição:** Retorna uma congregação específica pelo ID.

---

### 6. Deletar Congregação
```http
DELETE /ebd/congregacoes/{id}
```

**Path Parameters:**
- `id` (Integer) - ID da congregação

**Descrição:** Desativa uma congregação.

---

## 📍 Setores
**Base Path:** `/ebd/setores`

### 1. Criar Setor
```http
POST /ebd/setores/criar
```

**Request Body:**
```json
{
  "nome": "string",
  "igreja": { "id": 1 }
}
```

**Descrição:** Cria um novo setor.

---

### 2. Atualizar Setor
```http
PUT /ebd/setores/{id}
```

**Path Parameters:**
- `id` (Integer) - ID do setor

**Request Body:** Mesmo formato do criar setor

**Descrição:** Atualiza os dados de um setor existente.

---

### 3. Listar por Igreja
```http
GET /ebd/setores/listar
```

**Query Parameters:**
- `igrejaId` (Integer) - ID da igreja

**Descrição:** Lista todos os setores de uma igreja.

---

### 4. Buscar por ID
```http
GET /ebd/setores/{id}
```

**Path Parameters:**
- `id` (Integer) - ID do setor

**Descrição:** Retorna um setor específico pelo ID.

---

## 🎓 Classes
**Base Path:** `/ebd/classes`

### 1. Criar Classe
```http
POST /ebd/classes/criar
```

**Request Body:**
```json
{
  "nome": "string",
  "professor": { "id": 1 },
  "congregacao": { "id": 1 },
  "igreja": { "id": 1 },
  "status": 1
}
```

**Descrição:** Cria uma nova classe/turma.

---

### 2. Atualizar Classe
```http
PUT /ebd/classes/{id}
```

**Path Parameters:**
- `id` (Integer) - ID da classe

**Request Body:** Mesmo formato do criar classe

**Descrição:** Atualiza os dados de uma classe existente.

---

### 3. Desativar Classe
```http
DELETE /ebd/classes/{id}
```

**Path Parameters:**
- `id` (Integer) - ID da classe

**Descrição:** Desativa uma classe.

---

### 4. Listar Classes Ativas
```http
GET /ebd/classes/listar
```

**Query Parameters:**
- `igrejaId` (Integer) - ID da igreja

**Descrição:** Lista todas as classes ativas de uma igreja.

---

### 5. Classes do Professor
```http
GET /ebd/classes/professor/{pessoaId}
```

**Path Parameters:**
- `pessoaId` (Integer) - ID do professor

**Query Parameters:**
- `igrejaId` (Integer) - ID da igreja

**Descrição:** Lista todas as classes de um professor específico.

---

### 6. Listar Professores de uma Classe
```http
GET /ebd/classes/{classeId}/professores
```

**Path Parameters:**
- `classeId` (Integer) - ID da classe

**Response:**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "telefone": "3199999999",
    "dt_nascimento": "1980-05-15",
    "endereco": "Rua das Flores",
    "numero": "123",
    "bairro": "Centro",
    "cargo": 2,
    "congregacao": 1,
    "igreja": 1,
    "ativo": true,
    "admin": false,
    "podeRelatorio": true
  },
  {
    "id": 2,
    "nome": "Maria Santos",
    "telefone": "3188888888",
    "dt_nascimento": "1985-08-20",
    "endereco": "Avenida Principal",
    "numero": "456",
    "bairro": "Bairro Novo",
    "cargo": 2,
    "congregacao": 1,
    "igreja": 1,
    "ativo": true,
    "admin": false,
    "podeRelatorio": true
  }
]
```

**Descrição:** Retorna todos os professores cadastrados para uma classe específica. A lista pode conter de 1 a 3 professores, conforme configurado na classe.

---

## 📅 Trimestres
**Base Path:** `/ebd/trimestres`

### 1. Criar Trimestre (Automático)
```http
POST /ebd/trimestres/auto
```

**Request Body:**
```json
{
  "igrejaId": 1,
  "ano": 2024,
  "trimestre": 1
}
```

**Descrição:** Cria um trimestre automaticamente e gera todas as chamadas do período (13 domingos).

---

### 2. Fechar Trimestre
```http
PUT /ebd/trimestres/{id}/fechar
```

**Path Parameters:**
- `id` (Integer) - ID do trimestre

**Descrição:** Fecha um trimestre, impedindo novos registros.

---

### 3. Buscar Trimestre por ID
```http
GET /ebd/trimestres/{id}
```

**Path Parameters:**
- `id` (Integer) - ID do trimestre

**Response:**
```json
{
  "id": 1,
  "igreja": 1,
  "ano": 2024,
  "dataInicio": "2024-01-07",
  "dataFim": "2024-03-31",
  "status": "Aberto"
}
```

**Descrição:** Retorna um trimestre específico pelo ID.

---

### 4. Listar Trimestres por Igreja
```http
GET /ebd/trimestres/listar/igreja/{igrejaId}
```

**Path Parameters:**
- `igrejaId` (Integer) - ID da igreja

**Descrição:** Lista todos os trimestres de uma igreja, ordenados por data de início (mais recentes primeiro).

---

### 5. Listar Trimestres Ativos por Igreja
```http
GET /ebd/trimestres/listar/igreja/{igrejaId}/ativos
```

**Path Parameters:**
- `igrejaId` (Integer) - ID da igreja

**Response:**
```json
[
  {
    "id": 1,
    "igreja": 1,
    "ano": 2024,
    "dataInicio": "2024-01-07",
    "dataFim": "2024-03-31",
    "status": "Aberto"
  }
]
```

**Descrição:** Lista apenas os trimestres ativos (status: Aberto) de uma igreja específica. **Este é o endpoint principal para buscar apenas os trimestres em vigência.**

---

## ✅ Chamadas
**Base Path:** `/ebd/chamadas`

### 1. Buscar Chamadas do Dia (todas)
```http
GET /ebd/chamadas/dia
```

**Query Parameters:**
- `igrejaId` (Integer) - ID da igreja
- `trimId` (Integer) - ID do trimestre

**Descrição:** Retorna as chamadas do **próximo domingo (ou o mesmo domingo)** para a igreja e trimestre informados, sem filtrar status.

---

### 2. Buscar Chamadas do Dia (somente ativas/abertas)
```http
GET /ebd/chamadas/dia-ativos
```

**Query Parameters:**
- `igrejaId` (Integer) - ID da igreja
- `trimId` (Integer) - ID do trimestre

**Descrição:** Retorna apenas chamadas com status **Aberto** do **próximo domingo (ou o mesmo domingo)**.

---

### 3. Buscar Chamadas do Dia por Classe
```http
GET /ebd/chamadas/dia/classe
```

**Query Parameters:**
- `igrejaId` (Integer) - ID da igreja
- `trimId` (Integer) - ID do trimestre
- `classeId` (Integer) - ID da classe

**Descrição:** Retorna chamadas **Abertas** do **próximo domingo (ou o mesmo domingo)** para uma classe específica.

---

### 4. Abrir Chamada
```http
PUT /ebd/chamadas/{id}/abrir
```

**Path Parameters:**
- `id` (Integer) - ID da chamada

**Descrição:** Abre uma chamada para registro de presença.

---

### 5. Fechar Chamada
```http
PUT /ebd/chamadas/{id}/fechar
```

**Path Parameters:**
- `id` (Integer) - ID da chamada

**Request Body:**
```json
{
  "responsavelId": 10,
  "visitantes": 5,
  "oferta": 150.00
}
```

**Response:**
```json
"Chamada fechada com sucesso"
```

**Descrição:** Fecha uma chamada e consolida os dados.

---

### 6. Fechar Todas as Chamadas do Dia
```http
PUT /ebd/chamadas/fechar-dia
```

**Query Parameters:**
- `dataDomingo` (LocalDate) - Data de referência (formato: YYYY-MM-DD)
- `igrejaId` (Integer) - ID da igreja
- `trimId` (Integer) - ID do trimestre

**Exemplo:**
```http
PUT /ebd/chamadas/fechar-dia?dataDomingo=2026-03-03&igrejaId=1&trimId=1
```

**Regra da data:**
- O sistema calcula automaticamente o **próximo domingo ou o mesmo domingo** a partir da data enviada.
- Exemplo: `2026-03-03` -> `2026-03-08`
- Exemplo: `2026-03-08` -> `2026-03-08`

**Response:**
```json
"12 chamada(s) fechada(s) com sucesso"
```

**Validações:**
- Só fecha chamadas com status "Aberto"
- Retorna erro se nenhuma chamada aberta for encontrada para o domingo calculado

**Descrição:** Fecha todas as chamadas do domingo calculado de uma só vez. Este endpoint simplifica o processo de fechamento, evitando a necessidade de fechar cada classe individualmente.

---

### 7. Abrir Todas as Chamadas do Dia
```http
PUT /ebd/chamadas/abrir-dia
```

**Query Parameters:**
- `dataDomingo` (LocalDate) - Data de referência (formato: YYYY-MM-DD)
- `igrejaId` (Integer) - ID da igreja
- `trimId` (Integer) - ID do trimestre

**Exemplo:**
```http
PUT /ebd/chamadas/abrir-dia?dataDomingo=2026-03-03&igrejaId=1&trimId=1
```

**Regra da data:**
- O sistema calcula automaticamente o **próximo domingo ou o mesmo domingo** a partir da data enviada.
- Exemplo: `2026-03-03` -> `2026-03-08`
- Exemplo: `2026-03-08` -> `2026-03-08`

**Response:**
```json
"12 chamada(s) aberta(s) com sucesso"
```

**Validações:**
- Só abre chamadas com status "Fechado"
- Retorna erro se nenhuma chamada fechada for encontrada para o domingo calculado

**Descrição:** Reabre todas as chamadas do domingo calculado de uma só vez. **Ideal para botão de alternar abrir/fechar chamada no front-end.**

---

## 📝 Registro de Chamada
**Base Path:** `/ebd/registro-chamada`

### 1. Listar Registros de Chamada
```http
GET /ebd/registro-chamada/{chamadaId}
```

**Path Parameters:**
- `chamadaId` (Integer) - ID da chamada

**Response:**
```json
[
  {
    "chamada": { "id": 1 },
    "aluno": { "id": 1 },
    "biblia": true,
    "revista": true,
    "status": 1
  }
]
```

**Descrição:** Lista todos os registros de presença de uma chamada específica.

---

### 2. Atualizar Registros em Lote
```http
POST /ebd/registro-chamada/atualizar
```

**Request Body:**
```json
{
  "chamadaId": 1,
  "registros": [
    {
      "alunoId": 1,
      "status": 1,
      "biblia": true,
      "revista": true
    }
  ]
}
```

**Response:**
```json
"Alunos registrados com sucesso"
```

**Descrição:** Registra ou atualiza presença/ausência de múltiplos alunos de uma vez.

**Status de Presença:**
- `1` - Presente
- `2` - Ausente
- `3` - Visitante

---

## � Dados Adicionais
**Base Path:** `/ebd/dados-adicionais`

### 1. Buscar Dados Adicionais por Chamada
```http
GET /ebd/dados-adicionais/chamada/{chamadaId}
```

**Path Parameters:**
- `chamadaId` (Integer) - ID da chamada

**Response:**
```json
{
  "id": 1,
  "chamada": { "id": 1 },
  "oferta": 150.50,
  "visitantes": 5,
  "presentes": 25,
  "ausentes": 3,
  "matriculados": 28,
  "totalPresenca": 25,
  "biblias": 24,
  "revistas": 23,
  "responsavel": 1
}
```

**Descrição:** Retorna todos os dados adicionais associados a uma chamada específica.

---

### 2. Registrar Dados Adicionais e Gerar Presença
```http
PUT /ebd/dados-adicionais/chamada/{chamadaId}
```

**Request Body:**
```json
{
  "chamadaId": 1,
  "visitantes": 5,
  "oferta": 150.50,
  "professorId": 2,
  "auxiliaresIds": [3, 4]
}
```

**Campos do Request:**
- `chamadaId` (Integer, obrigatório) - ID da chamada
- `visitantes` (Integer, opcional) - Número de visitantes
- `oferta` (BigDecimal, opcional) - Valor da oferta (pode ser null)
- `professorId` (Integer, opcional) - ID do professor responsável pela aula
- `auxiliaresIds` (Array de Integer, opcional) - IDs dos professores auxiliares

**Response:**
```json
{
  "chamadaId": 1,
  "visitantes": 5,
  "oferta": 150.50,
  "professorId": 2,
  "auxiliaresIds": [3, 4]
}
```

**Processamento Automático (Fluxo Importante):**

Quando este endpoint é chamado, o sistema automaticamente:

1. **Salva os dados adicionais** (visitantes, oferta) associados à chamada
2. **Gera presença automática** para o professor (`professorId`):
   - Busca a classe original do professor (através do vínculo Pessoa → AlunoResponsavel)
   - Localiza a chamada da classe original do mesmo dia
   - Cria/atualiza registro de presença com status **PRESENTE (1)**
   - Marca automaticamente com **bíblia (1)** e **revista (1)**

3. **Gera presença automática** para cada auxiliar em `auxiliaresIds`:
   - Mesmo processo acima para cada ID fornecido
   - Todos são marcados como presentes com bíblia e revista

**Descrição:** Endpoint crítico para fechar o ciclo de uma chamada/aula. Registra dados complementares (visitantes, oferta) e automaticamente marca presença para professor e auxiliares na sua classe original no mesmo dia. Este fluxo garante que professores sejam contabilizados como presentes quando ministram aulas.

---

### 3. Buscar Dados Adicionais por ID
```http
GET /ebd/dados-adicionais/{id}
```

**Path Parameters:**
- `id` (Integer) - ID dos dados adicionais

**Descrição:** Retorna os dados adicionais específicos pelo ID.

---

## �📊 Relatórios
**Base Path:** `/ebd/relatorios`

### 📘 Relatórios de Alunos

#### 1. Relatório Individual de Aluno
```http
GET /ebd/relatorios/aluno/{alunoId}
```

**Path Parameters:**
- `alunoId` (Integer) - ID do aluno

**Query Parameters:**
- `trimestreId` (Integer, opcional) - ID do trimestre
- `classeId` (Integer, **obrigatório**) - ID da classe

**Response:**
```json
{
  "alunoId": 1,
  "alunoNome": "string",
  "classeNome": "string",
  "totalPresencas": 10,
  "totalFaltas": 2,
  "totalBiblias": 9,
  "totalRevistas": 8,
  "pontuacao": 37,
  "frequenciaPercentual": 83.33
}
```

**Descrição:** Gera relatório completo de um aluno específico (presenças, faltas, materiais).

---

#### 2. Relatórios de Todos os Alunos
```http
GET /ebd/relatorios/alunos
```

**Query Parameters:**
- `congregacaoId` (Integer, opcional) - Filtrar por congregação
- `trimestreId` (Integer, opcional) - Filtrar por trimestre
- `classeId` (Integer, **obrigatório**) - ID da classe

**Descrição:** Lista relatórios de todos os alunos com filtros opcionais.

---

### 📗 Relatórios de Classes

#### 3. Relatório de Classe
```http
GET /ebd/relatorios/classe/{classeId}
```

**Path Parameters:**
- `classeId` (Integer) - ID da classe

**Query Parameters:**
- `trimestreId` (Integer, opcional) - ID do trimestre

**Response:**
```json
{
  "classeId": 1,
  "classeNome": "string",
  "professorNome": "string",
  "totalAlunos": 15,
  "totalPresencas": 120,
  "totalFaltas": 25,
  "totalBiblias": 110,
  "totalRevistas": 105,
  "frequenciaMedia": 82.75,
  "pontuacaoTotal": 465
}
```

**Descrição:** Gera relatório completo de uma classe específica.

---

#### 4. Relatórios de Todas as Classes
```http
GET /ebd/relatorios/classes
```

**Query Parameters:**
- `congregacaoId` (Integer, opcional) - Filtrar por congregação
- `trimestreId` (Integer, opcional) - Filtrar por trimestre

**Descrição:** Lista relatórios de todas as classes.

---

### 📙 Relatórios de Chamadas

#### 5. Relatório de Chamada
```http
GET /ebd/relatorios/chamada/{chamadaId}
```

**Path Parameters:**
- `chamadaId` (Integer) - ID da chamada

**Response:**
```json
{
  "chamadaId": 1,
  "data": "2024-01-07",
  "classeNome": "string",
  "totalPresentes": 12,
  "totalAusentes": 3,
  "totalVisitantes": 1,
  "totalBiblias": 10,
  "totalRevistas": 11,
  "status": "Fechado"
}
```

**Descrição:** Gera relatório detalhado de uma chamada específica.

---

#### 6. Relatórios de Chamadas
```http
GET /ebd/relatorios/chamadas
```

**Query Parameters:**
- `classeId` (Integer, opcional) - Filtrar por classe
- `trimestreId` (Integer, opcional) - Filtrar por trimestre

**Descrição:** Lista relatórios de chamadas com filtros. **Retorna apenas chamadas abertas.**

---

#### 7. Relatório Geral de Chamadas do Dia
```http
GET /ebd/relatorios/chamadas/geral
```

**Query Parameters:**
- `dataDomingo` (LocalDate) - Data do domingo (formato: YYYY-MM-DD)

**Exemplo:**
```http
GET /ebd/relatorios/chamadas/geral?dataDomingo=2024-03-10
```

**Response:**
```json
{
  "data": "2024-03-10",
  "classes": [
    {
      "nomeClasse": "Adultos A",
      "matriculados": 45,
      "ausentes": 5,
      "presentes": 40,
      "biblias": 40,
      "revistas": 38,
      "visitantes": 2,
      "totalPresenca": 40,
      "oferta": 250.00
    },
    {
      "nomeClasse": "Adolescentes",
      "matriculados": 28,
      "ausentes": 3,
      "presentes": 25,
      "biblias": 25,
      "revistas": 24,
      "visitantes": 1,
      "totalPresenca": 25,
      "oferta": 150.50
    }
  ],
  "totalMatriculados": 285,
  "totalClasses": 8,
  "totalPresentes": 145,
  "totalAusentes": 23,
  "totalVisitantes": 12,
  "totalOferta": 850.50,
  "totalBiblias": 290,
  "totalRevistas": 288
}
```

**Descrição:** Gera um relatório consolidado com detalhes de CADA classe e os totais gerais de TODAS as classes de um domingo específico.

**Dados por Classe:**
- **nomeClasse**: Nome da classe/turma
- **matriculados**: Total de alunos matriculados e ativos (dependentes + responsáveis)
- **ausentes**: Quantidade de alunos ausentes naque dia
- **presentes**: Quantidade de alunos presentes naquele dia
- **biblias**: Total de bíblias distribuídas naquela classe
- **revistas**: Total de revistas distribuídas naquela classe
- **visitantes**: Total de visitantes registrados naquela classe
- **totalPresenca**: Total de presentes (mesmo valor de "presentes")
- **oferta**: Valor total coletado de oferta naquela classe

**Totais Gerais (consolidado de TODAS as classes):**
- **data**: Data do domingo
- **totalMatriculados**: Soma de todos os alunos matriculados e ativos em todas as classes
- **totalClasses**: Quantidade de classes que tiveram chamada naquele dia
- **totalPresentes**: Soma de todos os alunos presentes em todas as classes
- **totalAusentes**: Soma de todos os alunos ausentes em todas as classes
- **totalVisitantes**: Soma de todos os visitantes registrados em todas as classes
- **totalOferta**: Soma total das ofertas recolhidas em todas as classes
- **totalBiblias**: Soma total de bíblias distribuídas em todas as classes
- **totalRevistas**: Soma total de revistas distribuídas em todas as classes

**Este é o relatório ideal para consolidação de dados ao final do culto dominical, com visibilidade tanto dos dados individuais de cada classe quanto dos totais gerais.**

---

### 📕 Relatórios de Trimestres

#### 8. Relatório de Trimestre
```http
GET /ebd/relatorios/trimestre/{trimestreId}
```

**Path Parameters:**
- `trimestreId` (Integer) - ID do trimestre

**Response:**
```json
{
  "trimestreId": 1,
  "ano": 2024,
  "trimestre": 1,
  "dataInicio": "2024-01-01",
  "dataFim": "2024-03-31",
  "totalClasses": 8,
  "totalAlunos": 120,
  "totalChamadas": 13,
  "frequenciaGeral": 85.5
}
```

**Descrição:** Gera relatório completo de um trimestre específico.

---

#### 8. Relatórios de Trimestres
```http
GET /ebd/relatorios/trimestres
```

**Query Parameters:**
- `igrejaId` (Integer, opcional) - Filtrar por igreja
- `ano` (Integer, opcional) - Filtrar por ano

**Descrição:** Lista relatórios de trimestres com filtros.

---

### 🎂 Relatórios de Aniversariantes

#### 9. Aniversariantes do Mês
```http
GET /ebd/relatorios/aniversariantes/mes/{mes}
```

**Path Parameters:**
- `mes` (Integer) - Número do mês (1-12)

**Query Parameters:**
- `congregacaoId` (Integer, opcional) - Filtrar por congregação

**Response:**
```json
[
  {
    "alunoId": 1,
    "alunoNome": "string",
    "dataNascimento": "2010-05-15",
    "idade": 14,
    "classeNome": "string",
    "tipo": "Dependente"
  }
]
```

**Descrição:** Lista todos os aniversariantes de um mês específico. **Busca em `aluno_dependente` e `aluno_responsavel`**.

---

#### 10. Aniversariantes do Dia
```http
GET /ebd/relatorios/aniversariantes/dia
```

**Query Parameters:**
- `data` (LocalDate, **obrigatório**) - Data específica (formato: `2024-01-15`)
- `congregacaoId` (Integer, opcional) - Filtrar por congregação

**Descrição:** Lista aniversariantes de uma data específica.

---

#### 11. Próximos Aniversariantes
```http
GET /ebd/relatorios/aniversariantes/proximos
```

**Query Parameters:**
- `dias` (Integer, default: 30) - Número de dias futuros
- `congregacaoId` (Integer, opcional) - Filtrar por congregação

**Descrição:** Lista os próximos aniversariantes nos próximos X dias.

---

### 🏆 Rankings

#### 12. Ranking de Alunos
```http
GET /ebd/relatorios/ranking/alunos
```

**Query Parameters:**
- `trimestreId` (Integer, opcional) - Filtrar por trimestre
- `classeId` (Integer, opcional) - Filtrar por classe
- `congregacaoId` (Integer, opcional) - Filtrar por congregação
- `limite` (Integer, opcional) - Limitar número de resultados

**Response:**
```json
[
  {
    "alunoId": 1,
    "alunoNome": "string",
    "classeNome": "string",
    "pontuacao": 45,
    "totalPresencas": 12,
    "totalBiblias": 11,
    "totalRevistas": 10,
    "posicao": 1
  }
]
```

**Descrição:** Gera ranking de alunos por pontuação (presença + bíblias + revistas).

**Cálculo de Pontuação:**
- Presença = 1 ponto
- Bíblia = 1 ponto
- Revista = 1 ponto
- Pontuação máxima por chamada = 3 pontos

---

#### 13. Ranking Geral de Alunos
```http
GET /ebd/relatorios/ranking/alunos/geral
```

**Query Parameters:**
- `trimestreId` (Integer, opcional) - Filtrar por trimestre
- `limite` (Integer, opcional) - Limitar número de resultados

**Descrição:** Ranking unificado de dependentes e responsáveis. **Usa registro interno `AlunoInfoUnico` para otimização.**

---

#### 14. Ranking de Dependentes
```http
GET /ebd/relatorios/ranking/alunos/dependentes
```

**Query Parameters:**
- `trimestreId` (Integer, opcional) - Filtrar por trimestre
- `limite` (Integer, opcional) - Limitar número de resultados

**Descrição:** Ranking apenas de alunos dependentes (menores de idade).

---

#### 15. Ranking de Responsáveis
```http
GET /ebd/relatorios/ranking/alunos/responsaveis
```

**Query Parameters:**
- `trimestreId` (Integer, opcional) - Filtrar por trimestre
- `limite` (Integer, opcional) - Limitar número de resultados

**Descrição:** Ranking apenas de alunos responsáveis (adultos).

---

#### 16. Ranking de Classes
```http
GET /ebd/relatorios/ranking/classes
```

**Query Parameters:**
- `trimestreId` (Integer, opcional) - Filtrar por trimestre
- `congregacaoId` (Integer, opcional) - Filtrar por congregação
- `limite` (Integer, opcional) - Limitar número de resultados

**Response:**
```json
[
  {
    "classeId": 1,
    "classeNome": "string",
    "professorNome": "string",
    "pontuacaoTotal": 450,
    "totalAlunos": 15,
    "frequenciaMedia": 85.5,
    "posicao": 1
  }
]
```

**Descrição:** Gera ranking de classes por pontuação total.

---

#### 17. Ranking de Alunos por Frequência
```http
GET /ebd/relatorios/ranking/alunos/frequencia
```

**Query Parameters:**
- `trimestreId` (Integer, opcional) - Filtrar por trimestre
- `limite` (Integer, default: 10) - Limitar número de resultados

**Descrição:** Ranking baseado em frequência percentual de presença.

---

#### 18. Ranking de Classes por Frequência
```http
GET /ebd/relatorios/ranking/classes/frequencia
```

**Query Parameters:**
- `trimestreId` (Integer, opcional) - Filtrar por trimestre
- `limite` (Integer, default: 10) - Limitar número de resultados

**Descrição:** Ranking baseado em frequência média de presença das classes.

---

#### 19. Ranking por Faixa Etária (Trimestral)
```http
GET /ebd/relatorios/ranking/idade-grupos/trimestral
```

**Query Parameters:**
- `trimestreId` (Integer, **obrigatório**) - ID do trimestre

**Response:**
```json
[
  {
    "grupoIdade": "0-12",
    "totalAlunos": 25,
    "pontuacaoMedia": 35.5,
    "frequenciaMedia": 82.0
  }
]
```

**Descrição:** Ranking de alunos agrupados por faixa etária em um trimestre.

---

#### 20. Ranking por Faixa Etária (Anual)
```http
GET /ebd/relatorios/ranking/idade-grupos/anual/{ano}
```

**Path Parameters:**
- `ano` (Integer) - Ano para análise

**Descrição:** Ranking de alunos agrupados por faixa etária em um ano completo.

---

## 📌 Notas Importantes

### Códigos de Status de Presença
- **1** - Presente
- **2** - Ausente
- **3** - Visitante

### Status de Chamada
- **Aberto** - Chamada em andamento, aceita registros
- **Fechado** - Chamada finalizada, não aceita mais registros

### Status de Ativação
- **1** - Ativo
- **0** - Inativo

### Observações Importantes

1. **Chave de Login da Igreja**: A `chaveLogin` da igreja é a validação geral do sistema. Ela deve ser única e é usada para autenticação e controle de acesso. Use o endpoint `/ebd/igrejas/{id}/chave-login` para obter essa chave.

2. **Relatório de Aluno Individual**: Sempre requer o parâmetro `classeId` para evitar confusão entre ID de dependentes e responsáveis.

3. **Relatório de Chamadas**: Por padrão, retorna apenas chamadas com status "Aberto". Use filtros de trimestre/classe conforme necessário.

4. **Aniversariantes**: Os dados são buscados nas tabelas `aluno_dependente` e `aluno_responsavel`, não na tabela `pessoa`.

5. **Rankings**: Utilizam caching de nomes de classes para otimização de performance.

6. **Pontuação**: Calculada como: Presenças + Bíblias + Revistas (máximo 3 pontos por chamada).

7. **Trimestre Automático**: Ao criar um trimestre com `/auto`, o sistema gera automaticamente 13 chamadas (domingos do trimestre).

---

## 🔧 Swagger UI

Para visualização interativa e testes de todos os endpoints, acesse:

```
http://localhost:8080/swagger-ui.html
```

---

**Documentação atualizada em:** Fevereiro de 2026
