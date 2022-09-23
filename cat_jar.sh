# script to build the jar file version of CatShop
# command to run this from jenkins is
#     $WORKSPACE/cat_jar.sh

# Note: if you add new clients to catshop, you need to add them to the list
# of files to compile here

# Note2: there is no script to update the Manifest files (there is code for
# it in the Makefile). If there was, it should probably be run first

# You could write another script and a Jenkins pipleine task to test running 
# CatShop from the jar file(s) instead of the main directory

. cat_defs.sh

jar cfm  catshop.jar \
	Manifest.MF \
	catalogue/*.class \
	middle/*.class \
	dbAccess/*.class \
	orders/*.class  \
	clients/*.class \
	remote/*.class \
	debug/*.class \
	clients/backDoor/*.class \
	clients/cashier/*.class \
	clients/collection/*.class \
	clients/customer/*.class \
	clients/shopDisplay/*.class \
	clients/warehousePick/*.class \
	DataBase.txt \
	

jar cfm  setup.jar \
	Manifest2.MF \
	catalogue/*.class \
	middle/*.class \
	dbAccess/*.class \
	orders/*.class \
	clients/*.class \
	remote/*.class \
	debug/*.class \
	clients/backDoor/*.class \
	clients/cashier/*.class \
	clients/collection/*.class \
	clients/customer/*.class \
	clients/shopDisplay/*.class \
	clients/warehousePick/*.class \
	DataBase.txt \
	
