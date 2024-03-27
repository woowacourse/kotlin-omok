package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {
    private fun createBoard(
        board: Board,
        stones: List<Stone>,
    ) {
        stones.forEach { stone ->
            board.putStone(stone)
        }
    }

    @Test
    fun `현재 오목 게임이 진행중인 상태라면 true를 반환한다`() {
        // given
        val initBoard = Board(Stones())
        val omokGame = OmokGame(initBoard)

        // when
        val expected = omokGame.isRunning()

        // then
        assertThat(expected).isTrue()
    }

    @Test
    fun `플레이어가 착수 할 수 있는 위치에 돌을 놓았다면, 돌의 상태는 보드에 놓일 수 있는 상태다`() {
        // given
        val initBoard = Board(Stones())
        val omokGame = OmokGame(initBoard)
        val player = Player(Color.BLACK)

        // when
        val stoneState = omokGame.playTurn(player, COORDINATE_F4)

        // then
        assertThat(stoneState).isEqualTo(StoneState.SuccessfulPlaced)
    }

    /*
    9 [ ] [ ] [ ] [ ] [ ]
    8 [ ] [ ] [ ] [ ] [ ]
    7 [ ] [●] [ ] [ ] [ ]
    6 [ ] [ ] [ ] [ ] [ ]
    5 [ ] [ ] [ ] [ ] [ ]
      E   F
  */
    @Test
    fun `플레이어가 이미 돌이 놓여진 자리에 돌을 놓았다면, 돌의 상태는 보드에 놓일 수 없는 상태다`() {
        // given
        val initBoard = Board(Stones())
        val omokGame = OmokGame(initBoard)
        createBoard(initBoard, listOf(Stone(Color.BLACK, COORDINATE_F7)))
        val player = Player(Color.WHITE)

        // when
        val stoneState = omokGame.playTurn(player, COORDINATE_F7)

        // then
        assertThat(stoneState).isEqualTo(StoneState.FailedPlaced("이미 돌이 착수된 위치입니다."))
    }

    /*
      9 [ ] [●] [ ] [ ] [ ]
      8 [ ] [●] [ ] [ ] [ ]
      7 [ ] [●] [ ] [ ] [ ]
      6 [ ] [●] [ ] [ ] [ ]
      5 [ ] [●] [ ] [ ] [ ]
         E   F
     */
    @Test
    fun `플레이어턴에 오목을 만들었다면 게임의 상태가 실행중이 아니어야 한다`() {
        // given
        val initBoard = Board(Stones())
        val omokGame = OmokGame(initBoard)
        val player = Player(Color.BLACK)
        createBoard(
            initBoard,
            listOf(
                Stone(Color.BLACK, COORDINATE_F5),
                Stone(Color.BLACK, COORDINATE_F6),
                Stone(Color.BLACK, COORDINATE_F7),
                Stone(Color.BLACK, COORDINATE_F8),
            ),
        )

        // when
        omokGame.playTurn(player, COORDINATE_F9)
        val expected = omokGame.isRunning()

        // then
        assertThat(expected).isFalse()
    }
}
