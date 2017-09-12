package de.nyxcode.mojangapi

import java.util.*

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