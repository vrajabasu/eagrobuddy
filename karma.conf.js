module.exports = function (config) {
  config.set({
    basePath: '',
    frameworks: ['jasmine', '@angular/cli'],
    plugins: [
      require('karma-jasmine'),
      require('karma-chrome-launcher'),
      // require('karma-ie-launcher'),
      require('karma-jasmine-html-reporter'),
      require('karma-coverage-istanbul-reporter'),
      require('@angular/cli/plugins/karma')
    ],
    client:{
      clearContext: false // leave Jasmine Spec Runner output visible in browser
    },
    files: [
      { pattern: './src/main/webapp/test.ts', watched: false }
    ],
    // customLaunchers: {
    //     'ChromeHeadless': {
    //         base: 'Chrome',
    //         flags: [
    //             '--headless',
    //             '--disable-gpu',
    //             '--disable-popup-blocking',
    //             // Without a remote debugging port, Google Chrome exits immediately.
    //             '--remote-debugging-port=9222'
    //         ],
    //         debug: true
    //     }
    // },    
    preprocessors: {
      './src/main/webapp/test.ts': ['@angular/cli']
    },
    mime: {
      'text/x-typescript': ['ts','tsx']
    },
    coverageIstanbulReporter: {
      reports: [ 'html', 'lcovonly' ],
      fixWebpackSourcePaths: true
    },
    angularCli: {
      environment: 'dev'
    },
    reporters: config.angularCli && config.angularCli.codeCoverage
              ? ['progress', 'coverage-istanbul']
              : ['progress', 'kjhtml'],
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ['Chrome'],
    singleRun: false
  });
};
