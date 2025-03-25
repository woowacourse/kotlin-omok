package omok.domain.model

import omok.domain.model.position.Column
import omok.domain.model.position.Position
import omok.domain.model.position.Row
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
        // Given
        val position = Position(Column.from('A'), Row(1))
        val omokStone = OmokStone(position, StoneType.BLACK)

        // When
        board = board.placeStone(omokStone)

        // Then
        assertThat(board.stones.last()).isEqualTo(omokStone)
    }

    @Test
    fun `바둑돌이 이미 존재하는 위치는 둘 수 없다`() {
        // Given
        val position = Position(Column.from('A'), Row(1))
        val omokStone = OmokStone(position, StoneType.BLACK)
        board = board.placeStone(omokStone)

        // Then
        assertThatThrownBy { board.placeStone(omokStone) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("바둑돌이 있는 곳에는 둘 수 없습니다.")
    }
}
