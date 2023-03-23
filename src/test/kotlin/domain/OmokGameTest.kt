package domain

import domain.event.*
import domain.stone.Point
import domain.stone.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {

    @Test
    fun `좌표가 오목판의 크기를 벗어난다면 좌표는 오목판에 포함되어 있지 않은 것이다`() {
        val point = Point(OmokGame.BOARD_SIZE + 1, 1)

        assertThat(OmokGame.boardContains(point)).isFalse
    }

    @Test
    fun `좌표가 오목판의 크기를 벗어나지 않는다면 좌표는 오목판에 포함되어 있는 것이다`() {
        val point = Point(OmokGame.BOARD_SIZE, OmokGame.BOARD_SIZE)

        assertThat(OmokGame.boardContains(point)).isTrue
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
        val omokGame = OmokGame(finishEventManager = finishEventManager)
        omokGame.place(Stone('A', 1))
        omokGame.place(Stone('B', 1))
        omokGame.place(Stone('A', 2))
        omokGame.place(Stone('B', 2))
        omokGame.place(Stone('A', 3))
        omokGame.place(Stone('B', 3))
        omokGame.place(Stone('A', 4))
        omokGame.place(Stone('B', 4))

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
