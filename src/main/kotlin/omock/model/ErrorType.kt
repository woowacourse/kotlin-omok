package omock.model

sealed class ErrorType : Throwable() {
    class RanjuRuleException : Throwable()

    class AlreadyExistStone : Throwable()
}
