require 'sinatra'
require 'json'

require 'worker'

class API < Sinatra::Base

  def initialize(redis, app_environment_id:)
    @redis = redis
    @app_environment_id = app_environment_id
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

  attr_reader :redis, :app_environment_id

  def request_data
    keys = redis.keys("#{app_environment_id}:aggregatedMetadata*")

    keys.each_with_object({}) do |key, map|
      key_components = key.split(/:/)
      method = key_components[2]
      path = key_components[3]
      path_map = map.fetch(method, {})
      map[method] = path_map
      path_map[path] = redis.get(key).to_i
    end
  end

  def worker_data
    workers = Worker.all_workers(redis, app_environment_id: app_environment_id)

    workers.each_with_object([]) do |worker, worker_data|
      return worker_data unless worker.exists?

      worker_data << {
        name: worker.name,
        requestRate: worker.requests_per_second
      }
    end
  end


end