package app.pago.recyclerview.lib

import androidx.recyclerview.widget.RecyclerView
import app.pago.recyclerview.models.PagoRecyclerAdapterBaseItem
import app.pago.recyclerview.models.PagoRecyclerViewCell
import app.pago.recyclerview.models.PagoRecyclerBaseViewHolder

class PagoRecyclerViewControllerBuilder {
    private val cellList = mutableListOf<PagoRecyclerViewCell>()
    private val dataList = mutableListOf<PagoRecyclerAdapterBaseItem>()
    private val conflictSolvers =
        mutableListOf<(item: PagoRecyclerAdapterBaseItem) ->
        Class<Any>>()

    // private val dynamicDecorators = mutableListOf<PagoRecyclerViewDecorator>()
    fun useCells(
        cellList: Array<PagoRecyclerViewCell>
    ): PagoRecyclerViewControllerBuilder {
        this.cellList.clear()
        this.cellList.addAll(cellList)
        return this
    }

    fun loadData(
        dataList: List<PagoRecyclerAdapterBaseItem>,
        shouldClear: Boolean = true
    ): PagoRecyclerViewControllerBuilder {
        if (shouldClear) {
            this.dataList.clear()
        }
        this.dataList.addAll(dataList)
        return this
    }

    fun onConflict(
        conflictSolver: (item: PagoRecyclerAdapterBaseItem) ->
        Class<Any>
    ): PagoRecyclerViewControllerBuilder {
        conflictSolvers.add(conflictSolver)
        return this
    }

    fun build(recyclerAdapter: RecyclerView.Adapter<PagoRecyclerBaseViewHolder>): PagoRecyclerViewController {
        return PagoRecyclerViewController(
            cellList,
            dataList,
            conflictSolvers,
            recyclerAdapter
        )
    }
}