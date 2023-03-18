package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
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
}
