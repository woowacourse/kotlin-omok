package woowacourse.omok.model.rule

sealed class CoordinateResult {
    data class Success(val row: Int, val col: Int) : CoordinateResult()
    data class Failure(val reason: CoordinateError) : CoordinateResult()
}