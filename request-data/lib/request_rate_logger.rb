require 'redis'
require 'worker'

class RequestRateLogger
  def initialize(redis)
    @redis = redis
  end

  def on_subscribe
    worker.register
  end

  def handle_message(_)
    worker.increment_request_count
  end

  private

  attr_reader :redis

  def worker
    @worker ||= Worker.this_worker(redis)
  end
end