angular.module('aggregatedFeedback', ['feedbackService'])
  //.factory('AggregatedFeedbackModel', function () {
  //  var feedback = {
  //    happiness: [0, 0, 0, 0, 0],
  //    learning: [0, 0, 0]
  //  };
  //
  //  function totalVotes(votes) {
  //    return votes.reduce(function (sum, value) {
  //      return sum + value;
  //    })
  //  }
  //
  //  function percentage(votes, level) {
  //    return votes[level] * 100 / totalVotes(votes)
  //  }
  //
  //  return {
  //    setFeedback: function(feedback) {
  //      this.feedback = feedback;
  //    },
  //
  //    happinessLevelPercentage: function(level) {
  //      return percentage(feedback.happiness, level);
  //    },
  //
  //    learningLevelPercentage: function(level) {
  //      return percentage(feedback.learning, level);
  //    }
  //  }
  //})

  .value('AggregatedFeedbackModel', {
    happinessLevels: [
      {
        description: 'This sucks!',
        votes: 0
      },
      {
        description: 'A bit miserable',
        votes: 0
      },
      {
        description: 'Meh',
        votes: 0
      },
      {
        description: 'Pretty good',
        votes: 0
      },
      {
        description: 'Awesome!',
        votes: 0
      }
    ],
    learningLevels: [
      {
        description: 'Nothing!',
        votes: 0
      },
      {
        description: 'A little',
        votes: 0
      },
      {
        description: 'Soaking up new knowledge like a sponge!',
        votes: 0
      }
    ],
    totalVotes: 0
  })

  .controller('AggregatedFeedbackController', function($scope, $interval, FeedbackService, AggregatedFeedbackModel) {
    function updateAggregatedFeedback() {
      function totalVotes(feedback) {
        return feedback.happiness.reduce(function (sum, value) {
          return sum + value;
        })
      }

      function updateModelVotes(modelArray, newLevels) {
        console.log("Model array: " + modelArray);
        console.log("newLevels: " + newLevels);
        for (var i = 0; i < modelArray.length; i++) {
          modelArray[i].votes = newLevels[i];
        }
      }

      FeedbackService.getAggregatedFeedback().$promise.then(function (feedback) {
        updateModelVotes(AggregatedFeedbackModel.happinessLevels, feedback.happiness);
        updateModelVotes(AggregatedFeedbackModel.learningLevels, feedback.learning);
        AggregatedFeedbackModel.totalVotes = totalVotes(feedback);
      });
    }

    $scope.aggregatedFeedback = AggregatedFeedbackModel;

    $interval(updateAggregatedFeedback, 3000)
  });