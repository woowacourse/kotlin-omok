package omok.model.result

object PutResultUtils {
    private const val DOUBLE_THREE = "3-3"
    private const val DOUBLE_FOUR = "4-4"
    private const val EXCEED_FIVE = "장목"
    private const val FORBIDDEN_MESSAGE = "금수입니다."
    private const val PLACED_STONE_POSITION = "이미 놓여진 자리입니다."
    private const val WINNER_MESSAGE = "%s의 승리입니다. 🏆"

    fun isOccupiedOrForbidden(putResult: PutResult): Boolean {
        return isFailure(putResult) || isRunning(putResult).not()
    }

    fun handleHintMessage(putResult: PutResult): String {
        return when (putResult) {
            PutResult.Running -> ""
            PutResult.Failure -> PLACED_STONE_POSITION
            PutResult.DoubleThree -> "$DOUBLE_THREE $FORBIDDEN_MESSAGE"
            PutResult.DoubleFour -> "$DOUBLE_FOUR $FORBIDDEN_MESSAGE"
            PutResult.ExceedFive -> "$EXCEED_FIVE $FORBIDDEN_MESSAGE"
            PutResult.Omok -> WINNER_MESSAGE
        }
    }

    fun isOmok(putResult: PutResult) = putResult == PutResult.Omok

    fun isRunning(putResult: PutResult) = putResult == PutResult.Running

    private fun isFailure(putResult: PutResult) = putResult == PutResult.Failure
}
