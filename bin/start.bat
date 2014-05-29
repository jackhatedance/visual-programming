
set home_dir=%~dp0%..

cd "%home_dir%"

remm for %%i in (cooby-?.?.jar) do set jar=%%i
rem echo %jar%

rem the conf classpath is to overwrite default configurations
java -cp conf;* com.bluesky.visualprogramming.ui.Main

