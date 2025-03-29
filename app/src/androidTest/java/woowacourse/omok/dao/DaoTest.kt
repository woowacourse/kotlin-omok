package woowacourse.omok.dao

import androidx.test.platform.app.InstrumentationRegistry
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.omok.entity.OmokBoardEntity

class DaoTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val db =
        FakeDaoImpl(
            FakeDbHelper(context),
        )

    @Before
    fun init() {
        db.insertBoard(
            OmokBoardEntity("test", "B|1|1/W|4|3"),
        )
    }

    @Test
    fun `닉네임으로_테이블을_찾을_수_있다`() {
        val result = db.findBoardByNickName("test")
        assertThat(result).isEqualTo(
            OmokBoardEntity("test", "B|1|1/W|4|3"),
        )
    }

    @Test
    fun `오목_테이블을_변경할_수_있다`() {
        val result = db.updateBoard(OmokBoardEntity("test", "B|1|1/W|4|3/B/1/2"))
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `닉네임에_해당하는_테이블이_없으면_새로운_테이블을_생성한다`() {
        val result = db.insertBoard(OmokBoardEntity("test2", "B|1|1/W|4|3/B/1/2"))
        assertThat(result).isEqualTo(1)
    }
}
