package de.nyxcode.mojangapi;

import de.nyxcode.mojangapi.internal.CachedAsyncMojangAPI;
import de.nyxcode.mojangapi.internal.DefaultAsyncMojangAPI;

import java.io.Closeable;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * An interface to the Mojang API.
 * All operations are performed asynchronously and will return instantly without blocking the calling thread.
 */
public interface AsyncMojangAPI extends Closeable {
    /**
     * Creates a new {@link AsyncMojangAPI}
     *
     * @return the instance
     */
    static AsyncMojangAPI create() {
        return new DefaultAsyncMojangAPI();
    }

    /**
     * Creates a new {@link AsyncMojangAPI} which caches all lookups.
     *
     * @param api               the underlying {@link AsyncMojangAPI}
     * @param useWeakReferences if {@code true}, the garbage collector will be able to collect and release
     *                          cached data to free memory.
     * @param maximumSize       The maximum size of the underlying cache.
     * @param expiresAfter      The time until cached data will be invalidated.
     * @param unit              the {@link TimeUnit} of expiresAfter
     * @return the instance
     */
    static AsyncMojangAPI createCached(AsyncMojangAPI api,
                                       boolean useWeakReferences,
                                       int maximumSize,
                                       long expiresAfter,
                                       TimeUnit unit) {
        return new CachedAsyncMojangAPI(api, useWeakReferences, maximumSize, expiresAfter, unit);
    }

    /**
     * Creates a new {@link AsyncMojangAPI} which caches all lookups.
     * see {@link AsyncMojangAPI#createCached(AsyncMojangAPI, boolean, int, long, TimeUnit)}
     */
    static AsyncMojangAPI createCached(AsyncMojangAPI api, long expiresAfter, TimeUnit unit) {
        return createCached(api, false, 5000, expiresAfter, unit);
    }

    /**
     * Creates a new {@link AsyncMojangAPI} which caches all lookups.
     * see {@link AsyncMojangAPI#createCached(AsyncMojangAPI, boolean, int, long, TimeUnit)}
     */
    static AsyncMojangAPI createCached(AsyncMojangAPI api, boolean useWeakReferences, int maximumSize) {
        return createCached(api, useWeakReferences, maximumSize, 1, TimeUnit.HOURS);
    }

    /**
     * Creates a new {@link AsyncMojangAPI} which caches all lookups.
     * see {@link AsyncMojangAPI#createCached(AsyncMojangAPI, boolean, int, long, TimeUnit)}
     */
    static AsyncMojangAPI createCached(AsyncMojangAPI api, boolean useWeakReferences) {
        return createCached(api, useWeakReferences, 5000);
    }

    /**
     * Creates a new {@link AsyncMojangAPI} which caches all lookups.
     * see {@link AsyncMojangAPI#createCached(AsyncMojangAPI, boolean, int, long, TimeUnit)}
     */
    static AsyncMojangAPI createCached(AsyncMojangAPI api, int maximumSize) {
        return createCached(api, false, maximumSize);
    }

    /**
     * Looks up the current name of the player with the given {@link UUID}.
     *
     * @param id the players ID
     * @return a {@link CompletableFuture} which will be completed when the lookup is completed.
     * The returned future may complete exceptionally with a
     * {@link PlayerNotFoundException} if no player with the given ID was found,
     * {@link TooManyRequestsException} if the request limit of the API endpoint is reached or an
     * {@link InvalidResponseException} if the API endpoints response was invalid.
     */
    CompletableFuture<PlayerName> nameById(UUID id);

    /**
     * Looks up all names the player with the given {@link UUID} ever had.
     *
     * @param id the players ID
     * @return a {@link CompletableFuture} which will be completed when the lookup is completed.
     * The returned future may complete exceptionally with a
     * {@link PlayerNotFoundException} if no player with the given ID was found,
     * {@link TooManyRequestsException} if the request limit of the API endpoint is reached or an
     * {@link InvalidResponseException} if the API endpoints response was invalid.
     */
    CompletableFuture<PlayerName[]> namesById(UUID id);

    /**
     * Looks up the ID of the player with the given name.
     *
     * @param name the players name
     * @return a {@link CompletableFuture} which will be completed when the lookup is completed.
     * The returned future may complete exceptionally with a
     * {@link PlayerNotFoundException} if no player with the given name was found,
     * {@link TooManyRequestsException} if the request limit of the API endpoint is reached or an
     * {@link InvalidResponseException} if the API endpoints response was invalid.
     */
    CompletableFuture<PlayerID> idByName(String name);

    /**
     * Looks up the profile of the player with the given {@link UUID}
     *
     * @param id the players ID
     * @return a {@link CompletableFuture} which will be completed when the lookup is completed.
     * The returned future may complete exceptionally with a
     * {@link PlayerNotFoundException} if no player with the given ID was found,
     * {@link TooManyRequestsException} if the request limit of the API endpoint is reached or an
     * {@link InvalidResponseException} if the API endpoints response was invalid.
     */
    CompletableFuture<PlayerProfile> profileById(UUID id);

    /**
     * Closes this instance.
     * After invoking this method, behaviour of the object is undefined.
     */
    @Override
    void close();
}
