package woowacourse.omok.db

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.omok.model.Color

@RunWith(AndroidJUnit4::class)
class OmokDaoTest {
    private val omokDao = OmokDao(ApplicationProvider.getApplicationContext())

    @After
    fun tearDown() {
        omokDao.resetAll()
    }

    @Test
    fun `데이터베이스에_돌을_저장하면_돌의_색상과_좌표가_저장된다`() {
        // given
        val entity = OmokEntity("BLACK", 10, 4)

        // when
        val actual = omokDao.insertOmok(entity)

        // then
        assertThat(actual.stoneColor).isEqualTo(Color.BLACK.name)
        assertThat(actual.positionX).isEqualTo(10)
        assertThat(actual.positionY).isEqualTo(4)
    }

    @Test
    fun `데이터베이스에_저장된_모든_데이터를_조회한다`() {
        // given
        val entity1 = OmokEntity("BLACK", 10, 4)
        val entity2 = OmokEntity("WHITE", 5, 10)
        omokDao.insertOmok(entity1)
        omokDao.insertOmok(entity2)

        // when
        val actual = omokDao.findAllOmok()

        // then
        assertThat(actual.size).isEqualTo(2)
        assertThat(actual[0].stoneColor).isEqualTo(Color.BLACK.name)
        assertThat(actual[1].stoneColor).isEqualTo(Color.WHITE.name)
        assertThat(actual[0].positionX).isEqualTo(10)
        assertThat(actual[1].positionX).isEqualTo(5)
        assertThat(actual[0].positionY).isEqualTo(4)
        assertThat(actual[1].positionY).isEqualTo(10)
    }

    @Test
    fun `데이터베이스에_저장된_모든_데이터를_삭제한다`() {
        // given
        val entity1 = OmokEntity("BLACK", 11, 11)
        val entity2 = OmokEntity("WHITE", 5, 10)
        omokDao.insertOmok(entity1)
        omokDao.insertOmok(entity2)

        // when
        omokDao.resetAll()
        val actual = omokDao.findAllOmok()

        // then
        assertThat(actual).isEmpty()
    }
}
