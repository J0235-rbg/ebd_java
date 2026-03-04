# 📚 API EBD - Sistema de Gerenciamento de Escola Bíblica Dominical

## 📋 Visão Geral

Sistema completo para gerenciamento de Escola Bíblica Dominical (EBD), incluindo controle de presença, gestão de classes, alunos, professores e geração de relatórios analíticos e rankings.

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 4.0.2** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Segurança e autenticação
- **Spring Web MVC** - API REST

### Banco de Dados
- **PostgreSQL** - Banco de dados relacional
- **Flyway** - Versionamento e migração de banco de dados

### Documentação
- **SpringDoc OpenAPI 2.5.0** - Documentação automática da API (Swagger)

### Ferramentas de Desenvolvimento
- **Lombok** - Redução de boilerplate code
- **Maven** - Gerenciamento de dependências
- **Docker & Docker Compose** - Containerização

### Testes
- **Spring Boot Test** - Framework de testes
- **Testcontainers** - Testes de integração com containers

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

```
api_ebd/
├── config/           # Configurações (Security, JPA, Jackson, Swagger)
├── controller/       # Camada de apresentação (REST endpoints)
├── service/          # Lógica de negócio
├── repository/       # Acesso a dados (JPA Repositories)
├── domain/
│   ├── entity/       # Entidades JPA
│   └── enums/        # Enumerações
├── dto/              # Data Transfer Objects
│   ├── request/      # DTOs de entrada
│   ├── response/     # DTOs de saída
│   └── relatorio/    # DTOs de relatórios
└── exception/        # Tratamento de exceções customizadas
```

## 🔧 Configuração do Ambiente

### Pré-requisitos
- Java 21 ou superior
- Maven 3.8+
- Docker e Docker Compose
- PostgreSQL 15+ (ou usar o Docker)

### Configuração do Banco de Dados

#### Opção 1: Usando Docker (Recomendado)
```bash
# No diretório raiz do projeto
docker-compose up -d
```

Ou use os scripts auxiliares:
```bash
# Windows
start-docker.bat

# Linux/Mac
./start-docker.sh
```

#### Opção 2: PostgreSQL Local
Configure o arquivo `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ebd_db
spring.datasource.username=ebd_user
spring.datasource.password=ebd_password
```

### Executando a Aplicação

```bash
# Compilar o projeto
./mvnw clean install

# Executar a aplicação
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

## 📖 Documentação da API

Após iniciar a aplicação, acesse a documentação interativa Swagger em:

```
http://localhost:8080/swagger-ui.html
```

## 🗄️ Migrações de Banco de Dados

O Flyway gerencia automaticamente as migrações do banco de dados. Os scripts SQL estão localizados em:

```
src/main/resources/db/migration/
```

Migrações disponíveis:
- `V1__Create_initial_tables.sql` - Criação das tabelas iniciais
- `V2__Reset_Sequences.sql` - Reset de sequences

## 🔐 Segurança

O sistema utiliza Spring Security com autenticação baseada em login e chave de acesso (keyApp). A configuração de segurança está em `SecurityConfig.java`.

## 📊 Principais Funcionalidades

### Gestão de Entidades
- ✅ Congregações e Setores
- ✅ Pessoas (Membros, Professores, Alunos)
- ✅ Classes/Turmas
- ✅ Trimestres
- ✅ Chamadas (Presença/Ausência)

### Relatórios e Análises
- 📈 Relatórios individuais de alunos
- 📈 Relatórios de classes
- 📈 Relatórios de chamadas
- 📈 Relatórios de trimestres
- 🎂 Aniversariantes (mês, dia, próximos)

### Rankings
- 🏆 Ranking de alunos (geral, dependentes, responsáveis)
- 🏆 Ranking de classes
- 🏆 Ranking por frequência
- 🏆 Ranking por faixa etária

## 📁 Estrutura de Dados

### Principais Entidades
- **Igreja** - Igreja/Ministério
- **Setor** - Divisão regional
- **Congregacao** - Congregação/Ponto de pregação
- **Pessoa** - Membros, professores, administradores
- **Classe** - Turmas da EBD
- **AlunoDependente** - Alunos menores de idade
- **AlunoResponsavel** - Alunos adultos/responsáveis
- **Trim** - Trimestre letivo
- **Chamada** - Registro de chamada de presença
- **RegistroChamada** - Registro individual de presença/ausência

## 🔄 Status e Códigos

### Status de Presença
- `1` - Presente
- `2` - Ausente
- `3` - Visitante

### Status de Chamada
- `Aberto` - Chamada em andamento
- `Fechado` - Chamada finalizada

### Status de Ativação
- `1` - Ativo
- `0` - Inativo

## 🛠️ Desenvolvimento

### Build do Projeto
```bash
./mvnw clean package
```

### Executar Testes
```bash
./mvnw test
```

### Gerar Documentação
```bash
./mvnw javadoc:javadoc
```

## 📝 Convenções de Código

- **Controllers**: Camada de apresentação REST, validações básicas
- **Services**: Lógica de negócio, regras e validações complexas
- **Repositories**: Apenas acesso a dados, queries customizadas quando necessário
- **DTOs**: Transferência de dados entre camadas
- **Entities**: Mapeamento JPA das tabelas do banco

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/NovaFuncionalidade`)
5. Abra um Pull Request

## 📧 Contato

Para dúvidas ou sugestões, entre em contato com a equipe de desenvolvimento.

## 📄 Licença

Este projeto é proprietário e confidencial.

---

**Desenvolvido com ❤️ para o gerenciamento eficiente da Escola Bíblica Dominical**
