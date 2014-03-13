#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo $DIR
cd $DIR
cd ..

JAR=$(ls lib/cooby*.jar)
#echo $JAR
java -jar $JAR

