package woowacourse.omok.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import omok.domain.place.Black
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.omok.entity.LatestStoneEntity

class SimpleLatestStoneDaoTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db =
        FakeLatestStoneDaoImpl(
            FakeDbHelper(context),
        )

    @Before
    fun init() {
        db.updateBoard(
            LatestStoneEntity(1, "test", Black(1, 1)),
        )
    }

    @Test
    fun `오목_테이블을_변경할_수_있다`() {
        val result = db.updateBoard(LatestStoneEntity(1, "test", Black(1, 1)))
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `마지막으로_둔_돌을_찾을_수_있다`() {
        val result = db.findLatestStoneByNickName("test")
        assertThat(result).isEqualTo(
            LatestStoneEntity(1, "test", Black(1, 1)),
        )
    }
}
