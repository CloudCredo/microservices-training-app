require 'redis'

require 'redis_subscriber'

RSpec.describe RedisSubscriber do

  let(:redis) { instance_double(Redis) }
  let(:handler1) { double('handler1') }
  let(:handler2) { double('handler2') }
  let(:redis_key) { 'requestMetadata' }

  subject(:subscriber) { described_class.new(redis)}

  before do
    allow(redis).to receive(:blpop).and_return([redis_key, 'metadata'])
  end

  describe '#subscribe' do
    it 'notifies handlers when they have been subscribed' do
      thread = nil

      expect(handler1).to receive(:on_subscribe)
      expect(handler2).to receive(:on_subscribe)
      allow(handler1).to receive(:handle_message)
      allow(handler2).to receive(:handle_message)

      subscriber.subscribe(redis_key, handler1)
      subscriber.subscribe(redis_key, handler2)
    end

    it 'subscribes handlers to the correct redis queue' do
      thread = nil

      expect(redis).to receive(:blpop).with(redis_key)
      allow(handler1).to receive(:on_subscribe)
      allow(handler2).to receive(:on_subscribe)
      expect(handler1).to receive(:handle_message).with('metadata')
      expect(handler2).to receive(:handle_message).with('metadata') { thread.exit }

      subscriber.subscribe(redis_key, handler1)
      thread = subscriber.subscribe(redis_key, handler2)
      subscriber.join
    end
  end
end