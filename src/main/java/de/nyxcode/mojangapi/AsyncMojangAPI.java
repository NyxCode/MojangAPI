package de.nyxcode.mojangapi;

import de.nyxcode.mojangapi.internal.CachedAsyncMojangAPI;
import de.nyxcode.mojangapi.internal.DefaultAsyncMojangAPI;

import java.io.Closeable;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public interface AsyncMojangAPI extends Closeable {
    static AsyncMojangAPI create() {
        return new DefaultAsyncMojangAPI();
    }

    static AsyncMojangAPI createCached(AsyncMojangAPI api,
                                       boolean useWeakReferences,
                                       int maximumSize,
                                       long expiresAfter,
                                       TimeUnit unit) {
        return new CachedAsyncMojangAPI(api, useWeakReferences, maximumSize, expiresAfter, unit);
    }

    static AsyncMojangAPI createCached(AsyncMojangAPI api,
                                       long expiresAfter,
                                       TimeUnit unit) {
        return createCached(api, false, 5000, expiresAfter, unit);
    }

    static AsyncMojangAPI createCached(AsyncMojangAPI api,
                                       boolean useWeakReferences,
                                       int maximumSize) {
        return createCached(api, useWeakReferences, maximumSize, 1, TimeUnit.HOURS);
    }

    static AsyncMojangAPI createCached(AsyncMojangAPI api,
                                       boolean useWeakReferences) {
        return createCached(api, useWeakReferences, 5000);
    }

    static AsyncMojangAPI createCached(AsyncMojangAPI api,
                                       int maximumSize) {
        return createCached(api, false, maximumSize);
    }

    CompletableFuture<PlayerName> nameById(UUID id);

    CompletableFuture<PlayerName[]> namesById(UUID id);

    CompletableFuture<PlayerID> idByName(String name);

    CompletableFuture<PlayerProfile> profileById(UUID id);

    @Override
    void close();
}
