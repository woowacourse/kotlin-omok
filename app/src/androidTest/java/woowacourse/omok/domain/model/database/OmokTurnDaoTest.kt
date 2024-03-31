package woowacourse.omok.domain.model.database

import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OmokTurnDaoTest {
    private lateinit var omokTurnDao: OmokTurnDao

    @Before
    fun setUp() {
        omokTurnDao = OmokTurnDao(OmokTurnDbHelper(ApplicationProvider.getApplicationContext()))
        omokTurnDao.createTable()
    }

    @After
    fun tearDown() {
        omokTurnDao.drop()
    }

    @Test
    fun saveTest() {
        val turn = OmokTurn(1, 1, "black")
        val actual = omokTurnDao.save(turn)
        Log.d("actual", actual.toString())
        assertThat(actual.row).isEqualTo(1)
        assertThat(actual.column).isEqualTo(1)
        assertThat(actual.stoneColor).isEqualTo("black")
        assertThat(actual.id).isEqualTo(1)
    }

    @Test
    fun findAllTest() {
        val turn1 = OmokTurn(1, 1, "black")
        val turn2 = OmokTurn(2, 2, "white")
        val turn3 = OmokTurn(5, 5, "black")

        omokTurnDao.save(turn1)
        omokTurnDao.save(turn2)
        omokTurnDao.save(turn3)

        val omokTurns = omokTurnDao.findAll()
        Log.d("omokTurns", omokTurns.toString())
        omokTurns[0].let {
            assertThat(it.row).isEqualTo(1)
            assertThat(it.column).isEqualTo(1)
            assertThat(it.stoneColor).isEqualTo("black")
            assertThat(it.id).isEqualTo(1)
        }
        omokTurns[1].let {
            assertThat(it.row).isEqualTo(2)
            assertThat(it.column).isEqualTo(2)
            assertThat(it.stoneColor).isEqualTo("white")
            assertThat(it.id).isEqualTo(2)
        }
        omokTurns[2].let {
            assertThat(it.row).isEqualTo(5)
            assertThat(it.column).isEqualTo(5)
            assertThat(it.stoneColor).isEqualTo("black")
            assertThat(it.id).isEqualTo(3)
        }
    }

    @Test
    fun findLatestStoneColor() {
        val turn1 = OmokTurn(1, 1, "black")
        val turn2 = OmokTurn(2, 2, "white")
        val turn3 = OmokTurn(5, 5, "black")

        omokTurnDao.save(turn1)
        omokTurnDao.save(turn2)
        omokTurnDao.save(turn3)
        val omokTurns = omokTurnDao.findAll()
        Log.d("omokTurns", omokTurns.toString())

        val latestStoneColor = omokTurnDao.findLatestStoneColor()
        assertThat(latestStoneColor).isEqualTo("black")
    }
}
