echo off
set SEP=;
if defined JAVA_HOME goto :JAVA_FOUND
rem # --------------------------------------------------------------------
rem # Locate JDK system - scan through all available in standard places and 
rem # remember just the last one
rem # Search order prefers 64 bit over 32 bit, and highest release numbers
rem # --------------------------------------------------------------------
for /D %%D in ("c:\Program Files (x86)"\Java\jdk* "c:\Program Files"\Java\jdk*) do set "JAVA_HOME=%%D"
rem Add Java binaries to PATH
set "PATH=%JAVA_HOME%\bin%SEP%%PATH%"
:JAVA_FOUND
if defined WORKSPACE goto :WORKSPACE_FOUND
set "WORKSPACE=."
:WORKSPACE_FOUND
rem # --------------------------------------------------------------------
rem # Base of derby database system
rem # --------------------------------------------------------------------
set "DERBY_HOME=%WORKSPACE%\lib\db-derby-10.14.2.0-lib"
rem # --------------------------------------------------------------------
rem # Home of JUnit
rem # --------------------------------------------------------------------
set "JUNIT5=%WORKSPACE%\lib\junit5-5.6.2"
rem # --------------------------------------------------------------------
rem # --------------------------------------------------------------------
rem # Do not need to change beyond here
rem # --------------------------------------------------------------------
rem #
rem # --------------------------------------------------------------------
rem # Other definitions used
rem # --------------------------------------------------------------------
set "CP_DERBY=%DERBY_HOME%\lib\derbyshared.jar%SEP%%DERBY_HOME%\lib\derby.jar%SEP%%DERBY_HOME%\lib\derbytools.jar%SEP%."
set "JUNIT_JUPITER=%JUNIT5%\junit-jupiter-5.6.2.jar%SEP%%JUNIT5%\junit-jupiter-api-5.6.2.jar%SEP%%JUNIT5%\junit-jupiter-engine-5.6.2#.jar%SEP%%JUNIT5%\junit-jupiter-migrationsupport-5.6.2.jar%SEP%%JUNIT5%\junit-jupiter-params-5.6.2.jar"
set "JUNIT_PLATFORM=%JUNIT5%\junit-platform-commons-1.6.2.jar%SEP%%JUNIT5%\junit-platform-engine-1.6.2.jar%SEP%%JUNIT5%\junit-platform-launcher-1.6.2.jar%SEP%%JUNIT5%\junit-platform-reporting-1.6.2.jar%SEP%%JUNIT5%\junit-platform-runner-1.6.2.jar%SEP%%JUNIT5%\junit-platform-suite-api-1.6.2.jar%SEP%%JUNIT5%\junit-platform-testkit-1.6.2.jar"
set "JUNIT_VINTAGE=%JUNIT5%\junit-vintage-engine-5.6.2.jar"
set "JUNIT_DEPS=%JUNIT5%\apiguardian-api-1.1.0.jar%SEP%%JUNIT5%\opentest4j-1.2.0.jar"
set "CP_JUNIT5=%JUNIT_JUPITER%%SEP%%JUNIT_PLATFORM%%SEP%%JUNIT_VINTAGE%%SEP%%JUNIT_DEPS%"
rem # set FLAGS=-source 1.8 -Xlint:unchecked
set FLAGS=-source 1.8
echo on
