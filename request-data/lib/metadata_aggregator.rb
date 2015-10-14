class MetadataAggregator

  def initialize(redis, app_environment_id:)
    @redis = redis
    @app_environment_id = app_environment_id
    @aggregated_metadata = {}
  end

  def on_subscribe

  end

  def handle_message(request_metadata)
    request_metadata = JSON.parse(request_metadata)
    increment(request_metadata['method'], request_metadata['path'])
  end

  private

  attr_reader :aggregated_metadata, :redis, :app_environment_id

  def increment(method, path)
    key = "#{app_environment_id}:aggregatedMetadata:#{method}:#{path}"
    redis.incr(key)
  end

end