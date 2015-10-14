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
      stub_const('ENV', {
        'PORT' => 1234,
        'VCAP_APPLICATION' => {
          'space_id' => 'space_id'
        }.to_json
      })
    end

    it 'returns a map containing request information' do
      expected_response = {
        requests: {
          GET: {
            '/feedback' => 2
          },
          POST: {
            '/feedback' => 1,
            '/questions' => 3
          }
        }
      }.to_json

      allow(redis).to receive(:keys).with('space_id:aggregatedMetadata*').and_return(%w[
        space_id:aggregatedMetadata:GET:/feedback
        space_id:aggregatedMetadata:POST:/feedback
        space_id:aggregatedMetadata:POST:/questions
      ])
      allow(redis).to receive(:get).with('space_id:aggregatedMetadata:GET:/feedback').and_return('2')
      allow(redis).to receive(:get).with('space_id:aggregatedMetadata:POST:/feedback').and_return('1')
      allow(redis).to receive(:get).with('space_id:aggregatedMetadata:POST:/questions').and_return('3')

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
          }
        }.to_json

        allow(redis).to receive(:keys).with('space_id:aggregatedMetadata*').and_return(%w[
          space_id:aggregatedMetadata:GET:/feedback
          space_id:aggregatedMetadata:POST:/feedback
          space_id:aggregatedMetadata:POST:/questions
        ])
        allow(redis).to receive(:get).with('space_id:aggregatedMetadata:GET:/feedback').and_return('2')
        allow(redis).to receive(:get).with('space_id:aggregatedMetadata:POST:/feedback').and_return('1')
        allow(redis).to receive(:get).with('space_id:aggregatedMetadata:POST:/questions').and_return('3')

        get '/request-data'

        expect(last_response.body).to eq(expected_response)
      end
    end
  end

end