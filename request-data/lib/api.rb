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
      requests: request_data,
      workers: worker_data
    }.to_json
  end

  private

  attr_reader :redis

  def request_data
    keys = redis.keys('aggregatedMetadata*')

    keys.each_with_object({}) do |key, map|
      key_components = key.split(/:/)
      method = key_components[1]
      path = key_components[2]
      path_map = map.fetch(method, {})
      map[method] = path_map
      path_map[path] = redis.get(key)
    end
  end

  def worker_data
    worker_keys = redis.smembers('requestRateLogger:instances')

    worker_keys.map do |worker_key|
      {
        name: worker_key.split(':').last,
        requestRate: worker_requests_per_second(worker_key)
      }
    end
  end

  def worker_requests_per_second(worker_key)
    requests = redis.get("#{worker_key}:requestCount")
    worker_start_time = Time.parse(redis.get("#{worker_key}:startTime"))
    seconds_running = (Time.now - worker_start_time)

    requests / seconds_running
  end

end