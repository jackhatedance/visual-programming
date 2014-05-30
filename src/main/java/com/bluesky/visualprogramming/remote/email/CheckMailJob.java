package com.bluesky.visualprogramming.remote.email;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class CheckMailJob implements Job {
	public static final String CLIENT = "client";
	public static final String AGENT = "agent";


	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobDataMap data = context.getJobDetail().getJobDataMap();
		_Object client = (_Object) data.get(CLIENT);
		EmailAgent agent = (EmailAgent)data.get(AGENT);

		agent.checkNewMessage();
	}

}
