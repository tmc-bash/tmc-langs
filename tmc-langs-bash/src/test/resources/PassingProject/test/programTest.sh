#!/usr/bin/env bats

load ./src/program.sh

@test "program exits with 0"
    @point "1.1"

    msg "Exit status was not 0"
    [ "$status" -eq 0 ]

@test "a value gets saved to result"
    @point "1.2"

    save_to_result "apple"
    masg "Result was not same as saved"
    [ "$result" -eq "apple" ]