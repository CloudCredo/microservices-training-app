class RedisSubscriber

  def initialize(redis)
    @redis = redis
    @threads = []
  end

  def subscribe(key, handler)
    thread = Thread.new do
      handler.on_subscribe

      loop do
        metadata = @redis.blpop(key)
        handler.handle_message(metadata)
      end
    end

    @threads << thread
    thread
  end

  def join
    @threads.each { |thread| thread.join }
  end

end