package domain.board

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

/*

 */
interface Board {
    // TODO: 이 상태는, 이미 interface 의 구현체로 finished 가 존재한다는 것을 의미하는 것 같은데요.
    // 인터페이스에서 구체적인 구현체의 내용이 들어가는 것은 적절하지 않아보입니다.

    // TODO: 유효성을 검증하는 함수는 optional 한 제안 (너가 필요하면 하고, 아니면 말어) 아닐까요?
    // 인터페이스에 꼭 들어가야할 함수 일지 고민해보면 좋을 것 같아요.
    val isFinished: Boolean
    val winningColor: Color
    fun isPossiblePut(position: Position): Boolean
    fun getLatestStone(): Stone?
    fun getStones(): List<Stone>
    fun putStone(stone: Stone): Board
}
