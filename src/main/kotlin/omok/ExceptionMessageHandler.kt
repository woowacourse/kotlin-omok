package omok

interface ExceptionMessageHandler {
    fun onMessageHandle(error: Throwable)
}
