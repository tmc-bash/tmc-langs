#!/bin/bash

if [ -z ${TEST_PATH+x} ]; then
  TEST_PATH=`find ./ -name "test"`

  TEST_FILES=()
  for file in $TEST_PATH/*
    do
      TEST_FILES+=("$file")
  done
fi

AVAILABLE_POINTS=".tmc_available_points.json"
if [ -e $AVAILABLE_POINTS ]; then
  rm $AVAILABLE_POINTS
fi

printf "{}" >> $AVAILABLE_POINTS

punc=""
count=0

while read line
do
  var=$(echo $line | cut -d'"' -f 2)

  if [ $(($count % 2)) == 0 ]; then
    if grep -q "\"" "$AVAILABLE_POINTS"; then
      punc=","
    fi
    
    sed -i "s/}/$punc\"$var\"}/" $AVAILABLE_POINTS

  else
    sed -i "s/}/:[\"$var\"]}/" $AVAILABLE_POINTS
  fi

  count=$(($count+1))
done <<< "$(grep -e '@test\|@point' ${TEST_FILES[@]})"
