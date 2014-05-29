#!/bin/bash

BIN_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo bin dir:$BIN_DIR
cd $BIN_DIR
cd ..

BASE_DIR="$( pwd )"
echo base dir:$BASE_DIR
#JAR=$(ls lib/cooby*.jar)
#echo $JAR

#the conf classpath is to overwrite default configurations
java -cp $BASE_DIR/conf:* com.bluesky.visualprogramming.ui.Main

