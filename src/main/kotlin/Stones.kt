class Stones(private val stones: List<Stone>) {
    constructor(vararg stones: Stone) : this(stones.toList())

    init {
        require(stones.distinct().size == stones.size) { DUPLICATED_ERROR_MESSAGE }
    }

    companion object {
        private const val DUPLICATED_ERROR_MESSAGE = "중복되는 위치의 오목알을 가질 수 없습니다."
    }
}
