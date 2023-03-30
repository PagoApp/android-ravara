package app.adi_random.pagorecyclerview

import androidx.viewbinding.ViewBinding
import app.pago.ravara.RavaraRecyclerViewAdapter
import app.pago.ravara.decorators.RavaraDecorator
import app.pago.ravara.lib.RavaraController
import app.pago.ravara.models.RavaraBaseItem
import app.pago.ravara.models.RavaraCell
import app.pago.sample.databinding.ListItemSimpleBottomSheetBinding
import org.junit.Test


class ConflictSolversTest {

    class Model(id: String) : RavaraBaseItem(id) {
    }

    class CellA : RavaraCell(ListItemSimpleBottomSheetBinding::inflate, Model::class.java) {
        override fun onBindViewHolder(
            binding: ViewBinding,
            item: RavaraBaseItem,
            decorators: List<RavaraDecorator>
        ) {
            TODO("Not yet implemented")
        }
    }

    class CellB: RavaraCell(ListItemSimpleBottomSheetBinding::inflate, Model::class.java) {
        override fun onBindViewHolder(
            binding: ViewBinding,
            item: RavaraBaseItem,
            decorators: List<RavaraDecorator>
        ) {
            TODO("Not yet implemented")
        }
    }

    @Test
    fun checkConflictSolvers() {
        val model = Model("1")
        val cellA = CellA()
        val cellB = CellB()
        val conflictSolvers = listOf<(item: RavaraBaseItem) -> Class<*>?>(
            { item -> if (item.id != "1") CellA::class.java else null },
            { item -> if (item.id == "1") CellB::class.java else null }
        )
        val cells = listOf(cellA, cellB)
        val controller = RavaraController(cells, mutableListOf(model), conflictSolvers, RavaraRecyclerViewAdapter())
        assert(controller.getViewType(0) == 1)
    }
}