package it.bigdata.web.mb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class GuestPreferences implements Serializable {
   private Map<String, String> themeColors;
   private String theme = "ortho";
   private String menuClass = null;
   private String profileMode = "inline";
   private String menuLayout = "static";
   private boolean compact = true;
   private boolean orientationRTL;

   @PostConstruct
   public void init() {
      this.themeColors = new HashMap();
      this.themeColors.put("ortho", "#008fd0");
      this.themeColors.put("indigo", "#3F51B5");
      this.themeColors.put("blue", "#03A9F4");
      this.themeColors.put("blue-grey", "#607D8B");
      this.themeColors.put("brown", "#795548");
      this.themeColors.put("cyan", "#00bcd4");
      this.themeColors.put("green", "#4CAF50");
      this.themeColors.put("purple-amber", "#673AB7");
      this.themeColors.put("purple-cyan", "#673AB7");
      this.themeColors.put("teal", "#009688");
   }

   public String getMenuClass() {
      return this.menuClass;
   }

   public String getProfileMode() {
      return this.profileMode;
   }

   public String getTheme() {
      return this.theme;
   }

   public String getMenuLayout() {
      if (this.menuLayout.equals("static")) {
         return "menu-layout-static";
      } else if (this.menuLayout.equals("overlay")) {
         return "menu-layout-overlay";
      } else {
         return this.menuLayout.equals("horizontal") ? "menu-layout-static menu-layout-horizontal" : "menu-layout-static";
      }
   }

   public void setTheme(String theme) {
      this.theme = theme;
   }

   public void setLightMenu() {
      this.menuClass = null;
   }

   public void setDarkMenu() {
      this.menuClass = "layout-menu-dark";
   }

   public void setProfileMode(String profileMode) {
      this.profileMode = profileMode;
   }

   public void setMenuLayout(String menuLayout) {
      this.menuLayout = menuLayout;
   }

   public Map getThemeColors() {
      return this.themeColors;
   }

   public void setCompact(boolean value) {
      this.compact = value;
   }

   public boolean isCompact() {
      return this.compact;
   }

   public boolean isOrientationRTL() {
      return this.orientationRTL;
   }

   public void setOrientationRTL(boolean orientationRTL) {
      this.orientationRTL = orientationRTL;
   }
}
