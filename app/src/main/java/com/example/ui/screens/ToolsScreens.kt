package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlin.math.*

// ==========================================
// 1. TOOLS HUB SCREEN
// ==========================================
@Composable
fun ToolsHubScreen(
    lang: AppLang,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hub Header
        Text(
            text = if (lang == AppLang.FA) "ابزارهای موسیقیایی تعاملی" else "Interactive Music Tools",
            color = OnMusicDarkBg,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(if (lang == AppLang.FA) Alignment.End else Alignment.Start)
        )

        val toolsList = listOf(
            ToolHubItem(Screen.TOOL_CHORDS, "🎸", "آکوردهای گام", "Scale Chords", "ماژور و مینور با Roman numerals", "Major/Minor chords with roman numerals"),
            ToolHubItem(Screen.TOOL_SCALES, "🎵", "گام‌نماها", "Scale Viewer", "نت‌های همه گام‌های موسیقی", "Full visual guide of scale notes"),
            ToolHubItem(Screen.TOOL_INTERVALS, "📐", "جدول فواصل", "Interval Table", "جدول کامل با فواصل نیم‌پرده‌ای", "Complete semitone interval matrix"),
            ToolHubItem(Screen.TOOL_MODES, "🎭", "ابزار مدهای کلیسایی", "Modes Tool", "۷ مد با آکوردها و توضیحات کامل", "7 church modes with chords and formulas"),
            ToolHubItem(Screen.TOOL_CIRCLE, "⭕", "دایره پنجم‌ها", "Circle of Fifths", "چرخ تعاملی گام‌ها و علائم سرکلید", "Interactive visual chord key wheel")
        )

        toolsList.forEach { tool ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(tool.screen) },
                colors = CardDefaults.cardColors(containerColor = MusicSurface),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MusicBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (lang != AppLang.FA) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MusicAccentPurple.copy(alpha = 0.1f))
                                .border(1.dp, MusicAccentPurple.copy(alpha = 0.25f), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = tool.icon, fontSize = 22.sp)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = if (lang == AppLang.FA) Alignment.End else Alignment.Start
                    ) {
                        Text(
                            text = if (lang == AppLang.FA) tool.titleFa else tool.titleEn,
                            color = MusicAccentGold,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (lang == AppLang.FA) tool.subFa else tool.subEn,
                            color = OnMusicMuted,
                            fontSize = 11.5.sp,
                            textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left
                        )
                    }

                    if (lang == AppLang.FA) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MusicAccentGold.copy(alpha = 0.1f))
                                .border(1.dp, MusicAccentGold.copy(alpha = 0.25f), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = tool.icon, fontSize = 22.sp)
                        }
                    }
                }
            }
        }
    }
}

data class ToolHubItem(
    val screen: Screen,
    val icon: String,
    val titleFa: String,
    val titleEn: String,
    val subFa: String,
    val subEn: String
)


