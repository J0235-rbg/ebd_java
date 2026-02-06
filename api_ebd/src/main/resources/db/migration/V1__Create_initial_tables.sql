-- V1__Create_initial_tables.sql

-- Tabela Igreja
CREATE TABLE IF NOT EXISTS igreja (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255),
    chave_login VARCHAR(255) NOT NULL UNIQUE,
    ativa BOOLEAN DEFAULT TRUE
);

-- Tabela Setor
CREATE TABLE IF NOT EXISTS setor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    id_igreja INTEGER NOT NULL,
    CONSTRAINT fk_setor_igreja FOREIGN KEY (id_igreja) REFERENCES igreja(id)
);

-- Tabela Congregacao
CREATE TABLE IF NOT EXISTS congregacao (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    id_setor INTEGER NOT NULL,
    id_igreja INTEGER NOT NULL,
    CONSTRAINT fk_congregacao_setor FOREIGN KEY (id_setor) REFERENCES setor(id),
    CONSTRAINT fk_congregacao_igreja FOREIGN KEY (id_igreja) REFERENCES igreja(id)
);

-- Tabela Cargo
CREATE TABLE IF NOT EXISTS cargo (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255)
);

-- Tabela Pessoa
CREATE TABLE IF NOT EXISTS pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(255),
    dt_nascimento DATE,
    endereco VARCHAR(255),
    numero VARCHAR(50),
    bairro VARCHAR(255),
    id_cargo INTEGER,
    id_congregacao INTEGER,
    login VARCHAR(255),
    key_app VARCHAR(255),
    admin BOOLEAN DEFAULT FALSE,
    pode_relatorio BOOLEAN DEFAULT FALSE,
    ativo BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_pessoa_cargo FOREIGN KEY (id_cargo) REFERENCES cargo(id),
    CONSTRAINT fk_pessoa_congregacao FOREIGN KEY (id_congregacao) REFERENCES congregacao(id)
);

-- Tabela Classe
CREATE TABLE IF NOT EXISTS classe (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    id_igreja INTEGER,
    id_setor INTEGER,
    id_congregacao INTEGER,
    id_professor_01 INTEGER,
    id_professor_02 INTEGER,
    id_professor_03 INTEGER,
    faix_idade_grupo VARCHAR(50),
    ativo BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_classe_igreja FOREIGN KEY (id_igreja) REFERENCES igreja(id),
    CONSTRAINT fk_classe_setor FOREIGN KEY (id_setor) REFERENCES setor(id),
    CONSTRAINT fk_classe_congregacao FOREIGN KEY (id_congregacao) REFERENCES congregacao(id),
    CONSTRAINT fk_classe_professor_01 FOREIGN KEY (id_professor_01) REFERENCES pessoa(id),
    CONSTRAINT fk_classe_professor_02 FOREIGN KEY (id_professor_02) REFERENCES pessoa(id),
    CONSTRAINT fk_classe_professor_03 FOREIGN KEY (id_professor_03) REFERENCES pessoa(id)
);

-- Tabela Trim
CREATE TABLE IF NOT EXISTS trim (
    id SERIAL PRIMARY KEY,
    id_igreja INTEGER,
    ano INT,
    data_inicio DATE,
    data_fim DATE,
    status VARCHAR(50),
    CONSTRAINT fk_trim_igreja FOREIGN KEY (id_igreja) REFERENCES igreja(id)
);

-- Tabela Aluno Responsável
CREATE TABLE IF NOT EXISTS aluno_responsavel (
    id SERIAL PRIMARY KEY,
    id_igreja INTEGER,
    id_setor INTEGER,
    id_congregacao INTEGER,
    id_classe INTEGER,
    nome VARCHAR(250) NOT NULL,
    sexo INT,
    email VARCHAR(255),
    observacao TEXT,
    status INT,
    CONSTRAINT fk_aluno_responsavel_igreja FOREIGN KEY (id_igreja) REFERENCES igreja(id),
    CONSTRAINT fk_aluno_responsavel_setor FOREIGN KEY (id_setor) REFERENCES setor(id),
    CONSTRAINT fk_aluno_responsavel_congregacao FOREIGN KEY (id_congregacao) REFERENCES congregacao(id),
    CONSTRAINT fk_aluno_responsavel_classe FOREIGN KEY (id_classe) REFERENCES classe(id)
);

