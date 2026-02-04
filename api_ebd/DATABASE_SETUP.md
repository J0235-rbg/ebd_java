# Configuração do Banco de Dados - EBD API

## Pré-requisitos

- Docker instalado
- Docker Compose instalado
- Java 21+
- Maven 3.8+

## Instruções de Setup

### 1. Inicie o PostgreSQL com Docker

#### No Windows:
```bash
start-docker.bat
```

#### No Linux/Mac:
```bash
chmod +x start-docker.sh
./start-docker.sh
```

#### Ou manualmente:
```bash
docker-compose up -d
```

### 2. Verifique se o banco está rodando

```bash
docker-compose ps
```

Você deve ver algo como:
```
NAME                COMMAND             STATUS
ebd_postgres_db     postgres            Up (healthy)
```

### 3. Compile o projeto

```bash
mvn clean install
```

### 4. Inicie a aplicação

A aplicação iniciará e o Flyway executará as migrações automaticamente:

```bash
mvn spring-boot:run
```

## Credenciais de Acesso

- **Host**: localhost
- **Porta**: 5432
- **Banco**: ebd_db
- **Usuário**: ebd_user
- **Senha**: ebd_password

## Conexão com ferramentas externas

### DBeaver, pgAdmin, etc:

```
URL: jdbc:postgresql://localhost:5432/ebd_db
Driver: PostgreSQL
User: ebd_user
Password: ebd_password
```

### Terminal (psql):

```bash
psql -h localhost -U ebd_user -d ebd_db
# Password: ebd_password
```

## Estrutura das Tabelas Criadas

- **Igreja** - Instituição religiosa
- **Setor** - Divisão da igreja
- **Congregação** - Parte do setor
- **Pessoa** - Cadastro de pessoas
- **Cargo** - Cargos de professores e coordenadores
- **Classe** - Turmas de EBD
- **Trim** - Trimestres/Períodos
- **Aluno Responsável** - Alunos maiores de idade
- **Aluno Dependente** - Menores de idade
- **Chamada** - Registro de presença por aula
- **Registro Chamada** - Detalhes individuais de presença
- **Chamada Dados Adicionais** - Dados extras de chamada
- **Tesouraria** - Controle de ofertas

## Comandos Úteis

### Ver logs do PostgreSQL:
```bash
docker-compose logs -f postgres
```

### Parar o PostgreSQL:
```bash
docker-compose down
```

### Parar e remover volumes (limpar dados):
```bash
docker-compose down -v
```

### Reiniciar o container:
```bash
docker-compose restart postgres
```

### Executar query no banco:
```bash
docker exec -it ebd_postgres_db psql -U ebd_user -d ebd_db -c "SELECT * FROM pessoa;"
```

## Migrações Flyway

As migrações são executadas automaticamente ao iniciar a aplicação. Elas estão localizadas em:
```
src/main/resources/db/migration/
```

### Formato de arquivos de migração:
- `V1__Create_initial_tables.sql` - Versão 1 (inicial)
- `V2__Add_new_columns.sql` - Versão 2 (próxima migração)

### Criar uma nova migração:

1. Crie um arquivo em `src/main/resources/db/migration/`
2. Siga o padrão: `V<número>__<descricao>.sql`
3. Escreva o SQL (apenas para criar/alterar, não para deletar em produção)
4. Ao iniciar a aplicação, o Flyway executará automaticamente

## Troubleshooting

### "Conexão recusada" ao iniciar a aplicação:

Verifique se o PostgreSQL está rodando:
```bash
docker-compose ps
```

Se não estiver, inicie:
```bash
docker-compose up -d
```

### "Usuário ou banco não existem":

Recrie os containers:
```bash
docker-compose down -v
docker-compose up -d
```

### Port 5432 já está em uso:

Altere a porta no `docker-compose.yml`:
```yaml
ports:
  - "5433:5432"  # Use 5433 em vez de 5432
```

E atualize o `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/ebd_db
```

## Backup e Restore

### Fazer backup do banco:
```bash
docker exec ebd_postgres_db pg_dump -U ebd_user -d ebd_db > backup.sql
```

### Restaurar de um backup:
```bash
docker exec -i ebd_postgres_db psql -U ebd_user -d ebd_db < backup.sql
```

---

Para mais informações sobre Flyway: https://flywaydb.org/
Para mais informações sobre PostgreSQL: https://www.postgresql.org/
