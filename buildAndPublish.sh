set -e

./gradlew build -p recyclerView -x lint publishGithub -PversionName=$1