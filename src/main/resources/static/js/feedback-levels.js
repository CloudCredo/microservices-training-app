angular.module('feedbackLevels', [])
  .factory('FeedbackLevels', function () {

    var darkRed = '#bf0000',
      lightRed = '#ff0000',
      darkOrange = '#e49902',
      lightOrange = '#ffe600',
      darkGreen = '#85cc1a',
      lightGreen = '#a6ff21';

    return {
      happinessLevels: [
        {index: 0, description: "This sucks!", gaugeColours: [darkRed, lightRed]},
        {index: 1, description: "Not great", gaugeColours: [darkRed, darkOrange]},
        {index: 2, description: "Meh", gaugeColours: [darkOrange, lightOrange]},
        {index: 3, description: "Pretty good", gaugeColours: [darkOrange, darkGreen]},
        {index: 4, description: "Awesome!", gaugeColours: [darkGreen, lightGreen]}
      ],
      learningLevels: [
        {index: 0, description: "I've learnt nothing!", gaugeColours: [darkRed, lightRed]},
        {index: 1, description: "I've learnt something", gaugeColours: [darkOrange, lightOrange]},
        {index: 2, description: "Mind blown!", gaugeColours: [darkGreen, lightGreen]}
      ]
    }
  });