package com.bluesky.visualprogramming.ui.svg;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGOMCircleElement;
import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.batik.dom.svg.SVGOMRectElement;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import com.bluesky.visualprogramming.core.ObjectType;

public class SVGUtils {

	public static String SCENE = "svg/scene.svg";

	public static Document createScene() {
		return createDocumentFromResource(SCENE);
	}

	protected static SVGDocument createDocumentFromString(String str) {
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);

		// parse String into DOM Document
		StringReader reader = new StringReader(str);
		// do not forget the URI, even it is fake eg: c://svg/sample.svg
		// this is specially important if your doc reference other doc
		// in relative URI
		try {
			SVGDocument svgDoc = f.createSVGDocument("sample.svg", reader);

			// below stuff to parse CSSstyle. usually it is parsed when
			// rendering.
			//
			UserAgent userAgent;
			DocumentLoader loader;
			BridgeContext ctx;
			GVTBuilder builder;
			GraphicsNode rootGN;

			userAgent = new UserAgentAdapter();
			loader = new DocumentLoader(userAgent);
			ctx = new BridgeContext(userAgent, loader);
			ctx.setDynamicState(BridgeContext.DYNAMIC);
			builder = new GVTBuilder();
			rootGN = builder.build(ctx, svgDoc);

			return svgDoc;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	protected static SVGDocument createDocumentFromResource(String resource) {
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
			UserAgent userAgent;
			DocumentLoader loader;
			BridgeContext ctx;
			GVTBuilder builder;
			GraphicsNode rootGN;

			userAgent = new UserAgentAdapter();
			loader = new DocumentLoader(userAgent);
			ctx = new BridgeContext(userAgent, loader);
			ctx.setDynamicState(BridgeContext.DYNAMIC);
			builder = new GVTBuilder();
			rootGN = builder.build(ctx, svgDoc);

			return svgDoc;

		} catch (IOException e) {

			throw new RuntimeException(e);
		}

	}

	public static Document createObjectDocument(ObjectType objectType) {
		Document doc = createDocumentFromResource(objectType.getSvgResource());

		return doc;
	}

	public static Document createObjectDocument(String svgContent) {
		Document doc = createDocumentFromString(svgContent);

		return doc;
	}

	public static Long getObjectId(Element element) {
		String elementId = element.getAttribute("id");
		// if (elementId.isEmpty())
		// return null;

		int idx = elementId.indexOf('-');

		return Long.valueOf(elementId.substring(0, idx));

	}

	/**
	 * point at left,top
	 * 
	 * @param element
	 * @return
	 */
	public static SVGOMPoint getStartPoint(Element element) {
		// support rectangle(x,y) and circle(rx,ry)
		float x, y;
		if (element instanceof SVGOMRectElement) {
			x = Float.valueOf(element.getAttribute("x"));
			y = Float.valueOf(element.getAttribute("y"));

		} else if (element instanceof SVGOMCircleElement) {
			float r = Float.valueOf(element.getAttribute("r"));

			x = Float.valueOf(element.getAttribute("cx")) - r;
			y = Float.valueOf(element.getAttribute("cy")) - r;

		} else {
			x = 0f;
			y = 0f;
		}

		return new SVGOMPoint(x, y);
	}
}
