class RedisSubscriber

  def initialize(redis)
    @redis = redis
    @threads = {}
    @handlers = {}
  end

  def subscribe(redis_key, handler)
    handlers = @handlers.fetch(redis_key, Set.new)
    handlers << handler
    @handlers[redis_key] = handlers
    handler.on_subscribe

    return @threads[redis_key] if @threads[redis_key]

    thread = Thread.new do
      loop do
        _, value = @redis.blpop(redis_key)
        @handlers[redis_key].each { |handler| handler.handle_message(value) }
      end
    end

    @threads[redis_key] = thread
  end

  def join
    @threads.values.each { |thread| thread.join }
  end

end