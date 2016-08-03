#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
app.run(run);

app.config(function(${symbol_dollar}mdThemingProvider, ${symbol_dollar}stateProvider, ${symbol_dollar}urlRouterProvider) {
	${symbol_dollar}mdThemingProvider
      .theme('default')
        .primaryPalette('grey', {
          'default': '600'
        })
        .accentPalette('teal', {
          'default': '500'
        })
        .warnPalette('defaultPrimary');

	${symbol_dollar}mdThemingProvider.theme('dark', 'default')
      .primaryPalette('defaultPrimary')
      .dark();

	${symbol_dollar}mdThemingProvider.theme('grey', 'default')
      .primaryPalette('grey');

	${symbol_dollar}mdThemingProvider.theme('custom', 'default')
      .primaryPalette('defaultPrimary', {
        'hue-1': '50'
    });

	${symbol_dollar}mdThemingProvider.definePalette('defaultPrimary', {
      '50':  '#FFFFFF',
      '100': 'rgb(255, 198, 197)',
      '200': '#E75753',
      '300': '#E75753',
      '400': '#E75753',
      '500': '#E75753',
      '600': '#E75753',
      '700': '#E75753',
      '800': '#E75753',
      '900': '#E75753',
      'A100': '#E75753',
      'A200': '#E75753',
      'A400': '#E75753',
      'A700': '#E75753'
    });

	${symbol_dollar}stateProvider
	.state('login', {
        url: '/',
        templateUrl: 'pages/open/login.html',
      })
	 .state('home', {
        url: '/home',
        templateUrl: 'pages/open/restricted/home.html',
      })
	 .state('cadastro', {
        url: '/cadastro',
        templateUrl: 'pages/open/restricted/cadastro.html',
        controller: 'AppCtrl',
        data: {
          title: 'Cadastro Usuário',
        }
      })
      .state('consulta', {
        url: '/consulta',
        templateUrl: 'pages/open/restricted/consulta.html',
        controller: 'AppCtrl',
        data: {
          title: 'Consulta',
        }
      });

	${symbol_dollar}urlRouterProvider.otherwise('/');
  });



run.${symbol_dollar}inject = ['${symbol_dollar}rootScope', '${symbol_dollar}log', '${symbol_dollar}state', '${symbol_dollar}location','${symbol_dollar}http'];
function run (${symbol_dollar}rootScope, ${symbol_dollar}log, ${symbol_dollar}state, ${symbol_dollar}location,${symbol_dollar}http) { 
	  ${symbol_dollar}rootScope.${symbol_dollar}on( "${symbol_dollar}stateChangeStart", function(event, next, current) {
		if(next.name != "login") {
			${symbol_dollar}rootScope.authenticated = true;
		} else {
			${symbol_dollar}rootScope.authenticated = false;
		}
	});
};
