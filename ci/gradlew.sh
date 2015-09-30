#!/bin/sh

set -ex

export TERM=vt100

./gradlew "$@"
