require 'redis'
require 'timecop'

require 'worker'

RSpec.describe Worker do

  let(:redis) { instance_double(Redis, 'redis', get: nil, set: nil, sadd: nil, incr: nil) }
  let(:worker_instance_number) { '0' }
  let(:worker_key_prefix) { "requestRateLogger:#{worker_instance_number}" }

  subject(:worker) { Worker.new(redis, worker_key_prefix)}

  before(:all) do
    Timecop.freeze
    # stub_const('ENV', { 'CF_INSTANCE_INDEX' => 0})
  end

  describe '#register' do
    it 'adds the worker to the set of all instances' do
      expect(redis).to receive(:sadd).with('requestRateLogger:instances', worker_key_prefix)
      worker.register
    end

    it 'sets the start time in Redis, with a TTL of one minute' do
      expect(redis).to receive(:set).with("#{worker_key_prefix}:startTime", Time.now, ex: 60)
      worker.register
    end

    it 'sets the worker\'s request count to 0' do
      expect(redis).to receive(:set).with("#{worker_key_prefix}:requestCount", 0)
      worker.register
    end
  end

  describe '#deregister' do
    it 'removes the worker from the list of all instances' do
      expect(redis).to receive(:srem).with('requestRateLogger:instances', worker_key_prefix)
      worker.deregister
    end
  end

  describe '#name' do
    it 'is the worker instance number' do
      expect(worker.name).to eq "Worker #{worker_instance_number}"
    end
  end

  describe '#requests_per_second' do
    it 'returns the number of requests per second received by the worker'
  end

  describe '#increment_request_count' do
    it 'increments the request count in Redis' do
      expect(redis).to receive(:incr).with("#{worker_key_prefix}:requestCount")
      worker.increment_request_count
    end

    context 'when the worker does not already exist' do
      it 'adds the worker to the set of all instances' do
        expect(redis).to receive(:sadd).with('requestRateLogger:instances', worker_key_prefix)
        worker.increment_request_count
      end

      it 'sets the start time in Redis, with a TTL of one minute' do
        expect(redis).to receive(:set).with("#{worker_key_prefix}:startTime", Time.now, ex: 60)
        worker.increment_request_count
      end

      it 'sets the worker\'s request count to 0' do
        expect(redis).to receive(:set).with("#{worker_key_prefix}:requestCount", 0)
        worker.increment_request_count
      end
    end
  end

  describe '#request_count' do
    it 'returns the request count' do
      allow(redis).to receive(:get).with("#{worker_key_prefix}:requestCount").and_return('7')
      expect(worker.request_count).to eq 7
    end

    context 'when the count doesn\'t exist' do
      it 'returns nil' do
        allow(redis).to receive(:get).with("#{worker_key_prefix}:requestCount").and_return(nil)
        expect(worker.request_count).to be_nil
      end
    end
  end

  describe '#start_time' do
    xit 'returns the worker start time' do
      start_time = Time.now - 2*60
      expect(redis).to receive(:get).with("#{worker_key_prefix}:startTime").and_return(start_time.to_s)
      expect(worker.start_time).to eq start_time
    end
  end

  describe '#exists' do
    it 'is true if we have values for both the request count and start time' do
      allow(redis).to receive(:get).with("#{worker_key_prefix}:startTime").and_return(Time.now.to_s)
      allow(redis).to receive(:get).with("#{worker_key_prefix}:requestCount").and_return('7')
      expect(worker.exists?).to be_truthy
    end

    it 'is false if we have no start time' do
      allow(redis).to receive(:get).with("#{worker_key_prefix}:startTime").and_return(nil)
      allow(redis).to receive(:get).with("#{worker_key_prefix}:requestCount").and_return('7')
      expect(worker.exists?).to be_falsey
    end

    it 'is false if we have no request count' do
      allow(redis).to receive(:get).with("#{worker_key_prefix}:startTime").and_return(Time.now.to_s)
      allow(redis).to receive(:get).with("#{worker_key_prefix}:requestCount").and_return(nil)
      expect(worker.exists?).to be_falsey
    end
  end

end