import de.nyxcode.mojangapi.PlayerName
import de.nyxcode.mojangapi.PlayerProfile
import de.nyxcode.mojangapi.internal.gson
import de.nyxcode.mojangapi.internal.toUUID
import org.junit.Assert
import org.junit.Test

class TestJsonParsing {
    @Test
    fun testPlayerNameParsing() {
        val name1Json = """{"name": "Gold"}"""
        val name2Json = """{"name": "Diamond", "changedToAt": -1}"""
        val name1 = PlayerName("Gold", null)
        val name2 = PlayerName("Diamond", -1)
        Assert.assertEquals(gson.fromJson(name1Json, PlayerName::class.java), name1)
        Assert.assertEquals(gson.fromJson(name2Json, PlayerName::class.java), name2)
    }

    @Test
    fun testPlayerNameArrayParsing() {
        val namesJson = """[{"name": "Gold"}, {"name": "Diamond", "changedToAt": -1}]"""
        val names = arrayOf(PlayerName("Gold", null), PlayerName("Diamond", -1))
        Assert.assertArrayEquals(gson.fromJson(namesJson, Array<PlayerName>::class.java), names)
    }

    @Test
    fun testPlayerProfileParsing() {
        val json = """{"id":"4566e69fc90748ee8d71d7ba5aa00d20","name":"Thinkofdeath","properties":[{"name":"textures","value":"eyJ0aW1lc3RhbXAiOjE1MDQ3MTE5MzQyMTIsInByb2ZpbGVJZCI6IjQ1NjZlNjlmYzkwNzQ4ZWU4ZDcxZDdiYTVhYTAwZDIwIiwicHJvZmlsZU5hbWUiOiJUaGlua29mZGVhdGgiLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTNlODFiOWUxOWFiMWVmMTdhOTBjMGFhNGUxMDg1ZmMxM2NkNDdjZWQ1YTdhMWE0OTI4MDNiMzU2MWU0YTE1YiJ9LCJDQVBFIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjJiOWM1ZWE3NjNjODZmYzVjYWVhMzNkODJiMGZhNjVhN2MyMjhmZDMyMWJhNTQ3NjZlYTk1YTNkMGI5NzkzIn19fQ=="}]}"""
        val profile = PlayerProfile(
                id = "4566e69fc90748ee8d71d7ba5aa00d20".toUUID(),
                name = "Thinkofdeath",
                skinUrl = "http://textures.minecraft.net/texture/13e81b9e19ab1ef17a90c0aa4e1085fc13cd47ced5a7a1a492803b3561e4a15b",
                capeUrl = "http://textures.minecraft.net/texture/22b9c5ea763c86fc5caea33d82b0fa65a7c228fd321ba54766ea95a3d0b9793")
        Assert.assertEquals(gson.fromJson(json, PlayerProfile::class.java), profile)
    }
}