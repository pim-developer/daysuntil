package com.dhaen.daysuntil.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhaen.daysuntil.data.repository.Repository
import com.dhaen.daysuntil.domain.countdownevent.CountDownEventModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val countDownEventsFeedState: StateFlow<List<CountDownEventModel>> =
        repository.readCountDownEvents()
            .map { events ->
                events.sortedBy { it.dateEpochSecondsUTC } // Sort by oldest to newest (ascending order)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    var editCountDownEventState = mutableStateOf(
        EditCountDownEventState(),
    )
        private set

    fun onEvent(event: HomeViewModelEvent) {
        when (event) {
            is HomeViewModelEvent.ResetEditCountDownEventState -> {
                editCountDownEventState.value = EditCountDownEventState()
            }

            is HomeViewModelEvent.ViewCountDownEvent -> {
                editCountDownEventState.value = EditCountDownEventState(
                    idHexString = event.idHexString,
                    name = event.name,
                    dateEpochSecondsUTC = event.dateEpochSecondsUTC
                )
            }

            is HomeViewModelEvent.ChangeCountDownEventName -> {
                editCountDownEventState.value =
                    editCountDownEventState.value.copy(name = event.newName)
//                editCountDownEventState.value.name = event.newName
            }

            HomeViewModelEvent.DeleteCountDownEvent -> {
                if (editCountDownEventState.value.idHexString != null) {
                    viewModelScope.launch {
                        repository.deleteCountDownEvent(editCountDownEventState.value.idHexString!!)
                        editCountDownEventState.value = EditCountDownEventState()
                    }
                } else {
                    editCountDownEventState.value = EditCountDownEventState()
                }
            }

            is HomeViewModelEvent.SaveChanges -> {
                viewModelScope.launch {
                    val result = repository.upsertCountDownEvent(
                        idHexString = editCountDownEventState.value.idHexString,
                        name = editCountDownEventState.value.name,
                        dateEpochSecondsUTC = event.dateEpochMillisUTC / 1000 // ui validates the date
                    )
                    editCountDownEventState.value = EditCountDownEventState()
                }
            }
        }
    }

    sealed class HomeViewModelEvent {
        data class ViewCountDownEvent(
            val idHexString: String,
            val name: String,
            val dateEpochSecondsUTC: Long
        ) : HomeViewModelEvent()

        data class ChangeCountDownEventName(val newName: String) : HomeViewModelEvent()

        data class SaveChanges(val dateEpochMillisUTC: Long) : HomeViewModelEvent()

        data object DeleteCountDownEvent : HomeViewModelEvent()

        data object ResetEditCountDownEventState : HomeViewModelEvent()
    }

    data class EditCountDownEventState(
        var idHexString: String? = null,
        var name: String? = null,
        var dateEpochSecondsUTC: Long? = null
    )
}