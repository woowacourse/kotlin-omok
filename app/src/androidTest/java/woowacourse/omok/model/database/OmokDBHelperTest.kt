package woowacourse.omok.model.database

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OmokDBHelperTest {
    private lateinit var dbHelper: OmokDBHelper
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        dbHelper = OmokDBHelper(context)
        val db = dbHelper.writableDatabase
        db.close()
    }

    @After
    fun close() {
        dbHelper.close()
        context.deleteDatabase(OmokDBHelper.DATABASE_NAME)
    }

    @Test // 새 게임방 생성 테스트
    fun testFetchNewGameRoom() {
        // given
        val blackPlayerName = "메다"
        val whitePlayerName = "크롱"

        // when
        val gameRoom = dbHelper.fetchNewGameRoom(blackPlayerName, whitePlayerName)

        // then
        assertNotNull(gameRoom)
        assertEquals(blackPlayerName, gameRoom?.blackStonePlayerName)
        assertEquals(whitePlayerName, gameRoom?.whiteStonePlayerName)
    }

    @Test // 여러 게임방 생성 후 목록 조회 테스트
    fun testFetchDBGameRooms() {
        // given
        dbHelper.fetchNewGameRoom("메다", "크롱")
        dbHelper.fetchNewGameRoom("조이", "메다")

        // when
        val gameRooms = dbHelper.fetchDBGameRooms()

        // then
        assertEquals(2, gameRooms.size)
        assertTrue(gameRooms.any { it.blackStonePlayerName == "메다" && it.whiteStonePlayerName == "크롱" })
        assertTrue(gameRooms.any { it.blackStonePlayerName == "조이" && it.whiteStonePlayerName == "메다" })
    }

    @Test // 플레이어 정보 추가 및 조회 테스트
    fun testAddPlayerHistoryAndGetPlayerInfo() {
        // given
        val playerName = "메다"

        // when
        dbHelper.addPlayerHistory(
            name = playerName,
            playCount = 5,
            blackWinCount = 2,
            whiteWinCount = 1,
        )

        val playerInfo = dbHelper.getPlayerInfo(playerName)

        // then
        assertNotNull(playerInfo)
        assertEquals(playerName, playerInfo?.name)
        assertEquals(5, playerInfo?.playCount)
        assertEquals(2, playerInfo?.blackWinCount)
        assertEquals(1, playerInfo?.whiteWinCount)
    }

    @Test // 기존 플레이어 정보 업데이트 테스트
    fun testUpdatePlayerHistory() {
        // given
        val playerName = "메다"

        dbHelper.addPlayerHistory(
            name = playerName,
            playCount = 3,
            blackWinCount = 1,
            whiteWinCount = 0,
        )

        // when
        dbHelper.addPlayerHistory(
            name = playerName,
            playCount = 1,
            blackWinCount = 2,
            whiteWinCount = 1,
        )

        // then
        val playerInfo = dbHelper.getPlayerInfo(playerName)
        assertEquals(4, playerInfo?.playCount) // 3+1
        assertEquals(3, playerInfo?.blackWinCount) // 1+2
        assertEquals(1, playerInfo?.whiteWinCount) // 0+1
    }

    @Test // 게임방 삭제 테스트
    fun testGameRoomDelete() {
        // given
        val gameRoom = dbHelper.fetchNewGameRoom("메다", "크롱")

        // when
        gameRoom?.id?.let { roomId ->
            dbHelper.roomWithStonesDelete(roomId)
        }

        // then
        val gameRooms = dbHelper.fetchDBGameRooms()
        assertTrue(gameRooms.isEmpty())
    }

    @Test // 존재하지 않는 플레이어 정보 조회 테스트
    fun testGetNonExistentPlayerInfo() {
        val alphaGo = dbHelper.getPlayerInfo("알파고")

        assertNull(alphaGo)
    }
}
