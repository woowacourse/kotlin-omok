import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ForbiddenStoneTest {

    @Test
    fun `test`() {
        val board = ForbiddenStone(
            PlayingBoard(
                placedStones = listOf(
                    Stone(8, 9),
                    Stone(8, 8),
                    Stone(8, 7),
                    Stone(7, 8),
                )
            ),
            y = 8,
            x = 9
        )

        assertThat(board.hasConcave33(1)).isTrue
    }
}
