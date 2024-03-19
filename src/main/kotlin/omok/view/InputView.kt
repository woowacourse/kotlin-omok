package omok.view

import omok.model.Position

object InputView {
    private const val INPUT_POSITION_MESSAGE = "위치를 입력하세요: "

    fun readPosition(): Position {
        print(INPUT_POSITION_MESSAGE)
        return readln().run {
            Position.from(this)
        }
    }
}
