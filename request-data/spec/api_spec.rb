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
    Timecop.freeze(Time.now.round)
  end

  after(:all) do
    Timecop.return
  end

  describe 'GET /request-data' do

    before do
      stub_const('ENV', { 'PORT' => 1234 })
    end

    it 'returns a map containing request and worker information' do
      expected_response = {
        requests: {
          GET: {
            '/feedback' => 2
          },
          POST: {
            '/feedback' => 1,
            '/questions' => 3
          }
        },
        workers: [{
                    name: 'Worker 1',
                    requestRate: 10.0
                  },
                  {
                    name: 'Worker 2',
                    requestRate: 20.0
                  }]
      }.to_json

      allow(redis).to receive(:keys).with('aggregatedMetadata*').and_return(%w[
        aggregatedMetadata:GET:/feedback
        aggregatedMetadata:POST:/feedback
        aggregatedMetadata:POST:/questions
      ])
      allow(redis).to receive(:get).with('aggregatedMetadata:GET:/feedback').and_return('2')
      allow(redis).to receive(:get).with('aggregatedMetadata:POST:/feedback').and_return('1')
      allow(redis).to receive(:get).with('aggregatedMetadata:POST:/questions').and_return('3')
      allow(redis).to receive(:smembers).with('requestRateLogger:instances').and_return(%w[requestRateLogger:1 requestRateLogger:2])
      allow(redis).to receive(:get).with('requestRateLogger:1:requestCount').and_return('20')
      allow(redis).to receive(:get).with('requestRateLogger:2:requestCount').and_return('60')
      allow(redis).to receive(:get).with('requestRateLogger:1:startTime').and_return((Time.now - 2).to_s)
      allow(redis).to receive(:get).with('requestRateLogger:2:startTime').and_return((Time.now - 3).to_s)

      get '/request-data'

      expect(last_response.body).to eq(expected_response)
    end

    context 'when a worker\'s keys have expired' do
      it 'does not return data for the worker' do
        expected_response = {
          requests: {
            GET: {
              '/feedback' => 2
            },
            POST: {
              '/feedback' => 1,
              '/questions' => 3
            }
          },
          workers: [{
                      name: 'Worker 1',
                      requestRate: 10.0
                    }]
        }.to_json

        allow(redis).to receive(:keys).with('aggregatedMetadata*').and_return(%w[
          aggregatedMetadata:GET:/feedback
          aggregatedMetadata:POST:/feedback
          aggregatedMetadata:POST:/questions
        ])
        allow(redis).to receive(:get).with('aggregatedMetadata:GET:/feedback').and_return('2')
        allow(redis).to receive(:get).with('aggregatedMetadata:POST:/feedback').and_return('1')
        allow(redis).to receive(:get).with('aggregatedMetadata:POST:/questions').and_return('3')
        allow(redis).to receive(:smembers).with('requestRateLogger:instances').and_return(%w[requestRateLogger:1 requestRateLogger:2])
        allow(redis).to receive(:get).with('requestRateLogger:1:requestCount').and_return('20')
        allow(redis).to receive(:get).with('requestRateLogger:2:requestCount').and_return(nil)
        allow(redis).to receive(:get).with('requestRateLogger:1:startTime').and_return((Time.now - 2).to_s)
        allow(redis).to receive(:get).with('requestRateLogger:2:startTime').and_return(nil)

        get '/request-data'

        expect(last_response.body).to eq(expected_response)
      end
    end
  end

end