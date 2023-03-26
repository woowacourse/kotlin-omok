package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.SampleData.generateSampleData
import woowacourse.omok.roomList.RoomListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generateSampleData(this)

        startActivity(Intent(this, RoomListActivity::class.java))
    }
}
