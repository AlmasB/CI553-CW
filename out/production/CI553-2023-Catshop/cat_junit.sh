# script to run junit tests in CatShop
# command to run this from jenkins is
#     $WORKSPACE/cat_junit.sh -disable-ansi-colors -scan-classpath

# you *might* not need the -disable-ansi-colours in linux/macos - try it and see :-)

# You can give this script more specific testing arguments, if you want a task
# which doesn't test everything (check out documentation for 
# 'junit-platform-console-standalone')

. cat_defs.sh

java -jar "${JUNIT5}${DIRSEP}junit-platform-console-standalone-1.6.2.jar" -cp "${CP_CATSHOP}" $*
