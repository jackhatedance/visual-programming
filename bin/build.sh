#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
#echo $DIR
cd $DIR
cd ..

mvn clean install

echo run bin/start.sh
