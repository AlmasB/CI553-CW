# script to compile all the catshop java classes
# command to run this from jenkins is
#     $WORKSPACE/cat_compile.sh

# Note: if you add new clients to catshop, you need to add them to the list
# of files to compile here

. cat_defs.sh

# compiler configuration options
DEPRECATED=
#DEPRECATED=-Xlint:deprecation
#DEPRECATED=-Xlint:unchecked

FLAGS=
#FLAGS= -source 1.8 -Xlint:unchecked
#FLAGS= -source 1.8


echo Compile the CatShop system

javac  ${DEPRECATED} ${FLAGS} -cp "${CP_JUNIT5}" \
	catalogue/*.java \
	middle/*.java \
	dbAccess/*.java \
	orders/*.java \
	clients/*.java \
	remote/*.java \
	debug/*.java \
	clients/backdoor/*.java \
	clients/cashier/*.java \
	clients/collection/*.java \
	clients/customer/*.java \
	clients/shopDisplay/*.java \
	clients/warehousePick/*.java \
	
