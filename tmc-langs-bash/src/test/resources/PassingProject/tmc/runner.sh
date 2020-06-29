#!/bin/bash

. ./tmc/utils.sh

TEST_PATH=`find ./ -name "test"`

TEST_FILES=()
for file in $TEST_PATH/*
  do
    TEST_FILES+=("$file")
done

RESULTS_FILE=".tmc_test_results.json"
clear_results

printf "[]" >> $RESULTS_FILE

TEMP_RESULTS_FILE="temp_results.txt"

bats ${TEST_FILES[@]} >> $TEMP_RESULTS_FILE


# Get names, points, status and msg and print to json
while read line
do

  if grep -q '{' "$RESULTS_FILE"; then
    punc=","
  fi

  name=$(echo $line | awk -F'ok ' '{print $2;}')
  point=$(echo $line | awk -F'point:|, msg' '{print $2;}')
  status=true

  if [[ $line == *"not ok"* ]]; then
    msg=$(echo $line | awk -F'msg:|, result:' '{print $(NF-1)}')
    status=false
  else
    msg=""
  fi
    
  sed -i "s/]$/${punc}{\"name\": \"${name}\", \"points\": [\"${point}\"], \"status\": ${status}, \"message\": \"${msg}\", \"backtrace\": []}]/" $RESULTS_FILE

done <<< "$(grep -e 'ok' $TEMP_RESULTS_FILE)"

clear_temps
