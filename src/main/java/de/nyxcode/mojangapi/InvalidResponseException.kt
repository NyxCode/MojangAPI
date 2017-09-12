package de.nyxcode.mojangapi

class InvalidResponseException : RuntimeException {
    constructor(statusCode: Int) : super("status code: $statusCode")
    constructor(message: String) : super(message)
}