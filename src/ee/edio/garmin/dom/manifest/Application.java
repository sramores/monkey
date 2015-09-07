package ee.edio.garmin.dom.manifest;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.Required;

public interface Application extends ManifestDomElement {

//  <iq:application entry="mybApp" id="" launcherIcon="LauncherIcon" name="AppName" type="watch-app">

  @Required
  GenericAttributeValue<String> getEntry();

  GenericAttributeValue<String> getId();

  @Attribute("launcherIcon")
  GenericAttributeValue<String> getLauncherIcon();

  GenericAttributeValue<String> getName();

  GenericAttributeValue<String> getType();

  Products getProducts();

  Permissions getPermissions();

  Languages getLanguages();
}