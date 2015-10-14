#!/usr/bin/env ruby
$LOAD_PATH << 'lib'

require 'api'
require 'redis_client_factory'

app_environment_id = JSON.parse(ENV.fetch('VCAP_APPLICATION')).fetch('space_id')

redis = RedisClientFactory.create('async-redis')
run API.new(redis, app_environment_id: app_environment_id)