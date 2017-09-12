package de.nyxcode.mojangapi

import java.util.*

/**
 * The 'Profile' of a player.
 */
/*
 I am not using the 'data' modifier here because the Kotlin compiler generates 'componentN()'
 functions useful for the destructuring deceleration which would be irritating when using this library from Java.
 https://discuss.kotlinlang.org/t/disable-destructuring-declarations/4560
*/
class PlayerProfile(val id: UUID,
                    val name: String,
                    val skinUrl: String?,
                    val capeUrl: String?) {

    val hasSkin get() = skinUrl != null
    val hasCape get() = capeUrl != null

    override fun toString() = "PlayerProfile(id=$id, name='$name', skinUrl=$skinUrl, capeUrl=$capeUrl)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayerProfile

        if (id != other.id) return false
        if (name != other.name) return false
        if (skinUrl != other.skinUrl) return false
        if (capeUrl != other.capeUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (skinUrl?.hashCode() ?: 0)
        result = 31 * result + (capeUrl?.hashCode() ?: 0)
        return result
    }
}