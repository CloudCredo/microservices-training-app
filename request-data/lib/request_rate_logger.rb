require 'redis'
require 'worker'

class RequestRateLogger
  def initialize(redis, app_environment_id:, app_instance_id:)
    @redis = redis
    @app_environment_id = app_environment_id
    @app_instance_id = app_instance_id
  end

  def on_subscribe
    worker.register
  end

  def handle_message(_)
    worker.increment_request_count
  end

  private

  attr_reader :redis, :app_environment_id, :app_instance_id

  def worker
    @worker ||= Worker.this_worker(
      redis,
      app_environment_id: app_environment_id,
      app_instance_id: app_instance_id
    )
  end
end