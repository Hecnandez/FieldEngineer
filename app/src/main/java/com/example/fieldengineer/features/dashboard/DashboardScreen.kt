package com.example.fieldengineer.features.dashboard

import android.R
import android.net.http.SslCertificate
import android.view.RoundedCorner
import android.widget.ScrollView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldengineer.core.data.local.dao.PartCatalogDao
import com.example.fieldengineer.core.data.local.entity.ConsumedPartEntity
import com.example.fieldengineer.core.data.local.entity.PartCatalogEntity
import com.example.fieldengineer.core.data.local.entity.WorkOrderEntity
import com.example.fieldengineer.ui.theme.ACCENT
import com.example.fieldengineer.ui.theme.Background
import com.example.fieldengineer.ui.theme.Border
import com.example.fieldengineer.ui.theme.SECONDARY
import com.example.fieldengineer.ui.theme.Text
import okhttp3.internal.notify

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Header()
        Row {
            WorkOrders(uiState.workOrders, modifier = Modifier.weight(0.25f))
            WorkOrderDetail(mockWorkOrders.first(), modifier = Modifier.weight(0.75f))
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(Background)
            .padding(16.dp)
    ) {
        Text("Field Engineer", color = Text, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun WorkOrders(workOrders: List<WorkOrderEntity>, modifier: Modifier) {
    Row(
        modifier = modifier.background(Background)
            .padding(12.dp)
    ) {
        Column {
            Text("WORK ORDERS", color = Text)
            Text("${workOrders.size} ACTIVE", color = Text, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(items = workOrders) { workOrder ->
                    WorkOrderItem(workOrder)
                }
            }
        }
        Row(
            modifier = Modifier
                .border(width = 1.dp, color = SECONDARY)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Sync data",
                tint = SECONDARY
            )
            Text("SYNC", color = SECONDARY)
        }
    }
}

@Composable
fun WorkOrderItem(workOrderEntity: WorkOrderEntity) {
    Column(
        modifier = Modifier.padding(12.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusBadge(workOrderEntity.status)
            Text(text = workOrderEntity.id, color = Text)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = if (workOrderEntity.isSynced) "LOCAL" else "SYNCED",
                color = Text
            )
        }
        Text(text = workOrderEntity.assetId, color = Text)
        Text(text = workOrderEntity.title, color = Text)
    }
}

@Composable
fun WorkOrderDetail(workOrder: WorkOrderEntity, modifier: Modifier) {
    val spacerModifier = Modifier.fillMaxWidth().height(1.dp).background(SECONDARY)
    Column(
        modifier = modifier.fillMaxSize()
            .background(Background)
    ) {
        WorkOrderHeader(workOrder)
        SchematicView(workOrder.assetId)
        Spacer(spacerModifier)
        PartsConsumed(mockConsumedParts, mockPartCatalog)
        Spacer(spacerModifier)
        Notes(workOrder.technicianNotes)
    }
}

@Composable
fun WorkOrderHeader(workOrder: WorkOrderEntity) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = workOrder.assetId, color = Text, fontSize = 21.sp, fontWeight = FontWeight.Bold)
            StatusBadge(workOrder.status)
        }
        Row(
            modifier = Modifier
                .border(width = 2.dp, color = Color(0x0FC2C2C2))
                .padding(12.dp)
        ) {
            Text("RESET", color = SECONDARY)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Row(modifier = Modifier
            .background(color = ACCENT)
            .padding(12.dp)
        ) {
            Text("SUBMIT")
        }
    }
}

@Composable
fun SchematicView(assetId: String) {
    Box {

    }
}

@Composable
fun StatusBadge(status: String) {
    val color = Color(0xFFFF5722)
    when (status.lowercase()) {
        "critical" -> Color(0xFFFF5722)
        "pending" -> Color(0xFFFF9800)
        "completed" -> Color(0xFF777777)
    }
    Text(status, modifier = Modifier
        .background(color = color)
        .padding(10.dp, 6.dp),
        fontSize = 12.sp,
        color = Text
    )
}

@Composable
fun PartsConsumed(partsConsumed: List<ConsumedPartEntity>, catalogParts: List<PartCatalogEntity>) {
    Column(modifier = Modifier.padding(12.dp)) {
        Text(text = "PARTS CONSUMED", color = Text, modifier = Modifier.padding(vertical = 10.dp))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(items = partsConsumed) { part ->
                val catalogPart = catalogParts.find { it -> it.id == part.partId }
                if (catalogPart != null) {
                    PartItem(catalogPart, part.quantityUsed)
                }

            }
            items(items = catalogParts) { part ->
                PartItem(part, 0)
            }
        }
    }
}

@Composable
fun PartItem(part: PartCatalogEntity, consumedParts: Int) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .border(width = 2.dp, color = Border)
            .padding(12.dp)
    ) {
        Text(part.partNumber, color = Text)
        Text(part.name, color = Text)
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .border(width = 1.dp, color = SECONDARY)
                    .padding(12.dp)
                    .clickable(onClick = {})
            ){
                Text("-", color = SECONDARY)
            }
            Text(text = consumedParts.toString(), color = Text, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Box(
                modifier = Modifier.background(Color(0x6BFFEB3B))
                    .border(width = 1.dp, color = Color(0xFFFFEB3B))
                    .padding(12.dp)
                    .clickable(onClick = {})
            ) {
                Text("+")
            }
        }
    }
}

@Composable
fun Notes(notes: String) {
    val scrollState = rememberScrollState()
    Column {
        Row(modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("TECHNICIAN OPERATIONAL NOTES", color = Text, modifier = Modifier.weight(1f))
            Row(modifier = Modifier
                .background(Color(0x609A8D37))
                .border(width = 2.dp, color = Color(0xFFFFE300))
                .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("SAVE DRAFT", color = Color(0xFFFFE300))
            }
        }
        Column(Modifier.verticalScroll(scrollState)
            .padding(12.dp)
        ) {
            Text(text = notes, color = Text)
        }
    }
}

@Preview(showBackground = true, widthDp = 1280, heightDp = 720)
@Composable
fun DashboardScreenPreview() {
    //DashboardScreen()
}