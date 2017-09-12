package de.nyxcode.mojangapi

import java.util.*

/**
 * The ID and name of a player.
 */
/*
 This class contains also a name because Mojangs API seems to respond
 with both ID and name and it would be a waste just ignoring it.

 I am not using the 'data' modifier here because the Kotlin compiler generates 'componentN()'
 functions useful for the destructuring deceleration which would be irritating when using this library from Java.
 https://discuss.kotlinlang.org/t/disable-destructuring-declarations/4560
 */
class PlayerID(val id: UUID, val name: String) {
    override fun toString() = "PlayerID(id=$id, name='$name')"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayerID

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}