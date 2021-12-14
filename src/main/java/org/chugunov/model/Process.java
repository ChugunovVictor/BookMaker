package org.chugunov.model;

import javafx.beans.property.*;

import java.net.URL;
import java.util.Optional;

public class Process {

  private SimpleStringProperty site_ = new SimpleStringProperty();
  private SimpleStringProperty addressToStart_ = new SimpleStringProperty();
  private SimpleStringProperty author_ = new SimpleStringProperty();
  private SimpleStringProperty title_ = new SimpleStringProperty();
  private SimpleStringProperty selectorContent_ = new SimpleStringProperty();
  private SimpleStringProperty selectorTitle_ = new SimpleStringProperty();
  private SimpleStringProperty selectorNavigationNext_ = new SimpleStringProperty();
  private SimpleStringProperty outputPath_ = new SimpleStringProperty();
  private SimpleObjectProperty<Type> type_ = new SimpleObjectProperty(Type.NOVEL);

  private SimpleBooleanProperty debug_ = new SimpleBooleanProperty(false);
  private SimpleIntegerProperty numberOfBlocksOnPage_ = new SimpleIntegerProperty(40);

  public Process(){}

  public SimpleStringProperty getSiteProperty() { return site_; }
  public SimpleStringProperty getAddressToStartProperty() { return addressToStart_; }
  public SimpleStringProperty getAuthorProperty() { return author_; }
  public SimpleStringProperty getTitleProperty() { return title_; }
  public SimpleStringProperty getSelectorContentProperty() { return selectorContent_; }
  public SimpleStringProperty getSelectorTitleProperty() { return selectorTitle_; }
  public SimpleStringProperty getSelectorNavigationNextProperty() { return selectorNavigationNext_; }
  public SimpleBooleanProperty isDebugProperty() { return debug_; }
  public SimpleIntegerProperty getNumberOfBlocksOnPageProperty() { return numberOfBlocksOnPage_; }
  public SimpleStringProperty getOutputPathProperty() { return outputPath_; }
  public SimpleObjectProperty getTypeProperty() { return type_; }

  public String getSite() { return site_.getValue(); }
  public String getAddressToStart() { return addressToStart_.getValue(); }
  public String getAuthor() { return author_.getValue(); }
  public String getTitle() { return title_.getValue(); }
  public String getSelectorContent() { return selectorContent_.getValue(); }
  public String getSelectorTitle() { return selectorTitle_.getValue(); }
  public String getSelectorNavigationNext() { return selectorNavigationNext_.getValue(); }
  public Type getType() { return type_.getValue(); }
  public boolean isDebug() { return debug_.getValue(); }
  public int getNumberOfBlocksOnPage() { return numberOfBlocksOnPage_.getValue(); }
  public String getOutputPath() { return outputPath_.getValue(); }

  public Process site(String value) { this.site_.setValue(value); return this; }
  public Process addressToStart(String value) { this.addressToStart_.setValue(value); return this; }
  public Process author(String value) { this.author_.setValue(value); return this; }
  public Process title(String value) { this.title_.setValue(value); return this; }
  public Process selectorContent(String value) { this.selectorContent_.setValue(value); return this; }
  public Process selectorTitle(String value) { this.selectorTitle_.setValue(value); return this; }
  public Process selectorNavigationNext(String value) { this.selectorNavigationNext_.setValue(value); return this; }
  public Process type(Type value) { this.type_.setValue(value); return this; }
  public Process debug(boolean value) { this.debug_.setValue(value); return this; }
  public Process numberOfBlocksOnPage(int value) { this.numberOfBlocksOnPage_.setValue(value); return this; }
  public Process outputPath(String value) { this.outputPath_.setValue(value); return this; }

  public void setSite_(String value) { this.site_.setValue(value); }
  public void setAddressToStart_(String value) { this.addressToStart_.setValue(value); }
  public void setAuthor_(String value) { this.author_.setValue(value); }
  public void setTitle_(String value) { this.title_.setValue(value); }
  public void setSelectorContent_(String value) { this.selectorContent_.setValue(value); }
  public void setSelectorTitle_(String value) { this.selectorTitle_.setValue(value); }
  public void setSelectorNavigationNext_(String value) { this.selectorNavigationNext_.setValue(value); }
  public void setType_(Type value) { this.type_.setValue(value); }
  public void setDebug_(boolean value) { this.debug_.setValue(value); }
  public void setNumberOfBlocksOnPage_(int value) { this.numberOfBlocksOnPage_.setValue(value); }
  public void setOutputPath_(String value) { this.outputPath_.setValue(value); }

  @Override
  public String toString() {
    return "Process{" +
        "site_=" + site_ +
        ", addressToStart_=" + addressToStart_ +
        ", author_='" + author_ + '\'' +
        ", title_='" + title_ + '\'' +
        ", selectorContent_='" + selectorContent_ + '\'' +
        ", selectorTitle_='" + selectorTitle_ + '\'' +
        ", selectorNavigationNext_='" + selectorNavigationNext_ + '\'' +
        ", type_=" + type_ +
        ", debug_=" + debug_ +
        ", numberOfBlocksOnPage_=" + numberOfBlocksOnPage_ +
        ", outputPath_=" + outputPath_ +
        '}';
  }
}
