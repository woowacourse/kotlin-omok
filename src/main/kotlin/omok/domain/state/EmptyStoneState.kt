package omok.domain.state

object EmptyStoneState : StoneState {
    override val korean: String = ""

    override fun next(): StoneState {
        throw IllegalStateException(ERROR_EMPTY_NO_NEXT)
    }

    private const val ERROR_EMPTY_NO_NEXT = "빈 돌은 다음 순서가 없습니다."
}
