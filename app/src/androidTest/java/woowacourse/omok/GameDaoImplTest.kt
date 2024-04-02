package woowacourse.omok

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.omok.database.DatabaseHelper
import woowacourse.omok.database.GameDaoImpl
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.omokGame.Board.Companion.BOARD_SIZE

@RunWith(AndroidJUnit4::class)
class GameDaoImplTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val dbHelper = DatabaseHelper(context)
    private val gameDao = GameDaoImpl(dbHelper)

    @Test
    fun resetGameTest() {
        val initialBoard = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Stone.EMPTY } }
        initialBoard[0][0] = Stone.BLACK
        gameDao.saveGame(initialBoard)
        gameDao.resetGame()
        gameDao.saveCurrentStone(Stone.WHITE.value)

        val resetBoard = gameDao.loadGame()
        val resetStoneType = gameDao.loadCurrentStone()

        assertTrue(resetBoard.all { row -> row.all { it == Stone.EMPTY } })
        assertEquals(-1, resetStoneType)
    }

    @Test
    fun saveAndLoadCurrentStoneTest() {
        val currentStoneType = Stone.BLACK.ordinal
        gameDao.saveCurrentStone(currentStoneType)
        val loadedStoneType = gameDao.loadCurrentStone()
        assertEquals(currentStoneType, loadedStoneType)
    }

    @Test
    fun saveAndLoadGameTest() {
        val testBoard = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Stone.EMPTY } }
        testBoard[0][0] = Stone.BLACK
        testBoard[1][1] = Stone.WHITE

        gameDao.saveGame(testBoard)

        val loadedBoard = gameDao.loadGame()

        assertEquals(Stone.BLACK, loadedBoard[0][0])
        assertEquals(Stone.WHITE, loadedBoard[1][1])
        for (i in 0 until BOARD_SIZE) {
            for (j in 0 until BOARD_SIZE) {
                if ((i != 0 || j != 0) and (i != 1 || j != 1)) {
                    assertEquals(Stone.EMPTY, loadedBoard[i][j])
                }
            }
        }
    }
}
