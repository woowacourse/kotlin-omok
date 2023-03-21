package omok.domain.state

object EmptyStoneState : StoneState {
    private const val ERROR_EMPTY_NO_NEXT = "빈 돌은 다음 순서가 없습니다."
    override fun next(): StoneState {
        error(ERROR_EMPTY_NO_NEXT)
    }
}
