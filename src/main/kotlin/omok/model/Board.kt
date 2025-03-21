package omok.model

class Board() {
    val stones: MutableSet<Stone2> = mutableSetOf()

    fun add(stone: Stone2) {
        stones.add(stone)
    }
}
