require 'json'

class Worker

  class << self
    def this_worker(redis, app_environment_id:, app_instance_id:)
      Worker.new(
          redis,
          app_environment_id: app_environment_id,
          worker_key_prefix: "#{app_environment_id}:requestRateLogger:#{app_instance_id}"
      )
    end

    def all_workers(redis, app_environment_id:)
      redis.smembers(worker_instances_set_key(app_environment_id)).map do |worker_key|
        Worker.new(
            redis,
            app_environment_id: app_environment_id,
            worker_key_prefix: worker_key
        )
      end
    end

    def worker_instances_set_key(app_environment_id)
      "#{app_environment_id}:requestRateLogger:instances"
    end
  end

  def initialize(redis, app_environment_id:, worker_key_prefix:)
    @redis = redis
    @app_environment_id = app_environment_id
    @worker_key_prefix = worker_key_prefix
  end

  def register
    redis.sadd(worker_instances_set_key, worker_key_prefix)
    redis.set("#{worker_key_prefix}:startTime", Time.now, ex: 60)
    redis.set("#{worker_key_prefix}:requestCount", 0)
    self
  end

  def deregister
    redis.srem(worker_instances_set_key, worker_key_prefix)
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

  attr_reader :redis, :worker_key_prefix, :app_environment_id

  def worker_instances_set_key
    Worker.worker_instances_set_key(app_environment_id)
  end
end