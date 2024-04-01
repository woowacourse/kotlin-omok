package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StonesTest {
    @Test
    fun `게임 시작 시 바둑돌은 오목판 위에 없다`() {
        // given
        val stones = Stones()

        // then
        assertThat(stones.stones.size).isEqualTo(0)
    }

    @Test
    fun `흰 돌을 착수하면 해당 돌을 가지고있어야 한다`() {
        // given
        val stones = Stones()
        val whiteStone = Stone(white, COORDINATE_F5)

        // when
        stones.putStone(whiteStone)

        // then
        assertThat(stones.stones).contains(whiteStone)
    }

    @Test
    fun `검은 돌을 착수하면 해당 돌을 가지고있어야 한다`() {
        // given
        val stones = Stones()
        val blackStone = Stone(black, COORDINATE_F5)

        // when
        stones.putStone(blackStone)

        // then
        assertThat(stones.stones).contains(blackStone)
    }

    /*
      9 [ ] [●] [ ] [ ] [ ]
      8 [ ] [●] [ ] [ ] [ ]
      7 [ ] [○] [ ] [ ] [ ]
      6 [ ] [●] [ ] [ ] [ ]
      5 [ ] [●] [ ] [ ] [ ]
         E   F
     */
    @Test
    fun `한 방향으로 연속된 같은 색상의 돌 개수를 반환한다`() {
        // given
        val initStones =
            listOf(
                Stone(Color.BLACK, COORDINATE_F5),
                Stone(Color.BLACK, COORDINATE_F6),
                Stone(Color.WHITE, COORDINATE_F7),
                Stone(Color.BLACK, COORDINATE_F9),
            )
        val stones = Stones(initStones)

        // when
        val actual =
            stones.countSameColorStoneInDirection(
                Stone(Color.BLACK, COORDINATE_F8),
                Direction.TOP,
            )

        // then
        assertThat(actual).isEqualTo(2)
    }

    /*
      9 [ ] [●] [ ] [ ] [ ]
      8 [ ] [●] [ ] [ ] [ ]
      7 [ ] [●] [ ] [ ] [ ]
      6 [ ] [●] [ ] [ ] [ ]
      5 [ ] [●] [ ] [ ] [ ]
         E   F
    * */
    @Test
    fun `같은 색상의 연속된 돌이 5개 이상이라면 true를 반환한다`() {
        // given
        val initStones =
            listOf(
                Stone(Color.BLACK, COORDINATE_F5),
                Stone(Color.BLACK, COORDINATE_F6),
                Stone(Color.BLACK, COORDINATE_F7),
                Stone(Color.BLACK, COORDINATE_F9),
            )
        val stones = Stones(initStones)

        // when
        val actual = stones.findOmok(Stone(Color.BLACK, COORDINATE_F8))

        // then
        assertThat(actual).isTrue()
    }

    @Test
    fun `마지막에 착수된 돌의 좌표를 가져온다`() {
        // given
        val stones = Stones()
        val stone = Stone(black, COORDINATE_F5)
        stones.putStone(stone)

        // when
        val lastCoordinate = stones.getLastStoneCoordinate()

        // then
        assertThat(lastCoordinate).isEqualTo(COORDINATE_F5)
    }

    @Test
    fun `마지막에 착수된 돌이 없다면 null을 반환한다`() {
        // given
        val stones = Stones()

        // when
        val lastCoordinate = stones.getLastStoneCoordinate()

        // then
        assertThat(lastCoordinate).isNull()
    }
}
