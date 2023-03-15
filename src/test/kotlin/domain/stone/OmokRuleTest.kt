package domain.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokRuleTest {

    @Test
    fun `5개이상의 돌이 이어져 있으면 오목조건을 충족한다`() {
        val stones: Stones = Stones()
        stones.add(Stone(StonePosition.from(2, 1)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(3, 1)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(4, 1)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(5, 1)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(6, 1)!!, StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(6, 1)!!, StoneType.BLACK)

        val board: List<List<Stone?>> = stones.matrixBoard()

        val omokRule: OmokRule = OmokRule()
        assertThat(omokRule.isOmokCondition(board, stone)).isTrue
    }
}
