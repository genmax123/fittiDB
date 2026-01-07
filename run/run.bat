@echo off
REM =========================================
REM FittiDB Startskript (JavaFX + SQLite)
REM =========================================

cd /d %~dp0..

java ^
 --module-path "target\lib" ^
 --add-modules javafx.controls,javafx.fxml ^
 -jar "target\fittidb-1.0.0.jar"

pause
