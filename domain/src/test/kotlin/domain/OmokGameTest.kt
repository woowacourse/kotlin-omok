package domain

import domain.event.*
import domain.stone.Stone
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class OmokGameTest {

    @Test
    fun `오목 게임은 흑이 먼저 시작한다`() {
        val omokGame = OmokGame()

        assertThat(omokGame.getTurn()).isEqualTo(Team.BLACK)
    }

    @Test
    fun `흑턴일 때 돌을 두면 백턴이 된다`() {
        val omokGame = OmokGame()

        omokGame.place(Stone('A', 1))

        assertThat(omokGame.getTurn()).isEqualTo(Team.WHITE)
    }

    @Test
    fun `백턴일 때 돌을 두면 흑턴이 된다`() {
        val omokGame = OmokGame().apply { place(Stone('B', 1)) }

        omokGame.place(Stone('A', 1))

        assertThat(omokGame.getTurn()).isEqualTo(Team.BLACK)
    }

    @Test
    fun `만약 게임이 끝난 상태일 때 돌을 두면 에러가 발생한다`() {
        val omokGame = OmokGame().apply {
            place(Stone('A', 1))
            place(Stone('B', 1))
            place(Stone('A', 2))
            place(Stone('B', 2))
            place(Stone('A', 3))
            place(Stone('B', 3))
            place(Stone('A', 4))
            place(Stone('B', 4))
            place(Stone('A', 5))
        }

        assertThatIllegalArgumentException().isThrownBy { omokGame.place(Stone('B', 5)) }
            .withMessage("돌을 둘 수 없습니다.")
    }

    @Test
    fun `오목 게임이 생성되면 게임 이벤트 리스너가 게임 생성 알림을 받는다`() {
        val gameEventManager = GameEventManager()
        val listener = TestGameEventListener()
        gameEventManager.add(listener)

        OmokGame(gameEventManager)

        assertThat(listener.notifiedGameCreateEvent).isTrue
    }

    @Test
    fun `돌을 뒀을 때 게임이 끝나지 않았다면 돌 두기 이벤트 리스너가 알림을 받는다`() {
        val gameEventManager = GameEventManager()
        val listener = TestGameEventListener()
        gameEventManager.add(listener)
        val omokGame = OmokGame(gameEventManager)

        omokGame.place(Stone('A', 1))

        assertThat(listener.notifiedStonePlaceEvent).isTrue
    }

    @Test
    fun `돌을 뒀을 때 게임이 끝났다면 종료 이벤트 리스너가 알림을 받는다`() {
        val gameEventManager = GameEventManager()
        val listener = TestGameEventListener()
        gameEventManager.add(listener)
        val omokGame = OmokGame(gameEventManager).apply {
            place(Stone('A', 1))
            place(Stone('B', 1))
            place(Stone('A', 2))
            place(Stone('B', 2))
            place(Stone('A', 3))
            place(Stone('B', 3))
            place(Stone('A', 4))
            place(Stone('B', 4))
        }

        omokGame.place(Stone('A', 5))

        assertThat(listener.notifiedGameFinishEvent).isTrue
    }

    private class TestGameEventListener : GameEventListener {
        var notifiedGameCreateEvent = false
        var notifiedStonePlaceEvent = false
        var notifiedGameFinishEvent = false
        override fun onGameCreated(omokGame: OmokGame) {
            notifiedGameCreateEvent = true
        }

        override fun onStonePlaced(omokGame: OmokGame) {
            notifiedStonePlaceEvent = true
        }

        override fun onGameFinished(omokGame: OmokGame) {
            notifiedGameFinishEvent = true
        }
    }
}
