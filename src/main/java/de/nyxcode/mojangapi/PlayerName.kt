package de.nyxcode.mojangapi

/**
 * The name of a player and the data on which it was chosen.
 * If this is the first name of the player, [changedToAt] will be null.
 */
/*
 I am not using the 'data' modifier here because the Kotlin compiler generates 'componentN()'
 functions useful for the destructuring deceleration which would be irritating when using this library from Java.
 https://discuss.kotlinlang.org/t/disable-destructuring-declarations/4560
*/
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