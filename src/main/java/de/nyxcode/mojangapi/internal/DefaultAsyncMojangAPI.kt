package de.nyxcode.mojangapi.internal

import de.nyxcode.mojangapi.*
import org.apache.http.HttpResponse
import org.apache.http.HttpStatus.SC_NO_CONTENT
import org.apache.http.HttpStatus.SC_OK
import org.apache.http.client.methods.HttpGet
import org.apache.http.concurrent.FutureCallback
import org.apache.http.impl.nio.client.HttpAsyncClients
import java.lang.Exception
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.reflect.KClass

internal class DefaultAsyncMojangAPI : AsyncMojangAPI {
    private var closed = true
    private val client = HttpAsyncClients.createMinimal()

    init {
        client.start()
        closed = false
    }

    private class MojangAPICallback<T : Any>(
            private val type: KClass<T>,
            private val future: CompletableFuture<T>) : FutureCallback<HttpResponse> {
        override fun completed(response: HttpResponse) {
            try {
                val status = response.statusLine.statusCode
                when (status) {
                    SC_OK -> response.entity.content.bufferedReader().use { input ->
                        val names = gson.fromJson(input, type.java)
                        future.complete(names)
                    }
                    SC_NO_CONTENT -> failed(PlayerNotFoundException())
                    SC_TOO_MANY_REQUESTS -> failed(TooManyRequestsException())
                    else -> failed(InvalidResponseException(status))
                }
            } catch (e: Exception) {
                failed(e)
            }
        }

        override fun failed(ex: Exception) {
            future.completeExceptionally(ex)
        }

        override fun cancelled() {
            future.cancel(true)
        }
    }

    override fun nameById(uuid: UUID): CompletableFuture<PlayerName> = namesById(uuid).thenApply { it.first() }

    override fun namesById(uuid: UUID): CompletableFuture<Array<PlayerName>> {
        check(!closed)
        val future = CompletableFuture<Array<PlayerName>>()
        val id = uuid.toString().replace("-", "")
        val callback = MojangAPICallback(Array<PlayerName>::class, future)
        client.execute(HttpGet(namesByIdURL.format(id)), callback)
        return future
    }

    override fun idByName(name: String): CompletableFuture<PlayerID> {
        check(!closed)
        val future = CompletableFuture<PlayerID>()
        val callback = MojangAPICallback(PlayerID::class, future)
        client.execute(HttpGet(idByNameURL.format(name)), callback)
        return future
    }

    override fun profileById(uuid: UUID): CompletableFuture<PlayerProfile> {
        check(!closed)
        val future = CompletableFuture<PlayerProfile>()
        val callback = MojangAPICallback(PlayerProfile::class, future)
        client.execute(HttpGet(profileByIdURL.format(uuid)), callback)
        return future
    }

    override fun close() {
        check(!closed)
        closed = true
        client.close()
    }
}