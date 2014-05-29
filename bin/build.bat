


set home_dir=%~dp0%..

cd "%home_dir%"

call mvn clean install

echo run bin\start.bat
