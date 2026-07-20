package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.*
import com.example.ui.theme.*
import kotlinx.coroutines.launch
import kotlin.math.sin
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainLayout()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // App state
    var currentLang by remember { mutableStateOf(AppLang.FA) }
    var globalScore by remember { mutableStateOf(0) }
    val visitedPages = remember { mutableStateOf(setOf(Screen.HOME)) }

    // Screen navigation stack history for custom robust back navigation
    val navigationStack = remember { mutableStateListOf(Screen.HOME) }
    val currentScreen = navigationStack.lastOrNull() ?: Screen.HOME

    // Score pulse anim trigger
    var scorePulseTrigger by remember { mutableStateOf(0) }
    val scoreScale by animateFloatAsState(
        targetValue = if (scorePulseTrigger > 0) 1.25f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        finishedListener = { scorePulseTrigger = 0 },
        label = "scoreScale"
    )

    // Decorative floating background particles (musical notes)
    var particles by remember { mutableStateOf(List(12) { createRandomParticle() }) }
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(80)
            particles = particles.map { p ->
                val nextY = p.y - p.speed
                if (nextY < -0.1f) {
                    createRandomParticle()
                } else {
                    p.copy(y = nextY, drift = p.drift + p.driftSpeed)
                }
            }
        }
    }

    // Modal Drawer Sheet sidebar mapping
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MusicSurface,
                drawerTonalElevation = 8.dp,
                modifier = Modifier.width(280.dp)
            ) {
                SidebarContent(
                    lang = currentLang,
                    currentScreen = currentScreen,
                    visitedPages = visitedPages.value,
                    onNavigate = { screen ->
                        if (navigationStack.lastOrNull() != screen) {
                            navigationStack.add(screen)
                            visitedPages.value = visitedPages.value + screen
                        }
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CustomTopBar(
                    lang = currentLang,
                    currentScreen = currentScreen,
                    navigationStackSize = navigationStack.size,
                    visitedCount = visitedPages.value.size,
                    globalScore = globalScore,
                    scoreScale = scoreScale,
                    onToggleLang = { currentLang = if (currentLang == AppLang.FA) AppLang.EN else AppLang.FA },
                    onOpenDrawer = { scope.launch { drawerState.open() } },
                    onBack = {
                        if (navigationStack.size > 1) {
                            navigationStack.removeAt(navigationStack.size - 1)
                        }
                    }
                )
            },
            containerColor = MusicDarkBg,
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Background floating music notes layer
                CanvasParticlesBackdrop(particles = particles)

                // Screen routing
                when (currentScreen) {
                    Screen.HOME -> {
                        HomeScreen(
                            lang = currentLang,
                            visitedPages = visitedPages.value,
                            onNavigate = { screen ->
                                if (navigationStack.lastOrNull() != screen) {
                                    navigationStack.add(screen)
                                    visitedPages.value = visitedPages.value + screen
                                }
                            }
                        )
                    }

                    Screen.QUIZ -> {
                        QuizScreen(
                            lang = currentLang,
                            onScoreEarned = { scoreAdded ->
                                if (scoreAdded > 0) {
                                    globalScore += scoreAdded
                                    scorePulseTrigger = 1
                                }
                            }
                        )
                    }

                    Screen.TOOLS_HUB -> {
                        ToolsHubScreen(
                            lang = currentLang,
                            onNavigate = { screen ->
                                if (navigationStack.lastOrNull() != screen) {
                                    navigationStack.add(screen)
                                    visitedPages.value = visitedPages.value + screen
                                }
                            }
                        )
                    }

                    Screen.TOOL_CHORDS -> {
                        ToolChordsScreen(lang = currentLang)
                    }

                    Screen.TOOL_SCALES -> {
                        ToolScalesScreen(lang = currentLang)
                    }

                    Screen.TOOL_INTERVALS -> {
                        ToolIntervalsScreen(lang = currentLang)
                    }

                    Screen.TOOL_MODES -> {
                        ToolModesScreen(lang = currentLang)
                    }

                    Screen.TOOL_CIRCLE -> {
                        ToolCircleScreen(lang = currentLang)
                    }

                    else -> {
                        // Lesson Screen router (Alteration, Notes, etc.)
                        val activeLesson = LESSONS_LIST.find { it.screen == currentScreen }
                        if (activeLesson != null) {
                            LessonScreen(lesson = activeLesson, lang = currentLang)
                        } else {
                            // Fallback
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (currentLang == AppLang.FA) "درس در دست ساخت..." else "Lesson is coming soon...",
                                    color = OnMusicMuted
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Sidebar Drawer Content
@Composable
fun SidebarContent(
    lang: AppLang,
    currentScreen: Screen,
    visitedPages: Set<Screen>,
    onNavigate: (Screen) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MusicSurface)
    ) {
        // Sidebar Header Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MusicSurfaceVariant)
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Animated bouncing M3 equalizer visual bars (ambient theme)
                SidebarEqualizer()

                Column(
                    horizontalAlignment = if (lang == AppLang.FA) Alignment.End else Alignment.Start,
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Text(
                        text = if (lang == AppLang.FA) "تئوری موسیقی" else "Music Theory",
                        color = OnMusicDarkBg,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (lang == AppLang.FA) "آموزش جامع + ابزارها" else "Full Course & Tools",
                        color = OnMusicMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        HorizontalDivider(color = MusicBorder, thickness = 1.dp)

        // Navigation Categories List
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(vertical = 12.dp)
        ) {
            // Category: START
            CategorySection(
                title = if (lang == AppLang.FA) "شروع" else "Start",
                lang = lang,
                color = MusicAccentGold
            ) {
                SidebarNavItem(Screen.HOME, "🏠", "خانه", "Home", currentScreen, visitedPages, lang, onNavigate)
            }

            // Category: BASICS
            CategorySection(
                title = if (lang == AppLang.FA) "پایه" else "Basics",
                lang = lang,
                color = MusicBlue
            ) {
                SidebarNavItem(Screen.ALTERATION, "♯", "آلتراسیون", "Alteration", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.NOTES, "𝅝", "انواع نت‌ها", "Note Types", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.METER, "⏱", "میزان و ریتم", "Meter & Rhythm", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.SOUND, "🔊", "صدای موسیقیایی", "Musical Sound", currentScreen, visitedPages, lang, onNavigate)
            }

            // Category: SCALES
            CategorySection(
                title = if (lang == AppLang.FA) "گام‌ها" else "Scales",
                lang = lang,
                color = MusicAccentPurple
            ) {
                SidebarNavItem(Screen.MAJOR, "☀️", "گام ماژور", "Major Scale", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.MINOR, "🌙", "گام مینور", "Minor Scale", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.PENTATONIC, "⭐", "پنتاتونیک و بلوز", "Pentatonic & Blues", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.GMODES, "🎭", "مدهای کلیسایی", "Church Modes", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.KEYSIG, "🔑", "دیزها و بمل‌ها", "Key Signatures", currentScreen, visitedPages, lang, onNavigate)
            }

            // Category: HARMONY
            CategorySection(
                title = if (lang == AppLang.FA) "هارمونی" else "Harmony",
                lang = lang,
                color = MusicGreen
            ) {
                SidebarNavItem(Screen.DEGREES, "🎯", "درجات گام", "Scale Degrees", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.CHORDS, "🎸", "ساختار آکورد", "Chord Structure", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.INTERVALS, "📏", "فواصل مطبوع", "Consonant Intervals", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.POLYPHONY, "🎼", "انواع موسیقی", "Music Types", currentScreen, visitedPages, lang, onNavigate)
            }

            // Category: PRACTICE
            CategorySection(
                title = if (lang == AppLang.FA) "تمرین" else "Practice",
                lang = lang,
                color = MusicPink
            ) {
                SidebarNavItem(Screen.QUIZ, "🧠", "آزمون", "Quiz", currentScreen, visitedPages, lang, onNavigate)
            }

            // Category: TOOLS
            CategorySection(
                title = if (lang == AppLang.FA) "🛠 ابزارها" else "🛠 Tools",
                lang = lang,
                color = MusicRed
            ) {
                SidebarNavItem(Screen.TOOLS_HUB, "🗂", "همه ابزارها", "All Tools", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.TOOL_CHORDS, "🎸", "آکوردهای گام", "Scale Chords", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.TOOL_SCALES, "🎵", "گام‌نماها", "Scale Viewer", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.TOOL_INTERVALS, "📐", "جدول فواصل", "Interval Table", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.TOOL_MODES, "🎭", "ابزار مدها", "Modes Tool", currentScreen, visitedPages, lang, onNavigate)
                SidebarNavItem(Screen.TOOL_CIRCLE, "⭕", "دایره پنجم‌ها", "Circle of Fifths", currentScreen, visitedPages, lang, onNavigate)
            }
        }
    }
}

@Composable
fun CategorySection(
    title: String,
    lang: AppLang,
    color: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = title,
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 6.dp),
            textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left
        )
        content()
    }
}

@Composable
fun SidebarNavItem(
    screen: Screen,
    icon: String,
    titleFa: String,
    titleEn: String,
    currentScreen: Screen,
    visitedPages: Set<Screen>,
    lang: AppLang,
    onNavigate: (Screen) -> Unit
) {
    val isActive = currentScreen == screen
    val isVisited = visitedPages.contains(screen)

    val itemBg = if (isActive) MusicAccentPurple.copy(alpha = 0.12f) else Color.Transparent
    val itemTextColor = if (isActive) MusicAccentPurple else OnMusicMuted

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(itemBg)
            .clickable { onNavigate(screen) }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (lang != AppLang.FA) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = icon, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = titleEn,
                    color = itemTextColor,
                    fontSize = 13.sp,
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium
                )
            }
        }

        val dotBgColor = if (isVisited) MusicGreen else Color.Transparent
        val dotBorderColor = if (isVisited) Color.Transparent else MusicBorder

        // Visited completion green glowing dot
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(RoundedCornerShape(50))
                .background(dotBgColor)
                .border(0.5.dp, dotBorderColor, RoundedCornerShape(50))
        )

        if (lang == AppLang.FA) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = titleFa,
                    color = itemTextColor,
                    fontSize = 13.sp,
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = icon, fontSize = 14.sp)
            }
        }
    }
}

