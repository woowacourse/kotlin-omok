package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.data.Player
import woowacourse.omok.data.Room
import woowacourse.omok.dbHelper.OmokPlayerDbHelper
import woowacourse.omok.dbHelper.OmokRoomDbHelper

class MainActivity : AppCompatActivity() {
    private val playerNames: List<Player> =
        listOf(
            Player(name = "아크", profile = R.drawable.player_ark),
            Player(name = "크롱", profile = R.drawable.player_krrong),
            Player(name = "해시", profile = R.drawable.player_hash),
            Player(name = "뽀또", profile = R.drawable.player_bbotto),
            Player(name = "베리", profile = R.drawable.player_berryloffy),
            Player(name = "글로", profile = R.drawable.player_gloria),
            Player(name = "베르", profile = R.drawable.player_ber),
            Player(name = "산군", profile = R.drawable.player_ber),
            Player(name = "반달", profile = R.drawable.player_ber),
            Player(name = "둘리", profile = R.drawable.player_ber),
            Player(name = "로피", profile = R.drawable.player_ber),
            Player(name = "멘델", profile = R.drawable.player_ber),
            Player(name = "부나", profile = R.drawable.player_ber),
            Player(name = "빅스", profile = R.drawable.player_ber),
            Player(name = "수달", profile = R.drawable.player_ber),
            Player(name = "스캇", profile = R.drawable.player_ber),
            Player(name = "써니", profile = R.drawable.player_ber),
            Player(name = "링링", profile = R.drawable.player_ber),
            Player(name = "우기", profile = R.drawable.player_ber),
            Player(name = "코건", profile = R.drawable.player_ber),
            Player(name = "코비", profile = R.drawable.player_ber),
            Player(name = "토마스", profile = R.drawable.player_ber),
            Player(name = "핑구", profile = R.drawable.player_ber),
            Player(name = "하티", profile = R.drawable.player_ber),
            Player(name = "멧돼지", profile = R.drawable.player_ber),
            Player(name = "레아", profile = R.drawable.player_ber),
            Player(name = "제이슨", profile = R.drawable.player_ber),
            Player(name = "제임스", profile = R.drawable.player_ber),
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeSampleData()
        startActivity(Intent(this, RoomListActivity::class.java))
    }

    private fun makeSampleData() {
        val db = OmokRoomDbHelper(this)
        val playerDb = OmokPlayerDbHelper(this)
        playerNames.forEach(playerDb::insertOrReplace)
        val player = playerDb.getPlayers()
        player.forEachIndexed { idx, it ->
            println("idx: $idx, it: $it")
            val room = Room(
                player = it,
                title = "제목${idx + 1}",
                status = 0,
                time = 0,
            )
            db.insertOrReplace(room)
        }
        db.close()
        playerDb.close()
    }
}
