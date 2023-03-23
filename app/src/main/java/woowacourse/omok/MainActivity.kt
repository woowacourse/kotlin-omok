package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.data.OverallRecord
import woowacourse.omok.data.Player
import woowacourse.omok.data.Room

class MainActivity : AppCompatActivity() {
    private val playerNames: List<Player> =
        listOf(
            Player(
                name = "아크",
                overallRecord = OverallRecord(
                    win = 100000,
                    lose = 5,
                    draw = 3,
                ),
                profile = R.drawable.player_ark,
            ),
            Player(
                name = "크롱",
                overallRecord = OverallRecord(
                    win = 10,
                    lose = 5,
                    draw = 3,
                ),
                profile = R.drawable.player_krrong,
            ),
            Player(
                name = "해시",
                overallRecord = OverallRecord(
                    win = 10,
                    lose = 5,
                    draw = 3,
                ),
                profile = R.drawable.player_hash,
            ),
            Player(
                name = "뽀또",
                overallRecord = OverallRecord(
                    win = 10,
                    lose = 5,
                    draw = 3,
                ),
                profile = R.drawable.player_bbotto,
            ),
            Player(
                name = "베리",
                overallRecord = OverallRecord(
                    win = 10,
                    lose = 5,
                    draw = 3,
                ),
                profile = R.drawable.player_berryloffy,
            ),
            Player(
                name = "글로",
                overallRecord = OverallRecord(
                    win = 10,
                    lose = 5,
                    draw = 3,
                ),
                profile = R.drawable.player_gloria,
            ),
            Player(
                name = "베르",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "산군",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "반달",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "둘리",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "로피",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "멘델",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "부나",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "빅스",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "수달",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "스캇",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "써니",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "링링",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "우기",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "코건",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "코비",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "토마스",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "핑구",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "하티",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "멧돼지",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "레아",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "제이슨",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
            Player(
                name = "제임스",
                overallRecord = OverallRecord(
                    win = 0,
                    lose = 500000000,
                    draw = 3,
                ),
                profile = R.drawable.player_ber,
            ),
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeSampleData()
        startActivity(Intent(this, RoomListActivity::class.java))
    }

    private fun makeSampleData() {
        val db = OmokRoomDbHelper(this)
        val playerDb = OmokPlayerDbHelper(this)
        db.onUpgrade(db.writableDatabase, 1, 1)
        playerDb.onUpgrade(playerDb.writableDatabase, 1, 1)
        playerNames.forEach(playerDb::insert)
        val player = playerDb.getPlayers()
        player.forEachIndexed { idx, it ->
            println("idx: $idx, it: $it")
            val room = Room(
                player = it,
                title = "제목$idx",
                status = 0,
                time = 0,
            )
            db.insert(room)
        }
        db.close()
        playerDb.close()
    }
}
