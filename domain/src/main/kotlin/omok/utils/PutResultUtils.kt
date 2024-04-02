package omok.utils

import omok.model.result.PutResult

object PutResultUtils {
    private const val DOUBLE_THREE = "3-3"
    private const val DOUBLE_FOUR = "4-4"
    private const val EXCEED_FIVE = "장목"
    private const val FORBIDDEN_MESSAGE = "금수입니다.\n"
    private const val PLACED_STONE_POSITION = "이미 놓여진 자리입니다.\n"

    fun isFailure(putResult: PutResult) = putResult == PutResult.Failure

    fun isRunning(putResult: PutResult) = putResult == PutResult.Running

    fun isOccupiedOrForbidden(putResult: PutResult): Boolean {
        if (isFailure(putResult) || isRunning(putResult).not()) {
            showPutResult(putResult)
            return true
        }
        return false
    }

    private fun showPutResult(putResult: PutResult) {
        val hintMessage = handleHintMessage(putResult)
        println(hintMessage)
    }

    private fun handleHintMessage(putResult: PutResult): String {
        return when (putResult) {
            PutResult.Running -> ""
            PutResult.Failure -> PLACED_STONE_POSITION
            PutResult.DoubleThree -> "$DOUBLE_THREE $FORBIDDEN_MESSAGE"
            PutResult.DoubleFour -> "$DOUBLE_FOUR $FORBIDDEN_MESSAGE"
            PutResult.ExceedFive -> "$EXCEED_FIVE $FORBIDDEN_MESSAGE"
        }
    }
}
