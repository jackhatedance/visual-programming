package com.bluesky.visualprogramming.core.serialization;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import javax.naming.spi.ObjectFactory;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeImpl.proto.ListProto;
import com.bluesky.visualprogramming.utils.Config;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class DdwrtSerializer extends  AbstractConfigurableObjectSerializer {

	 
	
	@Override
	public void serialize(_Object obj, Writer writer, Config config) {
		throw new RuntimeException("not supported");
	}

	@Override
	public _Object deserialize(Reader reader, Config config) {

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
		ObjectRepository repo = getRepo();
		_Object result = repo.createObject(ObjectType.NORMAL,
				ObjectScope.ExecutionContext);

		Map<String, String[]> map = d.getMap();
		for (String key : map.keySet()) {
			String[] values = d.getMap().get(key);

			_Object items = repo.createObject(ObjectType.NORMAL,
					ObjectScope.ExecutionContext);

			_Object listProto = repo.getObjectByPath(ObjectRepository.ROOT_OBJECT
					+ ".core.prototype.list");

			items.setPrototype(listProto);


			for (String value : values) {

				_Object valueObj = repo.createObject(ObjectType.STRING,
						ObjectScope.ExecutionContext);

				valueObj.setValue(value);

				ListProto.add(items, valueObj);
			}

			result.setField(key, items, true);
		}

		return result;
	}

	 

}
