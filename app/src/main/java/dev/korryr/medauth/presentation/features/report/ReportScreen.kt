package dev.korryr.medauth.presentation.features.report

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.korryr.medauth.core.designsystem.theme.SuccessGreen
import dev.korryr.medauth.core.ui.components.InfoCard
import dev.korryr.medauth.core.ui.components.PrimaryButton
import dev.korryr.medauth.core.ui.components.SecondaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    onNavigateBack: () -> Unit,
    viewModel: ReportViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val notes by viewModel.notes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report Product", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (uiState) {
                is ReportState.Idle -> {
                    ReportFormView(
                        notes = notes,
                        onNotesChange = viewModel::updateNotes,
                        onSubmit = viewModel::submitReport
                    )
                }
                is ReportState.Submitting -> {
                    LoadingSubmissionView()
                }
                is ReportState.Success -> {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically()
                    ) {
                        SuccessReportView(onDone = onNavigateBack)
                    }
                }
                is ReportState.Error -> {
                    // Similar to empty error state
                }
            }
        }
    }
}

@Composable
fun ReportFormView(
    notes: String,
    onNotesChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        InfoCard(
            text = "Reporting invalid codes helps prevent the distribution of counterfeit medication."
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Additional Details",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = notes,
            onValueChange = onNotesChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            placeholder = { Text("Where did you purchase this product? Any other anomalies?") },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            shape = MaterialTheme.shapes.medium,
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(24.dp))

        SecondaryButton(
            text = "Attach Evidence (Optional)",
            onClick = { /* TODO: Launch Image Picker */ }
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = "Submit Report",
            onClick = onSubmit,
            enabled = notes.isNotBlank()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun LoadingSubmissionView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Securely submitting your report...",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun SuccessReportView(onDone: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(SuccessGreen.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = SuccessGreen,
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Report Submitted",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Thank you for keeping the community safe. Your report has been sent securely.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        PrimaryButton(
            text = "Done",
            onClick = onDone
        )
    }
}
