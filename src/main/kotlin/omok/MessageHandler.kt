package omok

interface MessageHandler {
    fun onMessageHandle(error: Throwable)
}
