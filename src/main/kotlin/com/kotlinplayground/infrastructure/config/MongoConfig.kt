package com.kotlinplayground.infrastructure.config

import com.mongodb.TransactionOptions
import com.mongodb.WriteConcern
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import java.util.concurrent.TimeUnit

@Configuration
class MongoConfig {
    @Bean
    fun mongoTransactionManager(dbFactory: MongoDatabaseFactory): MongoTransactionManager {
        val mongoTransactionManager = MongoTransactionManager(dbFactory)
        mongoTransactionManager.setOptions(
            TransactionOptions
                .builder()
                .writeConcern(MONGO_WRITE_CONCERN)
                .build()
        )
        return mongoTransactionManager
    }

    companion object {
        val MONGO_WRITE_CONCERN = WriteConcern.MAJORITY
            .withJournal(false)
            .withWTimeout(30, TimeUnit.SECONDS)
    }
}
