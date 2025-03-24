package omok.view

class InputView {
    fun position(): Pair<Int, Int> {
        print("위치를 입력하세요: ")
        val input = readln()
        val column = requireNotNull(input[0].digitToIntOrNull())
        val row = requireNotNull(input.substring(1).toIntOrNull())
        return Pair(column, row)
    }
}
