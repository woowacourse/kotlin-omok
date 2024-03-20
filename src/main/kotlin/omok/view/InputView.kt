package omok.view

object InputView {
    private const val MESSAGE_INPUT_COORDINATE = "위치를 입력하세요: "

    fun inputStoneCoordinate(): String {
        println(MESSAGE_INPUT_COORDINATE)
        val input = readlnOrNull().orEmpty()
        return input
    }
}
