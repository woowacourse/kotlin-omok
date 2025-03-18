package omok.domain

class Stones(
    stones: List<Stone> = emptyList(),
) {
    private val _stones = stones.toMutableList()
    val stones = _stones.toList()

    operator fun plus(stone: Stone): Stones = Stones(_stones + stone)
}
