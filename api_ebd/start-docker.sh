#!/bin/bash

# Script para iniciar o banco de dados PostgreSQL com Docker

echo "Iniciando PostgreSQL..."
docker-compose up -d

echo ""
echo "Aguardando o banco de dados ficar pronto..."
sleep 10

echo ""
echo "==============================================="
echo "PostgreSQL iniciado com sucesso!"
echo "==============================================="
echo ""
echo "Detalhes da conexão:"
echo "  Host: localhost"
echo "  Porta: 5432"
echo "  Banco: ebd_db"
echo "  Usuário: ebd_user"
echo "  Senha: ebd_password"
echo ""
echo "Para parar o container, execute: docker-compose down"
echo "Para ver os logs, execute: docker-compose logs -f postgres"
echo ""
