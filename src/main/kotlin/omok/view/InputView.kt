package omok.view

import omok.domain.place.Empty
import omok.domain.place.Place
import omok.view.ext.position
import omok.view.ext.toLabel

object InputView {
    fun readStoneWithLastPosition(lastPosition: Place): String? {
        print(MESSAGE_PLAYER_TURN.format(lastPosition.opponent().toLabel()))
        if (lastPosition !is Empty) {
            println(MESSAGE_LAST_POSITION.format(lastPosition.position()))
        }
        print(MESSAGE_INPUT_POSITION)
        return readlnOrNull()?.trim()
    }

    fun printOnNull() {
        println(MESSAGE_EMPTY_INPUT)
    }

    private const val MESSAGE_PLAYER_TURN = "%s의 차례입니다."
    private const val MESSAGE_INPUT_POSITION = "위치를 입력하세요: "
    private const val MESSAGE_LAST_POSITION = "(마지막 돌의 위치: %s)"
    private const val MESSAGE_EMPTY_INPUT = "빈 값을 입력하셨습니다. 다시 입력해주세요."
}
