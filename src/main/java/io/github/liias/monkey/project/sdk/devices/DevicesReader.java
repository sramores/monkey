package io.github.liias.monkey.project.sdk.devices;

import io.github.liias.monkey.project.runconfig.TargetDevice;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DevicesReader {
  public static final String DEVICES_XML = "devices.xml";

  private static String TAG_DEVICES = "devices";
  private static String TAG_DEVICE = "device";

  public static final String DEVICE_ATTRIBUTE_ID = "id";
  public static final String DEVICE_ATTRIBUTE_NAME = "name";

  private final String sdkBinPath;
  private List<TargetDevice> devices;

  public DevicesReader(String sdkBinPath) {
    this.sdkBinPath = sdkBinPath;
  }

  public List<TargetDevice> parseDevicesXml() {
    String devicesXmlPath = sdkBinPath + DEVICES_XML;

    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new File(devicesXmlPath));
      this.devices = importDevices(doc);

    } catch (SAXException | IOException | ParserConfigurationException e) {
      e.printStackTrace();
    }
    return this.devices;
  }

  private List<TargetDevice> importDevices(Document doc) {
    Element rootElement = doc.getDocumentElement();
    Element devicesRoot = (Element) rootElement.getElementsByTagName(TAG_DEVICES).item(0);
    NodeList devices = devicesRoot.getElementsByTagName(TAG_DEVICE);

    List<TargetDevice> devicesList = new ArrayList<>();
    for (int i = 0; i < devices.getLength(); i++) {
      Element device = (Element) devices.item(i);
      NamedNodeMap attributes = device.getAttributes();
      String deviceId = attributes.getNamedItem(DEVICE_ATTRIBUTE_ID).getNodeValue();
      if (deviceId.endsWith("_sim")) {
        continue;
      }
      String deviceName = attributes.getNamedItem(DEVICE_ATTRIBUTE_NAME).getNodeValue();
      TargetDevice targetDevice = new TargetDevice(deviceId, deviceName);

      devicesList.add(targetDevice);
    }

    return devicesList;
  }
}
