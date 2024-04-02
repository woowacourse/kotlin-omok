package omok.model.result

import omok.model.stone.BlackStone.value
import omok.model.stone.GoStone

object ResultHandler {
    private const val ALREADY_PLACE_POSITION_MESSAGE = "이미 놓여진 자리입니다."
    private const val DOUBLE_THREE = "(33)"
    private const val DOUBLE_FOUR = "(44)"
    private const val EXCEED_FIVE = "(장목)"
    private const val OMOK_MESSAGE = "오목입니다."
    private const val WINNING_MESSAGE = "승리"
    private const val FORBIDDEN_MOVE_MESSAGE = "금수입니다."

    fun handleResult(
        putResult: PutResult,
        stone: GoStone,
    ) = when (putResult) {
        PutResult.Running -> ""
        PutResult.Failure -> ALREADY_PLACE_POSITION_MESSAGE
        PutResult.DoubleThree -> "$FORBIDDEN_MOVE_MESSAGE$DOUBLE_THREE"
        PutResult.DoubleFour -> "$FORBIDDEN_MOVE_MESSAGE$DOUBLE_FOUR"
        PutResult.ExceedFive -> "$FORBIDDEN_MOVE_MESSAGE$EXCEED_FIVE"
        PutResult.OMOK -> "$OMOK_MESSAGE ${stone.value()} $WINNING_MESSAGE"
    }

    fun isRunningResult(resultState: PutResult) = resultState == PutResult.Running

    fun isOmok(resultState: PutResult) = resultState == PutResult.OMOK

    fun isAvailableResult(resultState: PutResult) = isRunningResult(resultState) || isOmok(resultState)
}
