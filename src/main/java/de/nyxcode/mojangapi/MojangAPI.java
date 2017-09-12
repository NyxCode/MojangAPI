package de.nyxcode.mojangapi;

import de.nyxcode.mojangapi.internal.CachedMojangAPI;
import de.nyxcode.mojangapi.internal.DefaultMojangAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * An interface to the Mojang API.
 * All operations are preformed synchronously and will block the calling thread until completion.
 */
public interface MojangAPI extends Closeable {

    /**
     * Creates a new {@link MojangAPI}
     *
     * @return the instance
     */
    static MojangAPI create() {
        return new DefaultMojangAPI();
    }

    /**
     * Creates a new {@link MojangAPI} which caches all lookups.
     *
     * @param api               the underlying {@link MojangAPI}
     * @param useWeakReferences if {@code true}, the garbage collector will be able to collect and release
     *                          cached data to free memory.
     * @param maximumSize       The maximum size of the underlying cache.
     * @param expiresAfter      The time until cached data will be invalidated.
     * @param unit              the {@link TimeUnit} of expiresAfter
     * @return the instance
     */
    static MojangAPI createCached(MojangAPI api,
                                  boolean useWeakReferences,
                                  int maximumSize,
                                  long expiresAfter,
                                  TimeUnit unit) {
        return new CachedMojangAPI(api, useWeakReferences, maximumSize, expiresAfter, unit);
    }

    /**
     * Creates a new {@link MojangAPI} which caches all lookups.
     * see {@link MojangAPI#createCached(MojangAPI, boolean, int, long, TimeUnit)}
     */
    static MojangAPI createCached(MojangAPI api,
                                  boolean useWeakReferences,
                                  int maximumSize) {
        return createCached(api, useWeakReferences, maximumSize, 1, TimeUnit.HOURS);
    }

    /**
     * Creates a new {@link MojangAPI} which caches all lookups.
     * see {@link MojangAPI#createCached(MojangAPI, boolean, int, long, TimeUnit)}
     */
    static MojangAPI createCached(MojangAPI api,
                                  boolean useWeakReferences) {
        return createCached(api, useWeakReferences, 5000);
    }

    /**
     * Creates a new {@link MojangAPI} which caches all lookups.
     * see {@link MojangAPI#createCached(MojangAPI, boolean, int, long, TimeUnit)}
     */
    static MojangAPI createCached(MojangAPI api,
                                  int maximumSize) {
        return createCached(api, false, maximumSize);
    }

    /**
     * Creates a new {@link MojangAPI} which caches all lookups.
     * see {@link MojangAPI#createCached(MojangAPI, boolean, int, long, TimeUnit)}
     */
    static MojangAPI createCached(MojangAPI api) {
        return createCached(api, 5000);
    }


    /**
     * Looks up the current name of the player identified by the given {@link UUID}.
     * @param uuid the players unique ID
     * @return the players name
     * @throws PlayerNotFoundException If no player with the given ID was found
     * @throws InvalidResponseException If the API endpoint responded with an invalid response
     * @throws TooManyRequestsException If the API endpoints rate limit is reached
     */
    @NotNull
    PlayerName nameById(UUID uuid) throws PlayerNotFoundException, TooManyRequestsException, InvalidResponseException;

    /**
     * Looks up the current name of the player identified by the given {@link UUID}.
     * @param uuid the players unique ID
     * @return the players name, or {@code null} if the player was not found
     * @throws InvalidResponseException If the API endpoint responded with an invalid response
     * @throws TooManyRequestsException If the API endpoints rate limit is reached
     */
    @Nullable
    PlayerName nameByIdOrNull(UUID uuid) throws InvalidResponseException, TooManyRequestsException;

    /**
     * Returns all names the player with the given {@link UUID} ever had
     * @param uuid the players unique ID
     * @return the players names
     * @throws PlayerNotFoundException If no player with the given ID was found
     * @throws InvalidResponseException If the API endpoint responded with an invalid response
     * @throws TooManyRequestsException If the API endpoints rate limit is reached
     */
    @NotNull
    PlayerName[] namesById(UUID uuid) throws PlayerNotFoundException, TooManyRequestsException, InvalidResponseException;

    /**
     * Returns all names the player with the given {@link UUID} ever had
     * @param uuid the players unique ID
     * @return the players names, or {@code null} if the player was not found
     * @throws InvalidResponseException If the API endpoint responded with an invalid response
     * @throws TooManyRequestsException If the API endpoints rate limit is reached
     */
    @Nullable
    PlayerName[] namesByIdOrNull(UUID uuid) throws TooManyRequestsException, InvalidResponseException;

    /**
     * Returns the unique ID the player with the given name
     * @param name the players name
     * @return the players unique ID
     * @throws PlayerNotFoundException If no player with the given name was found
     * @throws InvalidResponseException If the API endpoint responded with an invalid response
     * @throws TooManyRequestsException If the API endpoints rate limit is reached
     */
    @NotNull
    PlayerID idByName(String name) throws PlayerNotFoundException, TooManyRequestsException, InvalidResponseException;

    /**
     * Returns the unique ID the player with the given name
     * @param name the players name
     * @return the players unique ID, or {@code null} if the player was not found
     * @throws InvalidResponseException If the API endpoint responded with an invalid response
     * @throws TooManyRequestsException If the API endpoints rate limit is reached
     */
    @Nullable
    PlayerID idByNameOrNull(String name) throws TooManyRequestsException, InvalidResponseException;

    /**
     * Returns the profile of the player with the given {@link UUID}
     * @param id the players unique ID
     * @return the players profile
     * @throws PlayerNotFoundException If no player with the given id was found
     * @throws InvalidResponseException If the API endpoint responded with an invalid response
     * @throws TooManyRequestsException If the API endpoints rate limit is reached
     */
    @NotNull
    PlayerProfile profileById(UUID id) throws PlayerNotFoundException, TooManyRequestsException, InvalidResponseException;

    /**
     * Returns the profile of the player with the given {@link UUID}
     * @param id the players unique ID
     * @return the players profile, or {@code null} if the player was not found
     * @throws InvalidResponseException If the API endpoint responded with an invalid response
     * @throws TooManyRequestsException If the API endpoints rate limit is reached
     */
    @Nullable
    PlayerProfile profileByIdOrNull(UUID id) throws TooManyRequestsException, InvalidResponseException;

    /**
     * Closes this instance.
     * After invoking this method, behaviour of the object is undefined.
     */
    @Override
    void close();
}
