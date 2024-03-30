package woowacourse.omok.model.state

sealed interface PlaceResult {
    data object Omok : PlaceResult

    data object Done : PlaceResult

    data object Duplicate : PlaceResult

    data object Block : PlaceResult
}
