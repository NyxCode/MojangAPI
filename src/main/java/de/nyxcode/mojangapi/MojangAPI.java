package de.nyxcode.mojangapi;

import de.nyxcode.mojangapi.internal.CachedMojangAPI;
import de.nyxcode.mojangapi.internal.DefaultMojangAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public interface MojangAPI extends Closeable {
    static MojangAPI create() {
        return new DefaultMojangAPI();
    }

    static MojangAPI createCached(MojangAPI api,
                                  boolean useWeakReferences,
                                  int maximumSize,
                                  long expiresAfter,
                                  TimeUnit unit) {
        return new CachedMojangAPI(api, useWeakReferences, maximumSize, expiresAfter, unit);
    }

    static MojangAPI createCached(MojangAPI api,
                                  boolean useWeakReferences,
                                  int maximumSize) {
        return createCached(api, useWeakReferences, maximumSize, 1, TimeUnit.HOURS);
    }

    static MojangAPI createCached(MojangAPI api,
                                  boolean useWeakReferences) {
        return createCached(api, useWeakReferences, 5000);
    }

    static MojangAPI createCached(MojangAPI api,
                                  int maximumSize) {
        return createCached(api, false, maximumSize);
    }

    static MojangAPI createCached(MojangAPI api) {
        return createCached(api, 5000);
    }

    @NotNull
    PlayerName nameById(UUID uuid) throws PlayerNotFoundException, InvalidResponseException;

    @Nullable
    PlayerName nameByIdOrNull(UUID uuid) throws InvalidResponseException;

    @NotNull
    PlayerName[] namesById(UUID uuid) throws PlayerNotFoundException, TooManyRequestsException, InvalidResponseException;

    @Nullable
    PlayerName[] namesByIdOrNull(UUID uuid) throws TooManyRequestsException, InvalidResponseException;

    @NotNull
    PlayerID idByName(String name) throws PlayerNotFoundException, TooManyRequestsException, InvalidResponseException;

    @Nullable
    PlayerID idByNameOrNull(String name) throws TooManyRequestsException, InvalidResponseException;

    @NotNull
    PlayerProfile profileById(UUID id) throws PlayerNotFoundException, TooManyRequestsException, InvalidResponseException;

    @Nullable
    PlayerProfile profileByIdOrNull(UUID id) throws TooManyRequestsException, InvalidResponseException;

    @Override
    void close();
}
