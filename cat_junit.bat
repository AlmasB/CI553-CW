echo off
call cat_defs.bat

java -jar "%JUNIT5%\junit-platform-console-standalone-1.6.2.jar" -cp "%CP_DERBY%" %*
