package woowacourse.omok

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.data.OverallRecord
import woowacourse.omok.data.PlayerInfo
import woowacourse.omok.data.RefreshButton
import woowacourse.omok.data.RoomInfo
import woowacourse.omok.data.RoomListType

class RoomListActivity : AppCompatActivity() {
    val adapter = CustomAdapter(
        { onRefreshButtonClicked() },
        { roomInfo -> onRoomClicked(roomInfo = roomInfo) },
    )

    private val roomNames: List<RoomListType> =
        listOf(
            RoomInfo(
                title = "방1",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "아크",
                    overallRecord = OverallRecord(
                        win = 100000,
                        lose = 5,
                        draw = 3,
                    ),
                    profile = R.drawable.player_ark,
                ),
            ),
            RoomInfo(
                title = "방2",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "크롱",
                    overallRecord = OverallRecord(
                        win = 10,
                        lose = 5,
                        draw = 3,
                    ),
                    profile = R.drawable.player_krrong,
                ),
            ),
            RoomInfo(
                title = "방3",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "해시",
                    overallRecord = OverallRecord(
                        win = 10,
                        lose = 5,
                        draw = 3,
                    ),
                    profile = R.drawable.player_hash,
                ),
            ),
            RoomInfo(
                title = "방4",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "뽀또",
                    overallRecord = OverallRecord(
                        win = 10,
                        lose = 5,
                        draw = 3,
                    ),
                    profile = R.drawable.player_bbotto,
                ),
            ),
            RoomInfo(
                title = "방5",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "베리",
                    overallRecord = OverallRecord(
                        win = 10,
                        lose = 5,
                        draw = 3,
                    ),
                    profile = R.drawable.player_berryloffy,
                ),
            ),
            RoomInfo(
                title = "방5",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "글로",
                    overallRecord = OverallRecord(
                        win = 10,
                        lose = 5,
                        draw = 3,
                    ),
                    profile = R.drawable.player_gloria,
                ),
            ),
            RoomInfo(
                title = "방5",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "베르",
                    overallRecord = OverallRecord(
                        win = 0,
                        lose = 500000000,
                        draw = 3,
                    ),
                    profile = R.drawable.player_ber,
                ),
            ),
            RefreshButton(
                title = "새로고침",
            ),
        )

    private val roomNames2: List<RoomListType> =
        listOf(
            RoomInfo(
                title = "방1",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "아크",
                    overallRecord = OverallRecord(
                        win = 100000,
                        lose = 5,
                        draw = 3,
                    ),
                    profile = R.drawable.player_ark,
                ),
            ),
            RoomInfo(
                title = "방2",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "크롱",
                    overallRecord = OverallRecord(
                        win = 10,
                        lose = 5,
                        draw = 3,
                    ),
                    profile = R.drawable.player_krrong,
                ),
            ),
            RoomInfo(
                title = "방3",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "해시",
                    overallRecord = OverallRecord(
                        win = 10,
                        lose = 5,
                        draw = 3,
                    ),
                    profile = R.drawable.player_hash,
                ),
            ),
            RoomInfo(
                title = "방4",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "뽀또",
                    overallRecord = OverallRecord(
                        win = 10,
                        lose = 5,
                        draw = 3,
                    ),
                    profile = R.drawable.player_bbotto,
                ),
            ),
            RoomInfo(
                title = "방5",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "베리",
                    overallRecord = OverallRecord(
                        win = 10,
                        lose = 5,
                        draw = 3,
                    ),
                    profile = R.drawable.player_berryloffy,
                ),
            ),
            RoomInfo(
                title = "방5",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "글로",
                    overallRecord = OverallRecord(
                        win = 10,
                        lose = 5,
                        draw = 3,
                    ),
                    profile = R.drawable.player_gloria,
                ),
            ),
            RoomInfo(
                title = "방5",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "베르",
                    overallRecord = OverallRecord(
                        win = 0,
                        lose = 500000000,
                        draw = 3,
                    ),
                    profile = R.drawable.player_ber,
                ),
            ),
            RoomInfo(
                title = "방5",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "베르",
                    overallRecord = OverallRecord(
                        win = 0,
                        lose = 500000000,
                        draw = 3,
                    ),
                    profile = R.drawable.player_ber,
                ),
            ),
            RoomInfo(
                title = "방5",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "베르",
                    overallRecord = OverallRecord(
                        win = 0,
                        lose = 500000000,
                        draw = 3,
                    ),
                    profile = R.drawable.player_ber,
                ),
            ),
            RoomInfo(
                title = "방5",
                status = "대기중",
                time = "10분",
                player = PlayerInfo(
                    name = "베르",
                    overallRecord = OverallRecord(
                        win = 0,
                        lose = 500000000,
                        draw = 3,
                    ),
                    profile = R.drawable.player_ber,
                ),
            ),
            RefreshButton(
                title = "새로고침",
            ),
        )

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_list)

        val roomList = findViewById<RecyclerView>(R.id.room_list)
        roomList.adapter = adapter

        adapter.submitList(roomNames)
    }

    private fun onRefreshButtonClicked() {
        adapter.submitList(roomNames2)
        Toast.makeText(this, "새로고침", Toast.LENGTH_SHORT).show()
    }

    private fun onRoomClicked(roomInfo: RoomInfo) {
        startActivity(Intent(this, RoomActivity::class.java))
    }
}
