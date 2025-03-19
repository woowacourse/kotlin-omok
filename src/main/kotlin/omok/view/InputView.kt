package omok.view

class InputView {
    fun inputStone(): String {
        print("위치를 입력하세요: ")
        return readlnOrNull()?.trim().toString()
    }
}
