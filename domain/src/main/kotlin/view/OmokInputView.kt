package view

import domain.point.Point
import domain.stone.StoneColor
import view.mapper.toDomain
import view.model.PointModel

class OmokInputView : InputView {
    override fun onTakeTurn(stoneColor: StoneColor): Point = askPosition()

    override fun onNotPlaceable() {
        askPosition()
    }

    private fun askPosition(): Point {
        print(ASK_POSITION_MESSAGE)
        val colRow = readln()
        if (colRow.length !in POSITION_INPUT_RANGE) {
            println(INVALID_FORMAT_ERROR_MESSAGE)
            return askPosition()
        }

        return PointModel(
            row = colRow.substring(ROW_INPUT_SIZE),
            col = colRow.first().toString(),
        ).toDomain() ?: askPosition()
    }

    companion object {
        private const val ASK_POSITION_MESSAGE = "위치를 입력하세요: "
        private const val INVALID_FORMAT_ERROR_MESSAGE = "포맷에 맞지 않는 입력값입니다."

        private const val ROW_INPUT_SIZE = 1
        private const val MIN_POSITION_INPUT_SIZE = 2
        private const val MAX_POSITION_INPUT_SIZE = 3
        private val POSITION_INPUT_RANGE = MIN_POSITION_INPUT_SIZE..MAX_POSITION_INPUT_SIZE
    }
}
