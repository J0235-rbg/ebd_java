@echo off
REM Script para iniciar o banco de dados PostgreSQL com Docker (Windows)

echo Iniciando PostgreSQL...
docker-compose up -d

echo.
echo Aguardando o banco de dados ficar pronto...
timeout /t 10

echo.
echo ===============================================
echo PostgreSQL iniciado com sucesso!
echo ===============================================
echo.
echo Detalhes da conexao:
echo   Host: localhost
echo   Porta: 5432
echo   Banco: ebd_db
echo   Usuario: ebd_user
echo   Senha: ebd_password
echo.
echo Para parar o container, execute: docker-compose down
echo Para ver os logs, execute: docker-compose logs -f postgres
echo.
pause
