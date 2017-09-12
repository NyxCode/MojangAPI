import de.nyxcode.mojangapi.AsyncMojangAPI;
import de.nyxcode.mojangapi.MojangAPI;
import de.nyxcode.mojangapi.PlayerID;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ExampleUsage {
    private MojangAPI synchronousAPI;
    private AsyncMojangAPI asynchronousAPI;

    @Before
    public void initializeAPI() {
        this.synchronousAPI = MojangAPI.create();
        this.asynchronousAPI = AsyncMojangAPI.create();
    }

    @Test
    public void getIdByName() throws ExecutionException, InterruptedException {
        String name = "Notch";
        String logMessage = "UUID of %s: %s";

        // Synchronous
        {
            UUID id = synchronousAPI.idByName(name).getId();
            System.out.println(String.format(logMessage, name, id));
            Assert.assertNotNull(id);
        }

        // Asynchronous
        {
            CompletableFuture<PlayerID> idFuture = asynchronousAPI.idByName(name);
            UUID id = idFuture.get().getId();
            System.out.println(String.format(logMessage, name, id));
            Assert.assertNotNull(id);
        }
    }

    @After
    public void cleanup() {
        synchronousAPI.close();
        asynchronousAPI.close();
    }
}
