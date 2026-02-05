-- V1__Create_initial_tables.sql

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tabela Igreja
CREATE TABLE IF NOT EXISTS igreja (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255),
    chave_login VARCHAR(255) NOT NULL UNIQUE,
    ativa BOOLEAN DEFAULT TRUE
);

-- Tabela Setor
CREATE TABLE IF NOT EXISTS setor (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(255) NOT NULL,
    id_igreja UUID NOT NULL,
    CONSTRAINT fk_setor_igreja FOREIGN KEY (id_igreja) REFERENCES igreja(id)
);

-- Tabela Congregacao
CREATE TABLE IF NOT EXISTS congregacao (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(255) NOT NULL,
    id_setor UUID NOT NULL,
    id_igreja UUID NOT NULL,
    CONSTRAINT fk_congregacao_setor FOREIGN KEY (id_setor) REFERENCES setor(id),
    CONSTRAINT fk_congregacao_igreja FOREIGN KEY (id_igreja) REFERENCES igreja(id)
);

-- Tabela Cargo
CREATE TABLE IF NOT EXISTS cargo (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(255)
);

-- Tabela Pessoa
CREATE TABLE IF NOT EXISTS pessoa (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(255),
    dt_nascimento DATE,
    endereco VARCHAR(255),
    numero VARCHAR(50),
    bairro VARCHAR(255),
    id_cargo UUID,
    id_congregacao UUID,
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
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(255) NOT NULL,
    id_igreja UUID,
    id_setor UUID,
    id_congregacao UUID,
    id_professor_01 UUID,
    id_professor_02 UUID,
    id_professor_03 UUID,
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
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_igreja UUID,
    ano INT,
    data_inicio DATE,
    data_fim DATE,
    status VARCHAR(50),
    CONSTRAINT fk_trim_igreja FOREIGN KEY (id_igreja) REFERENCES igreja(id)
);

-- Tabela Aluno Responsável
CREATE TABLE IF NOT EXISTS aluno_responsavel (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_igreja UUID,
    id_setor UUID,
    id_congregacao UUID,
    id_classe UUID,
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
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_responsavel UUID NOT NULL,
    nome VARCHAR(250) NOT NULL,
    id_igreja UUID,
    id_setor UUID,
    id_congregacao UUID,
    id_classe UUID,
    status INT,
    CONSTRAINT fk_aluno_dependente_responsavel FOREIGN KEY (id_responsavel) REFERENCES aluno_responsavel(id),
    CONSTRAINT fk_aluno_dependente_igreja FOREIGN KEY (id_igreja) REFERENCES igreja(id),
    CONSTRAINT fk_aluno_dependente_setor FOREIGN KEY (id_setor) REFERENCES setor(id),
    CONSTRAINT fk_aluno_dependente_congregacao FOREIGN KEY (id_congregacao) REFERENCES congregacao(id),
    CONSTRAINT fk_aluno_dependente_classe FOREIGN KEY (id_classe) REFERENCES classe(id)
);

-- Tabela Chamada
CREATE TABLE IF NOT EXISTS chamada (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_igreja UUID,
    setor_id UUID,
    congregacao_id UUID,
    classe_id UUID NOT NULL,
    trim_id UUID NOT NULL,
    data DATE NOT NULL,
    status VARCHAR(50),
    CONSTRAINT fk_chamada_igreja FOREIGN KEY (id_igreja) REFERENCES igreja(id),
    CONSTRAINT fk_chamada_setor FOREIGN KEY (setor_id) REFERENCES setor(id),
    CONSTRAINT fk_chamada_congregacao FOREIGN KEY (congregacao_id) REFERENCES congregacao(id),
    CONSTRAINT fk_chamada_classe FOREIGN KEY (classe_id) REFERENCES classe(id),
    CONSTRAINT fk_chamada_trim FOREIGN KEY (trim_id) REFERENCES trim(id),
    CONSTRAINT uk_chamada_classe_data_trim UNIQUE (classe_id, data, trim_id)
);

-- Tabela Registro Chamada
CREATE TABLE IF NOT EXISTS registro_chamada (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    chamada_id UUID NOT NULL,
    id_aluno UUID NOT NULL,
    biblia INT,
    revista INT,
    status INT,
    CONSTRAINT uq_registro UNIQUE (chamada_id, id_aluno),
    CONSTRAINT fk_registro_chamada_chamada FOREIGN KEY (chamada_id) REFERENCES chamada(id)
);

-- Tabela Dados Adicionais Chamada
CREATE TABLE IF NOT EXISTS dados_adicionais_chamada (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    chamada_id UUID NOT NULL UNIQUE,
    oferta DECIMAL(10, 2),
    visitantes INT,
    presentes INT,
    ausentes INT,
    matriculados INT,
    total_presenca INT,
    biblias INT,
    revistas INT,
    responsavel_id UUID,
    CONSTRAINT fk_dados_adicionais_chamada_chamada FOREIGN KEY (chamada_id) REFERENCES chamada(id),
    CONSTRAINT fk_dados_adicionais_chamada_responsavel FOREIGN KEY (responsavel_id) REFERENCES pessoa(id)
);

-- Tabela Tesouraria
CREATE TABLE IF NOT EXISTS tesouraria (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    chamada_id UUID,
    classe_id UUID,
    trim_id UUID,
    valor DECIMAL(10, 2),
    CONSTRAINT fk_tesouraria_chamada FOREIGN KEY (chamada_id) REFERENCES chamada(id),
    CONSTRAINT fk_tesouraria_classe FOREIGN KEY (classe_id) REFERENCES classe(id),
    CONSTRAINT fk_tesouraria_trim FOREIGN KEY (trim_id) REFERENCES trim(id)
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
CREATE INDEX IF NOT EXISTS idx_chamada_classe ON chamada(classe_id);
CREATE INDEX IF NOT EXISTS idx_chamada_trim ON chamada(trim_id);
CREATE INDEX IF NOT EXISTS idx_chamada_data ON chamada(data);
CREATE INDEX IF NOT EXISTS idx_registro_chamada_chamada ON registro_chamada(chamada_id);
CREATE INDEX IF NOT EXISTS idx_dados_adicionais_chamada_chamada ON dados_adicionais_chamada(chamada_id);
CREATE INDEX IF NOT EXISTS idx_dados_adicionais_chamada_responsavel ON dados_adicionais_chamada(responsavel_id);
CREATE INDEX IF NOT EXISTS idx_tesouraria_chamada ON tesouraria(chamada_id);
CREATE INDEX IF NOT EXISTS idx_tesouraria_classe ON tesouraria(classe_id);
CREATE INDEX IF NOT EXISTS idx_tesouraria_trim ON tesouraria(trim_id);
