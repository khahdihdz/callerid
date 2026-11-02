package com.khahdihdz.callerid

import android.app.role.RoleManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khahdihdz.callerid.data.LocalDatabase
import com.khahdihdz.callerid.data.PhoneNumberEntity
import com.khahdihdz.callerid.data.PhoneRepository
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val roleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()) {
                CallerIdHome(onEnableScreening = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        val manager = getSystemService(Context.ROLE_SERVICE) as RoleManager
                        if (manager.isRoleAvailable(RoleManager.ROLE_CALL_SCREENING) &&
                            !manager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)) {
                            roleLauncher.launch(manager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING))
                        }
                    }
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CallerIdHome(onEnableScreening: () -> Unit) {
    var query by remember { mutableStateOf("") }
    var entries by remember { mutableStateOf<List<PhoneNumberEntity>>(emptyList()) }
    var status by remember { mutableStateOf("Dữ liệu được xử lý cục bộ trên thiết bị.") }
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember { PhoneRepository(LocalDatabase.get(context).phoneDao()) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Caller ID & Spam Shield") }) },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Home, null) }, label = { Text("Trang chủ") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Call, null) }, label = { Text("Cuộc gọi") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Search, null) }, label = { Text("Tra cứu") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Block, null) }, label = { Text("Chặn spam") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Settings, null) }, label = { Text("Cài đặt") })
            }
        }
    ) { insets ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(insets).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                ElevatedCard {
                    Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Bảo vệ cuộc gọi", style = MaterialTheme.typography.titleLarge)
                        Text("Nhận diện trên thiết bị · Offline-first")
                        Button(onClick = onEnableScreening, modifier = Modifier.fillMaxWidth()) {
                            Icon(Icons.Default.Security, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Thiết lập sàng lọc cuộc gọi")
                        }
                        Text(status, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            item {
                Text("Tra cứu số điện thoại", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = { Text("Nhập số điện thoại hoặc tên") },
                    trailingIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                entries = repository.search(query)
                                status = if (entries.isEmpty()) "Không tìm thấy trong dữ liệu cục bộ." else "Tìm thấy ${entries.size} kết quả."
                            }
                        }) { Icon(Icons.Default.Search, contentDescription = "Tìm kiếm") }
                    }
                )
            }
            items(entries) { entry ->
                ListItem(
                    headlineContent = { Text(entry.displayName ?: entry.number) },
                    supportingContent = { Text("${entry.number} · ${entry.category} · Điểm spam ${entry.spamScore}/100") }
                )
                HorizontalDivider()
            }
            item {
                Text("Lưu ý", style = MaterialTheme.typography.titleMedium)
                Text("Ứng dụng chỉ nhận diện được số có trong dữ liệu cục bộ. Không có sẵn cơ sở dữ liệu danh tính thuê bao toàn cầu.")
            }
        }
    }
}
