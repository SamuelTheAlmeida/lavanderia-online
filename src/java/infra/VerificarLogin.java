/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infra;

import controller.LoginMB;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class VerificarLogin implements PhaseListener {
    private static final long serialVersionUID = 1;
    
    public void beforePhase(PhaseEvent event) {
    }
    
    public void afterPhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        String viewId = context.getViewRoot().getViewId();
        LoginMB loginMB = context.getApplication().evaluateExpressionGet(context, "#{loginMB}", LoginMB.class);
        System.out.println("viewid: " + viewId);
        // verifica se o usuario ja está logado e está tentando entrar na pagina de login ou cadastro
        if ( (viewId.equals("/login.xhtml") || viewId.equals("/cadastroCliente.xhtml")) && loginMB.isLogado()  ) {
            NavigationHandler handler = context.getApplication().
            getNavigationHandler();
            handler.handleNavigation(context, null, "index?faces-redirect=true");
            context.renderResponse();               
        } 
        // se o usuário não está logado, pode entrar nas páginas que não necessitam autenticação
        else if ( viewId.equals("/login.xhtml") || viewId.equals("/index.xhtml") || viewId.equals("/cadastroCliente.xhtml") ){
            return;           
        } 
        // se o usuário não está logado, não pode entrar nas páginas que precisam de autenticação
        else if (!loginMB.isLogado()) {
            NavigationHandler handler = context.getApplication().
            getNavigationHandler();
            handler.handleNavigation(context, null, "login?faces-redirect=true");
            context.renderResponse();         
        }
    }
    
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
