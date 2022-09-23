# script to run distributed CatShop
# command to run this from jenkins is
#     $WORKSPACE/cat_dist.sh
# (though it doesn't make sense to run this from Jenkins, because it is interactive)

# there is no neat way to stop CatShop - you need to quit CATSHOP the client
# windows individuCATSHOPy AND kill the java process running the server

# This starts the server first, then waits for you to hit <enter> and then 
# starts the clients (to give the server time to start up)
# It assumes the database has been setup already (eg with cat_database.sh)
# CATSHOP the clients pile up in the top left corner of the screeen - something you
# could try and fix? (How does CATSHOP-in-one version manage them?)

. cat_defs.sh
echo Run the distributed version: Networking to localhost
echo "Hit <enter> to start clients once server is running"
java -cp "${CP_CATSHOP}" middle/Server &
read
java -cp "${CP_CATSHOP}" clients/customer/CustomerClient &
java -cp "${CP_CATSHOP}" clients/cashier/CashierClient &
java -cp "${CP_CATSHOP}" clients/backDoor/BackDoorClient &
java -cp "${CP_CATSHOP}" clients/warehousePick/PickClient &
java -cp "${CP_CATSHOP}" clients/warehousePick/PickClient &
java -cp "${CP_CATSHOP}" clients/shopDisplay/DisplayClient &
java -cp "${CP_CATSHOP}" clients/collection/CollectClient &
