package omok

class Board {
    private val map: MutableMap<Stone, Boolean> = mutableMapOf()
    fun place(stone: Stone) {
        require(
            stone.x in 1..15 &&
                stone.y in 1..15,
        ) { "보드 밖에 돌을 두었습니다." }
        map[stone] = true
    }

    fun contains(stone: Stone): Boolean {
        return map.contains(stone)
    }
}
