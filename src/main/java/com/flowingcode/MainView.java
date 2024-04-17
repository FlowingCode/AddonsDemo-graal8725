package com.flowingcode;

import com.flowingcode.vaadin.addons.DemoLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route(value = "", layout = DemoLayout.class)
public class MainView extends VerticalLayout {

  public MainView() {
    add(new Span("HELLO WORLD"));
  }

}
