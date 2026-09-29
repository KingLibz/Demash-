package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.LanguageManager
import com.example.ui.components.DzimbabweTopBar
import com.example.ui.components.ProjectDetailModal
import com.example.ui.components.ServiceDetailModal
import com.example.ui.screens.AdminControlRoomScreen
import com.example.ui.screens.BookingContactScreen
import com.example.ui.screens.EstimatorScreen
import com.example.ui.screens.GuidesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ShowcaseScreen
import com.example.ui.screens.V60SingularityWebScreen
import com.example.ui.theme.DzimbabwePoolsTheme
import com.example.ui.theme.PoolAqua
import com.example.ui.theme.PoolDarkBg
import com.example.ui.theme.PoolGold
import com.example.ui.theme.PoolSurface
import com.example.ui.theme.PoolTextMuted
import com.example.ui.theme.PoolTextPrimary
import com.example.viewmodel.MainViewModel

data class NavTab(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val tag: String
)

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DzimbabwePoolsTheme {
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppScreen(viewModel: MainViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val isAdminUnlocked by viewModel.isAdminUnlocked.collectAsState()
    val activeServiceModal by viewModel.activeServiceModal.collectAsState()
    val activeProjectModal by viewModel.activeProjectModal.collectAsState()
    val toastMsg by viewModel.toastMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(toastMsg) {
        toastMsg?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearToast()
        }
    }

    // BackHandler to navigate to home tab if on secondary tab
    BackHandler(enabled = currentTab != 0) {
        viewModel.setTab(0)
    }

    val baseTabs = mutableListOf(
        NavTab(LanguageManager.t("nav_explore", currentLanguage), Icons.Filled.Home, Icons.Outlined.Home, "nav_home"),
        NavTab(LanguageManager.t("nav_estimator", currentLanguage), Icons.Filled.Calculate, Icons.Outlined.Calculate, "nav_estimator"),
        NavTab(LanguageManager.t("nav_showcase", currentLanguage), Icons.Filled.PhotoLibrary, Icons.Outlined.PhotoLibrary, "nav_showcase"),
        NavTab(LanguageManager.t("nav_guides", currentLanguage), Icons.Filled.MenuBook, Icons.Outlined.MenuBook, "nav_guides"),
        NavTab(LanguageManager.t("nav_booking", currentLanguage), Icons.Filled.DateRange, Icons.Outlined.DateRange, "nav_booking"),
        NavTab("V60 Web", Icons.Filled.Star, Icons.Outlined.Star, "nav_v60_web")
    )

    if (isAdminUnlocked) {
        baseTabs.add(
            NavTab("DZ Manager", Icons.Filled.Security, Icons.Outlined.Security, "nav_admin")
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(PoolDarkBg),
        topBar = {
            DzimbabweTopBar(
                onBookClick = { viewModel.setTab(4) },
                currentLanguage = currentLanguage,
                onLanguageChange = { viewModel.setLanguage(it) },
                isAdminActive = isAdminUnlocked,
                onLogoLongClick = { viewModel.toggleAdminMode(!isAdminUnlocked) }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = PoolSurface,
                modifier = Modifier
                    .navigationBarsPadding()
                    .testTag("bottom_nav_bar")
            ) {
                baseTabs.forEachIndexed { index, tab ->
                    val isSelected = currentTab == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.setTab(index) },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                contentDescription = tab.title,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                maxLines = 1
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PoolDarkBg,
                            selectedTextColor = PoolGold,
                            indicatorColor = PoolGold,
                            unselectedIconColor = PoolTextMuted,
                            unselectedTextColor = PoolTextMuted
                        ),
                        modifier = Modifier.testTag(tab.tag)
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(PoolDarkBg)
        ) {
            AnimatedContent(
                targetState = currentTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { targetIndex ->
                when (targetIndex) {
                    0 -> HomeScreen(
                        viewModel = viewModel,
                        onNavigateToEstimator = { viewModel.setTab(1) },
                        onNavigateToBooking = { viewModel.setTab(4) },
                        onNavigateToShowcase = { viewModel.setTab(2) },
                        onNavigateToV60Web = { viewModel.setTab(5) }
                    )
                    1 -> EstimatorScreen(viewModel = viewModel)
                    2 -> ShowcaseScreen(viewModel = viewModel)
                    3 -> GuidesScreen(viewModel = viewModel)
                    4 -> BookingContactScreen(viewModel = viewModel)
                    5 -> V60SingularityWebScreen(onNavigateBack = { viewModel.setTab(0) })
                    6 -> AdminControlRoomScreen(viewModel = viewModel)
                    else -> HomeScreen(
                        viewModel = viewModel,
                        onNavigateToEstimator = { viewModel.setTab(1) },
                        onNavigateToBooking = { viewModel.setTab(4) },
                        onNavigateToShowcase = { viewModel.setTab(2) },
                        onNavigateToV60Web = { viewModel.setTab(5) }
                    )
                }
            }
        }

        // Service Detail Modal
        activeServiceModal?.let { service ->
            ServiceDetailModal(
                service = service,
                onDismiss = { viewModel.openServiceDetails(null) },
                onBookService = { viewModel.setTab(4) }
            )
        }

        // Project Detail Modal
        activeProjectModal?.let { project ->
            ProjectDetailModal(
                project = project,
                onDismiss = { viewModel.openProjectDetails(null) },
                onQuoteThis = {
                    viewModel.openProjectDetails(null)
                    viewModel.setTab(1)
                }
            )
        }
    }
}
