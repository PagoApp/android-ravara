package app.pago.ravara.lib

import app.pago.ravara.models.RavaraBaseItem
import app.pago.ravara.models.RavaraCell

class RavaraControllerBuilder {
    private val cellList = mutableListOf<RavaraCell>()
    private val dataList = mutableListOf<RavaraBaseItem>()
    private val conflictSolvers =
        mutableListOf<(item: RavaraBaseItem) ->
        Class<Any>>()

    // private val dynamicDecorators = mutableListOf<PagoRecyclerViewDecorator>()
    fun useCells(
        cellList: Array<RavaraCell>
    ): RavaraControllerBuilder {
        this.cellList.clear()
        this.cellList.addAll(cellList)
        return this
    }

    fun loadData(
        dataList: List<RavaraBaseItem>,
        shouldClear: Boolean = true
    ): RavaraControllerBuilder {
        if (shouldClear) {
            this.dataList.clear()
        }
        this.dataList.addAll(dataList)
        return this
    }

    fun onConflict(
        conflictSolver: (item: RavaraBaseItem) ->
        Class<Any>
    ): RavaraControllerBuilder {
        conflictSolvers.add(conflictSolver)
        return this
    }

    fun build(): RavaraController {
        return RavaraController(
            cellList,
            dataList,
            conflictSolvers,
        )
    }
}