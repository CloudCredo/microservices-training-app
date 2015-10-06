require 'redis'

require 'redis_subscriber'

RSpec.describe RedisSubscriber do

  let(:redis) { instance_double(Redis) }
  let(:handler) { double('handler') }

  subject(:subscriber) { described_class.new(redis)}

  before do
    allow(redis).to receive(:blpop).and_return('metadata')
  end

  describe '#subscribe' do
    it 'notifies handlers when they have been subscribed' do
      thread = nil

      expect(handler).to receive(:on_subscribe) { thread.exit }
      allow(handler).to receive(:handle_message) { thread.exit }

      thread = subscriber.subscribe('requestMetadata', handler)
      thread.join
    end

    it 'subscribes handlers to the correct redis queue' do
      thread = nil

      expect(redis).to receive(:blpop).with('requestMetadata')
      expect(handler).to receive(:handle_message).with('metadata') { thread.exit }

      thread = subscriber.subscribe('requestMetadata', handler)
      thread.join
    end
  end
end