package com.bluesky.jack.jackap;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

/**
 * 
 * @author jack
 * 
 */
public class ExtractActiveUsers extends BaseNativeProcedure implements
		NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {
		StringValue htmlSV = (StringValue) ctx.get("html");
		String html = htmlSV.getValue();

		StringValue result = (StringValue) virtualMachine.getObjectRepository()
				.createObject(ObjectType.STRING, ObjectScope.ExecutionContext);

		// result.setValue(newStr);

		return result;
	}

	List<String> getWirelessUserMacs(String html) {
		List<String> list = new ArrayList<String>();

		// Status_Wireless.asp
		// Status_Wireless.live.asp (json)

		Document doc = Jsoup.parse(html);
		Elements rows = doc.select("#wireless_table td");
		for (Element tr : rows) {
			String mac = tr.child(0).text();
			list.add(mac);
		}

		return list;
	}

}
