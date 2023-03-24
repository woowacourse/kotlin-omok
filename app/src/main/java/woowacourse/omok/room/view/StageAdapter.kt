package woowacourse.omok.room.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import woowacourse.omok.room.domain.Stage
import woowacourse.omok.room.domain.Stages

class StageAdapter(var stages: Stages, val onSelectStage: (Stage) -> Unit) :
    RecyclerView.Adapter<StageAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.recycler_text)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_recycler, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text = stages.value[position].id.toString()
        viewHolder.itemView.setOnClickListener {
            onSelectStage(stages.value[position])
        }
    }

    override fun getItemCount() = stages.value.size
}
