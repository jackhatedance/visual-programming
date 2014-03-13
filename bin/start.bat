
set home_dir=%~dp0%..

cd "%home_dir%"

for %%i in (lib\cooby-?.?.jar) do set jar=%%i

echo %jar%
java -jar %jar%

