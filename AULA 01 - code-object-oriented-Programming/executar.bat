@echo off
setlocal
chcp 65001 >nul

set "ROOT=%~dp0"
set "JAVA=%ROOT%java\bin\java.exe"
set "JAVAC=%ROOT%java\bin\javac.exe"
set "BUILD=%ROOT%build-portatil"

if not exist "%JAVA%" goto java_ausente
if not exist "%JAVAC%" goto java_ausente

if not "%~1"=="" (
    set "PACOTE=%~1"
    goto validar
)

:menu
cls
echo ==============================================
echo     AULA DE POO - JAVA PORTATIL
echo ==============================================
echo.
echo  1 - Visita 1: classes e objetos
echo  2 - Visita 2: associacao e navegabilidade
echo  3 - Visita 3: multiplicidade
echo  4 - Visita 4: composicao
echo  5 - Visita 5: agregacao
echo  6 - Visita 6: encapsulamento
echo  7 - Visita 7: heranca
echo  8 - Visita 8: classes e metodos abstratos
echo  9 - Visita 9: interfaces e contratos
echo 10 - Visita 10: sobrecarga e sobrescrita
echo 11 - Visita 11: polimorfismo
echo 12 - Nucleo 1: exemplo consolidado
echo 13 - Nucleo 2: sistema consolidado
echo 14 - Nucleo 3: modelo completo
echo  0 - Sair
echo.
set /p "OPCAO=Digite uma opcao: "

if "%OPCAO%"=="1" set "PACOTE=visita1"
if "%OPCAO%"=="2" set "PACOTE=visita2"
if "%OPCAO%"=="3" set "PACOTE=visita3"
if "%OPCAO%"=="4" set "PACOTE=visita4"
if "%OPCAO%"=="5" set "PACOTE=visita5"
if "%OPCAO%"=="6" set "PACOTE=visita6"
if "%OPCAO%"=="7" set "PACOTE=visita7"
if "%OPCAO%"=="8" set "PACOTE=visita8"
if "%OPCAO%"=="9" set "PACOTE=visita9"
if "%OPCAO%"=="10" set "PACOTE=visita10"
if "%OPCAO%"=="11" set "PACOTE=visita11"
if "%OPCAO%"=="12" set "PACOTE=nucleo1"
if "%OPCAO%"=="13" set "PACOTE=nucleo2"
if "%OPCAO%"=="14" set "PACOTE=nucleo3"
if "%OPCAO%"=="0" exit /b 0

if not defined PACOTE (
    echo.
    echo Opcao invalida.
    pause
    goto menu
)

:validar
set "PACOTE_VALIDO="
for %%P in (
    visita1 visita2 visita3 visita4 visita5 visita6
    visita7 visita8 visita9 visita10 visita11
    nucleo1 nucleo2 nucleo3
) do if "%PACOTE%"=="%%P" set "PACOTE_VALIDO=1"

if not defined PACOTE_VALIDO (
    echo Pacote invalido: %PACOTE%
    goto erro
)

if not exist "%ROOT%%PACOTE%\Aplicacao.java" (
    echo Pacote invalido: %PACOTE%
    goto erro
)

if not exist "%BUILD%" mkdir "%BUILD%"

pushd "%ROOT%"
if errorlevel 1 goto erro

echo.
echo Compilando %PACOTE% com o Java portatil...
"%JAVAC%" -encoding UTF-8 -d "%BUILD%" %PACOTE%\*.java
if errorlevel 1 (
    popd
    goto erro
)

echo.
echo Executando %PACOTE%.Aplicacao...
echo.
"%JAVA%" -Dfile.encoding=UTF-8 -cp "%BUILD%" "%PACOTE%.Aplicacao"
if errorlevel 1 (
    popd
    goto erro
)

popd

echo.
echo Execucao concluida com o Java incluido no projeto.
echo.
pause
exit /b 0

:java_ausente
echo ERRO: o Java portatil nao foi encontrado na pasta java\bin.
goto erro

:erro
echo.
echo A operacao nao foi concluida.
pause
exit /b 1
