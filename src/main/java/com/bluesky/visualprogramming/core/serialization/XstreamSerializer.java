package com.bluesky.visualprogramming.core.serialization;

import java.io.Reader;
import java.io.Writer;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core.Procedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.FloatValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.utils.Config;
import com.thoughtworks.xstream.XStream;

/**
 * it use xstream, to load/save objects. not that controllerable as other
 * formats but easy to implement.
 * 
 * it usually export non-Cooby fields that other formats donesn't.
 * 
 * @author jack
 * 
 */
public class XstreamSerializer implements ConfigurableObjectSerializer {
	XStream xstream;

	boolean omitGuiInfo;

	protected void initXstream(Config config) {

		omitGuiInfo = config.getBoolean("omitGuiInfo", false);

		xstream = new XStream();

		xstream.setMode(XStream.ID_REFERENCES);

		xstream.alias("object", _Object.class);
		
		xstream.omitField(_Object.class, "fieldNameMap");
		xstream.omitField(_Object.class, "childrenObjectMap");
		xstream.omitField(_Object.class, "applyingWorker");
		xstream.omitField(_Object.class, "ownerField");
		xstream.omitField(_Object.class, "field");


		//xstream.omitField(_Object.class, "area");
		// owner can be restored after deserialze.
		//xstream.omitField(_Object.class, "owner");
		
		xstream.alias("field", Field.class);
		xstream.omitField(Field.class, "selectedStatus");
		xstream.omitField(Field.class, "weakTarget");
		
		
		xstream.alias("boolean", BooleanValue.class);
		xstream.alias("integer", IntegerValue.class);
		xstream.alias("float", FloatValue.class);
		xstream.alias("string", StringValue.class);
		xstream.alias("link", Link.class);
		xstream.omitField(Link.class, "protocol");
		xstream.omitField(Link.class, "address");
		
		xstream.alias("procedure", Procedure.class);

		if (omitGuiInfo)
			omitGuiInfo();
		
		omitRuntimeInfo();
	}

	protected void omitGuiInfo() {

		Class cls = _Object.class;
		
		xstream.omitField(cls, "scaleRate");
		xstream.omitField(cls, "border");
		xstream.omitField(cls, "borderColor");

		xstream.omitField(Field.class, "selectedStatus");
	}
	
	protected void omitRuntimeInfo() {

		Class cls = _Object.class;

		xstream.omitField(cls, "messageQueue");
		xstream.omitField(cls, "asynMessageMap");
		
		xstream.omitField(cls, "worker");		
		
		
		Class fieldCls = Field.class;
		xstream.omitField(fieldCls, "selectedStatus");
		
		
		Class procedureCls = Procedure.class;
		xstream.omitField(procedureCls, "compiled");
		
	}


 

	@Override
	public void serialize(_Object obj, Writer writer, Config config) {
		initXstream(config);
		xstream.toXML(obj, writer);
	}

	@Override
	public _Object deserialize(Reader reader, Config config) {
		initXstream(config);
		return (_Object) xstream.fromXML(reader);
	}

}
