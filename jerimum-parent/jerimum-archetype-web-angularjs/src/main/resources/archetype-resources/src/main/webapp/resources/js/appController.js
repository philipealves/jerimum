#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
app.controller('AppCtrl',function(${symbol_dollar}scope, ${symbol_dollar}mdBottomSheet, ${symbol_dollar}state, ${symbol_dollar}mdSidenav, ${symbol_dollar}window, ${symbol_dollar}mdDialog, ${symbol_dollar}http, ${symbol_dollar}location, ${symbol_dollar}rootScope){

	${symbol_dollar}scope.toggleSidenav = function(menuId) {
    ${symbol_dollar}mdSidenav(menuId).toggle();
  };
 
 ${symbol_dollar}scope.menu = [
    {
      link : 'cadastro',
      title: 'Cadastro',
      icon: 'person_add'

    },
    {
      link : 'consulta',
      title: 'Consulta',
      icon: 'youtube_searched_for'
    }
  ];
  
  ${symbol_dollar}scope.setTitlePage = function(item) {
	  
	  ${symbol_dollar}scope.titlePage = item.title;
	  
  };
  
  ${symbol_dollar}scope.user = {
      title: 'Admin',
      email: 'contact@flatlogic.com',
      firstName: '',
      lastName: '' ,
      submissionDate: new Date(),
      company: 'FlatLogic Inc.' ,
      address: 'Fabritsiusa str, 4' ,
      city: 'Minsk' ,
      state: '' ,
      biography: 'We are young and ambitious full service design and technology company. ' +
      'Our focus is JavaScript development and User Interface design.',
      postalCode : '220007'
    };
  
  ${symbol_dollar}scope.tableData = [
      {
        issue: 'Nested views',
        progress: 100,
        status: 'Done',
        class: 'md-accent'
      },
      {
        issue: 'Table component',
        progress: 40,
        status: 'Feedback',
        class: ''
      },
      {
        issue: 'Dashboard tiles',
        progress: 100,
        status: 'Done',
        class: 'md-accent'
      },
      {
        issue: 'Panel widget',
        progress: 84,
        status: 'In progress',
        class: 'orange'
      },
      {
        issue: 'Form',
        progress: 100,
        status: 'Done',
        class: 'md-accent'
      },
      {
        issue: 'Custom CSS',
        progress: 20,
        status: 'Feedback',
        class: ''
      },
      {
        issue: 'Add backend',
        progress: 1,
        status: 'To do',
        class: 'md-warn'
      },
      {
        issue: 'Layout with sidebar',
        progress: 100,
        status: 'Done',
        class: 'md-accent'
      }
    ];
  
  ${symbol_dollar}scope.logout = function() {
		${symbol_dollar}http.post('logout', {}).success(function() {
			${symbol_dollar}rootScope.authenticated = false;
			${symbol_dollar}state.go('login').finally(function() {${symbol_dollar}window.location.reload();});
		}).error(function(data) {
			console.log("Logout failed")
			${symbol_dollar}rootScope.authenticated = false;
		});
	}	


});