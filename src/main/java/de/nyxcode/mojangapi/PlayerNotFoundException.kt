package de.nyxcode.mojangapi

import java.util.*

/**
 * Thrown if a player could not be found.
 */
class PlayerNotFoundException : RuntimeException {
    constructor() : super()
    constructor(id: UUID) : super(id.toString())
    constructor(message: String) : super(message)
}