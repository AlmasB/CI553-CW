call cat_defs.bat

echo Create jar files

jar cfm  catshop.jar Manifest.MF catalogue\*.class middle\*.class dbAccess\*.class orders\*.class clients\*.class remote\*.class debug\*.class clients\backDoor\*.class clients\cashier\*.class clients\collection\*.class clients\customer\*.class clients\shopDisplay\*.class clients\warehousePick\*.class DataBase.txt

jar cfm  setup.jar Manifest2.MF catalogue\*.class middle\*.class dbAccess\*.class orders\*.class clients\*.class remote\*.class debug\*.class clients\backDoor\*.class clients\cashier\*.class clients\collection\*.class clients\customer\*.class clients\shopDisplay\*.class clients\warehousePick\*.class DataBase.txt
