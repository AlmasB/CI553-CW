# script to run all-in-one CatShop
# command to run this from jenkins is
#     $WORKSPACE/cat_run.sh

# (though it doesn't make sense to run this from Jenkins, because it is interactive)
# It assumes the database has been setup already (eg with cat_database.sh)

. cat_defs.sh

java -cp "${CP_CATSHOP}" clients/Main
