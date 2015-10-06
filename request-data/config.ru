#!/usr/bin/env ruby
$LOAD_PATH << 'lib'

require 'api'
require 'redis_client_factory'

redis = RedisClientFactory.create('async-redis')
run API.new(redis)