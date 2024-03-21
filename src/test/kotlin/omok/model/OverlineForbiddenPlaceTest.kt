package omok.model

import omok.model.rule.ban.OverlineForbiddenPlace
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OverlineForbiddenPlaceTest {
    private val forbiddenPlace = OverlineForbiddenPlace()

    @Test
    fun `돌을 두려는 위치로 장목이 되면 놓을 수 없다`() {
        val board = initBoard(
            StonePosition(Position(0, 0), Stone.WHITE),
            StonePosition(Position(0, 7), Stone.WHITE),
            StonePosition(Position(0, 1), Stone.BLACK),
            StonePosition(Position(0, 2), Stone.BLACK),
            StonePosition(Position(0, 4), Stone.BLACK),
            StonePosition(Position(0, 5), Stone.BLACK),
            StonePosition(Position(0, 6), Stone.BLACK),
        )

        val actual = forbiddenPlace.availablePosition(board, Position(0, 3))
        assertThat(actual).isFalse
    }


    @Test
    fun `돌을 두려는 위치로 오목이 되면 놓을 수 있다`() {
        val board = initBoard(
            StonePosition(Position(0, 1), Stone.BLACK),
            StonePosition(Position(0, 2), Stone.BLACK),
            StonePosition(Position(0, 4), Stone.BLACK),
            StonePosition(Position(0, 5), Stone.BLACK),
        )

        val actual = forbiddenPlace.availablePosition(board, Position(0, 3))
        assertThat(actual).isTrue
    }
}
