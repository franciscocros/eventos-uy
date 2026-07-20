package controllers;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;



@WebListener
public class AppContextListener implements ServletContextListener {
	//private Fabrica F = Fabrica.getInstance();
	//private IControladorUsuario ICU = F.getIControladorUsuario();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    
    }




}
