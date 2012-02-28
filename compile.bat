@echo off
echo 'Compiling...'
cd ./src/
javac -cp ./ -d ../bin/ *.java
pause