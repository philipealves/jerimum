#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
angular.module('helloworld', [ 'ngRoute' ]).config(function(${symbol_dollar}routeProvider, ${symbol_dollar}httpProvider) {

	${symbol_dollar}routeProvider.when('/', {
		templateUrl : 'pages/open/home.html',
		controller : 'home'
	}).when('/login', {
		templateUrl : 'pages/open/login.html',
		controller : 'navigation'
	}).otherwise('/');

	${symbol_dollar}httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

}).controller('navigation',

		function(${symbol_dollar}rootScope, ${symbol_dollar}scope, ${symbol_dollar}http, ${symbol_dollar}location, ${symbol_dollar}route) {

			${symbol_dollar}scope.tab = function(route) {
				return ${symbol_dollar}route.current && route === ${symbol_dollar}route.current.controller;
			};

			var authenticate = function(credentials, callback) {

				var headers = credentials ? {
					authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)
				} : {};

				${symbol_dollar}http.get('rest/user', {
					headers : headers
				}).success(function(data) {
					if (data.name) {
						${symbol_dollar}rootScope.authenticated = true;
					} else {
						${symbol_dollar}rootScope.authenticated = false;
					}
					callback && callback(${symbol_dollar}rootScope.authenticated);
				}).error(function() {
					${symbol_dollar}rootScope.authenticated = false;
					callback && callback(false);
				});

			}

			authenticate();

			${symbol_dollar}scope.credentials = {};
			${symbol_dollar}scope.login = function() {
				authenticate(${symbol_dollar}scope.credentials, function(authenticated) {
					if (authenticated) {
						console.log("Login succeeded")
						${symbol_dollar}location.path("/");
						${symbol_dollar}scope.error = false;
						${symbol_dollar}rootScope.authenticated = true;
					} else {
						console.log("Login failed")
						${symbol_dollar}location.path("/login");
						${symbol_dollar}scope.error = true;
						${symbol_dollar}rootScope.authenticated = false;
					}
				})
			};

			${symbol_dollar}scope.logout = function() {
				${symbol_dollar}http.post('logout', {}).success(function() {
					${symbol_dollar}rootScope.authenticated = false;
					${symbol_dollar}location.path("/");
				}).error(function(data) {
					console.log("Logout failed")
					${symbol_dollar}rootScope.authenticated = false;
				});
			}

}).controller('home', 
		function(${symbol_dollar}scope, ${symbol_dollar}http) {
			${symbol_dollar}http.get('rest/resource/').success(function(data) {
				${symbol_dollar}scope.greeting = data;
			})
});
