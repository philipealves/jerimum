#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.util;

/**
 * This class contains the navigation rules constants that will be used in the application.
 * 
 * @author Dali Freire: dalifreire@gmail.com
 * @since 10/2015
 */
public class NavigationConstants {

    private NavigationConstants() {

    }

    /**
     * Navegacao para a tela de home.
     */
    public static final String HOME = "pages/home";
    
    /**
     * Navegacao para o servico de login.
     */
    public static final String LOGIN_SPRING_SECURITY = "j_spring_security_check";

    /**
     * Navegacao para o servico de logout.
     */
    public static final String LOGOUT_SPRING_SECURITY = "j_spring_security_logout";

}
