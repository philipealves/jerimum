#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
app.controller('LoginCtrl',function(${symbol_dollar}scope, ${symbol_dollar}mdBottomSheet, ${symbol_dollar}state, ${symbol_dollar}mdSidenav, ${symbol_dollar}window, ${symbol_dollar}mdDialog, ${symbol_dollar}http, ${symbol_dollar}location, ${symbol_dollar}rootScope){
  
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

	${symbol_dollar}scope.credentials = {};
	${symbol_dollar}scope.login = function() {
		authenticate(${symbol_dollar}scope.credentials, function(authenticated) {
			if (authenticated) {
				console.log("Login succeeded")
				${symbol_dollar}rootScope.authenticated = true;
				${symbol_dollar}state.go('home').finally(function() {${symbol_dollar}window.location.reload();});
				${symbol_dollar}scope.error = false;
			} else {
				console.log("Login failed")
				${symbol_dollar}rootScope.authenticated = false;
				${symbol_dollar}state.go('login').finally(function() {${symbol_dollar}window.location.reload();});
				${symbol_dollar}scope.error = true;
				
			}
		})
	};
});