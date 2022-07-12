package com.rds.iam.rds.scheduler

import com.rds.iam.rds.entity.State
import com.rds.iam.rds.service.SampleService
import org.quartz.DisallowConcurrentExecution
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@DisallowConcurrentExecution
@Component
class SampleJob(private val service: SampleService) : Job {
    override fun execute(context: JobExecutionContext?) {
        val id = UUID.fromString("72759e38-a0ff-4e38-92cf-cc79f19ee965")
        val state = State(id = id)
        service.createState(state)
        val updatedState = service.findById(id)!!
        logger.info("updated state: ${updatedState}")

    }

    companion object {
        private val logger: org.slf4j.Logger by lazy { LoggerFactory.getLogger(SampleJob::class.java) }
    }
}