class RequestMetadataSubscriber

  CHANNEL='requestMetadata'
  private_constant :CHANNEL

  def initialize(redis)
    @redis = redis
  end

  def subscribe(handler)
    redis.subscribe(CHANNEL) do |on|
      on.message do |_, message|
        handler.handle_message(message)
      end
    end
  end

  private

  attr_reader :redis
end