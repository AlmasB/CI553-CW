call cat_defs.bat
echo Run the distributed version: Networking to localhost
start java -cp "%CP_DERBY%" middle/Server
echo When server finishes setup
pause
start java -cp "%CP_DERBY%" clients/customer/CustomerClient
start java -cp "%CP_DERBY%" clients/cashier/CashierClient
start java -cp "%CP_DERBY%" clients/backDoor/BackDoorClient
start java -cp "%CP_DERBY%" clients/warehousePick/PickClient
start java -cp "%CP_DERBY%" clients/warehousePick/PickClient
start java -cp "%CP_DERBY%" clients/shopDisplay/DisplayClient
start java -cp "%CP_DERBY%" clients/collection/CollectClient
rem # echo Run Customer applet
rem # start appletviewer -cp "%CP_DERBY%" WebCustomerClient.htm
