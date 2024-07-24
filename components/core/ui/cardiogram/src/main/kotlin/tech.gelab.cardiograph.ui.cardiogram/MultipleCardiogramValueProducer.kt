package tech.gelab.cardiograph.ui.cardiogram

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlin.math.abs

class MultipleCardiogramValueProducer(models: List<CardiogramModel>) {

    private val ids = models.map { it.id }.toSet()
    private val _models = models.toImmutableList()

    private val currentGaps = mutableMapOf<String, Int>()
    private val currentValues = mutableMapOf<String, MutableList<Float>>()

    private val cardiogramListStateFlow = MutableStateFlow(
        CardiogramListState(persistentMapOf(), mapOf())
    )

    init {
        models.forEach { model ->
            currentGaps[model.id] = CardiogramDefaults.DEFAULT_GAP
            currentValues[model.id] = mutableListOf()
        }
    }

    private fun validateIdFlow(id: String): Flow<Unit> {
        return flow {
            if (ids.contains(id).not()) {
                throw IllegalArgumentException("No model was found with given id = $id")
            }
            emit(Unit)
        }
    }

    fun getModels(): ImmutableList<CardiogramModel> {
        return _models
    }

    fun getStateFlow(): StateFlow<CardiogramListState> {
        return cardiogramListStateFlow
    }

    fun produceValue(values: ImmutableList<MultipleCardiogramValue>) {

        values.forEach { multipleCardiogramValue ->
            val id = multipleCardiogramValue.id
            val value = multipleCardiogramValue.value
            val absValue = abs(value)
            if (ids.contains(id).not()) {
                throw IllegalArgumentException("No model was found with given id = $id")
            }
            val gap = currentGaps[id] ?: CardiogramDefaults.DEFAULT_GAP
            if (absValue > gap) {
                currentGaps[id] =
                    (absValue.toInt() / CardiogramDefaults.DEFAULT_GAP + 1) * CardiogramDefaults.DEFAULT_GAP
            }
            val currentIdValues =
                currentValues[id] ?: mutableListOf<Float>().also { currentValues[id] = it }
            if (currentIdValues.size >= CardiogramDefaults.DEFAULT_DISPLAY_VALUES) {
                currentIdValues.removeAt(0)
            }
            currentIdValues.add(multipleCardiogramValue.value)
        }

        cardiogramListStateFlow.tryEmit(
            CardiogramListState(
                currentGaps.toImmutableMap(),
                currentValues.map { it.key to it.value.toImmutableList() }.toMap()
            )
        )
    }


}