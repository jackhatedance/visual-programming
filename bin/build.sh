#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
#echo $DIR
cd $DIR
cd ..

mvn package

mkdir -p lib
cp target/cooby*.jar lib

echo run bin/start.sh
