


set home_dir=%~dp0%..

cd "%home_dir%"

call mvn package

if not exist lib mkdir lib


copy target\cooby-?.?.jar lib


echo run bin\start.bat
