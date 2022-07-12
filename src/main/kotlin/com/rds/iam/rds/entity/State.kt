package com.rds.iam.rds.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.Id

@Entity(name = "state")
@EntityListeners(
    AuditingEntityListener::class
)
class State(
    @Id
    private val id: UUID = UUID.randomUUID(),

    @Column
    @CreationTimestamp
    private val createdAt: Timestamp = Timestamp(0L),

    @Column
    @UpdateTimestamp
    private val updatedAt: Timestamp = Timestamp(0L)
) {
    override fun toString(): String {
        return "State(id=$id, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}