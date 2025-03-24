package omok.view

class InputView {
    fun position(): Pair<Int, Int> {
        print("위치를 입력하세요: ")
        val input = readln()
        val column = convertColumn(input[0])
        val row = requireNotNull(input.substring(1).toIntOrNull())
        return Pair(column, row)
    }

    private fun convertColumn(column: Char): Int {
        val alphaBets = ('A'..'Z')
        require(column in alphaBets) { "알파뱃 대문자를 입력 해주세요" }
        return ('A'..'Z').indexOf(column) + 1
    }
}
