package app.pago.ravara

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.pago.ravara.models.RavaraBaseItem
import app.pago.ravara.lib.CoreAdapter
import app.pago.ravara.lib.RavaraController
import app.pago.ravara.lib.RavaraControllerBuilder
import app.pago.ravara.models.RavaraBaseViewHolder

class RavaraRecyclerViewAdapter() :
    RecyclerView.Adapter<RavaraBaseViewHolder>(),
    CoreAdapter<RavaraBaseItem> {

    var controller: RavaraController = RavaraControllerBuilder().build(this)

    // This is ignored. The actual data list is inside the controller
    override val list: MutableList<RavaraBaseItem> = controller.dataList

    override fun getItemCount() = controller.dataList.size

    override fun addItem(item: RavaraBaseItem?) {
        controller.addItem(item)
    }

    override fun addList(list: List<RavaraBaseItem?>, shouldClear: Boolean) {
        controller.addList(list, shouldClear)
    }

    override fun clearList() {
        controller.clearList()
    }

    override fun updateItem(item: RavaraBaseItem) {
        controller.updateItem(item)
    }

    override fun removeItem(itemId: String) {
        controller.removeItem(itemId)
    }

    override fun removeItem(item: RavaraBaseItem) {
        controller.removeItem(item)
    }

    fun removeItem(fn: (item: RavaraBaseItem) -> Boolean) {
        controller.removeItem(fn)
    }

    override fun getItemViewType(position: Int): Int {
        return controller.getViewType(position)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RavaraBaseViewHolder {
        return controller.getViewHolder(viewType, parent)
    }

    override fun onBindViewHolder(
        holder: RavaraBaseViewHolder,
        position: Int
    ) {
        controller.bindViewHolder(holder, position)
    }
}