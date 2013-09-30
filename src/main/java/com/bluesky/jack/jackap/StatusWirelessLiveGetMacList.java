package com.bluesky.jack.jackap;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectRepository;
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
public class StatusWirelessLiveGetMacList extends BaseNativeProcedure implements
		NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {
		StringValue dataSV = (StringValue) ctx.get("data");
		String data = dataSV.getValue();

		List<String> macs = getWirelessUserMacs(data);

		ObjectRepository repo = virtualMachine.getObjectRepository();
		_Object result = repo.createObject(ObjectType.NORMAL,
				ObjectScope.ExecutionContext);

		for (String mac : macs) {
			// System.out.println(mac);
			_Object macSV = repo.createObject(ObjectType.STRING,
					ObjectScope.ExecutionContext);

			macSV.setValue(mac);

			result.setField(null, macSV, true);
		}

		return result;
	}

	List<String> getWirelessUserMacs(String data) {
		List<String> list = new ArrayList<String>();

		// Status_Wireless.asp
		// Status_Wireless.live.asp (json alike, but not exactly)
		String regex = "\\{(\\w+)::([^\\}]*)\\}";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(data);
		while (matcher.find()) {
			String k = matcher.group(1);
			String v = matcher.group(2);

			// System.out.println(k);
			// System.out.println(v);
			if ("active_wireless".equals(k)) {

				String[] vv = v.split(",");
				for (int i = 0; i < vv.length / 9; i++) {
					String mac = vv[i * 9];
					list.add(mac.substring(1, mac.length() - 1));
				}
			}
		}

		return list;
	}

}
