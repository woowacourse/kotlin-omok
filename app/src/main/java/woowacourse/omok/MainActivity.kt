package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import controller.GameController
import woowacourse.omok.game.view.AndroidGameView
import woowacourse.omok.game.view.AndroidViewErrorHandler
import woowacourse.omok.room.controller.RoomController
import woowacourse.omok.room.db.OmokDBHelper
import woowacourse.omok.room.db.table.StageStonesTable
import woowacourse.omok.room.db.table.StageTable
import woowacourse.omok.room.db.table.StoneTable
import woowacourse.omok.room.db.table.UserStagesTable
import woowacourse.omok.room.db.table.UserTable
import woowacourse.omok.room.view.AndroidRoomView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageStones =
            findViewById<TableLayout>(R.id.board).children.filterIsInstance<TableRow>()
                .flatMap { it.children }.filterIsInstance<ImageView>().toList()
        val mainMessage = findViewById<TextView>(R.id.main_message)

        val room =
            layoutInflater.inflate(R.layout.fragment_room, findViewById(R.id.layout_room), true)
        val userRecycler = room.findViewById<RecyclerView>(R.id.recycler_user)
        val stageRecycler = room.findViewById<RecyclerView>(R.id.recycler_stage)
        val userAdd = room.findViewById<Button>(R.id.recycler_user_add_button)
        val stageAdd = room.findViewById<Button>(R.id.recycler_stage_add_button)

        val tables = listOf(
            UserTable, UserStagesTable, StageTable, StageStonesTable, StoneTable
        )

        deleteDatabase(OmokDBHelper.DB_NAME)
        val omokController = GameController(
            AndroidGameView(imageStones, mainMessage),
            AndroidViewErrorHandler(this)
        )

        RoomController(
            OmokDBHelper(this, tables),
            AndroidRoomView(userRecycler, stageRecycler, userAdd, stageAdd),
            omokController
        ).process()
    }
}
