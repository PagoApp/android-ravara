package app.pago.ravara

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.pago.ravara.models.RavaraBaseItem
import app.pago.ravara.lib.RavaraController
import app.pago.ravara.lib.RavaraControllerBuilder
import app.pago.ravara.models.RavaraBaseViewHolder

class RavaraRecyclerViewAdapter() :
    RecyclerView.Adapter<RavaraBaseViewHolder>(){

    var controller: RavaraController = RavaraControllerBuilder().build(this)

    override fun getItemCount() = controller.dataList.size

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