require 'redis'
require 'timecop'

require 'request_rate_logger'

RSpec.describe(RequestRateLogger) do

  def application_id
    'a0e43965-f54e-4e63-9a03-36f80e4ac2d8'
  end

  let(:redis) { instance_double(Redis) }
  subject(:rate_logger) { described_class.new(redis) }

  before(:each) do
    stub_const('ENV', {
      'VCAP_APPLICATION' => {
        'application_id' => application_id
      }.to_json
    })

    Timecop.freeze
  end


  describe '#on_subscribe' do
    it 'adds the worker key prefix to the set of all worker keys' do
      expect(redis).to receive(:sadd).with('requestRateLogger:instances', "requestRateLogger:#{application_id}")
      allow(redis).to receive(:set)

      rate_logger.on_subscribe
    end

    it 'updates the worker start time in Redis' do
      allow(redis).to receive(:sadd)
      expect(redis).to receive(:set).with("requestRateLogger:#{application_id}:startTime", Time.now)

      rate_logger.on_subscribe
    end
  end

  describe '#handle_message' do
    it 'counts the number of requests it receives' do
      expect(redis).to receive(:incr).with("requestRateLogger:#{application_id}:requestCount")

      rate_logger.handle_message('')
    end
  end

end