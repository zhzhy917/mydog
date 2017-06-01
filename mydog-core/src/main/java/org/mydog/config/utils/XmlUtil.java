package org.mydog.config.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtil {
	public static Document getDocument(InputStream xml) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(false);
		DocumentBuilder builder = factory.newDocumentBuilder();

		return builder.parse(xml);
	}

	public static Map<String, String> loadProperty(Element e) {
		Map<String, String> map = new HashMap<String, String>();

		NodeList nodeList = e.getElementsByTagName("property");

		for (int x = 0, n = nodeList.getLength(); x < n; x++) {
			Node item = nodeList.item(x);
			if (item instanceof Element) {
				Element subEle = (Element) item;
				String attrValue = subEle.getAttribute("name");
				String textContent = subEle.getAttribute("value");
				map.put(attrValue, textContent);
			}
		}
		return map;
	}

	public static Map<String, String> loadAttribute(Element e) {
		Map<String, String> map = new HashMap<String, String>();
		NamedNodeMap attributes = e.getAttributes();
		for(int x=0,n=attributes.getLength();x<n;x++){
			Node node = attributes.item(x);
			if (node instanceof Attr) {
                Attr attr = (Attr) node;
                map.put(attr.getName(), attr.getNodeValue());
            }
		}
		return map;
	}

	public static Element getElement(Element e, String tagName) {
		NodeList nodeList = e.getElementsByTagName(tagName);
		if (null != nodeList) {
			if (nodeList.getLength() > 0) {
				return (Element) nodeList.item(0);
			}
		}
		return null;
	}
	
	
	public static List<Element> getElements(Element e, String tagName) {
		List<Element> elements = new Vector<Element>();
		NodeList nodeList = e.getElementsByTagName(tagName);
		if (null != nodeList) {
			for(int x=0,n=nodeList.getLength();x<n;x++){
				elements.add( (Element)nodeList.item(x));
			}
		}
		return elements ;
	}
}
