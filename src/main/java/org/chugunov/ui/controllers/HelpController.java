package org.chugunov.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.chugunov.model.Scan;
import org.chugunov.ui.ClickListener;
import org.chugunov.ui.Controller;
import org.chugunov.utility.PreText;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.function.Consumer;

public class HelpController implements Controller {
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

  private Consumer<Scan> callback;

  public void init(String site, Consumer<Scan> callback) {
    this.callback = callback;
    WebEngine webEngine = browser.getEngine();
    webEngine.load(site);
    webEngine.getLoadWorker().stateProperty().addListener(
        new ClickListener(browser, treePane, pickEnabledField::isSelected));
  }

  public void save(){
    this.callback.accept(new Scan(
        addressToStartField.getText(),
        selectorTitleField.getText(),
        selectorContentField.getText(),
        selectorNavigationNextField.getText()
    ));
    ((Stage)this.browser.getScene().getWindow()).close();
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