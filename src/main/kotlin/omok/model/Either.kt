package omok.model

sealed class Either<out L, out R> {
    data class Left<L>(val value: L) : Either<L, Nothing>()

    data class Right<R>(val value: R) : Either<Nothing, R>()

    fun valueOf(
        onLeft: (L) -> Unit,
        onRight: (R) -> Unit,
    ) {
        when (this) {
            is Left -> onLeft(this.value)
            is Right -> onRight(this.value)
        }
    }
}

infix fun <L, R, P> Either<L, R>.flatmap(functor: (R) -> Either<L, P>): Either<L, P> {
    return when (this) {
        is Either.Left -> Either.Left(this.value)
        is Either.Right -> functor(this.value)
    }
}

infix fun <L, R, P> Either<L, R>.map(functor: (R) -> P): Either<L, P> {
    return this.flatmap {
        Either.Right(functor(it))
    }
}
