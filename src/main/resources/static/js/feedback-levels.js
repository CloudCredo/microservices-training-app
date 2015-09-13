angular.module('feedbackLevels', [])
  .constant('FeedbackLevels', {
    happinessLevels: [
      { index: 0, description: 'This sucks!' },
      { index: 1, description: 'Not great' },
      { index: 2, description: 'Meh' },
      { index: 3, description: 'Pretty good' },
      { index: 4, description: 'Awesome!' }
    ],
    learningLevels: [
      { index: 0, description: 'I\'ve learnt nothing!' },
      { index: 1, description: 'I\'ve learnt something' },
      { index: 2, description: 'Mind blown!' }
    ]
  });