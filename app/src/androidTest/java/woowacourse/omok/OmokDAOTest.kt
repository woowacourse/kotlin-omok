package woowacourse.omok

import woowacourse.omok.data.OmokDAO

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OmokDAOTest {

    private lateinit var dao: OmokDAO
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        context.deleteDatabase("omok.db")
        dao = OmokDAO(context)
    }

    @After
    fun clearDown() {
        dao.clearBoard()
    }

    @Test
    @DisplayName("돌을 놓으면 테이블에 추가한다")
    fun saveAndLoadStone() {
        dao.saveStone(1, 2, "BLACK")
        dao.saveStone(2, 3, "WHITE")

        val stones = dao.loadAllStones()

        assertEquals(Triple(1, 2, "BLACK"), stones[0])
        assertEquals(Triple(2, 3, "WHITE"), stones[1])
    }

    @Test
    @DisplayName("턴이 바뀌면 테이블에 추가한다")
    fun saveAndLoadTurn() {
        dao.saveTurn("WHITE")
        val turn = dao.loadTurn()

        assertNotNull(turn)
        assertEquals("WHITE", turn)
    }

    @Test
    @DisplayName("게임이 끝나면 db를 삭제한다")
    fun clearBoard_deletesStonesAndTurn() {
        dao.saveStone(1, 1, "BLACK")
        dao.saveTurn("BLACK")
        dao.clearBoard()

        val stones = dao.loadAllStones()
        val turn = dao.loadTurn()

        assertEquals(0, stones.size)
        assertEquals(null, turn)
    }
}
