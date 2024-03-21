package omok.view

import omok.model.Stone

object InputView {
    fun readPlayerInput(
        stone: Stone,
        currentPosition: String,
    ): String {
        print(
            "${stone}의 차례입니다. ${if (currentPosition == "") "" else "(마지막 돌의 위치: $currentPosition)"}\n" +
                "위치를 입력하세요: ",
        )
        return readln()
    }
}
