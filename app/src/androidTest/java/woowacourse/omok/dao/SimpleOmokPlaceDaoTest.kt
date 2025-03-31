package woowacourse.omok.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import omok.domain.place.Black
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.omok.entity.LatestStoneEntity

class SimpleOmokPlaceDaoTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val db =
        FakeOmokPlaceDaoImpl(
            FakeDbHelper(context),
        )

    @Before
    fun init() {
        db.insertBoard(
            LatestStoneEntity(1, "test", Black(1, 1)),
        )
    }

    @Test
    fun `닉네임으로_테이블을_찾을_수_있다`() {
        val result = db.findBoardByNickName("test")
        assertThat(result).isEqualTo(
            LatestStoneEntity(1, "test", Black(1, 1)),
        )
    }

    @Test
    fun `닉네임에_해당하는_테이블이_없으면_새로운_테이블을_생성한다`() {
        val result =
            db.insertBoard(
                LatestStoneEntity(1, "test2", Black(1, 1)),
            )
        assertThat(result).isEqualTo(1)
    }
}
