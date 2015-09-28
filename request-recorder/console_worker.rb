$LOAD_PATH << 'lib'

require 'redis'

require 'redis_subscriber'
require 'stdout_handler'

$stdout.sync = true
trap(:INT) { exit }

redis = Redis.new
stdout_handler = StdoutHandler.new

subscriber = RedisSubscriber.new(redis)
subscriber.subscribe('requestMetadata', stdout_handler)