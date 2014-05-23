package com.bluesky.visualprogramming.timer;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;

import com.bluesky.visualprogramming.core.AbstractObjectRepositoryListener;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.SystemField;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.Service;

public class TimerService implements Service {
	static Logger logger = Logger.getLogger(TimerService.class);

	ObjectRepository repo;

	Scheduler scheduler;

	Map<_Object, JobKey> jobs = new HashMap<_Object, JobKey>();

	private volatile boolean running;

	public TimerService(ObjectRepository repo) {

		this.repo = repo;

		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

		try {
			scheduler = schedFact.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
			logger.error("create quartz scheduler failed.");
		}

		repo.addListener(new AbstractObjectRepositoryListener() {

			@Override
			public void afterLoadFromFile(_Object obj) {
				StringValue type = (StringValue) obj.getSystemTopChild(SystemField.Type);
				if (type != null && type.getValue().equals("timer")) {

					BooleanValue enabledObj = (BooleanValue) obj
							.getChild("enabled");

					if (enabledObj != null) {
						boolean enabled = enabledObj.getBooleanValue();
						if (enabled) {
							subscribe(obj);

							logger.info("create timer: " + obj.getName());
						}
					}

				}

			}

		});
	}

	@Override
	public void init() {
		running = false;
	}

	@Override
	public void start() {

		try {
			scheduler.start();
			running = true;
			logger.info("timer service started");
		} catch (SchedulerException e) {

			e.printStackTrace();
			logger.info("timer service start failed");
			throw new RuntimeException(e);

		}

	}

	@Override
	public void pause() {

		try {
			scheduler.pauseAll();
			running = false;
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void resume() {
		try {
			scheduler.resumeAll();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}

		running = true;

	}

	@Override
	public void stop() {
		running = false;

		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void destroy() {

	}

	public synchronized void subscribe(final _Object client) {

		StringValue modeObj = (StringValue) client.getChild("mode");

		if (modeObj == null)
 {
			logger.error("mode is null");
			return;
		}
		String modeStr = modeObj.getValue();
		TimerMode mode = null;
		try {
			mode = TimerMode.valueOf(modeStr.toUpperCase());
		} catch (Exception e) {
			logger.error("mode is invalid:" + modeStr);
			return;
		}

		JobDetail job = JobBuilder.newJob(MyJob.class).build();

		job.getJobDataMap().put(MyJob.CLIENT, client);

		Trigger trigger = null;
		if (mode == TimerMode.SIMPLE) {
			IntegerValue intervalNumber = (IntegerValue) client
					.getChild("interval");
			int interval = (int) intervalNumber.getIntValue();
			trigger = newTrigger().withSchedule(
					SimpleScheduleBuilder.simpleSchedule()
							.withIntervalInSeconds(interval).repeatForever())

			.build();

		} else if (mode == TimerMode.CRON) {
			StringValue cron = (StringValue) client.getChild("cron");
			trigger = newTrigger().withSchedule(cronSchedule(cron.getValue()))
					.forJob(job).build();
		}

		if (trigger != null) {
			try {
				scheduler.scheduleJob(job, trigger);

				jobs.put(client, job.getKey());
			} catch (SchedulerException e) {

				throw new RuntimeException(e);
			}
		}
	}

	public synchronized void unsubscribe(_Object client) {

		JobKey jobKey = jobs.get(client);
		try {
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

}
