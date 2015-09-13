angular.module('feedbackLevels', [])
  .constant('FeedbackLevels', {
    happinessLevels: [
      { description: 'This sucks!' },
      { description: 'Not great' },
      { description: 'Meh' },
      { description: 'Pretty good' },
      { description: 'Awesome!' }
    ],
    learningLevels: [
      { description: 'I\'ve learnt nothing!' },
      { description: 'I\'ve learnt something' },
      { description: 'Mind blown!' }
    ]
  });