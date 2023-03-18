package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class BoardTest {
    @Test
    fun `바둑판의 마지막 놓은 돌을 출력한다`() {
        //given
        val players = Players(listOf(BlackPlayer(),WhitePlayer()))
        val stones = Stones(listOf(BlackStone(1,5), WhiteStone(1,12)))
        val board = Board(players,stones)

        //when
        val actual = board.getLastColor()

        //then

        assertThat(actual).isEqualTo(Color.WHITE)
    }

    @Test
    fun `바둑판의 마지막 놓은 돌이 없을 경우 IllegalArgumentException이 발생한다`() {
        //given
        val players = Players(listOf(BlackPlayer(),WhitePlayer()))
        val stones = Stones(listOf())
        val board = Board(players,stones)

        //then
        assertThrows<IllegalArgumentException> {
            board.getLastColor()
        }

    }

    @Test
    fun `턴을 흑백 순으로 반복한다`() {

        val players = Players(listOf(BlackPlayer(Color.BLACK), WhitePlayer(Color.WHITE)))
        val stones = Stones()
        val board = Board(players,stones)
        val coordinates = listOf<Coordinate>(
            Coordinate.from(1, 1)!!,
            Coordinate.from(1, 2)!!,
            Coordinate.from(2, 1)!!,
            Coordinate.from(2, 2)!!,
            Coordinate.from(3, 1)!!,
            Coordinate.from(3, 2)!!,
            Coordinate.from(4, 1)!!,
            Coordinate.from(4, 2)!!,
            Coordinate.from(5, 1)!!,
        )
        var coordinateIndex = 0
        board.repeatTurn {
            coordinates[coordinateIndex++]
        }
        assertAll({
            assertThat(stones.value.filterIndexed { index, _ -> index % 2 == 0 }.all { it.color == Color.BLACK }).isTrue
        }, {
            assertThat(
                stones.value.filterIndexed { index, _ -> index % 2 == 1 }.all {
                    it.color == Color.WHITE
                }
            ).isTrue
        })
    }
}
