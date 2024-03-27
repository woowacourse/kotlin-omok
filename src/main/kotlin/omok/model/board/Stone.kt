package omok.model.board

enum class Stone(val value: Int) {
    BLACK(1),
    WHITE(-1),
    EMPTY(0),
}

fun convertStoneArrayToIntArray(stoneArray: Array<Array<Stone>>): Array<Array<Int>> {
    return stoneArray.map { row ->
        row.map { stone ->
            stone.value
        }.toTypedArray()
    }.toTypedArray()
}
