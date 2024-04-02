package omok.model.result

sealed interface PutStoneResult {
    data object Running : PutStoneResult

    data object Failure : PutStoneResult

    data object DoubleThree : PutStoneResult

    data object DoubleFour : PutStoneResult

    data object ExceedFive : PutStoneResult
}
