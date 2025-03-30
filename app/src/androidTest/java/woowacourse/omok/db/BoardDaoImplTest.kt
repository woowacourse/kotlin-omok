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

@RunWith(AndroidJUnit4::class)
class BoardDaoImplTest {
    private lateinit var boardDao: BoardDao

    @Before
    fun setUp() {
        boardDao = BoardDaoImpl(ApplicationProvider.getApplicationContext())
    }

    @Test
    @DisplayName("돌을 보드 테이블에 저장한다")
    fun insertStoneTest() {
        val boardDto = BoardDto(x = 1, y = 1, stoneColor = "BLACK")

        boardDao.insertStone(boardDto)

        val db = boardDao.dbHelper.readableDatabase
        val cursor =
            db.rawQuery(
                "SELECT x, y, state FROM ${DatabaseHelper.OMOK_BOARD} WHERE x = 1 AND y = 1",
                null,
            )
        assertTrue(cursor.moveToFirst())
        assertEquals(1, cursor.getInt(0))
        assertEquals(1, cursor.getInt(1))
        assertEquals("BLACK", cursor.getString(2))
        cursor.close()
    }

    @Test
    @DisplayName("테이블에 위치한 돌들의 정보를 불러온다")
    fun getAllStonesTest() {
        testStones.forEach {
            boardDao.insertStone(it)
        }
        val stones = boardDao.getAllStones()
        assertEquals(testStones, stones)
    }

    @Test
    @DisplayName("테이블에서 모든 돌을 삭제한다")
    fun clearBoardTest() {
        testStones.forEach {
            boardDao.insertStone(it)
        }

        boardDao.clearBoard()

        val db = boardDao.dbHelper.readableDatabase
        val cursor =
            db.rawQuery(
                "SELECT x, y, state FROM ${DatabaseHelper.OMOK_BOARD}",
                null,
            )

        cursor.use {
            assertFalse(cursor.moveToFirst(), "테이블에서 돌이 삭제되지 않았습니다.")
        }
        db.close()
    }

    companion object {
        private val testStones =
            listOf(
                BoardDto(x = 1, y = 1, stoneColor = "BLACK"),
                BoardDto(x = 1, y = 2, stoneColor = "WHITE"),
            )
    }
}
