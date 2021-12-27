package org.chugunov.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.chugunov.model.properties.ContentProperty;
import org.chugunov.model.Process;
import org.chugunov.ui.ClickListener;
import org.chugunov.ui.Controller;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class ContentController implements Controller {
  @FXML
  private WebView browser;
  @FXML
  private FlowPane treePane;
  @FXML
  private TextField addressToStartField,
      selectorTitleField, selectorContentField, selectorNavigationNextField,
      selectorTitleCheckField, selectorContentCheckField, selectorNavigationNextCheckField;
  @FXML
  private CheckBox pickEnabledField;

  private Process process;
  private ContentProperty contentProperty;

  public void init(Process target) {
    WebEngine webEngine = browser.getEngine();
    webEngine.load(target.getBasic().getSite());
    webEngine.getLoadWorker().stateProperty().addListener(
        new ClickListener(browser, treePane, pickEnabledField::isSelected));

    this.process = target;
    this.contentProperty = new ContentProperty(this.process.getContent());
    this.contentProperty = contentProperty
        .addressToStart(addressToStartField)
        .selectorContent(selectorContentField)
        .selectorNavigationNext(selectorNavigationNextField)
        .selectorTitle(selectorTitleField);
  }

  public void fillAddressToStart(){
    addressToStartField.setText(browser.getEngine().getLocation());
  }

  public void checkTitle() throws IOException {
    Document doc = Jsoup.connect(
        browser.getEngine().getLocation()
    ).timeout(0).get();

    String title = "";
    for (Element current_tag : doc.select(selectorTitleField.getText())){
      title += current_tag.text();
    }
    selectorTitleCheckField.setText(title);
  }

  public void checkNavigationNext() throws IOException {
    Document doc = Jsoup.connect(
        browser.getEngine().getLocation()
    ).timeout(0).get();

    String url = "";
    for (Element current_tag : doc.select(selectorNavigationNextField.getText())){
      url = current_tag.attr("href");
    }
    selectorNavigationNextCheckField.setText(url);
  }

  public void checkContent() throws IOException {
    Document doc = Jsoup.connect(
        browser.getEngine().getLocation()
    ).timeout(0).get();

    String text = "";
    for (Element current_tag : doc.select(selectorContentField.getText())){
      text += current_tag.text();
    }
    selectorContentCheckField.setText(text);
  }
}