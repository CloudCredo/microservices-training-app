#!/bin/bash

set -ex

export TERM=vt100

#./ci/gradlew test

pushd request-subscriber
bundle install
bundle exec rspec
popd