// Bouncing Sidebar Equalizer Visualizer Component
@Composable
fun SidebarEqualizer() {
    val transition = rememberInfiniteTransition(label = "equalizer")

    @Composable
    fun bounceAnim(delayMs: Int): State<Float> {
        return transition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(600, delayMillis = delayMs, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bounce"
        )
    }

    val h1 by bounceAnim(0)
    val h2 by bounceAnim(150)
    val h3 by bounceAnim(300)
    val h4 by bounceAnim(100)

    Row(
        modifier = Modifier
            .height(20.dp)
            .padding(end = 4.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        listOf(h1, h2, h3, h4).forEach { heightPercent ->
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .fillMaxHeight(heightPercent)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(MusicAccentGold, MusicAccentPurple)
                        )
                    )
            )
        }
    }
}


// ==========================================
// CUSTOM TOP BAR WITH PROGRESS & STAR BADGES
// ==========================================
@Composable
fun CustomTopBar(
    lang: AppLang,
    currentScreen: Screen,
    navigationStackSize: Int,
    visitedCount: Int,
    globalScore: Int,
    scoreScale: Float,
    onToggleLang: () -> Unit,
    onOpenDrawer: () -> Unit,
    onBack: () -> Unit
) {
    val screenInfo = SCREEN_INFO_MAP[currentScreen] ?: ScreenInfo("عنوان", "Title", "بخش", "Section")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MusicSurface.copy(alpha = 0.92f))
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hamburger Menu toggle or Back Arrow
            if (currentScreen == Screen.HOME) {
                IconButton(onClick = onOpenDrawer) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = OnMusicDarkBg)
                }
            } else {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = OnMusicDarkBg,
                        modifier = Modifier.rotate(if (lang == AppLang.FA) 180f else 0f)
                    )
                }
            }

            // Screen Info & Badges
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                // Category badge tag
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(MusicAccentPurple.copy(alpha = 0.15f))
                        .border(1.dp, MusicAccentPurple.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (lang == AppLang.FA) screenInfo.badgeFa else screenInfo.badgeEn,
                        color = MusicAccentPurple,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Title
                Text(
                    text = if (lang == AppLang.FA) screenInfo.titleFa else screenInfo.titleEn,
                    color = OnMusicDarkBg,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Progress bar, score, and language selector
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Star Score Badge
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(MusicAccentGold.copy(alpha = 0.1f))
                        .border(1.dp, MusicAccentGold.copy(alpha = 0.25f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .scaleAndPulse(scoreScale),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Stars",
                        tint = MusicAccentGold,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = globalScore.toString(),
                        color = MusicAccentGold,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Bilingual Toggle Selector Button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MusicSurfaceVariant)
                        .border(1.dp, MusicBorder, RoundedCornerShape(8.dp))
                        .clickable { onToggleLang() }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (lang == AppLang.FA) "EN" else "FA",
                        color = MusicAccentGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

        // Flat animated overall app completion progress bar
        val progressPercent = (visitedCount.toFloat() / Screen.values().size)
        val animatedProgress by animateFloatAsState(
            targetValue = progressPercent,
            animationSpec = spring(stiffness = Spring.StiffnessLow),
            label = "progress"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(MusicBorder)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(MusicAccentPurple, MusicAccentGold)
                        )
                    )
            )
        }
        HorizontalDivider(color = MusicBorder, thickness = 0.5.dp)
    }
}

