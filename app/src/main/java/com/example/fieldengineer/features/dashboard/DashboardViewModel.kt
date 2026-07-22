package com.example.fieldengineer.features.dashboard

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fieldengineer.core.data.local.entity.WorkOrderEntity
import com.example.fieldengineer.core.data.repository.AssetRepositoryImpl
import com.example.fieldengineer.core.data.repository.ConsumedPartRepositoryImpl
import com.example.fieldengineer.core.data.repository.InventoryRepositoryImpl
import com.example.fieldengineer.core.data.repository.PartCatalogRepositoryImpl
import com.example.fieldengineer.core.data.repository.WorkOrderRepositoryImpl
import com.example.fieldengineer.data.types.WorkOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState (
    val workOrders: List<WorkOrderEntity> = emptyList()
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val workOrderRepository: WorkOrderRepositoryImpl,
    private val assetRepository: AssetRepositoryImpl,
    private val inventoryRepository: InventoryRepositoryImpl,
    private val consumedPartRepository: ConsumedPartRepositoryImpl,
    private val partCatalogRepository: PartCatalogRepositoryImpl
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadWorkOrders()
    }

    fun loadWorkOrders() {
        viewModelScope.launch {
            workOrderRepository.getAllActiveWorkOrders().collect { orders ->
                _uiState.update { state ->
                    state.copy(
                        workOrders = orders
                    )
                }
            }
        }

    }
}