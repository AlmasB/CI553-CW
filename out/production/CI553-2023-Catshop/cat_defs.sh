# catshop set-up
# This script sets up the environment to run CatShop (linux/macos/git-bash)
# It should be 'sourced' at the top of from each command script 
# using the command 
#    . cat_defs.sh

if [ -z "$JAVA_HOME" ]; then 
# --------------------------------------------------------------------
# Locate JDK system - scan through standard places until you find one
# which exists. Then find the most recent java system there.
# Look for jdk-XX first (highest XX), if not found try jdk1.8 (old format)
# Once we have found the best java executable we can, we ask it to tell us
# what the value of JAVA_HOME should be
# --------------------------------------------------------------------
# JDK installation default folders:
# linux - /usr/java/jdk* or /usr/jdk/jdk*
# macos - /Library/Java/JavaVirtualMachines/jdk*/Contents/Home
# windows - c:\Program Files\Java\jdk* or c:\Program Files (x86)\Java\jdk* 
# gitbash - /c/Program Files/Java/jdk* or /c/Program Files (x86)/Java/jdk*

	dir=""
	file=""
	for dir in /usr/java /usr/jdk /Library/Java/JavaVirtualMachines "/c/Program Files/Java" "/c/Program Files (x86)/Java"
	do
		if [ -d "$dir" ]; then
			path="`find "$dir" -path '/*/jdk-*/javac*' -print | sort -r | head -1`"
			if [ -z "$path" ]; then
				path="`find "$dir" -path '/*/jdk1.8*/javac*' -print | sort -r | head -1`"
			fi
			if [ -n "$path" ]; then
				# remove /javac* from $path
				path=${path%/javac*}
				# set JAVA_HOME and add path to PATH
				JAVA_HOME="${path%/bin}" ; export JAVA_HOME
				PATH="${path}:${PATH}" ; export PATH
				break;
			fi
		fi
	done
fi;
# check if that all worked, and print a warning if not
# don't stop, but other things probably won't work later
if [ -z "$JAVA_HOME" ]; then
	echo 'Warning: cannot find JAVA JDK directory - set $JAVA_HOME manually'
fi

# set the path separator - most unix systems will use : but git-bash on windows needs ;
SEP=":"
DIRSEP="/"
if [ ${path:0:3} == "/c/" ] ; then SEP=";" ; DIRSEP="\\" ; fi
export SEP 
export DIRSEP

# $WORKSPACE is set-up by jenkins, and can also be used by any other external 
# caller to specify where CatShop is. If not set, we assume it is the current 
# directory
if [ -z "$WORKSPACE" ]
then
    WORKSPACE=.
fi
# --------------------------------------------------------------------
# Base of derby database system
# --------------------------------------------------------------------
DERBY_HOME=${WORKSPACE}/lib/db-derby-10.14.2.0-lib
# --------------------------------------------------------------------
# Home of JUnit
# --------------------------------------------------------------------
JUNIT5=${WORKSPACE}/lib/junit5-5.6.2
# --------------------------------------------------------------------
# --------------------------------------------------------------------
# Do not need to change beyond here
# --------------------------------------------------------------------
#
# --------------------------------------------------------------------
# Other definitions used
# --------------------------------------------------------------------
CP_DERBY=${DERBY_HOME}/lib/derbyshared.jar${SEP}${DERBY_HOME}/lib/derby.jar${SEP}${DERBY_HOME}/lib/derbytools.jar
JUNIT_JUPITER=${JUNIT5}/junit-jupiter-5.6.2.jar${SEP}${JUNIT5}/junit-jupiter-api-5.6.2.jar${SEP}${JUNIT5}/junit-jupiter-engine-5.6.2.jar${SEP}${JUNIT5}/junit-jupiter-migrationsupport-5.6.2.jar${SEP}${JUNIT5}/junit-jupiter-params-5.6.2.jar
JUNIT_PLATFORM=${JUNIT5}/junit-platform-commons-1.6.2.jar${SEP}${JUNIT5}/junit-platform-engine-1.6.2.jar${SEP}${JUNIT5}/junit-platform-launcher-1.6.2.jar${SEP}${JUNIT5}/junit-platform-reporting-1.6.2.jar${SEP}${JUNIT5}/junit-platform-runner-1.6.2.jar${SEP}${JUNIT5}/junit-platform-suite-api-1.6.2.jar${SEP}${JUNIT5}/junit-platform-testkit-1.6.2.jar
JUNIT_VINTAGE=${JUNIT5}/junit-vintage-engine-5.6.2.jar
JUNIT_DEPS=${JUNIT5}/apiguardian-api-1.1.0.jar${SEP}${JUNIT5}/opentest4j-1.2.0.jar
CP_JUNIT5=${JUNIT_JUPITER}${SEP}${JUNIT_PLATFORM}${SEP}${JUNIT_VINTAGE}${SEP}${JUNIT_DEPS}
CP_CATSHOP="${CP_DERBY}${SEP}${WORKSPACE}${SEP}."



