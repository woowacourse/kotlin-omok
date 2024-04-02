package woowacourse.omok

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import omok.model.Color
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrentPlayerColorDaoTest {
    private lateinit var currentPlayerColorDao: CurrentPlayerColorDao

    @Before
    fun setUp() {
        currentPlayerColorDao = CurrentPlayerColorDao(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        currentPlayerColorDao.deleteAll()
    }

    @Test
    fun insertColor() {
        currentPlayerColorDao.insertColor(Color.BLACK)
        val color = currentPlayerColorDao.currentPlayerColor()
        assertThat(color).isEqualTo(Color.BLACK)
    }

    @Test
    fun `현재_저장된_Player의_수가_2명_이상이면_예외를_발생시킨다`() {
        currentPlayerColorDao.insertColor(Color.BLACK)
        currentPlayerColorDao.insertColor(Color.WHITE)

        assertThrows<IllegalStateException> {
            currentPlayerColorDao.currentPlayerColor()
        }
    }

    @Test
    fun isEmpty() {
        assertThat(currentPlayerColorDao.isEmpty()).isTrue()
    }
}
