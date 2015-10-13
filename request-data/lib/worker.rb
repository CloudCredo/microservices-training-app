class Worker
  WORKER_INSTANCES_SET_KEY = 'requestRateLogger:instances'

  private_constant :WORKER_INSTANCES_SET_KEY

  class << self
    def this_worker(redis)
      Worker.new(redis, "requestRateLogger:#{ENV.fetch('CF_INSTANCE_INDEX')}")
    end

    def all_workers(redis)
      redis.smembers(WORKER_INSTANCES_SET_KEY).map do |worker_key|
        Worker.new(redis, worker_key)
      end
    end
  end

  def initialize(redis, worker_key_prefix)
    @redis = redis
    @worker_key_prefix = worker_key_prefix
  end

  def register
    redis.sadd(WORKER_INSTANCES_SET_KEY, worker_key_prefix)
    redis.set("#{worker_key_prefix}:startTime", Time.now, ex: 60)
    redis.set("#{worker_key_prefix}:requestCount", 0)
    self
  end

  def deregister
    redis.srem(WORKER_INSTANCES_SET_KEY, worker_key_prefix)
  end

  def name
    "Worker #{worker_key_prefix.split(':').last}"
  end

  def requests_per_second
    seconds_running = (Time.now - start_time)
    request_count / seconds_running
  end

  def increment_request_count
    register unless exists?
    redis.incr("#{worker_key_prefix}:requestCount")
  end

  def request_count
    count = redis.get("#{worker_key_prefix}:requestCount")
    count ? count.to_i : nil
  end

  def start_time
    startTime = redis.get("#{worker_key_prefix}:startTime")
    startTime ? Time.parse(startTime) : nil
  end

  def exists?
    not (request_count.nil? || start_time.nil?)
  end

  private

  attr_reader :redis, :worker_key_prefix
end