-- Tabela Aluno Dependente
CREATE TABLE IF NOT EXISTS aluno_dependente (
    id SERIAL PRIMARY KEY,
    id_responsavel INTEGER NOT NULL,
    nome VARCHAR(250) NOT NULL,
    id_igreja INTEGER,
    id_setor INTEGER,
    id_congregacao INTEGER,
    id_classe INTEGER,
    status INT,
    CONSTRAINT fk_aluno_dependente_responsavel FOREIGN KEY (id_responsavel) REFERENCES aluno_responsavel(id),
    CONSTRAINT fk_aluno_dependente_igreja FOREIGN KEY (id_igreja) REFERENCES igreja(id),
    CONSTRAINT fk_aluno_dependente_setor FOREIGN KEY (id_setor) REFERENCES setor(id),
    CONSTRAINT fk_aluno_dependente_congregacao FOREIGN KEY (id_congregacao) REFERENCES congregacao(id),
    CONSTRAINT fk_aluno_dependente_classe FOREIGN KEY (id_classe) REFERENCES classe(id)
);

-- Tabela Chamada
CREATE TABLE IF NOT EXISTS chamada (
    id SERIAL PRIMARY KEY,
    id_igreja INTEGER,
    id_setor INTEGER,
    id_congregacao INTEGER,
    id_classe INTEGER NOT NULL,
    id_trim INTEGER NOT NULL,
    data DATE NOT NULL,
    status VARCHAR(50),
    CONSTRAINT fk_chamada_igreja FOREIGN KEY (id_igreja) REFERENCES igreja(id),
    CONSTRAINT fk_chamada_setor FOREIGN KEY (id_setor) REFERENCES setor(id),
    CONSTRAINT fk_chamada_congregacao FOREIGN KEY (id_congregacao) REFERENCES congregacao(id),
    CONSTRAINT fk_chamada_classe FOREIGN KEY (id_classe) REFERENCES classe(id),
    CONSTRAINT fk_chamada_trim FOREIGN KEY (id_trim) REFERENCES trim(id),
    CONSTRAINT uk_chamada_classe_data_trim UNIQUE (id_classe, data, id_trim)
);

-- Tabela Registro Chamada
CREATE TABLE IF NOT EXISTS registro_chamada (
    id SERIAL PRIMARY KEY,
    id_chamada INTEGER NOT NULL,
    id_aluno INTEGER NOT NULL,
    biblia INT,
    revista INT,
    status INT,
    CONSTRAINT uq_registro UNIQUE (id_chamada, id_aluno),
    CONSTRAINT fk_registro_chamada_chamada FOREIGN KEY (id_chamada) REFERENCES chamada(id)
);

-- Tabela Dados Adicionais Chamada
CREATE TABLE IF NOT EXISTS dados_adicionais_chamada (
    id SERIAL PRIMARY KEY,
    id_chamada INTEGER NOT NULL UNIQUE,
    oferta DECIMAL(10, 2),
    visitantes INT,
    presentes INT,
    ausentes INT,
    matriculados INT,
    total_presenca INT,
    biblias INT,
    revistas INT,
    id_responsavel INTEGER,
    CONSTRAINT fk_dados_adicionais_chamada_chamada FOREIGN KEY (id_chamada) REFERENCES chamada(id),
    CONSTRAINT fk_dados_adicionais_chamada_responsavel FOREIGN KEY (id_responsavel) REFERENCES pessoa(id)
);

