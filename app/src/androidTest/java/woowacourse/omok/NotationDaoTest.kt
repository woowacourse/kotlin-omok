package woowacourse.omok

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.omok.domain.omok.model.Place

@RunWith(AndroidJUnit4::class)
class NotationDaoTest {
    private val dao = NotationDao(ApplicationProvider.getApplicationContext())

    @After
    fun tearDown() {
        dao.delete()
    }

    @Test
    fun `착수가_이뤄지면_기보에_기록되어야_한다`() {
        val place = Place("흑", 1, 2)
        val actual = dao.save(place)
        assertThat(actual.id).isGreaterThan(0)
        assertThat(actual.color).isEqualTo("흑")
        assertThat(actual.rowCoordinate).isEqualTo(1)
        assertThat(actual.colCoordinate).isEqualTo(2)
    }

    @Test
    fun `착수를_안하면_기보가_비어있어야_한다`() {
        val actual = dao.findAll()
        assertThat(actual).isEmpty()
    }

    @Test
    fun `착수가_여러번_이루어지면_기보에_모두_기록되어야_한다`() {
        // given
        val place1 = Place("흑", 2, 3)
        val place2 = Place("백", 10, 11)
        // when
        dao.save(place1)
        dao.save(place2)
        val actual = dao.findAll()
        // then
        assertThat(actual[0].color).isEqualTo("흑")
        assertThat(actual[0].rowCoordinate).isEqualTo(2)
        assertThat(actual[0].colCoordinate).isEqualTo(3)
        assertThat(actual[1].color).isEqualTo("백")
        assertThat(actual[1].rowCoordinate).isEqualTo(10)
        assertThat(actual[1].colCoordinate).isEqualTo(11)
    }
}
