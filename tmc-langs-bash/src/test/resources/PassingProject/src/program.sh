#!/bin/bash

save_to_result() {
    result="$1"
    return $result
}

save_to_result "$1"

echo "$?"