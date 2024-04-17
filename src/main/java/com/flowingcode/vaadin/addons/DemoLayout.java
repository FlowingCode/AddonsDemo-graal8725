package com.flowingcode.vaadin.addons;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouteData;
import com.vaadin.flow.router.RouterLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
@CssImport("./styles/styles.css")
public class DemoLayout extends AppLayout {

  private static final Logger logger = LoggerFactory.getLogger(DemoLayout.class);

  private static boolean buildDrawerMenuWarnings_logged = false;


  public DemoLayout() {
      DrawerToggle toggle = new DrawerToggle();
      setDrawerOpened(false);
      addToDrawer(buildDrawerMenu());
      addToNavbar(toggle);

    }

    private Tabs buildDrawerMenu() {
      Tabs tabs = new Tabs();
      tabs.setOrientation(Tabs.Orientation.VERTICAL);

      int metadataCount = 0;
      for (RouteData route : RouteConfiguration.forApplicationScope().getAvailableRoutes()) {
        Class<?> clazz = route.getNavigationTarget();
        if (clazz.getSimpleName().equals("ApplayoutDemoView")) {
          continue;
        }
        if (clazz.getSimpleName().equals("VerticalmenuDemoView")) {
          continue;
        }
        if (route.getNavigationTarget().getSimpleName().endsWith("DemoView")) {
          String title = clazz.getPackage().getImplementationTitle();
          if (title != null) {
            metadataCount++;
            tabs.add(createTab(title, route.getNavigationTarget()));
          } else {
            if (!buildDrawerMenuWarnings_logged) {
              logger.warn("No package metadata for " + route.getNavigationTarget().getSimpleName());
            }
          }
        }
      }
      if (!buildDrawerMenuWarnings_logged) {
        logger.warn("Metadata count: " + metadataCount);
      }
      buildDrawerMenuWarnings_logged = true;
      return tabs;
    }

    private Tab createTab(String viewName, Class<? extends Component> component) {
      RouterLink link = new RouterLink();
      link.add(new Span(viewName));
      link.setRoute(component);
      link.setTabIndex(-1);
      return new Tab(link);
    }

}
