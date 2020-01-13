package it.bigdata.web.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

public class BaseMB implements Serializable {
   private ResourceBundle bundleMsg;

   protected HttpServletRequest getHttpServletRequest() {
      return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
   }

   protected HttpServletResponse getHttpServletResponse() {
      return (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
   }

   protected void invalidateSession() {
      ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
   }

   private void addMessage(String summary, String detail, Severity severity, boolean flashed) {
      FacesContext context = FacesContext.getCurrentInstance();
      if (flashed) {
         Flash flash = context.getExternalContext().getFlash();
         flash.setKeepMessages(true);
      }

      context.addMessage((String)null, new FacesMessage(severity, summary, detail));
   }

   protected void addInfoMessage(String summary) {
      this.addInfoMessage(summary, "");
   }

   protected void addInfoMessageNotFlashed(String summary) {
      this.addMessage(summary, "", FacesMessage.SEVERITY_INFO, false);
   }

   protected void addInfoMessage(String summary, String detail) {
      this.addMessage(summary, detail, FacesMessage.SEVERITY_INFO, true);
   }

   protected void addWarnMessage(String summary) {
      this.addWarnMessage(summary, "");
   }

   protected void addWarnMessageNotFlashed(String summary) {
      this.addMessage(summary, "", FacesMessage.SEVERITY_WARN, false);
   }

   protected void addWarnMessage(String summary, String detail) {
      this.addMessage(summary, detail, FacesMessage.SEVERITY_WARN, true);
   }

   protected void addErrorMessage(String summary) {
      this.addErrorMessage(summary, "");
   }

   protected void addErrorMessageNotFlashed(String summary) {
      this.addMessage(summary, "", FacesMessage.SEVERITY_ERROR, false);
   }

   protected void addErrorMessage(String summary, String detail) {
      this.addMessage(summary, detail, FacesMessage.SEVERITY_ERROR, true);
   }

   protected ResourceBundle getResourceBundle() {
      if (this.bundleMsg == null) {
         FacesContext context = FacesContext.getCurrentInstance();
         this.bundleMsg = context.getApplication().getResourceBundle(context, "msgs");
      }

      return this.bundleMsg;
   }

   protected String getParameter(String name) {
      return (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
   }

   protected void gotoPage(String outcome) {
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.getApplication().getNavigationHandler().handleNavigation(fc, (String)null, outcome);
   }

   protected UIComponent getUIComponentById(String componentId) {
      UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
      UIComponent component = viewRoot.findComponent(componentId);
      return component;
   }

   protected Object removeObjectFromSession(String key) {
      return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);
   }

   protected void saveObjectToSession(String key, Object value) {
      FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
   }

   protected String getUrlCtxPath() {
      HttpServletRequest req = this.getHttpServletRequest();
      return req.getScheme() + "://" + req.getServerName() + ":" + req.getLocalPort() + req.getContextPath();
   }

   public void scrollToComponent(String component) {
      try {
         RequestContext.getCurrentInstance().scrollTo(component);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   protected List<SelectItem> getPropertiesSelectItem(String keyPrefix) {
      ArrayList result = new ArrayList();

      try {
         ResourceBundle bundle = this.getResourceBundle();
         Enumeration<String> keys = bundle.getKeys();
         Enumeration e = keys;

         while(keys.hasMoreElements()) {
            String key = (String)e.nextElement();
            SelectItem item = new SelectItem();
            if (key.startsWith(keyPrefix)) {
               String value = bundle.getString(key);
               String[] arr = value.split("\\|");
               item.setValue(arr[0]);
               item.setLabel(arr[1]);
               result.add(item);
            }
         }

         return result;
      } catch (Exception var10) {
         return null;
      }
   }
}
