package woowacourse.omok

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.runner.RunWith
import woowacourse.omok.data.dao.OmokDao

@RunWith(AndroidJUnit4::class)
class OmokDaoTest {
    lateinit var omokDao: OmokDao

    @AnnotationSpec.Before
    fun setUp() {
        omokDao = OmokDao(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun insertOmokTest() {
        setUp()

        omokDao.insertOmok(0, 0, "BLACK")
        val stones = omokDao.getAllStones()

        assertAll(
            { assertThat(stones).hasSize(1) },
            { assertThat(stones[0].row).isEqualTo(0) },
            { assertThat(stones[0].col).isEqualTo(0) },
            { assertThat(stones[0].color).isEqualTo("BLACK") },
        )
    }

    @Test
    fun hasOmokDataTest() {
        setUp()
        omokDao.insertOmok(0, 0, "BLACK")
        omokDao.insertOmok(2, 1, "WHITE")
        omokDao.insertOmok(0, 10, "BLACK")

        assertThat(omokDao.hasOmokData()).isTrue()
    }

    @Test
    fun getAllStonesTest() {
        setUp()

        omokDao.insertOmok(1, 0, "BLACK")
        omokDao.insertOmok(2, 2, "WHITE")
        val stones = omokDao.getAllStones()

        assertAll(
            { assertThat(stones).hasSize(2) },
            { assertThat(stones[1].row).isEqualTo(2) },
            { assertThat(stones[1].col).isEqualTo(2) },
            { assertThat(stones[1].color).isEqualTo("WHITE") },
        )
    }

    @Test
    fun deleteDatabaseTest() {
        setUp()

        omokDao.insertOmok(1, 0, "BLACK")
        omokDao.insertOmok(2, 2, "WHITE")

        omokDao.deleteDatabase()

        assertThat(omokDao.getAllStones().size).isEqualTo(0)
    }
}
