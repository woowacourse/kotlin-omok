package domain.rule

import domain.Stone
import domain.rule.data.Inclination

class ThreeThreeBlackRule : Rule() {

    override fun checkRule(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean {

        // 참고로, 열린 4가 만들어 질 수 있는 것을 3이라고 한다
        val nextBlackStones = blackStones + nextStone
        var count33 = 0
        Inclination.values().forEach { inclination ->
            if (inclination.directions.any {
                    isOpen4WhenPutBlackStoneWithThisDirection(
                        nextBlackStones,
                        whiteStones,
                        nextStone,
                        it,
                    )
                }
            ) {
                println(true)
                count33++
            }
        }
        return count33 >= 2
    }
}