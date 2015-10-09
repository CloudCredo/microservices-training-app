require 'redis'
require 'timecop'

require 'request_rate_logger'

RSpec.describe(RequestRateLogger) do

  def instance_id
    '0'
  end

  let(:redis) { instance_double(Redis, get: nil, set: nil, sadd: nil) }
  subject(:rate_logger) { described_class.new(redis) }

  before(:each) do
    stub_const('ENV', {
      'CF_INSTANCE_INDEX' => instance_id
    })

    Timecop.freeze
  end


  describe '#on_subscribe' do
    it 'adds the worker key prefix to the set of all worker keys' do
      expect(redis).to receive(:sadd).with('requestRateLogger:instances', "requestRateLogger:#{instance_id}")
      allow(redis).to receive(:set)

      rate_logger.on_subscribe
    end

    it 'updates the worker start time in Redis' do
      allow(redis).to receive(:sadd)
      expect(redis).to receive(:set).with("requestRateLogger:#{instance_id}:startTime", Time.now, ex: 60)

      rate_logger.on_subscribe
    end
  end

  describe '#handle_message' do
    it 'counts the number of requests it receives' do
      expect(redis).to receive(:incr).with("requestRateLogger:#{instance_id}:requestCount")

      rate_logger.handle_message('')
    end
  end

end