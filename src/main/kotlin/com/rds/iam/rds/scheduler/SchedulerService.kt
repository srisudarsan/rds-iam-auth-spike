package com.rds.iam.rds.scheduler

import org.quartz.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class SchedulerService(private val scheduler: Scheduler) {
    @PostConstruct
    fun scheduleJobs() {
        val cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 * * ? * *")
        val sampleJob = buildJob()
        val sampleTrigger = buildTrigger(cronScheduleBuilder)
        try {
            scheduler.scheduleJob(sampleJob, sampleTrigger)
        } catch (schedulerException: SchedulerException) {
            logger.error("Error while scheduling jobs", schedulerException)
        }
    }

    private fun buildTrigger(cronScheduleBuilder: CronScheduleBuilder?) =
        TriggerBuilder.newTrigger()
            .withIdentity(TriggerKey.triggerKey("SampleTrigger", "SampleTriggerGroup"))
            .withSchedule(cronScheduleBuilder).build()

    private fun buildJob() =
        JobBuilder.newJob(SampleJob::class.java).withIdentity("sampleJob").build()

    companion object {
        private val logger: Logger by lazy { LoggerFactory.getLogger(SchedulerService::class.java) }
    }
}