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
      requests: request_data
    }.to_json
  end

  private

  attr_reader :redis

  def unique_environment_id
    JSON.parse(ENV.fetch('VCAP_APPLICATION')).fetch('space_id')
  end

  def request_data
    keys = redis.keys("#{unique_environment_id}:aggregatedMetadata*")

    keys.each_with_object({}) do |key, map|
      key_components = key.split(/:/)
      method = key_components[2]
      path = key_components[3]
      path_map = map.fetch(method, {})
      map[method] = path_map
      path_map[path] = redis.get(key).to_i
    end
  end

end