require 'redis'

class RequestRateLogger
  def initialize(redis)
    @redis = redis
  end

  def on_subscribe
    register_worker
    update_worker_start_time
  end

  def handle_message(_)
    increment_api_request_count
  end

  private

  attr_reader :redis

  def application_id
    JSON.parse(ENV.fetch('VCAP_APPLICATION')).fetch('application_id')
  end

  def worker_key_prefix
    "requestRateLogger:#{application_id}"
  end

  def register_worker
    redis.sadd('requestRateLogger:instances', worker_key_prefix)
  end

  def update_worker_start_time
    redis.set("#{worker_key_prefix}:startTime", Time.now)
  end

  def increment_api_request_count
    redis.incr("#{worker_key_prefix}:requestCount")
  end
end