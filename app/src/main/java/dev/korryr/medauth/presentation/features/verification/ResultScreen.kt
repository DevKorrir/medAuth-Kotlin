package dev.korryr.medauth.presentation.features.verification

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.korryr.medauth.core.designsystem.theme.DangerRed
import dev.korryr.medauth.core.designsystem.theme.SuccessGreen
import dev.korryr.medauth.core.designsystem.theme.UnknownGray
import dev.korryr.medauth.core.designsystem.theme.WarningAmber
import dev.korryr.medauth.core.ui.components.ErrorStateView
import dev.korryr.medauth.core.ui.components.InfoCard
import dev.korryr.medauth.core.ui.components.LoadingShimmer
import dev.korryr.medauth.core.ui.components.PrimaryButton
import dev.korryr.medauth.data.repository.ResultStatus
import dev.korryr.medauth.core.ui.components.ResultStatusBadge
import dev.korryr.medauth.core.ui.components.SecondaryButton
import dev.korryr.medauth.data.repository.VerificationResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    onNavigateBack: () -> Unit,
    onReportSuspicious: () -> Unit,
    viewModel: ResultViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan Result", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (val state = uiState) {
                is ResultScreenState.Loading -> {
                    ResultLoadingView()
                }
                is ResultScreenState.Error -> {
                    ErrorStateView(
                        message = state.message,
                        onRetry = { viewModel.verifyCode() }
                    )
                }
                is ResultScreenState.Success -> {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { 50 })
                    ) {
                        ResultSuccessView(
                            result = state.result,
                            onReportSuspicious = onReportSuspicious,
                            onScanAnother = onNavigateBack
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ResultLoadingView() {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        LoadingShimmer(modifier = Modifier.size(120.dp).clip(CircleShape))
        Spacer(modifier = Modifier.height(32.dp))
        LoadingShimmer(modifier = Modifier.fillMaxWidth().height(40.dp).clip(MaterialTheme.shapes.medium))
        Spacer(modifier = Modifier.height(16.dp))
        LoadingShimmer(modifier = Modifier.fillMaxWidth(0.7f).height(24.dp).clip(MaterialTheme.shapes.small))
    }
}

@Composable
fun ResultSuccessView(
    result: VerificationResult,
    onReportSuspicious: () -> Unit,
    onScanAnother: () -> Unit
) {
    val (icon, color) = when (result.status) {
        ResultStatus.VERIFIED -> Icons.Default.CheckCircle to SuccessGreen
        ResultStatus.SUSPICIOUS -> Icons.Default.Warning to WarningAmber
        ResultStatus.INVALID -> Icons.Default.Error to DangerRed
        ResultStatus.UNKNOWN -> Icons.AutoMirrored.Filled.Help to UnknownGray
        else -> Icons.AutoMirrored.Filled.Help to UnknownGray
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        ResultStatusBadge(status = result.status)

        Spacer(modifier = Modifier.height(32.dp))

        if (result.drugName != null) {
            Text(
                text = result.drugName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = result.manufacturer ?: "Unknown Manufacturer",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        InfoCard(text = result.explanation)
        
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recommendation:",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = result.recommendedAction,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (result.status == ResultStatus.INVALID || result.status == ResultStatus.SUSPICIOUS) {
            PrimaryButton(
                text = "Report Suspicious Product",
                onClick = onReportSuspicious
            )
            Spacer(modifier = Modifier.height(16.dp))
            SecondaryButton(
                text = "Scan Again",
                onClick = onScanAnother
            )
        } else {
            PrimaryButton(
                text = "Scan Another",
                onClick = onScanAnother
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}
