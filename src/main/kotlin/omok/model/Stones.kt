package omok.model

class Stones(
    private val stones: List<Stone>,
) {
    init {
        require(stones.map { it.position() }.toSet().size == stones.size) {
            "돌들의 좌표는 중복될 수 없습니다."
        }
    }

    fun positions() = stones.map { stone -> stone.position() }
}
