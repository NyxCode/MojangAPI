package de.nyxcode.mojangapi

class PlayerName(val name: String, val changedToAt: Long?) {
    override fun toString(): String {
        return "PlayerName(name='$name', changedToAt=$changedToAt)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayerName

        if (name != other.name) return false
        if (changedToAt != other.changedToAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (changedToAt?.hashCode() ?: 0)
        return result
    }
}