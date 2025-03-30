package woowacourse.omok.db

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import woowacourse.omok.model.stone.StoneColor

@RunWith(AndroidJUnit4::class)
class TurnDaoImplTest {
    private lateinit var turnDao: TurnDaoImpl

    @Before
    fun setUp() {
        turnDao = TurnDaoImpl(ApplicationProvider.getApplicationContext())
    }

    @Test
    @DisplayName("테이블에 현재 턴을 저장한다")
    fun saveTurnTest() {
        val currentColor = StoneColor.WHITE

        turnDao.saveTurn(currentColor)

        val db = turnDao.dbHelper.readableDatabase
        val cursor =
            db.rawQuery(
                "SELECT id,color FROM ${DatabaseHelper.CURRENT_TURN} WHERE id = 1",
                null,
            )
        assertTrue(cursor.moveToFirst())
        assertEquals(1, cursor.getInt(0))
        assertEquals("WHITE", cursor.getString(1))
        cursor.close()
    }

    @Test
    @DisplayName("테이블의 마지막 턴을 불러온다")
    fun getLastTurnTest() {
        testColors.forEach {
            turnDao.saveTurn(it)
        }
        val result = turnDao.getLastTurn()
        assertEquals(StoneColor.WHITE, result)
    }

    @Test
    @DisplayName("테이블의 모든 값을 삭제한다")
    fun deleteTurnTest() {
        testColors.forEach {
            turnDao.saveTurn(it)
        }

        turnDao.deleteTurn()

        val db = turnDao.dbHelper.readableDatabase
        val cursor =
            db.rawQuery(
                "SELECT * FROM ${DatabaseHelper.CURRENT_TURN}",
                null,
            )

        cursor.use {
            assertFalse(cursor.moveToFirst(), "테이블에서 색이 삭제되지 않았습니다.")
        }
        db.close()
    }

    companion object {
        private val testColors = listOf(StoneColor.BLACK, StoneColor.WHITE)
    }
}
