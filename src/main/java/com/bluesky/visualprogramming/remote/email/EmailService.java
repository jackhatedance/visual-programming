package com.bluesky.visualprogramming.remote.email;

import static org.quartz.TriggerBuilder.newTrigger;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.remote.AbstractProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolType;
import com.bluesky.visualprogramming.utils.Config;

public class EmailService extends AbstractProtocolService implements
		ProtocolService {
	static Logger logger = Logger.getLogger(EmailService.class);

	public static final String CHECK_ENABLED = "check.enabled";
	public static final String CHECK_INTERVAL = "check.interval";
	public static final String CHECK_FOLDER = "check.folder";
	public static final String USER = "user";
	public static final String PASSWORD = "password";

	Map<String, EmailAgent> agents = new HashMap<String, EmailAgent>();

	/**
	 * to check mail
	 */
	Scheduler scheduler;

	public EmailService() {

		supportedTypes = new ProtocolType[] { ProtocolType.EMAIL };

		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

		try {
			scheduler = schedFact.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.error("create quartz scheduler failed.");
		}

	}

	@Override
	public void register(ProtocolType protocol, String address, _Object obj,
			Config config) {

		if (addressObjectMap.containsKey(address))
			throw new RuntimeException("already registered:" + address);

		addressObjectMap.put(address, obj);

		EmailAgent agent = new EmailAgent(address, obj, config);
		agents.put(address, agent);

		boolean checkEnabled = config.getBoolean(CHECK_ENABLED, false);
		if (checkEnabled) {
			JobDetail job = JobBuilder.newJob(CheckMailJob.class).build();

			job.getJobDataMap().put(CheckMailJob.CLIENT, obj);

			int interval = config.getInteger(CHECK_INTERVAL, 900);

			Trigger trigger = newTrigger().withSchedule(
					SimpleScheduleBuilder.simpleSchedule()
							.withIntervalInSeconds(interval).repeatForever())
					.build();
			try {
				scheduler.scheduleJob(job, trigger);
			} catch (SchedulerException e) {

				throw new RuntimeException(e);
			}
		}

	}

	@Override
	public void send(String receiverAddress, Message message) {

		String senderAddress = getPrimaryAddress(message.sender);
		EmailAgent agent = agents.get(senderAddress);

		try {
			agent.send(receiverAddress, message);
			String response = "sent successfully.";
			StringValue responseBody = getObjectFactory()
					.createString(response);

			replySuccessfulInternalRequest(message, responseBody);

		} catch (Exception e) {
			replyFailureInternalRequest(message, e);
		}

	}

}
