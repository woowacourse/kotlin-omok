package error

interface ErrorHandler {
    fun log(exception: OmokError)
}
