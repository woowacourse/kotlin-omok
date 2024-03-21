package omok.model

import omok.model.Stones.Companion.TOP
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StonesTest {
    private lateinit var stones: Stones

    @BeforeEach
    fun setUp() {
        stones = Stones()
    }

    @Test
    fun `게임 시작 시 바둑돌은 오목판 위에 없다`() {
        assertThat(stones.stones.size).isEqualTo(0)
    }

    @Test
    fun `흰 돌을 착수하면 해당 돌을 가지고있어야 한다`() {
        stones.putStone(Stone(white, COORDINATE_F5))
        assertThat(stones.stones).contains(Stone(white, COORDINATE_F5))
    }

    @Test
    fun `검은 돌을 착수하면 해당 돌을 가지고있어야 한다`() {
        stones.putStone(Stone(black, COORDINATE_F5))
        assertThat(stones.stones).contains(Stone(black, COORDINATE_F5))
    }

    @Test
    fun `착수하려는 돌의 위치가 중복되면 예외를 던져야한다`() {
        stones.putStone(Stone(white, COORDINATE_F5))
        assertThrows<IllegalStateException> {
            stones.putStone(Stone(black, COORDINATE_F5))
        }
    }

    @Test
    fun `한 방향으로 연속된 같은 색상의 돌 개수를 반환한다`() {
        stones.putStone(Stone(Color.BLACK, COORDINATE_F5))
        stones.putStone(Stone(Color.BLACK, COORDINATE_F6))
        stones.putStone(Stone(Color.WHITE, COORDINATE_F7))
        stones.putStone(Stone(Color.BLACK, COORDINATE_F9))

        val actual =
            stones.countSameColorStoneInDirection(
                Stone(Color.BLACK, COORDINATE_F8),
                TOP,
            )
        assertThat(actual).isEqualTo(2)
    }

    @Test
    fun `같은 색상의 연속된 돌이 5개 이상이라면 true를 반환한다`() {
        stones.putStone(Stone(Color.BLACK, COORDINATE_F5))
        stones.putStone(Stone(Color.BLACK, COORDINATE_F6))
        stones.putStone(Stone(Color.BLACK, COORDINATE_F7))
        stones.putStone(Stone(Color.BLACK, COORDINATE_F9))

        val actual = stones.findOmok(Stone(Color.BLACK, COORDINATE_F8))

        assertThat(actual).isTrue()
    }

    @Test
    fun `마지막에 착수된 돌의 좌표를 가져온다`() {
        stones.putStone(Stone(black, COORDINATE_F5))
        val lastCoordinate = stones.getLastStoneCoordinate()

        assertThat(lastCoordinate).isEqualTo(COORDINATE_F5)
    }

    @Test
    fun `마지막에 착수된 돌이 없다면 null을 반환한다`() {
        assertThat(stones.getLastStoneCoordinate()).isNull()
    }
}
