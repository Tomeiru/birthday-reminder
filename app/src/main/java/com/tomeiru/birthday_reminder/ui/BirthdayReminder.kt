package com.tomeiru.birthday_reminder.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tomeiru.birthday_reminder.batch_import.BatchImportActivity
import com.tomeiru.birthday_reminder.batch_import.BirthdayCSV
import com.tomeiru.birthday_reminder.birthday_catalog.BirthdayCatalog
import com.tomeiru.birthday_reminder.birthday_catalog.CatalogTopBar
import com.tomeiru.birthday_reminder.birthday_entry.BirthdayEntryActivity
import com.tomeiru.birthday_reminder.homepage.Homepage
import com.tomeiru.birthday_reminder.homepage.HomepageTopBar
import com.tomeiru.birthday_reminder.settings.Settings
import com.tomeiru.birthday_reminder.settings.SettingsTopBar
import kotlinx.coroutines.launch
import java.io.InputStream


fun openBirthdayEntryActivity(context: Context) {
    val intent = Intent(context, BirthdayEntryActivity::class.java)
    val bundle = Bundle()
    bundle.putBoolean("edit", false)
    intent.putExtras(bundle)
    context.startActivity(intent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayReminder(defaultScreen: Int) {
    val context = LocalContext.current
    var screen by remember { mutableIntStateOf(defaultScreen) }
    var showEntryDialog: Boolean by remember { mutableStateOf(false) }
    var showBatchImportHelp: Boolean by remember { mutableStateOf(false) }
    val scrollBehavior = enterAlwaysScrollBehavior(rememberTopAppBarState())
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val batchImportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            if (uri == null) {
                scope.launch {
                    snackbarHostState.showSnackbar("No file provided!")
                }
                return@rememberLauncherForActivityResult
            }
            val validationOutput = context.contentResolver.openInputStream(uri)
                .use<InputStream?, BirthdayCSV.ValidationOutput?> {
                    if (it == null) return@use null
                    return@use BirthdayCSV().validateFormat(it.readBytes().decodeToString())
                }
            if (validationOutput == null || !validationOutput.isValid) {
                scope.launch {
                    snackbarHostState.showSnackbar("File could not be read or is invalid!")
                }
                return@rememberLauncherForActivityResult
            }
            val intent = Intent(context, BatchImportActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("validationOutput", validationOutput)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    )
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            when (screen) {
                0 -> HomepageTopBar()
                1 -> CatalogTopBar(scrollBehavior)
                2 -> SettingsTopBar()
            }
        },
        bottomBar = {
            if (screen < 3) {
                BottomNavigationBar(screen) {
                    screen = it
                    showEntryDialog = false
                }
            }
        },
        floatingActionButton = {
            if (screen == 1) {
                FloatingActionButton(onClick = {
                    showEntryDialog = true
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Birthday")
                }
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(8.dp)
                .padding(innerPadding),
        ) {
            when (screen) {
                0 -> Homepage()
                1 -> BirthdayCatalog()
                2 -> Settings()
            }
        }
        if (screen == 1 && showEntryDialog && !showBatchImportHelp) {
            EntryDialog(
                innerPadding = innerPadding,
                onDismissRequest = { showEntryDialog = false },
                onSingleEntryClick = {
                    showEntryDialog = false
                    openBirthdayEntryActivity(context)
                },
                onBatchImportClick = {
                    showEntryDialog = false
                    batchImportLauncher.launch(arrayOf("text/csv", "text/plain"))
                },
                onBatchImportHelpClick = {
                    showBatchImportHelp = true
                }
            )
        }
        if (screen == 1 && showBatchImportHelp) {
            BatchImportHelpDialog(
                onDismissRequest = { showBatchImportHelp = false }
            )
        }
    }
}