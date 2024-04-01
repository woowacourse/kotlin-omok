package omok.utils

import omok.PutResult

object HandlingPutResultUtils {
    private const val DOUBLE_THREE = "3-3"
    private const val DOUBLE_FOUR = "4-4"
    private const val EXCEED_FIVE = "장목"
    private const val FORBIDDEN_MESSAGE = "금수입니다.\n"
    private const val PLACED_STONE_POSITION = "이미 놓여진 자리입니다.\n"

    fun displayPutStatus(putResult: PutResult) {
        when (putResult) {
            PutResult.Running -> {}
            PutResult.Failure -> println(PLACED_STONE_POSITION)
            PutResult.DoubleThree -> println("$DOUBLE_THREE $FORBIDDEN_MESSAGE")
            PutResult.DoubleFour -> println("$DOUBLE_FOUR $FORBIDDEN_MESSAGE")
            PutResult.ExceedFive -> println("$EXCEED_FIVE $FORBIDDEN_MESSAGE")
        }
    }
}
