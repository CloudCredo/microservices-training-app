require 'cf-app-utils'
require 'redis'

class RedisClientFactory
  class << self
    def create(service_name)
      redis_credentials = CF::App::Credentials.find_by_service_name(service_name)
      redis_url = redis_credentials ? redis_credentials.fetch('url') : 'redis://localhost:6379/0'
      Redis.new(url: redis_url)
    end
  end
end