// Particle details for ambient backdrop
data class BackgroundParticle(
    val id: Int,
    val x: Float, // 0..1 relative width
    var y: Float, // 0..1 relative height
    val text: String,
    val speed: Float,
    var drift: Float,
    val driftSpeed: Float,
    val size: Float,
    val color: Color
)

fun createRandomParticle(): BackgroundParticle {
    val notes = listOf("♩", "🎵", "🎶", "♭", "♯")
    val colors = listOf(MusicAccentGold, MusicAccentPurple, MusicBlue, MusicGreen, MusicPink)
    return BackgroundParticle(
        id = Random.nextInt(),
        x = Random.nextFloat(),
        y = 1f + Random.nextFloat() * 0.1f, // spawn slightly below the screen
        text = notes.random(),
        speed = 0.001f + Random.nextFloat() * 0.002f,
        drift = Random.nextFloat() * 100f,
        driftSpeed = 0.01f + Random.nextFloat() * 0.02f,
        size = 12f + Random.nextFloat() * 14f,
        color = colors.random().copy(alpha = 0.04f + Random.nextFloat() * 0.06f)
    )
}

// Draw ambient backdrop particles on canvas
@Composable
fun CanvasParticlesBackdrop(particles: List<BackgroundParticle>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        particles.forEach { p ->
            val px = p.x * w + sin(p.drift) * 15f
            val py = p.y * h

            // Draw note text on canvas
            drawContext.canvas.nativeCanvas.drawText(
                p.text,
                px,
                py,
                android.graphics.Paint().apply {
                    color = p.color.toArgb()
                    textSize = p.size.dp.toPx()
                    typeface = android.graphics.Typeface.DEFAULT_BOLD
                }
            )
        }
    }
}

// Inline modifier extensions
fun Modifier.scaleAndPulse(scale: Float) = this.then(
    Modifier.graphicsLayer(
        scaleX = scale,
        scaleY = scale
    )
)

fun Color.toArgb(): Int {
    return (this.alpha * 255.0f + 0.5f).toInt() shl 24 or
           ((this.red * 255.0f + 0.5f).toInt() shl 16) or
           ((this.green * 255.0f + 0.5f).toInt() shl 8) or
           (this.blue * 255.0f + 0.5f).toInt()
}
