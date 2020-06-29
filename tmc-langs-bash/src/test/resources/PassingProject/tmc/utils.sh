#!/bin/bash

clear_temps() {
  if [ -e $TEMP_RESULTS_FILE ]; then
      rm $TEMP_RESULTS_FILE
  fi
}

clear_results() {
  if [ -e $RESULTS_FILE ]; then
      rm $RESULTS_FILE
  fi
}