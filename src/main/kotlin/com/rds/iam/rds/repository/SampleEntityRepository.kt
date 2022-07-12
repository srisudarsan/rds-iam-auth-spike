package com.rds.iam.rds.repository

import com.rds.iam.rds.entity.State
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SampleEntityRepository : CrudRepository<State, UUID> {
}