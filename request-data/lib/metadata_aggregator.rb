class MetadataAggregator

  def initialize(redis)
    @redis = redis
    @aggregated_metadata = {}
  end

  def on_subscribe

  end

  def handle_message(request_metadata)
    request_metadata = JSON.parse(request_metadata)
    increment(request_metadata['method'], request_metadata['path'])
  end

  private

  attr_reader :aggregated_metadata, :redis

  def unique_environment_id
    JSON.parse(ENV.fetch('VCAP_APPLICATION')).fetch('space_id')
  end

  def increment(method, path)
    key = "#{unique_environment_id}:aggregatedMetadata:#{method}:#{path}"
    redis.incr(key)
  end

end