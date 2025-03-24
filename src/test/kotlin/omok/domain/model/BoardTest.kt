package omok.domain.model

import omok.domain.model.position.Stone
import omok.domain.model.stone.StoneType
import omok.positionOneAndOne
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun `바둑돌을 둔다`() {
        val position = positionOneAndOne
        val stone = Stone(position, StoneType.BLACK)
        board = board.addedBoard(stone)
        assertThat(board.stones.hasStone(Stone(position, StoneType.BLACK))).isTrue()
    }
}
