package omok.model

sealed class PlaceStoneError(val errorMessage: String) {
    class StoneOutOfBoard() : PlaceStoneError(STONE_OUT_OF_BOARD_MESSAGE)

    class StoneAlreadyExists() : PlaceStoneError(STONE_ALREADY_EXISTS_MESSAGE)

    companion object {
        private const val STONE_OUT_OF_BOARD_MESSAGE = "돌을 보드 밖에 두었습니다."
        private const val STONE_ALREADY_EXISTS_MESSAGE = "그 위치에는 이미 돌이 있습니다."
    }
}

sealed class Either<out L, out R> {
    data class Left<L>(val value: L) : Either<L, Nothing>()

    data class Right<R>(val value: R) : Either<Nothing, R>()
}

/*
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
 */
