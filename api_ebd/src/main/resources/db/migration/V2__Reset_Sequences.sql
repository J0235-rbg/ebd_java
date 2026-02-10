-- V2__Reset_Sequences.sql
-- Este script reseta automaticamente as sequências para o próximo valor disponível
-- Importante: Execute este script após restaurar um backup com dados antigos

-- Função para resetar sequências
CREATE OR REPLACE FUNCTION reset_sequences() RETURNS void AS $$
DECLARE
    r RECORD;
    table_name text;
    max_id integer;
BEGIN
    -- Define as tabelas que têm sequências
    FOR table_name IN 
        SELECT 'igreja'::text
        UNION ALL SELECT 'setor'
        UNION ALL SELECT 'congregacao'
        UNION ALL SELECT 'cargo'
        UNION ALL SELECT 'pessoa'
        UNION ALL SELECT 'classe'
        UNION ALL SELECT 'trim'
        UNION ALL SELECT 'aluno_responsavel'
        UNION ALL SELECT 'aluno_dependente'
        UNION ALL SELECT 'chamada'
        UNION ALL SELECT 'registro_chamada'
        UNION ALL SELECT 'dados_adicionais_chamada'
    LOOP
        -- Busca o máximo ID de cada tabela
        EXECUTE 'SELECT MAX(id) FROM ' || table_name INTO max_id;
        
        -- Se a tabela tem registros, atualiza a sequência
        IF max_id IS NOT NULL THEN
            EXECUTE 'ALTER SEQUENCE ' || table_name || '_id_seq RESTART WITH ' || (max_id + 1);
        END IF;
    END LOOP;
END;
$$ LANGUAGE plpgsql;

-- Executa a função para resetar todas as sequências
SELECT reset_sequences();

-- Remove a função após uso
DROP FUNCTION reset_sequences();
