package omok.model.result

object ResultHandler {
    private const val ALREADY_PLACE_POSITION_MESSAGE = "이미 놓여진 자리입니다."
    private const val DOUBLE_THREE = "(33)"
    private const val DOUBLE_FOUR = "(44)"
    private const val EXCEED_FIVE = "(장목)"
    private const val FORBIDDEN_MOVE_MESSAGE = "금수입니다."

    fun handleResult(putResult: PutResult) =
        when (putResult) {
            PutResult.Running -> {}
            PutResult.Failure -> println(ALREADY_PLACE_POSITION_MESSAGE)
            PutResult.DoubleThree -> println("$FORBIDDEN_MOVE_MESSAGE$DOUBLE_THREE")
            PutResult.DoubleFour -> println("$FORBIDDEN_MOVE_MESSAGE$DOUBLE_FOUR")
            PutResult.ExceedFive -> println("$FORBIDDEN_MOVE_MESSAGE$EXCEED_FIVE")
        }
}
