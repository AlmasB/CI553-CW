# ####################################################################
# Version 20-October-2015
# THESE DEFINITIONS WILL HAVE TO BE CHANGED FOR YOUR COMPUTER
# ####################################################################
#
# --------------------------------------------------------------------
#IMPORTANT variables
#  DERBY_HOME JUNIT
#   should be set to the appropriate path for your system
#  SEP
#   should be set to the appropriate separator between file names
#
# The last definition is the one used
#  (The currently set (last used) is for cygwin on windows)
#
# Note you will need to add the JUnit test file you create
# to the files that are compiled
# --------------------------------------------------------------------

# --------------------------------------------------------------------
#   The base of the Apache Derby database system
# --------------------------------------------------------------------
    DERBY_HOME=$(WORKSPACE)/lib/db-derby-10.14.2.0-lib
# --------------------------------------------------------------------

# --------------------------------------------------------------------
#   Where the JUnit jar file is held
#    Only needed if you are to compile a JUnit test class
#    By default you do (orders.OrderTest.java)
# --------------------------------------------------------------------
    JUNIT=/opt/java/current/lib/junit-4.8.2.jar
    JUNIT="C:/Program Files (x86)/BlueJ/lib/junit-4.8.2.jar"
# --------------------------------------------------------------------

# --------------------------------------------------------------------
#   Separator used in java classpath (; Windows : Unix/Linux)
# --------------------------------------------------------------------
    SEP=:
    SEP=\;
# --------------------------------------------------------------------

# ####################################################################
# DO NOT CHANGE BEYOND HERE
# ####################################################################

# --------------------------------------------------------------------
# class path for compilation/ run
# --------------------------------------------------------------------
#CP_DERBY=$(DERBY_HOME)/derby.jar$(SEP)$(DERBY_HOME)/derbytools.jar$(SEP).
CP_DERBY=$(DERBY_HOME)/lib/derbyshared.jar$(SEP)$(DERBY_HOME)/lib/derby.jar$(SEP)$(DERBY_HOME)/lib/derbytools.jar$(SEP).
#
DEPRECATED=-Xlint:deprecation
DEPRECATED=
FLAGS= -source 1.8 -Xlint:unchecked
FLAGS= -source 1.8
FLAGS=


compile:	
	javac $(DEPRECATED) $(FLAGS) -cp $(JUNIT)         \
                            catalogue/*.java              \
                            middle/*.java                 \
                            dbAccess/*.java               \
                            orders/Order.java             \
                            orders/OrderTest.java         \
                            orders/OrderX.java            \
                            orders/OrderTestX.java        \
                            clients/*.java                \
                            clients/*/*.java              \
                            remote/*.java                 \
                            debug/*.java

clean:
	rm -f -r catshop.db
	rm -f */*.class */*/*.class */*.bak *.bak
	rm -f -r html
	rm -f 00text 00text.rtf 000text 000text.rtf
	rm -f -r doc
	rm -f clients/__SHEL*
	rm -f _list.sh derby.log
	rm -f *.jar Mainifest.MF manifest2.MF

doc:
	echo "Making documentation"
	javadoc -sourcepath $(WIN_SDK1)\\src.zip              \
        -classpath $(JUNIT)                                   \
	-group catalogue Catalogue                            \
	-header "<FONT color="lightblue">CatShop</FONT>"      \
	-author -windowtitle "CatShop"                        \
	-use -splitindex -d html                              \
	-package clients/*.java                               \
	         clients/backDoor/*.java                      \
	         clients/cashier/*.java                       \
	         clients/collection/*.java                    \
	         clients/customer/*.java                      \
	         clients/shopDisplay/*.java                   \
	         clients/warehousePick/*.java                 \
	         catalogue/*.java middle/*.java               \
                 dbAccess/*.java orders/*.java remote/*.java  \
		 debug/*.java 

jar:
	echo "Requires derby.jar in the current directory"

	cp  $(DERBY_HOME)/derby.jar derby.jar

	echo Manifest-Version: 1.0         > Manifest.MF
	echo Created-By: mas              >> Manifest.MF
	echo Class-Path: ./derby.jar      >> Manifest.MF
	echo Main-Class: clients.Main     >> Manifest.MF


	jar cfm catshop.jar Manifest.MF                    \
                            catalogue/*.class              \
                            middle/*.class                 \
                            dbAccess/*.class               \
                            orders/Order*.class            \
                            clients/*.class                \
                            clients/*/*.class              \
                            remote/*.class                 \
                            debug/*.class                  \
                            DataBase.txt

	echo Manifest-Version: 1.0         > Manifest2.MF
	echo Created-By: mas              >> Manifest2.MF
	echo Class-Path: ./derby.jar      >> Manifest2.MF
	echo Main-Class: clients.Setup    >> Manifest2.MF

	jar cfm setup.jar Manifest2.MF                      \
                            catalogue/*.class              \
                            middle/*.class                 \
                            dbAccess/*.class               \
                            orders/Order*.class            \
                            clients/*.class                \
                            clients/*/*.class              \
                            remote/*.class                 \
                            debug/*.class                  \
                            DataBase.txt


jarrun:
	java -jar setup.jar
	java -jar catshop.jar


test:
	java -cp $(JUNIT)$(SEP)$(CP_DERBY) org.junit.runner.JUnitCore orders.OrderTest

testX:
	java -cp $(JUNIT)$(SEP)$(CP_DERBY) org.junit.runner.JUnitCore orders.OrderTestX

run:
	java -cp $(CP_DERBY)$(SEP)$(JUNIT) clients/Main

junit:
	java -cp $(JUNIT)$(SEP)$(CP_DERBY) org.junit.runner.JUnitCore orders.OrderTest

junitX:
	java -cp $(JUNIT)$(SEP)$(CP_DERBY) org.junit.runner.JUnitCore orders.OrderTestX

distributed:
	java -cp $(CP_DERBY) middle/Server &
	sleep 1
	java clients/backDoor/BackDoorClient&
	java clients/customer/CustomerClient&
	java clients/cashier/CashierClient&
	java clients/warehousePick/PickClient&
	java clients/shopDisplay/DisplayClient&
	java clients/collection/CollectClient&

database:
	java -cp $(CP_DERBY) clients/Setup

