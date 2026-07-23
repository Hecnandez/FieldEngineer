package com.fieldengineer

import com.fieldengineer.models.SyncPullResponse
import com.fieldengineer.models.SyncPushRequest
import com.fieldengineer.models.SyncPushResponse
import com.fieldengineer.models.WorkOrderDto
import com.fieldengineer.schema.WorkOrdersTable
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greater
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.upsert

@Serializable
data class HealthStatus(val status: String, val database: String)

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val dbHost = System.getenv("DB_HOST") ?: "postgres-db"
    val dbPort = System.getenv("DB_PORT") ?: "5432"
    val dbName = System.getenv("DB_NAME") ?: "field_engineer_db"
    val dbUser = System.getenv("DB_USER") ?: "postgres_user"
    val dbPassword = System.getenv("DB_PASSWORD") ?: "super_secret_password"

    Database.connect(
        url = "jdbc:postgresql://$dbHost:$dbPort/$dbName",
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    routing {
        get("/health") {
            call.respond(HealthStatus(status = "UP", database = "CONNECTED"))
        }

        get("/api/v1/sync/pull") {
            val sinceTimestamp = call.request.queryParameters["last_sync"]?.toLongOrNull() ?: 0L

            val modifiedWorkOrders = dbQuery {
                WorkOrdersTable
                    .select { WorkOrdersTable.updatedAt greater sinceTimestamp }
                    .map { row ->
                        WorkOrderDto(
                            id = row[WorkOrdersTable.id],
                            assetId = row[WorkOrdersTable.assetId],
                            assignedTo = row[WorkOrdersTable.assignedTo],
                            title = row[WorkOrdersTable.title],
                            description = row[WorkOrdersTable.description],
                            status = row[WorkOrdersTable.status],
                            technicianNotes = row[WorkOrdersTable.technicianNotes],
                            updatedAt = row[WorkOrdersTable.updatedAt],
                            isDeleted = row[WorkOrdersTable.isDeleted]
                        )
                    }
            }

            call.respond(
                SyncPullResponse(
                    serverTimestamp = System.currentTimeMillis(),
                    workOrders = modifiedWorkOrders
                )
            )
        }

        post("/api/v1/sync/push") {
            val request = call.receive<SyncPushRequest>()
            val syncedIds = mutableListOf<String>()

            dbQuery {
                request.workOrders.forEach { dto ->
                    WorkOrdersTable.upsert {
                        it[id] = dto.id
                        it[assetId] = dto.assetId
                        it[assignedTo] = dto.assignedTo
                        it[title] = dto.title
                        it[description] = dto.description
                        it[status] = dto.status
                        it[technicianNotes] = dto.technicianNotes
                        it[updatedAt] = dto.updatedAt
                        it[isDeleted] = dto.isDeleted
                    }
                    syncedIds.add(dto.id)
                }
            }

            call.respond(SyncPushResponse(syncedWorkOrderIds = syncedIds))
        }
    }
}

suspend fun <T> dbQuery(block: () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }
