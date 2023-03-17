package view

import domain.position.Position
import domain.position.Position.Companion.POSITION_RANGE
import domain.stone.StoneColor
import view.model.BoardModel

class OmokInputView : InputView {
    override fun onTakeTurn(stoneColor: StoneColor): Position = askPosition()

    override fun onNotPlaceable() {
        reAskPosition(CANT_PLACE_STONE_ERROR_MESSAGE)
    }

    private fun askPosition(): Position {
        print(ASK_POSITION_MESSAGE)
        val input = readln()
        if (input.length !in POSITION_INPUT_RANGE)
            return reAskPosition(INVALID_FORMAT_ERROR_MESSAGE)

        val col = BoardModel.getColInt(input.first().toString())
        val row = input.substring(ROW_INPUT_SIZE).toIntOrNull()
        if (row == null || row !in POSITION_RANGE || col !in POSITION_RANGE)
            return reAskPosition(INVALID_FORMAT_ERROR_MESSAGE)
        return Position(row, col)
    }

    private fun reAskPosition(message: String): Position {
        println(message)
        return askPosition()
    }

    companion object {
        private const val ASK_POSITION_MESSAGE = "위치를 입력하세요: "
        private const val INVALID_FORMAT_ERROR_MESSAGE = "포맷에 맞지 않는 입력값입니다."
        private const val CANT_PLACE_STONE_ERROR_MESSAGE = "해당 위치에는 오목알을 둘 수 없습니다."
        private const val MIN_POSITION_INPUT_SIZE = 2
        private const val MAX_POSITION_INPUT_SIZE = 3
        private val POSITION_INPUT_RANGE = MIN_POSITION_INPUT_SIZE..MAX_POSITION_INPUT_SIZE
        private const val ROW_INPUT_SIZE = 1
    }
}
