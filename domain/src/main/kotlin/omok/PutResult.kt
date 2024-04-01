package omok

sealed interface PutResult {
    data object Running : PutResult

    data object Failure : PutResult

    data object DoubleThree : PutResult

    data object DoubleFour : PutResult

    data object ExceedFive : PutResult
}
