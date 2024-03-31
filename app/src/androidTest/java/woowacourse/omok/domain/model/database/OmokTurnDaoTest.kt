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
    }

    @After
    fun tearDown() {
        omokTurnDao.drop()
    }

    @Test
    fun saveTest() {
        val turn = OmokTurn(1, 1, "black")
        val actual = omokTurnDao.save(turn)
        assertThat(actual.row).isEqualTo(1)
        assertThat(actual.column).isEqualTo(1)
        assertThat(actual.stoneColor).isEqualTo("black")
        assertThat(actual.id).isEqualTo(1)
    }
}
