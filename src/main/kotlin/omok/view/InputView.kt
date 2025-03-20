package omok.view

class InputView {
    fun getPosition(): String {
        print("위치를 입력하세요: ")
        return readlnOrNull() ?: throw IllegalArgumentException()
    }
}
