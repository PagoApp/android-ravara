package app.pago.recyclerview.lib

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.pago.recyclerview.PagoException
import app.pago.recyclerview.models.PagoRecyclerAdapterBaseItem
import app.pago.recyclerview.decorators.PagoRecyclerViewDecorator
import app.pago.recyclerview.models.PagoRecyclerViewCell
import app.pago.recyclerview.models.PagoRecyclerBaseViewHolder

open class PagoRecyclerViewController(
    private val cellList: List<PagoRecyclerViewCell>,
    val dataList: MutableList<PagoRecyclerAdapterBaseItem>,
    private val conflictSolvers:
    List<(item: PagoRecyclerAdapterBaseItem) ->
    Class<*>>,
    private val recyclerAdapter: RecyclerView.Adapter<PagoRecyclerBaseViewHolder>
) {

    init {
        cellList.forEachIndexed { index, cell ->
            cell.controller = this
            cell.viewType = index
        }
    }

    fun getViewType(itemPosition: Int): Int {
        // Get all the cells that have the data type the same class as the item
        // It there is only one cell, return it's view type
        // If there are more, run all the conflict solvers until one returns a cell class

        val item = dataList[itemPosition]
        val cells = cellList.filter { it.dataType == item.javaClass }
        if (cells.size == 1) {
            return cells[0].viewType ?: throw PagoException("View type is not yet initialized")
        } else {
            for (conflictSolver in conflictSolvers) {
                val cellClass = conflictSolver(item)
                return cellList.first { it.javaClass == cellClass }.viewType
                    ?: throw PagoException("View type is not yet initialized")
            }
            throw PagoException("No conflict solver returned a PagoRecyclerViewCell class")
        }
    }

    fun getViewHolder(
        viewType: Int,
        parent: ViewGroup,
    ): PagoRecyclerBaseViewHolder {
        // Get the cell with the same view type as the one passed in
        // If the cell is not null, create the associate viewHolder
        // If the cell is null, throw an PagoException
        val cell = cellList.firstOrNull { it.viewType == viewType }
        return cell?.createViewHolder(parent)
            ?: throw PagoException("No cell with view type $viewType")
    }

    fun bindViewHolder(
        viewHolder: PagoRecyclerBaseViewHolder,
        position: Int
    ) {
        val item = dataList[position]
        val decorators = viewHolder.cell.getDecorators(viewHolder.binding, item)
        val allDecorators = decorators + viewHolder.cell.dynamicDecorators
        val decoratorsGroupedByStrategy = allDecorators.groupBy { it.applyStrategy }

        for (decorator in decoratorsGroupedByStrategy[PagoRecyclerViewDecorator.DecoratorStrategy.PRE_BIND]
            ?: emptyList()) {
            decorator.apply()
        }

        viewHolder.bindViewHolder(item, allDecorators)

        for (decorator in decoratorsGroupedByStrategy[PagoRecyclerViewDecorator.DecoratorStrategy.POST_BIND]
            ?: emptyList()) {
            decorator.apply()
        }
    }

    fun addItem(item: PagoRecyclerAdapterBaseItem?) {
        item?.let {
            dataList.add(it)
            recyclerAdapter.notifyItemInserted(dataList.size - 1)
        }
    }

    fun addList(list: List<PagoRecyclerAdapterBaseItem?>, shouldClear: Boolean) {
        if (shouldClear) {
            clearList()
        }
        list.forEach {
            it?.let {
                this.dataList.add(it)
            }
        }
        recyclerAdapter.notifyDataSetChanged()
    }

    fun removeItem(fn: (item: PagoRecyclerAdapterBaseItem) -> Boolean) {
        // Remove all items that match the function passed in and notify the adapter
        val itemsToRemove = dataList.filter(fn)

        if (itemsToRemove.size == 1) {
            val itemToRemove = itemsToRemove[0]
            val position = dataList.indexOf(itemToRemove)
            dataList.remove(itemToRemove)
            recyclerAdapter.notifyItemRemoved(position)
        } else {
            dataList.removeAll(itemsToRemove)
            recyclerAdapter.notifyDataSetChanged()
        }
    }

    /**
     * Due to the way the recycler view works,
     * when we delete an item, it first updates the layout, making the height smaller, and then starts animating the items
     * If the view isn't constraint properly, the last item in the list might appear to blink
     * since it first is clipped and then animated back into the view
     */
    fun removeItem(itemId: String) {
        dataList.apply {
            // throw error if there are more than one item with the same id
            val itemsToRemove = filter { it.id == itemId }

            if (itemsToRemove.isEmpty()) {
                return
            }
            if (itemsToRemove.size > 1) {
                throw PagoException("There are more than one item with the same id. Use the removeItem(fn) method instead")
            }
            val itemToRemove = itemsToRemove[0]
            val position = indexOf(itemToRemove)
            if (position != -1) {
                removeAt(position)
                recyclerAdapter.notifyItemRemoved(position)
            }
        }
    }

    /**
     * Due to the way the recycler view works,
     * when we delete an item, it first updates the layout, making the height smaller, and then starts animating the items
     * If the view isn't constraint properly, the last item in the list might appear to blink
     * since it first is clipped and then animated back into the view
     */
    fun removeItem(item: PagoRecyclerAdapterBaseItem) {
        // remove the item from the data list and notify the adapter
        dataList.apply {
            val itemPosition = indexOf(item)
            if (itemPosition != -1) {
                removeAt(itemPosition)
                recyclerAdapter.notifyItemRemoved(itemPosition)
            }
        }
    }

    fun clearList() {
        dataList.clear()
        // we use this instead of the removeAll because the remove all one will kill our animations
        recyclerAdapter.notifyItemRangeRemoved(0, dataList.size)

    }

    /**
     * @return the index of the item in the list if it was found, -1 otherwise
     */
    fun updateItem(item: PagoRecyclerAdapterBaseItem) {
        val foundItem = dataList.firstOrNull {
            it.id == item.id
        }
        val index = dataList.indexOf(foundItem)
        if (index != -1) {
            dataList[index] = item
            recyclerAdapter.notifyItemChanged(index)
        }
    }

    fun notifyDataSetChanged() {
        recyclerAdapter.notifyDataSetChanged()
    }

}