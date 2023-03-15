package domain.stone

import domain.rule.OmokRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokRuleTest {

    @Test
    fun `5개이상의 돌이 가로로 이어져 있으면 오목조건을 충족한다`() {
        val stones: Stones = Stones()
        stones.add(Stone(StonePosition.from(2, 1)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(3, 1)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(4, 1)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(5, 1)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(6, 1)!!, StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(6, 1)!!, StoneType.BLACK)

        val board: List<List<StoneType>> = stones.matrixBoard()

        val omokRule: OmokRule = OmokRule()
        assertThat(omokRule.isOmokCondition(board, stone)).isTrue
    }

    @Test
    fun `5개이상의 돌이 세로로 이어져 있으면 오목조건을 충족한다`() {
        val stones: Stones = Stones()
        stones.add(Stone(StonePosition.from(1, 1)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(1, 2)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(1, 3)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(1, 4)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(1, 5)!!, StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(1, 5)!!, StoneType.BLACK)

        val board: List<List<StoneType>> = stones.matrixBoard()

        val omokRule: OmokRule = OmokRule()
        assertThat(omokRule.isOmokCondition(board, stone)).isTrue
    }

    @Test
    fun `5개이상의 돌이 오른쪽 대각선으로 이어져 있으면 오목조건을 충족한다`() {
        val stones: Stones = Stones()
        stones.add(Stone(StonePosition.from(1, 1)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(2, 2)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(3, 3)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(4, 4)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(5, 5)!!, StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(5, 5)!!, StoneType.BLACK)

        val board: List<List<StoneType>> = stones.matrixBoard()

        val omokRule: OmokRule = OmokRule()
        assertThat(omokRule.isOmokCondition(board, stone)).isTrue
    }

    @Test
    fun `5개이상의 돌이 왼쪽 대각선으로 이어져 있으면 오목조건을 충족한다`() {
        val stones: Stones = Stones()
        stones.add(Stone(StonePosition.from(1, 5)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(2, 4)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(3, 3)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(4, 2)!!, StoneType.BLACK))
        stones.add(Stone(StonePosition.from(5, 1)!!, StoneType.BLACK))

        val stone: Stone = Stone(StonePosition.from(5, 1)!!, StoneType.BLACK)

        val board: List<List<StoneType>> = stones.matrixBoard()

        val omokRule: OmokRule = OmokRule()
        assertThat(omokRule.isOmokCondition(board, stone)).isTrue
    }
}
