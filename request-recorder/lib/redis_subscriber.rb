class RedisSubscriber

  def initialize(redis)
    @redis = redis
  end

  def subscribe(channel, handler)
    @redis.subscribe(channel) do |on|
      on.message do |_, message|
        handler.handle_message(message)
      end
    end
  end

end