require 'redis'

require 'redis_subscriber'

RSpec.describe RedisSubscriber do

  let(:redis) { instance_double(Redis) }
  let(:on_subscribe) { double(:on_subscribe) }
  let(:handler) { double }

  subject(:subscriber) { described_class.new(redis)}

  before do
    allow(redis).to receive(:subscribe).and_yield(on_subscribe)
    allow(on_subscribe).to receive(:message).and_yield('channel', 'message')
  end

  it 'subscribes handlers to the correct redis channel' do
    expect(redis).to receive(:subscribe).with('requestMetadata')
    expect(on_subscribe).to receive(:message)
    expect(handler).to receive(:handle_message).with('message')

    subject.subscribe('requestMetadata', handler)
  end

end