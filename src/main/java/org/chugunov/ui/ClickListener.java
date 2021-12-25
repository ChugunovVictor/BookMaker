package org.chugunov.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ClickListener implements ChangeListener<Worker.State>, EventListener {
  private final WebView webView;
  private FlowPane flow;
  private BooleanSupplier pickEnabled;

  public ClickListener(WebView webView, FlowPane flow, BooleanSupplier pickEnabled) {
    this.webView = webView;
    this.flow = flow;
    this.pickEnabled = pickEnabled;
  }

  @Override
  public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
    if (Worker.State.SUCCEEDED.equals(newValue)) {
      Document document = webView.getEngine().getDocument();
      ((EventTarget) document.getDocumentElement())
          .addEventListener("click", this, false);
    }
  }

  private static javafx.scene.Node getRow(org.w3c.dom.Node node) {
    HBox h = new HBox();
    h.setSpacing(5);
    h.setAlignment(Pos.CENTER_LEFT);

    // Main tag
    Label l = new Label(node.getNodeName());
    l.setStyle("-fx-text-fill: white;" +
        "-fx-background-color: black;" +
        "-fx-padding: 4px;");
    h.getChildren().add(l);

    // Position details
    Node parent = node.getParentNode();
    String positionDetails = "";
    if(parent != null && parent.getChildNodes().getLength() > 1){
      if(parent.getFirstChild().isSameNode(node)){
        Label a = new Label("First");
        a.setStyle("-fx-padding: 2px;" +
            "-fx-border-color: darkblue;");
        h.getChildren().add(a);
      }
      if(parent.getLastChild().isSameNode(node)){
        Label a = new Label("Last");
        a.setStyle("-fx-padding: 2px;" +
            "-fx-border-color: darkblue;");
        h.getChildren().add(a);
      }

      NodeList nodeList = parent.getChildNodes();
      List<Node> list = IntStream.range(0, nodeList.getLength())
          .mapToObj(nodeList::item).filter(n -> n.getNodeName().equals(node.getNodeName())).collect(Collectors.toList());
      if(list.size() > 1) {
        if (list.get(0).isSameNode(node)) {
          Label a = new Label("First-of-Type");
          a.setStyle("-fx-padding: 2px;" +
              "-fx-border-color: darkblue;");
          h.getChildren().add(a);
        }
        if (list.get(list.size()-1).isSameNode(node)) {
          Label a = new Label("Last-of-Type");
          a.setStyle("-fx-padding: 2px;" +
              "-fx-border-color: darkblue;");
          h.getChildren().add(a);
        }
      }
    }

    // Attrs
    NamedNodeMap attributes = node.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      org.w3c.dom.Node attr = attributes.item(i);
      String additionalStyle = "";
      if (attr.getNodeName().equals("id"))
        additionalStyle = "-fx-border-color: crimson;";
      if (attr.getNodeName().equals("class")) {
        additionalStyle = "-fx-border-color: seagreen;";
      }

      Label a = new Label(
          String.format("%s=%s", attr.getNodeName(), attr.getTextContent())
      );
      a.setStyle("-fx-padding: 2px;" + additionalStyle);
      h.getChildren().add(a);
    }
    return h;
  }

  void fillPanel(List<Node> nodes) {
    TreeItem<Node> root = nodes.stream().map(n -> {
      TreeItem<org.w3c.dom.Node> item = new TreeItem<>(n);
      item.setExpanded(true);
      return item;
    }).reduce((l, r) -> {
      r.getChildren().add(l);
      return r;
    }).get();

    TreeView<Node> treeView = new TreeView<>(root);
    treeView.setPrefSize(400, 300);

    treeView.setCellFactory(p -> new TreeCell<Node>() {
      @Override
      protected void updateItem(final org.w3c.dom.Node item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
          setText(null);
          setGraphic(getRow(item));
        } else {
          setText(null);
          setGraphic(null);
        }
      }
    });
    this.flow.getChildren().clear();
    this.flow.getChildren().add(treeView);
  }

  private void createHiierarchy(Node element) {
    ArrayList res = new ArrayList();
    do {
      res.add(element);
      element = element.getParentNode();
    } while (element != null && !element.getNodeName().equals("#document"));
    fillPanel(res);
  }

  @Override
  public void handleEvent(Event event) {
    if(pickEnabled.getAsBoolean()){
      Node element = (Node) event.getTarget();
      createHiierarchy(element);
      event.preventDefault();
    }
  }
}