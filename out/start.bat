@echo off
chcp 65001 > nul
java -Dfile.encoding=UTF-8 -jar "%~dp0CalcStudentGrade.jar"
pause