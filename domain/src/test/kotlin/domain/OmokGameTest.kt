package domain

import domain.event.*
import domain.stone.Stone
import domain.stone.Team
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class OmokGameTest {

    @Test
    fun `오목 게임은 흑이 먼저 시작한다`() {
        val omokGame = OmokGame()

        assertThat(omokGame.turn).isEqualTo(Team.BLACK)
    }

    @Test
    fun `흑턴일 때 돌을 두면 백턴이 된다`() {
        val omokGame = OmokGame()

        omokGame.place(Stone('A', 1))

        assertThat(omokGame.turn).isEqualTo(Team.WHITE)
    }

    @Test
    fun `백턴일 때 돌을 두면 흑턴이 된다`() {
        val omokGame = OmokGame().apply { place(Stone('B', 1)) }

        omokGame.place(Stone('A', 1))

        assertThat(omokGame.turn).isEqualTo(Team.BLACK)
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
    fun `오목 게임이 생성되면 시작 이벤트 리스너가 알림을 받는다`() {
        val startEventManager = StartEventManager()
        val listener = TestStartEventListener()
        startEventManager.add(listener)

        OmokGame(startEventManager)

        assertThat(listener.notified).isTrue
    }

    private class TestStartEventListener : StartEventListener {
        var notified = false

        override fun notifyStartEventHasOccurred(omokGame: OmokGame) {
            notified = true
        }
    }

    @Test
    fun `돌을 뒀을 때 게임이 끝나지 않았다면 돌 두기 이벤트 리스너가 알림을 받는다`() {
        val placeStoneEventManager = PlaceStoneEventManager()
        val listener = TestPlaceStoneEventListener()
        placeStoneEventManager.add(listener)
        val omokGame = OmokGame(placeStoneEventManager = placeStoneEventManager)

        omokGame.place(Stone('A', 1))

        assertThat(listener.notified).isTrue
    }

    private class TestPlaceStoneEventListener : PlaceStoneEventListener {
        var notified = false

        override fun notifyPlaceStoneEventHasOccurred(omokGame: OmokGame) {
            notified = true
        }
    }

    @Test
    fun `돌을 뒀을 때 게임이 끝났다면 종료 이벤트 리스너가 알림을 받는다`() {
        val finishEventManager = FinishEventManager()
        val listener = TestFinishEventListener()
        finishEventManager.add(listener)
        val omokGame = OmokGame(finishEventManager = finishEventManager).apply {
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

        assertThat(listener.notified).isTrue
    }

    private class TestFinishEventListener : FinishEventListener {
        var notified = false

        override fun notifyFinishEventHasOccurred(omokGame: OmokGame) {
            notified = true
        }
    }
}