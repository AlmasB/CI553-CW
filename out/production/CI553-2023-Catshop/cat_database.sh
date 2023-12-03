# script to setup the catshop datatase
# command to run this from jenkins is
#     $WORKSPACE/cat_database.sh

# This script needs to be run once, before using CatShop
# The contents of 'database.txt' tells it what the 
# database configuration is (derby, or a remote sql service)
# (You could extend the jenkins pipleine to test this too)

. cat_defs.sh

echo set up the CatShop database

java -cp "${CP_CATSHOP}" clients/Setup
