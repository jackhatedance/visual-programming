package com.bluesky.visualprogramming.ui.avatar;

import java.io.IOException;
import java.net.URL;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGOMGElement;
import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import com.bluesky.visualprogramming.core.ObjectType;

public class SVGUtils {

	public static String SCENE = "svg/scene.svg";

	public static Document createScene() {
		return createDocument(SCENE);
	}

	protected static SVGDocument createDocument(String resource) {
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		// eg. "svg/scene.svg"
		URL urlScene = SVGUtils.class.getClassLoader().getResource(resource);
		try {

			SVGDocument svgDoc = (SVGDocument) f.createDocument(urlScene
					.toString());

			// below stuff to parse CSSstyle. usually it is parsed when
			// rendering.
			//
			// UserAgent userAgent;
			// DocumentLoader loader;
			// BridgeContext ctx;
			// GVTBuilder builder;
			// GraphicsNode rootGN;
			//
			// userAgent = new UserAgentAdapter();
			// loader = new DocumentLoader(userAgent);
			// ctx = new BridgeContext(userAgent, loader);
			// ctx.setDynamicState(BridgeContext.DYNAMIC);
			// builder = new GVTBuilder();
			// rootGN = builder.build(ctx, svgDoc);

			return svgDoc;

		} catch (IOException e) {

			throw new RuntimeException(e);
		}

	}

	public static Document createObjectDocument(String id, ObjectType objectType) {
		Document doc = createDocument(objectType.getSvgResource());
	 
		return doc;
	}

	public static Long getObjectId(Element element) {
		String elementId = element.getAttribute("id");
		// if (elementId.isEmpty())
		// return null;

		int idx = elementId.indexOf('-');

		return Long.valueOf(elementId.substring(0, idx));

	}

	public static SVGOMPoint getXY(Element element) {
		Float x = Float.valueOf(element.getAttribute("x"));
		Float y = Float.valueOf(element.getAttribute("y"));
		return new SVGOMPoint(x, y);
	}
}
