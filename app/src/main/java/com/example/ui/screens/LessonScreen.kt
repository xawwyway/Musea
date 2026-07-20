package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun LessonScreen(
    lesson: Lesson,
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
        // Lesson Intro Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(MusicSurface, MusicDarkBg)
                    )
                )
                .border(1.dp, MusicBorder, RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = if (lang == AppLang.FA) Alignment.End else Alignment.Start
            ) {
                Text(
                    text = if (lang == AppLang.FA) "آشنایی با مفاهیم" else "Introduction",
                    color = MusicAccentGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = if (lang == AppLang.FA) lesson.introFa else lesson.introEn,
                    color = OnMusicDarkBg,
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Render Blocks
        lesson.blocks.forEach { block ->
            when (block) {
                is LessonBlock.TextBlock -> {
                    Text(
                        text = if (lang == AppLang.FA) block.textFa else block.textEn,
                        color = OnMusicDarkBg,
                        fontSize = 14.sp,
                        lineHeight = 26.sp,
                        textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }

                is LessonBlock.HighlightBlock -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MusicSurface)
                            .drawBorderHighlight(lang == AppLang.FA, MusicAccentPurple)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = if (lang == AppLang.FA) block.textFa else block.textEn,
                            color = OnMusicDarkBg,
                            fontSize = 13.5.sp,
                            lineHeight = 24.sp,
                            textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                is LessonBlock.HighlightPointsBlock -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MusicSurface)
                            .drawBorderHighlight(lang == AppLang.FA, MusicAccentGold)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val points = if (lang == AppLang.FA) block.pointsFa else block.pointsEn
                        points.forEach { point ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = if (lang == AppLang.FA) Arrangement.End else Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (lang != AppLang.FA) {
                                    Text("⚡ ", fontSize = 14.sp, color = MusicAccentGold)
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                Text(
                                    text = point,
                                    color = OnMusicDarkBg,
                                    fontSize = 13.sp,
                                    lineHeight = 20.sp,
                                    textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left,
                                    modifier = Modifier.weight(1f, fill = false)
                                )
                                if (lang == AppLang.FA) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(" ⚡", fontSize = 14.sp, color = MusicAccentGold)
                                }
                            }
                        }
                    }
                }

                is LessonBlock.PatternBlock -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(vertical = 8.dp),
                        horizontalArrangement = if (lang == AppLang.FA) Arrangement.End else Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val list = if (lang == AppLang.FA) block.intervals.reversed() else block.intervals
                        list.forEachIndexed { idx, interval ->
                            // Step box
                            val isHalf = interval.contains("Half") || interval.contains("½") || interval.contains("0.5")
                            val isWhole = interval.contains("Whole") || interval.contains("1")
                            val bgColor = when {
                                isHalf -> MusicRed.copy(alpha = 0.15f)
                                isWhole -> MusicGreen.copy(alpha = 0.15f)
                                else -> MusicAccentGold.copy(alpha = 0.15f)
                            }
                            val strokeColor = when {
                                isHalf -> MusicRed
                                isWhole -> MusicGreen
                                else -> MusicAccentGold
                            }
                            val displayInterval = when {
                                interval == "Whole" && lang == AppLang.FA -> "پرده"
                                interval == "Half" && lang == AppLang.FA -> "نیم"
                                interval == "Aug" && lang == AppLang.FA -> "افزوده"
                                else -> interval
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(bgColor)
                                    .border(1.dp, strokeColor, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = displayInterval,
                                    color = strokeColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            if (idx < list.size - 1) {
                                Text(
                                    text = if (lang == AppLang.FA) " ← " else " → ",
                                    color = OnMusicMutedSoft,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp)
                                )
                            }
                        }
                    }
                }

                is LessonBlock.TableBlock -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(MusicSurface)
                            .border(1.dp, MusicBorder, RoundedCornerShape(10.dp))
                    ) {
                        val headers = if (lang == AppLang.FA) block.headersFa else block.headersEn
                        // Header Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MusicSurfaceVariant)
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            headers.forEach { header ->
                                Text(
                                    text = header,
                                    color = MusicAccentGold,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f),
                                    textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left
                                )
                            }
                        }
                        HorizontalDivider(color = MusicBorder, thickness = 1.dp)

                        // Data Rows
                        block.rows.forEachIndexed { rIdx, row ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(if (rIdx % 2 == 1) MusicSurfaceVariant.copy(alpha = 0.5f) else Color.Transparent)
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                row.forEach { cell ->
                                    Text(
                                        text = cell,
                                        color = OnMusicDarkBg,
                                        fontSize = 12.sp,
                                        lineHeight = 18.sp,
                                        modifier = Modifier.weight(1f),
                                        textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left
                                    )
                                }
                            }
                            if (rIdx < block.rows.size - 1) {
                                HorizontalDivider(color = MusicBorder.copy(alpha = 0.5f), thickness = 0.5.dp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// Custom modifier to draw a clean side accent border matching the web app's style
fun Modifier.drawBorderHighlight(isRtl: Boolean, color: Color): Modifier = this.then(
    Modifier.border(
        width = 1.dp,
        color = MusicBorder,
        shape = RoundedCornerShape(12.dp)
    ).padding(start = if (isRtl) 0.dp else 4.dp, end = if (isRtl) 4.dp else 0.dp)
        .background(
            color,
            shape = if (isRtl) RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
            else RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
        )
        .padding(start = if (isRtl) 0.dp else 12.dp, end = if (isRtl) 12.dp else 0.dp)
)
