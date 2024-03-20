package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StonesTest {
    private lateinit var stones: Stones
    private lateinit var blackStone: Stone
    private lateinit var whiteStone: Stone
    private lateinit var coordinate: Coordinate

    @BeforeEach
    fun setUp() {
        stones = Stones()
        val row = Row.from("6")
        val column = Column.from("C")
        coordinate = Coordinate(row, column)
        blackStone = Stone(Color.BLACK, coordinate)
        whiteStone = Stone(Color.WHITE, coordinate)
    }

    @Test
    fun `게임 시작 시 바둑돌은 오목판 위에 없다`() {
        assertThat(stones.blackStones.stones.size).isEqualTo(0)
        assertThat(stones.whiteStones.stones.size).isEqualTo(0)
    }

    @Test
    fun `흰 돌을 착수하면 해당 돌을 가지고있어야 한다`() {
        stones.putStone(whiteStone)
        assertThat(stones.whiteStones.stones).contains(whiteStone)
    }

    @Test
    fun `검은 돌을 착수하면 해당 돌을 가지고있어야 한다`() {
        stones.putStone(blackStone)
        assertThat(stones.blackStones.stones).contains(blackStone)
    }

    @Test
    fun `착수하려는 돌의 위치가 중복되면 예외를 던져야한다`() {
        stones.putStone(blackStone)
        assertThrows<IllegalStateException> {
            stones.putStone(whiteStone)
        }
    }
}
