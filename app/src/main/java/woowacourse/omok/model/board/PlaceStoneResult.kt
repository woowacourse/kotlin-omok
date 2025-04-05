package woowacourse.omok.model.board

sealed class PlaceStoneResult {
    data class Success(
        val point: Point,
    ) : PlaceStoneResult()

    data class Omok(
        val point: Point,
    ) : PlaceStoneResult()

    data object ForbiddenMove : PlaceStoneResult()

    data object AlreadyPlaced : PlaceStoneResult()
}
