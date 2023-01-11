set -e

./gradlew build -p ravara -x lint publishGithub -PversionName=$1