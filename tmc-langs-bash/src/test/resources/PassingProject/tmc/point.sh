#!/bin/bash

TEMP_RESULTS_FILE="temp_results.txt"


@point() {
    printf "\npoint:$1, " >> $TEMP_RESULTS_FILE
}

msg() {
    printf "msg:$1, result:" >> $TEMP_RESULTS_FILE
}
