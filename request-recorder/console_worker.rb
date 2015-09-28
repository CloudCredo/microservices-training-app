$LOAD_PATH << 'lib'

require 'redis'

require 'request_metadata_subscriber'
require 'stdout_handler'

$stdout.sync = true
trap(:INT) { exit }

redis = Redis.new
stdoutHandler = StdoutHandler.new

subscriber = RequestMetadataSubscriber.new(redis)
subscriber.subscribe(stdoutHandler)