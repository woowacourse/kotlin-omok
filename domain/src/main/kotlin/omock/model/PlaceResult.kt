package omock.model

import omock.model.board.OmokBoard

sealed interface PlaceResult

data class Success(val newBoard: OmokBoard) : PlaceResult

sealed interface Failure : PlaceResult

data object InvalidGameOver : Failure

data object InvalidOutOfBound : Failure

data object InvalidDuplicatedPlaced : Failure

data object InvalidThreeThreeRule : Failure

data object InvalidFourFourRule : Failure

data object InvalidOverLineRule : Failure

fun PlaceResult.isFailure(): Boolean = this is Failure

fun PlaceResult.isSuccess(): Boolean = this is Success

inline fun PlaceResult.onSuccess(block: (OmokBoard) -> Unit): PlaceResult {
    if (this is Success) block(newBoard)
    return this
}

inline fun PlaceResult.onFailure(block: (Failure) -> Unit): PlaceResult {
    if (this is Failure) block(this)
    return this
}
