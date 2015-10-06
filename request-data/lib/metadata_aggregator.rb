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

  def increment(method, path)
    key = "aggregatedMetadata:#{method}:#{path}"
    redis.incr(key)
  end

end