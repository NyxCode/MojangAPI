package de.nyxcode.mojangapi

import java.util.*

class PlayerNotFoundException : RuntimeException {
    constructor() : super()
    constructor(id: UUID) : super(id.toString())
    constructor(message: String) : super(message)
}