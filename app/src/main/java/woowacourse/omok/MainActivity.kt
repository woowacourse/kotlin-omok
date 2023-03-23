package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import controller.GameController
import woowacourse.omok.controller.RoomController
import woowacourse.omok.db.OmokDBHelper
import woowacourse.omok.db.table.StageStonesTable
import woowacourse.omok.db.table.StageTable
import woowacourse.omok.db.table.StoneTable
import woowacourse.omok.db.table.UserStagesTable
import woowacourse.omok.db.table.UserTable
import woowacourse.omok.view.AndroidGameView
import woowacourse.omok.view.AndroidRenderBoard
import woowacourse.omok.view.AndroidRoomView
import woowacourse.omok.view.AndroidViewErrorHandler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageStones =
            findViewById<TableLayout>(R.id.board).children.filterIsInstance<TableRow>()
                .flatMap { it.children }.filterIsInstance<ImageView>().toList()

        val room =
            layoutInflater.inflate(R.layout.fragment_room, findViewById(R.id.layout_room), true)
        val userRecycler = room.findViewById<RecyclerView>(R.id.recycler_user)
        val stageRecycler = room.findViewById<RecyclerView>(R.id.recycler_stage)

        val tables = listOf(
            UserTable, UserStagesTable, StageTable, StageStonesTable, StoneTable
        )

        deleteDatabase(OmokDBHelper.DB_NAME)

        RoomController(
            OmokDBHelper(this, tables),
            AndroidRoomView(userRecycler, stageRecycler)
        ).process()

        GameController(
            AndroidGameView(
                this,
                AndroidRenderBoard(imageStones),
                imageStones
            ),
            AndroidViewErrorHandler(this)
        ).process()
    }
}
