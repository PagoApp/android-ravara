package app.pago.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.pago.recyclerview.models.PagoRecyclerAdapterBaseItem
import app.pago.recyclerview.lib.CoreAdapter
import app.pago.recyclerview.lib.PagoRecyclerViewController
import app.pago.recyclerview.lib.PagoRecyclerViewControllerBuilder
import app.pago.recyclerview.models.PagoRecyclerBaseViewHolder

class PagoRecyclerViewAdapter() :
    RecyclerView.Adapter<PagoRecyclerBaseViewHolder>(),
    CoreAdapter<PagoRecyclerAdapterBaseItem> {

    var controller: PagoRecyclerViewController = PagoRecyclerViewControllerBuilder().build(this)

    // This is ignored. The actual data list is inside the controller
    override val list: MutableList<PagoRecyclerAdapterBaseItem> = controller.dataList

    override fun getItemCount() = controller.dataList.size

    override fun addItem(item: PagoRecyclerAdapterBaseItem?) {
        controller.addItem(item)
    }

    override fun addList(list: List<PagoRecyclerAdapterBaseItem?>, shouldClear: Boolean) {
        controller.addList(list, shouldClear)
    }

    override fun clearList() {
        controller.clearList()
    }

    override fun updateItem(item: PagoRecyclerAdapterBaseItem) {
        controller.updateItem(item)
    }

    override fun removeItem(itemId: String) {
        controller.removeItem(itemId)
    }

    override fun removeItem(item: PagoRecyclerAdapterBaseItem) {
        controller.removeItem(item)
    }

    fun removeItem(fn: (item: PagoRecyclerAdapterBaseItem) -> Boolean) {
        controller.removeItem(fn)
    }

    override fun getItemViewType(position: Int): Int {
        return controller.getViewType(position)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagoRecyclerBaseViewHolder {
        return controller.getViewHolder(viewType, parent)
    }

    override fun onBindViewHolder(
        holder: PagoRecyclerBaseViewHolder,
        position: Int
    ) {
        controller.bindViewHolder(holder, position)
    }
}