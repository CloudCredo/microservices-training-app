require 'json'
require 'redis'

require 'metadata_aggregator'

RSpec.describe MetadataAggregator do

  subject(:aggregator) { described_class.new(redis) }

  let(:redis) { instance_double(Redis) }
  let(:get_feedback) { {method: 'GET', path: '/feedback'}.to_json }
  let(:post_feedback) { {method: 'POST', path: '/feedback'}.to_json }
  let(:get_questions) { {method: 'GET', path: '/questions'}.to_json }

  describe '#on_subscribe' do
    it 'exists, but does nothing' do
      aggregator.on_subscribe
    end
  end

  describe '#handle_message' do
    it 'aggregates by endpoint and method' do
      expect(redis).to receive(:incr).with('aggregatedMetadata:GET:/feedback')
      expect(redis).to receive(:incr).with('aggregatedMetadata:POST:/feedback').exactly(2).times
      expect(redis).to receive(:incr).with('aggregatedMetadata:GET:/questions').exactly(4).times

      aggregator.handle_message(get_feedback)
      2.times { aggregator.handle_message(post_feedback) }
      4.times { aggregator.handle_message(get_questions) }
    end
  end

end