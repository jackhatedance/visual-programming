package com.bluesky.visualprogramming.timer;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class MyJob implements Job {
	public static final String CLIENT = "client";


	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobDataMap data = context.getJobDetail().getJobDataMap();
		_Object client = (_Object) data.get(CLIENT);

		notify1(client);
	}

	private void notify1(_Object timer) {

		Message msg = new Message(null, timer, "action", null,
				ParameterStyle.ByName, null, MessageType.SyncRequest);

		VirtualMachine.getInstance().getPostService().sendMessage(msg);

	}
}