-- Tabela Tesouraria
CREATE TABLE IF NOT EXISTS tesouraria (
    id SERIAL PRIMARY KEY,
    id_chamada INTEGER,
    id_classe INTEGER,
    id_trim INTEGER,
    valor DECIMAL(10, 2),
    CONSTRAINT fk_tesouraria_chamada FOREIGN KEY (id_chamada) REFERENCES chamada(id),
    CONSTRAINT fk_tesouraria_classe FOREIGN KEY (id_classe) REFERENCES classe(id),
    CONSTRAINT fk_tesouraria_trim FOREIGN KEY (id_trim) REFERENCES trim(id)
);

-- Índices
CREATE INDEX IF NOT EXISTS idx_setor_igreja ON setor(id_igreja);
CREATE INDEX IF NOT EXISTS idx_congregacao_setor ON congregacao(id_setor);
CREATE INDEX IF NOT EXISTS idx_congregacao_igreja ON congregacao(id_igreja);
CREATE INDEX IF NOT EXISTS idx_pessoa_cargo ON pessoa(id_cargo);
CREATE INDEX IF NOT EXISTS idx_pessoa_congregacao ON pessoa(id_congregacao);
CREATE INDEX IF NOT EXISTS idx_classe_igreja ON classe(id_igreja);
CREATE INDEX IF NOT EXISTS idx_classe_setor ON classe(id_setor);
CREATE INDEX IF NOT EXISTS idx_classe_congregacao ON classe(id_congregacao);
CREATE INDEX IF NOT EXISTS idx_classe_professor_01 ON classe(id_professor_01);
CREATE INDEX IF NOT EXISTS idx_classe_professor_02 ON classe(id_professor_02);
CREATE INDEX IF NOT EXISTS idx_classe_professor_03 ON classe(id_professor_03);
CREATE INDEX IF NOT EXISTS idx_trim_igreja ON trim(id_igreja);
CREATE INDEX IF NOT EXISTS idx_aluno_responsavel_igreja ON aluno_responsavel(id_igreja);
CREATE INDEX IF NOT EXISTS idx_aluno_responsavel_setor ON aluno_responsavel(id_setor);
CREATE INDEX IF NOT EXISTS idx_aluno_responsavel_congregacao ON aluno_responsavel(id_congregacao);
CREATE INDEX IF NOT EXISTS idx_aluno_responsavel_classe ON aluno_responsavel(id_classe);
CREATE INDEX IF NOT EXISTS idx_aluno_dependente_responsavel ON aluno_dependente(id_responsavel);
CREATE INDEX IF NOT EXISTS idx_aluno_dependente_igreja ON aluno_dependente(id_igreja);
CREATE INDEX IF NOT EXISTS idx_aluno_dependente_setor ON aluno_dependente(id_setor);
CREATE INDEX IF NOT EXISTS idx_aluno_dependente_congregacao ON aluno_dependente(id_congregacao);
CREATE INDEX IF NOT EXISTS idx_aluno_dependente_classe ON aluno_dependente(id_classe);
CREATE INDEX IF NOT EXISTS idx_chamada_classe ON chamada(id_classe);
CREATE INDEX IF NOT EXISTS idx_chamada_trim ON chamada(id_trim);
CREATE INDEX IF NOT EXISTS idx_chamada_data ON chamada(data);
CREATE INDEX IF NOT EXISTS idx_registro_chamada_chamada ON registro_chamada(id_chamada);
CREATE INDEX IF NOT EXISTS idx_dados_adicionais_chamada_chamada ON dados_adicionais_chamada(id_chamada);
CREATE INDEX IF NOT EXISTS idx_dados_adicionais_chamada_responsavel ON dados_adicionais_chamada(id_responsavel);
CREATE INDEX IF NOT EXISTS idx_tesouraria_chamada ON tesouraria(id_chamada);
CREATE INDEX IF NOT EXISTS idx_tesouraria_classe ON tesouraria(id_classe);
CREATE INDEX IF NOT EXISTS idx_tesouraria_trim ON tesouraria(id_trim);