// ==========================================
// 2. SCALE CHORDS TOOL
// ==========================================
@Composable
fun ToolChordsScreen(
    lang: AppLang,
    modifier: Modifier = Modifier
) {
    var selectedMajorKey by remember { mutableStateOf("C") }
    var selectedMinorKey by remember { mutableStateOf("Am") }

    var isMajorMenuExpanded by remember { mutableStateOf(false) }
    var isMinorMenuExpanded by remember { mutableStateOf(false) }

    val majorKeys = MAJOR_CHORDS_MAP.keys.toList()
    val minorKeys = MINOR_CHORDS_MAP.keys.toList()

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Grid with Major/Minor selects
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Major Selector Column
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (lang == AppLang.FA) "☀️ گام ماژور" else "☀️ Major Scale",
                    color = OnMusicDarkBg,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(if (lang == AppLang.FA) Alignment.End else Alignment.Start)
                )
                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(MusicSurface)
                        .border(1.dp, MusicBorder, RoundedCornerShape(10.dp))
                        .clickable { isMajorMenuExpanded = true }
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown", tint = OnMusicMuted)
                        Text(
                            text = "$selectedMajorKey Major",
                            color = MusicAccentGold,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    DropdownMenu(
                        expanded = isMajorMenuExpanded,
                        onDismissRequest = { isMajorMenuExpanded = false },
                        modifier = Modifier.background(MusicSurfaceVariant)
                    ) {
                        majorKeys.forEach { key ->
                            DropdownMenuItem(
                                text = { Text(text = "$key Major", color = OnMusicDarkBg, fontSize = 12.sp) },
                                onClick = {
                                    selectedMajorKey = key
                                    isMajorMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Minor Selector Column
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (lang == AppLang.FA) "🌙 گام مینور" else "🌙 Minor Scale",
                    color = OnMusicDarkBg,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(if (lang == AppLang.FA) Alignment.End else Alignment.Start)
                )
                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(MusicSurface)
                        .border(1.dp, MusicBorder, RoundedCornerShape(10.dp))
                        .clickable { isMinorMenuExpanded = true }
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown", tint = OnMusicMuted)
                        Text(
                            text = if (selectedMinorKey.endsWith("m")) {
                                "${selectedMinorKey.dropLast(1)} Minor"
                            } else {
                                "$selectedMinorKey Minor"
                            },
                            color = MusicAccentPurple,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    DropdownMenu(
                        expanded = isMinorMenuExpanded,
                        onDismissRequest = { isMinorMenuExpanded = false },
                        modifier = Modifier.background(MusicSurfaceVariant)
                    ) {
                        minorKeys.forEach { key ->
                            val label = if (key.endsWith("m")) "${key.dropLast(1)} Minor" else "$key Minor"
                            DropdownMenuItem(
                                text = { Text(text = label, color = OnMusicDarkBg, fontSize = 12.sp) },
                                onClick = {
                                    selectedMinorKey = key
                                    isMinorMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        // Display results
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Major Chords List
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (lang == AppLang.FA) "آکوردهای دیاتونیک" else "Diatonic Chords",
                    color = OnMusicMuted,
                    fontSize = 11.sp,
                    modifier = Modifier.align(if (lang == AppLang.FA) Alignment.End else Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))

                val chords = MAJOR_CHORDS_MAP[selectedMajorKey] ?: emptyList()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MusicSurface)
                        .border(1.dp, MusicBorder, RoundedCornerShape(12.dp))
                ) {
                    chords.forEachIndexed { idx, chord ->
                        ChordRowItem(chord = chord, roman = ROMANS_LIST[idx], color = MusicAccentGold, lang = lang)
                        if (idx < chords.size - 1) {
                            HorizontalDivider(color = MusicBorder, thickness = 0.5.dp)
                        }
                    }
                }
            }

            // Minor Chords List
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (lang == AppLang.FA) "آکوردهای دیاتونیک" else "Diatonic Chords",
                    color = OnMusicMuted,
                    fontSize = 11.sp,
                    modifier = Modifier.align(if (lang == AppLang.FA) Alignment.End else Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))

                val chords = MINOR_CHORDS_MAP[selectedMinorKey] ?: emptyList()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MusicSurface)
                        .border(1.dp, MusicBorder, RoundedCornerShape(12.dp))
                ) {
                    chords.forEachIndexed { idx, chord ->
                        ChordRowItem(chord = chord, roman = ROMANS_LIST[idx].lowercase(), color = MusicAccentPurple, lang = lang)
                        if (idx < chords.size - 1) {
                            HorizontalDivider(color = MusicBorder, thickness = 0.5.dp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChordRowItem(chord: String, roman: String, color: Color, lang: AppLang) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (lang != AppLang.FA) {
            Text(text = chord, color = OnMusicDarkBg, fontSize = 13.5.sp, fontWeight = FontWeight.Bold)
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(color.copy(alpha = 0.1f))
                .border(1.dp, color.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text = roman,
                color = color,
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        if (lang == AppLang.FA) {
            Text(text = chord, color = OnMusicDarkBg, fontSize = 13.5.sp, fontWeight = FontWeight.Bold)
        }
    }
}


// ==========================================
// 3. SCALE VIEWER TOOL
// ==========================================
@Composable
fun ToolScalesScreen(
    lang: AppLang,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (lang == AppLang.FA) "گام‌نمای کامل کلیدها" else "Scale Visualizer",
            color = OnMusicDarkBg,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(if (lang == AppLang.FA) Alignment.End else Alignment.Start)
        )

        SCALES_DATA_LIST.forEach { group ->
            var isExpanded by remember { mutableStateOf(false) }
            val arrowRotate by animateFloatAsState(targetValue = if (isExpanded) 90f else 0f, label = "arrow")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                colors = CardDefaults.cardColors(containerColor = MusicSurface),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MusicBorder)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val color = if (group.titleEn.contains("Major")) MusicAccentGold else MusicAccentPurple

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Expand",
                            tint = OnMusicMuted,
                            modifier = Modifier.rotate(arrowRotate)
                        )

                        Text(
                            text = if (lang == AppLang.FA) group.titleFa else group.titleEn,
                            color = color,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MusicSurfaceVariant.copy(alpha = 0.5f))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            group.scales.forEachIndexed { sIdx, scale ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (lang != AppLang.FA) {
                                        Text(text = scale.name, color = MusicAccentGold, fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(48.dp))
                                    }

                                    Text(
                                        text = scale.notes,
                                        color = OnMusicDarkBg,
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.Medium,
                                        textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left,
                                        modifier = Modifier.weight(1f)
                                    )

                                    if (lang == AppLang.FA) {
                                        Text(text = scale.name, color = MusicAccentGold, fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(48.dp), textAlign = TextAlign.End)
                                    }
                                }

                                if (sIdx < group.scales.size - 1) {
                                    HorizontalDivider(color = MusicBorder.copy(alpha = 0.5f), thickness = 0.5.dp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


// ==========================================
// 4. INTERVAL TABLE TOOL
// ==========================================
@Composable
fun ToolIntervalsScreen(
    lang: AppLang,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val intervalsRows = listOf(
        IntervalRowData("اول (Unison)", "Unison", "✗", "✗", "0", "✗", "1"),
        IntervalRowData("دوم (2nd)", "2nd", "0", "1", "✗", "2", "3"),
        IntervalRowData("سوم (3rd)", "3rd", "2", "3", "✗", "4", "5"),
        IntervalRowData("چهارم (4th)", "4th", "4", "✗", "5", "✗", "6"),
        IntervalRowData("پنجم (5th)", "5th", "6", "✗", "7", "✗", "8"),
        IntervalRowData("ششم (6th)", "6th", "7", "8", "✗", "9", "10"),
        IntervalRowData("هفتم (7th)", "7th", "9", "10", "✗", "11", "12"),
        IntervalRowData("هشتم (Octave)", "Octave", "11", "✗", "12", "✗", "13")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (lang == AppLang.FA) "📐 جدول کامل فواصل موسیقی" else "📐 Full Music Interval Matrix",
            color = OnMusicDarkBg,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(if (lang == AppLang.FA) Alignment.End else Alignment.Start)
        )

        // Matrix card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MusicSurface),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, MusicBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                // Table Headers
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MusicSurfaceVariant)
                        .padding(horizontal = 12.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = if (lang == AppLang.FA) "فاصله" else "Interval", color = MusicAccentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.width(110.dp))
                    Text(text = "Dim\nکاسته", color = MusicRed, fontWeight = FontWeight.Bold, fontSize = 11.sp, textAlign = TextAlign.Center, modifier = Modifier.width(48.dp))
                    Text(text = "Minor\nکوچک", color = MusicAccentGold, fontWeight = FontWeight.Bold, fontSize = 11.sp, textAlign = TextAlign.Center, modifier = Modifier.width(48.dp))
                    Text(text = "Perfect\nدرست", color = MusicGreen, fontWeight = FontWeight.Bold, fontSize = 11.sp, textAlign = TextAlign.Center, modifier = Modifier.width(48.dp))
                    Text(text = "Major\nبزرگ", color = MusicBlue, fontWeight = FontWeight.Bold, fontSize = 11.sp, textAlign = TextAlign.Center, modifier = Modifier.width(48.dp))
                    Text(text = "Aug\nافزوده", color = MusicPink, fontWeight = FontWeight.Bold, fontSize = 11.sp, textAlign = TextAlign.Center, modifier = Modifier.width(48.dp))
                }

                HorizontalDivider(color = MusicBorder, thickness = 1.dp)

                // Table Rows
                intervalsRows.forEachIndexed { idx, row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (idx % 2 == 1) MusicSurfaceVariant.copy(alpha = 0.4f) else Color.Transparent)
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (lang == AppLang.FA) row.nameFa else row.nameEn,
                            color = OnMusicDarkBg,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.width(110.dp)
                        )

                        IntervalTableCell(text = row.dim, color = MusicRed, modifier = Modifier.width(48.dp))
                        IntervalTableCell(text = row.minor, color = MusicAccentGold, modifier = Modifier.width(48.dp))
                        IntervalTableCell(text = row.perfect, color = MusicGreen, modifier = Modifier.width(48.dp))
                        IntervalTableCell(text = row.major, color = MusicBlue, modifier = Modifier.width(48.dp))
                        IntervalTableCell(text = row.aug, color = MusicPink, modifier = Modifier.width(48.dp))
                    }

                    if (idx < intervalsRows.size - 1) {
                        HorizontalDivider(color = MusicBorder.copy(alpha = 0.5f), thickness = 0.5.dp)
                    }
                }
            }
        }

        // Color explanation helper
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MusicSurface),
            border = BorderStroke(1.dp, MusicBorder),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = if (lang == AppLang.FA) Alignment.End else Alignment.Start
            ) {
                Text(
                    text = if (lang == AppLang.FA) "راهنما:" else "Legend:",
                    color = MusicAccentGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (lang == AppLang.FA) Arrangement.End else Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (lang == AppLang.FA) {
                            "اعداد درون جدول معرف تعداد نیم‌پرده‌های هر فاصله هستند. علامت ✗ نشان‌دهنده عدم وجود فیزیکی آن فاصله است."
                        } else {
                            "Numbers represent semitones. ✗ indicates the interval does not harmonically exist."
                        },
                        color = OnMusicMuted,
                        fontSize = 11.5.sp,
                        lineHeight = 16.sp,
                        textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

data class IntervalRowData(
    val nameFa: String,
    val nameEn: String,
    val dim: String,
    val minor: String,
    val perfect: String,
    val major: String,
    val aug: String
)

@Composable
fun IntervalTableCell(text: String, color: Color, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (text == "✗") {
            Text(text = "✗", color = OnMusicMutedSoft, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        } else {
            Text(text = text, color = color, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}


// ==========================================
// 5. CHURCH MODES TOOL
// ==========================================
@Composable
fun ToolModesScreen(
    lang: AppLang,
    modifier: Modifier = Modifier
) {
    var selectedModeId by remember { mutableStateOf<String?>("ionian") }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (lang == AppLang.FA) "🎭 مدهای کلیسایی — ۷ مد اصلی" else "🎭 Church Modes Explorer",
            color = OnMusicDarkBg,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(if (lang == AppLang.FA) Alignment.End else Alignment.Start)
        )

        // Filters horizontal chips row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // "All" chip
            FilterChip(
                selected = selectedModeId == null,
                onClick = { selectedModeId = null },
                label = { Text(text = if (lang == AppLang.FA) "همه" else "All") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MusicAccentPurple.copy(alpha = 0.2f),
                    selectedLabelColor = MusicAccentPurple,
                    containerColor = MusicSurface,
                    labelColor = OnMusicMuted
                ),
                border = FilterChipDefaults.filterChipBorder(
                    selectedBorderColor = MusicAccentPurple,
                    borderColor = MusicBorder,
                    enabled = true,
                    selected = selectedModeId == null
                )
            )

            MODES_DATA_LIST.forEach { mode ->
                FilterChip(
                    selected = selectedModeId == mode.id,
                    onClick = { selectedModeId = mode.id },
                    label = { Text(text = mode.name) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MusicAccentPurple.copy(alpha = 0.2f),
                        selectedLabelColor = MusicAccentPurple,
                        containerColor = MusicSurface,
                        labelColor = OnMusicMuted
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        selectedBorderColor = MusicAccentPurple,
                        borderColor = MusicBorder,
                        enabled = true,
                        selected = selectedModeId == mode.id
                    )
                )
            }
        }

        // Details block
        val singleMode = MODES_DATA_LIST.find { it.id == selectedModeId }
        AnimatedVisibility(
            visible = singleMode != null,
            enter = fadeIn() + expandVertically()
        ) {
            if (singleMode != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MusicSurface),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, MusicAccentPurple.copy(alpha = 0.4f))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = if (lang == AppLang.FA) Alignment.End else Alignment.Start
                    ) {
                        Text(
                            text = "${singleMode.name} — Mode ${singleMode.num}",
                            color = MusicAccentGold,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (lang == AppLang.FA) "فرمول فواصل: ${singleMode.formula}" else "Formula: ${singleMode.formula}",
                            color = OnMusicMuted,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = if (lang == AppLang.FA) "مثال روی کلید سی: ${singleMode.example}" else "Example on C: ${singleMode.example}",
                            color = MusicBlue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = MusicBorder, thickness = 0.5.dp)
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (lang == AppLang.FA) singleMode.descFa else singleMode.descEn,
                            color = OnMusicDarkBg,
                            fontSize = 13.sp,
                            lineHeight = 22.sp,
                            textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        // Mode cards grid display
        val filteredList = if (selectedModeId == null) MODES_DATA_LIST else MODES_DATA_LIST.filter { it.id == selectedModeId }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            filteredList.forEach { mode ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedModeId = mode.id },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedModeId == mode.id) MusicAccentPurple.copy(alpha = 0.05f) else MusicSurface
                    ),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (selectedModeId == mode.id) MusicAccentPurple else MusicBorder
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = if (lang == AppLang.FA) Alignment.End else Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = mode.num, color = OnMusicMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text(text = mode.name, color = MusicAccentGold, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(MusicAccentPurple.copy(alpha = 0.12f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (lang == AppLang.FA) mode.charFa else mode.charEn,
                                color = MusicAccentPurple,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Chord chips row
                        Text(
                            text = if (lang == AppLang.FA) "آکوردهای شاخص دیاتونیک:" else "Diatonic Chords:",
                            color = OnMusicMuted,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = if (lang == AppLang.FA) Arrangement.End else Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val chipsList = mutableListOf<ChordChipData>()
                            mode.maj.forEach { chipsList.add(ChordChipData("I${toRomanNum(it)}", MusicGreen)) }
                            mode.min.forEach { chipsList.add(ChordChipData("i${toRomanNum(it).lowercase()}", MusicAccentPurple)) }
                            mode.dim.forEach { chipsList.add(ChordChipData("I${toRomanNum(it)}°", MusicRed)) }

                            val listOrdered = if (lang == AppLang.FA) chipsList.reversed() else chipsList

                            listOrdered.forEach { chip ->
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 3.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(chip.color.copy(alpha = 0.1f))
                                        .border(1.dp, chip.color.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(text = chip.text, color = chip.color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

data class ChordChipData(val text: String, val color: Color)

fun toRomanNum(n: Int): String {
    return listOf("", "I", "II", "III", "IV", "V", "VI", "VII")[n]
}


// ==========================================
// 6. CIRCLE OF FIFTHS TOOL (DYNAMIC)
// ==========================================
@Composable
fun ToolCircleScreen(
    lang: AppLang,
    modifier: Modifier = Modifier
) {
    var selectedIdx by remember { mutableStateOf(0) }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (lang == AppLang.FA) "⭕ دایره پنجم‌ها (Circle of Fifths)" else "⭕ Circle of Fifths Explorer",
            color = OnMusicDarkBg,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(if (lang == AppLang.FA) Alignment.End else Alignment.Start)
        )

        Text(
            text = if (lang == AppLang.FA) {
                "روی هر بخش چرخ کلیک کنید تا گام موازی، سرکلید و آکوردهای دیاتونیک آن را مشاهده کنید."
            } else {
                "Tap any wedge on the wheel to view its parallel scale, signature stave & chords."
            },
            color = OnMusicMuted,
            fontSize = 11.5.sp,
            lineHeight = 16.sp,
            textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        // INTERACTIVE CIRCLE OF FIFTHS DRAWN ON CANVAS WITH TAP DETECTION
        Box(
            modifier = Modifier
                .size(280.dp)
                .clip(RoundedCornerShape(140.dp))
                .background(MusicSurface)
                .border(1.dp, MusicBorder, RoundedCornerShape(140.dp))
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val cx = size.width / 2f
                        val cy = size.height / 2f
                        val dx = offset.x - cx
                        val dy = offset.y - cy
                        val distance = sqrt(dx * dx + dy * dy)

                        // Angle in degrees from 0 to 360
                        var angleRad = atan2(dy, dx)
                        var angleDeg = Math.toDegrees(angleRad.toDouble()).toFloat()
                        // Normalize to 0..360 starting from top (which is -90 degrees)
                        angleDeg = (angleDeg + 90f + 360f) % 360f

                        // Inner/Outer ring detection
                        val maxRadius = size.width / 2f
                        val innerRadiusCutoff = maxRadius * 0.6f
                        val minRadiusCutoff = maxRadius * 0.28f

                        if (distance in minRadiusCutoff..maxRadius) {
                            // Find sector indices (12 sectors, 30 degrees each)
                            val sector = ((angleDeg + 15f) / 30f).toInt() % 12
                            selectedIdx = sector
                        }
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val cx = size.width / 2f
                val cy = size.height / 2f
                val radius = size.width / 2f

                // Outer radius rings
                val rOut = radius * 0.96f
                val rMid = radius * 0.66f
                val rIn = radius * 0.36f

                // Draw background circle partitions
                for (i in 0 until 12) {
                    val angleOffset = -90f + i * 30f
                    val angleRad = Math.toRadians(angleOffset.toDouble()).toFloat()
                    val dx = cos(angleRad)
                    val dy = sin(angleRad)

                    // Wedge partition line
                    drawLine(
                        color = MusicBorder.copy(alpha = 0.5f),
                        start = Offset(cx + dx * rIn, cy + dy * rIn),
                        end = Offset(cx + dx * rOut, cy + dy * rOut),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                // Draw selected sector highlight
                val startAngle = -90f + selectedIdx * 30f - 15f
                val highlightSweep = 30f

                // Outer sector highlight
                drawArc(
                    color = MusicAccentGold.copy(alpha = 0.15f),
                    startAngle = startAngle,
                    sweepAngle = highlightSweep,
                    useCenter = true,
                    size = Size(rOut * 2, rOut * 2),
                    topLeft = Offset(cx - rOut, cy - rOut)
                )

                // Inner sector highlight
                drawArc(
                    color = MusicAccentPurple.copy(alpha = 0.12f),
                    startAngle = startAngle,
                    sweepAngle = highlightSweep,
                    useCenter = true,
                    size = Size(rMid * 2, rMid * 2),
                    topLeft = Offset(cx - rMid, cy - rMid)
                )

                // Outer ring outline separator
                drawCircle(color = MusicBorder, radius = rOut, style = Stroke(width = 1.dp.toPx()))
                drawCircle(color = MusicBorder, radius = rMid, style = Stroke(width = 1.dp.toPx()))
                drawCircle(color = MusicBorder, radius = rIn, style = Stroke(width = 1.dp.toPx()))

                // Draw solid inner core
                drawCircle(color = MusicDarkBg, radius = rIn)
                drawCircle(color = MusicBorder, radius = rIn, style = Stroke(width = 1.dp.toPx()))
            }

            // Overlay Text widgets absolutely positioned on the wedges
            CIRCLE_DATA_LIST.forEachIndexed { idx, item ->
                val angleDeg = -90f + idx * 30f
                val angleRad = Math.toRadians(angleDeg.toDouble()).toFloat()

                // Positions
                val pxMaj = 140f + 110f * cos(angleRad)
                val pyMaj = 140f + 110f * sin(angleRad)

                val pxMin = 140f + 68f * cos(angleRad)
                val pyMin = 140f + 68f * sin(angleRad)

                val isSelected = selectedIdx == idx

                // Major key label
                Box(
                    modifier = Modifier
                        .offset(x = (pxMaj - 22f).dp, y = (pyMaj - 14f).dp)
                        .size(44.dp, 28.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.maj.split(" ")[0],
                        color = if (isSelected) MusicAccentGold else OnMusicDarkBg,
                        fontSize = if (isSelected) 13.sp else 12.sp,
                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                // Minor key label
                Box(
                    modifier = Modifier
                        .offset(x = (pxMin - 22f).dp, y = (pyMin - 14f).dp)
                        .size(44.dp, 28.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.min,
                        color = if (isSelected) MusicAccentPurple else OnMusicMuted,
                        fontSize = if (isSelected) 11.5.sp else 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Core center labels showing the active selection
            val coreItem = CIRCLE_DATA_LIST[selectedIdx]
            Column(
                modifier = Modifier
                    .size(90.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = coreItem.maj.split(" ")[0],
                    color = MusicAccentGold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = coreItem.min,
                    color = MusicAccentPurple,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        val activeItem = CIRCLE_DATA_LIST[selectedIdx]

        // Dynamic details card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MusicSurface),
            border = BorderStroke(1.dp, MusicBorder),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                horizontalAlignment = if (lang == AppLang.FA) Alignment.End else Alignment.Start
            ) {
                Text(
                    text = "${activeItem.maj} ماژور ⟷ ${activeItem.min} مینور",
                    color = MusicAccentGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left
                )

                Spacer(modifier = Modifier.height(4.dp))

                val sigLabel = if (activeItem.sigCount == 0) {
                    if (lang == AppLang.FA) "بدون علامت دیز و بمل" else "No sharps or flats"
                } else {
                    "${activeItem.sigCount} ${if (activeItem.sigType == "♯") "دیز" else "بمل"}: ${activeItem.accidentals.joinToString(" - ")}"
                }

                Text(
                    text = sigLabel,
                    color = OnMusicMuted,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left
                )

                Spacer(modifier = Modifier.height(16.dp))

                // DYNAMIC STAFF CANVAS SHEETS DRAWING
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(84.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MusicSurfaceVariant.copy(alpha = 0.5f))
                        .border(0.5.dp, MusicBorder, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    StaffCanvas(accidentals = activeItem.accidentals, sigType = activeItem.sigType)
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Nearest Neighbors guide (modulation)
                val prevItem = CIRCLE_DATA_LIST[(selectedIdx - 1 + 12) % 12]
                val nextItem = CIRCLE_DATA_LIST[(selectedIdx + 1) % 12]

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MusicAccentGold.copy(alpha = 0.05f))
                        .padding(12.dp)
                ) {
                    Text(
                        text = if (lang == AppLang.FA) {
                            "🎯 کلیدهای همسایه (یک پنجم فاصله): گام‌های ${prevItem.maj.split(" ")[0]} و ${nextItem.maj.split(" ")[0]} نزدیک‌ترین گام‌ها برای انتقال و مدولاسیون ریتمیک هستند."
                        } else {
                            "🎯 Nearest Neighbors: Keys of ${prevItem.maj.split(" ")[0]} (Subdominant) and ${nextItem.maj.split(" ")[0]} (Dominant) are the closest keys for smooth modulation."
                        },
                        color = OnMusicDarkBg,
                        fontSize = 11.5.sp,
                        lineHeight = 18.sp,
                        textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left
                    )
                }
            }
        }

        // Diatonic Chords Lists below wheel
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "آکوردهای ${activeItem.maj.split(" ")[0]} Major",
                    color = OnMusicMuted,
                    fontSize = 11.sp,
                    modifier = Modifier.align(if (lang == AppLang.FA) Alignment.End else Alignment.Start)
                )
                Spacer(modifier = Modifier.height(6.dp))

                val majChords = MAJOR_CHORDS_MAP[activeItem.majKey] ?: emptyList()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MusicSurface)
                        .border(1.dp, MusicBorder, RoundedCornerShape(12.dp))
                ) {
                    majChords.forEachIndexed { index, chord ->
                        ChordRowItem(chord = chord, roman = ROMANS_LIST[index], color = MusicAccentGold, lang = lang)
                        if (index < majChords.size - 1) {
                            HorizontalDivider(color = MusicBorder, thickness = 0.5.dp)
                        }
                    }
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "آکوردهای ${activeItem.min} Minor",
                    color = OnMusicMuted,
                    fontSize = 11.sp,
                    modifier = Modifier.align(if (lang == AppLang.FA) Alignment.End else Alignment.Start)
                )
                Spacer(modifier = Modifier.height(6.dp))

                val minChords = MINOR_CHORDS_MAP[activeItem.minKey] ?: emptyList()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MusicSurface)
                        .border(1.dp, MusicBorder, RoundedCornerShape(12.dp))
                ) {
                    minChords.forEachIndexed { index, chord ->
                        ChordRowItem(chord = chord, roman = ROMANS_LIST[index].lowercase(), color = MusicAccentPurple, lang = lang)
                        if (index < minChords.size - 1) {
                            HorizontalDivider(color = MusicBorder, thickness = 0.5.dp)
                        }
                    }
                }
            }
        }
    }
}

// dynamic staff drawing
@Composable
fun StaffCanvas(accidentals: List<String>, sigType: String) {
    Canvas(
        modifier = Modifier
            .width(180.dp)
            .height(64.dp)
    ) {
        val width = size.width
        val height = size.height

        val staffTop = height * 0.22f
        val lineGap = height * 0.14f

        // Draw 5 stave lines
        for (i in 0 until 5) {
            val y = staffTop + i * lineGap
            drawLine(
                color = OnMusicMuted.copy(alpha = 0.6f),
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 1.dp.toPx()
            )
        }

        // Draw Treble Clef Path dynamically
        val clefPath = Path().apply {
            moveTo(24f, staffTop + lineGap * 4.2f)
            cubicTo(
                32f, staffTop + lineGap * 4.2f,
                32f, staffTop + lineGap * 2.8f,
                24f, staffTop + lineGap * 3.2f
            )
            cubicTo(
                12f, staffTop + lineGap * 3.6f,
                10f, staffTop + lineGap * 4.8f,
                11f, staffTop + lineGap * 5.2f
            )
            cubicTo(
                14f, staffTop + lineGap * 5.8f,
                26f, staffTop + lineGap * 5.8f,
                28f, staffTop + lineGap * 4.6f
            )
            lineTo(24f, staffTop - lineGap * 0.6f)
            cubicTo(
                22f, staffTop - lineGap * 1.4f,
                30f, staffTop - lineGap * 1.4f,
                25f, staffTop + lineGap * 0.4f
            )
            lineTo(24f, staffTop + lineGap * 5.4f)
        }

        drawPath(
            path = clefPath,
            color = MusicAccentGold,
            style = Stroke(width = 2.dp.toPx())
        )

        // Draw accidental symbols on correct staff lines/spaces
        val sharpMap = mapOf(
            "F♯" to 0.0f, // F5 (line 5)
            "C♯" to 1.5f, // C5 (space 3)
            "G♯" to -0.5f, // G5 (space above line 5)
            "D♯" to 1.0f, // D5 (line 4)
            "A♯" to 2.5f, // A4 (space 2)
            "E♯" to 0.5f, // E5 (space 4)
            "B♯" to 2.0f  // B4 (line 3)
        )

        val flatMap = mapOf(
            "B♭" to 2.0f, // B4 (line 3)
            "E♭" to 0.5f, // E5 (space 4)
            "A♭" to 2.5f, // A4 (space 2)
            "D♭" to 1.0f, // D5 (line 4)
            "G♭" to 3.0f, // G4 (line 2)
            "C♭" to 1.5f, // C5 (space 3)
            "F♭" to 3.5f  // F4 (space 1)
        )

        val posMap = if (sigType == "♯") sharpMap else flatMap

        accidentals.forEachIndexed { i, accName ->
            val unit = posMap[accName] ?: 0.0f
            val y = staffTop + unit * lineGap
            val x = 56f + i * 14f

            // Drawing clean custom Sharp/Flat marks
            if (sigType == "♯") {
                // Draw a beautiful custom sharp (♯) sign using paths
                val sharpPath = Path().apply {
                    // vertical lines
                    moveTo(x, y - 6f)
                    lineTo(x, y + 10f)
                    moveTo(x + 5f, y - 10f)
                    lineTo(x + 5f, y + 6f)
                    // horizontal slashes
                    moveTo(x - 3f, y - 2f)
                    lineTo(x + 8f, y - 4f)
                    moveTo(x - 3f, y + 4f)
                    lineTo(x + 8f, y + 2f)
                }
                drawPath(
                    path = sharpPath,
                    color = MusicAccentPurple,
                    style = Stroke(width = 1.5.dp.toPx())
                )
            } else if (sigType == "♭") {
                // Draw a beautiful custom flat (♭) sign using paths
                val flatPath = Path().apply {
                    moveTo(x, y - 10f)
                    lineTo(x, y + 6f)
                    cubicTo(
                        x + 6f, y + 6f,
                        x + 6f, y,
                        x, y + 2f
                    )
                }
                drawPath(
                    path = flatPath,
                    color = MusicAccentPurple,
                    style = Stroke(width = 1.5.dp.toPx())
                )
            }
        }
    }
}
