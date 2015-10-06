require 'sinatra'
require 'json'

class API < Sinatra::Base

  def initialize(redis)
    @redis = redis
  end

  set :port, ENV['PORT']
  set :environment, :production

  error do
    content_type :json
    status 500

    e = env['sinatra.error']
    {:result => 'error', :message => e.message}.to_json
  end

  get '/request-data' do
    {
      workers: worker_data
    }.to_json
  end

  private

  attr_reader :redis

  def worker_data
    worker_keys = redis.smembers('requestRateLogger:instances')

    worker_keys.map do |worker_key|
      {
        name: worker_key.split(':').last,
        requestRate: worker_request_rate(worker_key)
      }
    end
  end

  def worker_request_rate(worker_key)
    requests = redis.get("#{worker_key}:requestCount")
    seconds_running = Time.now - redis.get("#{worker_key}:startTime")

    requests / seconds_running
  end

end