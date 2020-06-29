#!/usr/bin/env bats

load ./tmc/point.sh

@test "program exits with 0" {
    @point "1.1"
    run ./src/program.sh

    msg "Exit status was not 0"
    [ "$status" -eq 0 ]
}


@test "a value gets saved to result" {
    @point "1.2"
    run ./src/program.sh "100"

    msg "Result was not same as saved"
    [ "$output" = "100" ]    
}
