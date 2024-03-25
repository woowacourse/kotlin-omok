package omok.model.rule.ban

import omok.model.Position
import omok.model.Stone
import omok.model.StonePosition
import omok.model.initBoard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OverlineForbiddenPlaceTest {
    private val forbiddenPlace = OverlineForbiddenPlace()

    @Test
    fun `돌을 두려는 위치로 장목이 되면 놓을 수 없다`() {
        val board =
            initBoard(
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
        val board =
            initBoard(
                StonePosition(Position(0, 1), Stone.BLACK),
                StonePosition(Position(0, 2), Stone.BLACK),
                StonePosition(Position(0, 4), Stone.BLACK),
                StonePosition(Position(0, 5), Stone.BLACK),
            )

        val actual = forbiddenPlace.availablePosition(board, Position(0, 3))
        assertThat(actual).isTrue
    }
}

class OverlineForbiddenPlaceTest2 {
    @Test
    fun `승리 조건이 오목일 때, 돌을 두려는 위치로 육목 이상이 되면 놓을 수 없다`() {
        val forbiddenPlace = OverlineForbiddenPlace2(5)

        val board =
            initBoard(
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
    fun `승리 조건이 사목일 때, 돌을 두려는 위치로 오목 이상이 되면 놓을 수 없다`() {
        val forbiddenPlace = OverlineForbiddenPlace2(4)

        val board =
            initBoard(
                StonePosition(Position(0, 1), Stone.BLACK),
                StonePosition(Position(0, 2), Stone.BLACK),
                StonePosition(Position(0, 4), Stone.BLACK),
                StonePosition(Position(0, 5), Stone.BLACK),
            )

        val actual = forbiddenPlace.availablePosition(board, Position(0, 3))
        assertThat(actual).isFalse
    }
}
