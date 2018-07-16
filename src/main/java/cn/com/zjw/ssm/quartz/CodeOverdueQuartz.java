package cn.com.zjw.ssm.quartz;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class CodeOverdueQuartz implements Job {

    private static Logger logger = Logger.getLogger(CodeOverdueQuartz.class);

    private static StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();

    private static Scheduler scheduler = null;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        try {
            scheduler = getScheduler();

            // job 唯一标识 test.test-1
            JobKey jobKey = new JobKey("CodeOverdue" , "CodeOverdue");
            JobDetail jobDetail = JobBuilder.newJob(CodeOverdueQuartz.class).withIdentity(jobKey).build();
            SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().
                    withIdentity("CodeOverdue" , "CodeOverdue")
                    // 立刻执行
                    .startNow()
                    // 每隔300秒执行一次(repeatForever()重复执行)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2))
                    .build();
            scheduler.scheduleJob(jobDetail , simpleTrigger);

            // 开始调度
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return;
        }
//        Thread.sleep(1000);
//        // 删除job
//        scheduler.shutdown();
    }

    private static Scheduler getScheduler() throws SchedulerException {
         return stdSchedulerFactory.getScheduler();
    }
}
