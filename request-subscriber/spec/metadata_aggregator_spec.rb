require 'json'
require 'redis'

require 'metadata_aggregator'

RSpec.describe MetadataAggregator do

  subject(:aggregator) { described_class.new(redis) }

  let(:redis) { instance_double(Redis) }
  let(:get_feedback) { {method: 'GET', path: '/feedback'}.to_json }
  let(:post_feedback) { {method: 'POST', path: '/feedback'}.to_json }
  let(:get_questions) { {method: 'GET', path: '/questions'}.to_json }

  it 'aggregates by endpoint and method' do

    expect(redis).to receive(:incr).with('aggregatedMetadata:GET:/feedback')
    expect(redis).to receive(:incr).with('aggregatedMetadata:POST:/feedback').exactly(2).times
    expect(redis).to receive(:incr).with('aggregatedMetadata:GET:/questions').exactly(4).times

    aggregator.aggregate(get_feedback)
    2.times { subject.aggregate(post_feedback) }
    4.times { subject.aggregate(get_questions) }

  end

end