package com.rds.iam.rds.service

import com.rds.iam.rds.entity.State
import com.rds.iam.rds.repository.SampleEntityRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class SampleService(private val repository: SampleEntityRepository) {
    fun createState(state: State): State {
        return repository.save(state)
    }

    fun findById(id: UUID): State? {
        return repository.findByIdOrNull(id)
    }
}