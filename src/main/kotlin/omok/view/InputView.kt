package omok.view

class InputView {
    fun inputStone(): String {
        print(INPUT_STONE_POSITION)
        return readlnOrNull()?.trim().toString()
    }

    companion object {
        private const val INPUT_STONE_POSITION = "위치를 입력하세요: "
    }
}
