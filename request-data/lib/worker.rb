class Worker
  WORKER_INSTANCES_SET_KEY = 'requestRateLogger:instances'

  private_constant :WORKER_INSTANCES_SET_KEY

  class << self
    def all_workers(redis)
      redis.smembers(WORKER_INSTANCES_SET_KEY).map do |worker_key|
        Worker.new(redis, worker_key)
      end
    end
  end

  def initialize(redis, worker_key)
    @redis = redis
    @worker_key = worker_key
  end

  def deregister
    redis.srem(WORKER_INSTANCES_SET_KEY, @worker_key)
  end

  def name
    worker_key.split(':').last
  end

  def requests_per_second
    seconds_running = (Time.now - start_time)

    request_count / seconds_running
  end

  def request_count
    count = redis.get("#{worker_key}:requestCount")
    count ? count.to_i : nil
  end

  def start_time
    startTime = redis.get("#{worker_key}:startTime")
    startTime ? Time.parse(startTime) : nil
  end

  def exists?
    request_count && start_time
  end

  private

  attr_reader :redis, :worker_key
end