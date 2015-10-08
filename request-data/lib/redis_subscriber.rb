class RedisSubscriber

  def initialize(redis)
    @redis = redis
    @threads = {}
    @handlers = {}
  end

  def subscribe(key, handler)
    handlers = @handlers.fetch(key, Set.new)
    handlers << handler
    @handlers[key] = handlers
    handler.on_subscribe

    return @threads[key] if @threads[key]

    thread = Thread.new do
      loop do
        data = @redis.blpop(key)
        @handlers[key].each { |handler| handler.handle_message(data) }
      end
    end

    @threads[key] = thread
  end

  def join
    @threads.values.each { |thread| thread.join }
  end

end