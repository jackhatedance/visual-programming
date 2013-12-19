package com.bluesky.visualprogramming.core.serialization.rpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Map;

import com.bluesky.jack.jackap.DDwrtValues;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.list.ListObject;
import com.bluesky.visualprogramming.core.serialization.dump.ObjectSerializer;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class DdwrtSerializer implements ObjectSerializer {

	@Override
	public void serialize(_Object obj, Writer writer) {
		throw new RuntimeException("not supported");
	}

	@Override
	public _Object deserialize(Reader reader) {

		StringBuilder sb = new StringBuilder();
		while (true) {
			char[] buff = new char[1024];
			int len;
			try {
				len = reader.read(buff);
				if (len < 0)
					break;
				
				sb.append(buff, 0, len);
			} catch (IOException e) {

				e.printStackTrace();
				throw new RuntimeException(e);
			}

		}

		DDwrtValues d = new DDwrtValues(sb.toString());

		// create a list object
		VirtualMachine virtualMachine = VirtualMachine.getInstance();
		ObjectRepository repo = virtualMachine.getObjectRepository();
		_Object result = repo.createObject(ObjectType.NORMAL,
				ObjectScope.ExecutionContext);

		Map<String, String[]> map = d.getMap();
		for (String key : map.keySet()) {
			String[] values = d.getMap().get(key);

			_Object kvObj = repo.createObject(ObjectType.NORMAL,
					ObjectScope.ExecutionContext);

			_Object list = repo.getObjectByPath("root.prototype.list");
			kvObj.setField("_prototype", list, false);
			ListObject kvListObject = new ListObject(kvObj);

			for (String value : values) {

				_Object valueObj = repo.createObject(ObjectType.STRING,
						ObjectScope.ExecutionContext);

				valueObj.setValue(value);

				kvListObject.add(valueObj);
			}

			result.setField(key, kvObj, true);
		}

		return result;
	}

}
