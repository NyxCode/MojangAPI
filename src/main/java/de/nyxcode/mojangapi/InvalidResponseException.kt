package de.nyxcode.mojangapi

/**
 * Thrown if an API endpoint has responded with an invalid response.
 */
class InvalidResponseException : RuntimeException {
    constructor(statusCode: Int) : super("status code: $statusCode")
    constructor(message: String) : super(message)
}