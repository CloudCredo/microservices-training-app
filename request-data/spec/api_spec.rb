require 'redis'
require 'rack/test'
require 'timecop'

require 'api'

RSpec.describe API do

  include Rack::Test::Methods

  let(:redis) { instance_double(Redis) }

  def app
    API.new(redis)
  end

  before(:all) do

    Timecop.freeze
  end

  describe 'GET /request-data' do

    before(:each) do
      stub_const('ENV', { 'PORT' => 1234 })
    end

    let(:expected_response) do
      {
        workers: [{
                    name: 'worker1',
                    requestRate: 10.0
                  },
                  {
                    name: 'worker2',
                    requestRate: 20.0
                  }]
      }.to_json
    end

    it 'returns a map containing request and worker information' do
      allow(redis).to receive(:smembers).with('requestRateLogger:instances').and_return(%w[requestRateLogger:worker1 requestRateLogger:worker2])
      allow(redis).to receive(:get).with('requestRateLogger:worker1:requestCount').and_return(1200)
      allow(redis).to receive(:get).with('requestRateLogger:worker2:requestCount').and_return(3600)
      allow(redis).to receive(:get).with('requestRateLogger:worker1:startTime').and_return(Time.now - 2*60)
      allow(redis).to receive(:get).with('requestRateLogger:worker2:startTime').and_return(Time.now - 3*60)

      get '/request-data'

      expect(last_response.body).to eq(expected_response)
    end
  end

end