require 'redis'
require 'timecop'

require 'request_rate_logger'

RSpec.describe(RequestRateLogger) do

  let(:redis) { instance_double(Redis, get: nil, set: nil, sadd: nil) }
  let(:app_environment_id) { 'space_id' }
  let(:app_instance_id) { '0' }

  subject(:rate_logger) do
    described_class.new(redis, app_environment_id: app_environment_id, app_instance_id: app_instance_id)
  end

  before(:each) do
    Timecop.freeze
  end

  describe '#on_subscribe' do
    it 'adds the worker key prefix to the set of all worker keys' do
      expect(redis).to receive(:sadd).with("#{app_environment_id}:requestRateLogger:instances", "#{app_environment_id}:requestRateLogger:#{app_instance_id}")
      allow(redis).to receive(:set)

      rate_logger.on_subscribe
    end

    it 'updates the worker start time in Redis' do
      allow(redis).to receive(:sadd)
      expect(redis).to receive(:set).with("#{app_environment_id}:requestRateLogger:#{app_instance_id}:startTime", Time.now, ex: 60)

      rate_logger.on_subscribe
    end
  end

  describe '#handle_message' do
    it 'counts the number of requests it receives' do
      expect(redis).to receive(:incr).with("#{app_environment_id}:requestRateLogger:#{app_instance_id}:requestCount")

      rate_logger.handle_message('')
    end
  end

end