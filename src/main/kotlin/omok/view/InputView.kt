package omok.view

object InputView {
    fun readStonePosition(
        currentColor: String,
        previousStonePosition: String?,
    ): String {
        print(
            "${currentColor}의 차례입니다. ${if (previousStonePosition == null) "" else "(마지막 돌의 위치: $previousStonePosition)"}\n" +
                "위치를 입력하세요: ",
        )
        return readlnOrNull() ?: ""
    }
}
