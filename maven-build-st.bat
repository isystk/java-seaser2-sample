@echo off

set PJT_HOME=%~dp0\..
set JAVA_HOME=%PJT_HOME%\tools\java
set MAVEN_HOME=%PJT_HOME%\tools\maven

set PATH=%JAVA_HOME%\bin;%PATH%
set PATH=%MAVEN_HOME%\bin;%PATH%

call mvn clean package -P st assembly:assembly -f %PJT_HOME%\src\pom.xml

pause