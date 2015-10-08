require 'redis'

require 'redis_subscriber'

RSpec.describe RedisSubscriber do

  let(:redis) { instance_double(Redis) }
  let(:handler1) { double('handler1') }
  let(:handler2) { double('handler2') }

  subject(:subscriber) { described_class.new(redis)}

  before do
    allow(redis).to receive(:blpop).and_return('metadata')
  end

  describe '#subscribe' do
    it 'notifies handlers when they have been subscribed' do
      thread = nil

      expect(handler1).to receive(:on_subscribe)
      expect(handler2).to receive(:on_subscribe)
      allow(handler1).to receive(:handle_message)
      allow(handler2).to receive(:handle_message)

      subscriber.subscribe('requestMetadata', handler1)
      subscriber.subscribe('requestMetadata', handler2)
    end

    it 'subscribes handlers to the correct redis queue' do
      thread = nil

      expect(redis).to receive(:blpop).with('requestMetadata')
      allow(handler1).to receive(:on_subscribe)
      allow(handler2).to receive(:on_subscribe)
      expect(handler1).to receive(:handle_message).with('metadata')
      expect(handler2).to receive(:handle_message).with('metadata') { thread.exit }

      subscriber.subscribe('requestMetadata', handler1)
      thread = subscriber.subscribe('requestMetadata', handler2)
      subscriber.join
    end
  end
end