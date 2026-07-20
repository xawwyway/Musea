package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    lang: AppLang,
    visitedPages: Set<Screen>,
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
        // Hero Section Banner - Today's Lesson styled card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp)) // rounded-[28px] in HTML design
                .background(Color(0xFFEADDFF)) // bg-[#EADDFF] today's lesson background
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Eyebrow Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF21005D)) // bg-[#21005D] deep purple badge
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (lang == AppLang.FA) "آموزش کامل و تعاملی موسیقی" else "Interactive Music Theory Tutor",
                        color = Color.White, // white text on deep purple badge
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = if (lang == AppLang.FA) "🎵 تئوری موسیقی" else "🎵 Music Theory",
                    color = Color(0xFF21005D), // text-[#21005D] title
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtitle
                Text(
                    text = if (lang == AppLang.FA) {
                        "از آلتراسیون تا آکورد، از گام ماژور تا مدهای کلیسایی — دسته‌بندی، تعاملی و با ابزار!"
                    } else {
                        "From alteration to chords, from major scales to church modes — structured, interactive & with tools!"
                    },
                    color = Color(0xFF21005D).copy(alpha = 0.8f), // text-[#21005D]/80 subtitle
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Flowing Waveform Animation
                AnimatedWaveform(modifier = Modifier.height(40.dp).fillMaxWidth(0.8f))

                Spacer(modifier = Modifier.height(16.dp))

                // Quick CTAs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onNavigate(Screen.ALTERATION) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6750A4), // bg-[#6750A4] start course button
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(100.dp), // rounded-full (pill)
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        Text(
                            text = if (lang == AppLang.FA) "شروع یادگیری" else "Start Course",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    OutlinedButton(
                        onClick = { onNavigate(Screen.TOOLS_HUB) },
                        border = BorderStroke(1.dp, Color(0xFF6750A4)), // border-[#6750A4]
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF6750A4)),
                        shape = RoundedCornerShape(100.dp), // rounded-full (pill)
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        Text(
                            text = if (lang == AppLang.FA) "مشاهده ابزارها" else "View Tools",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Section header
        Text(
            text = if (lang == AppLang.FA) "کارت‌های آموزشی" else "Lesson Modules",
            color = OnMusicDarkBg,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(if (lang == AppLang.FA) Alignment.End else Alignment.Start)
        )

        // Staggered Lesson & Quiz Grid
        val gridItems = listOf(
            GridCardData(Screen.ALTERATION, "♯♭", "آلتراسیون", "Alteration", "دیز، بمل، بکار", "Sharp, flat, natural", MusicAccentGold),
            GridCardData(Screen.MAJOR, "☀️", "گام ماژور", "Major Scale", "الگو + درجات", "Formula + degrees", MusicAccentPurple),
            GridCardData(Screen.MINOR, "🌙", "گام مینور", "Minor Scale", "هارمونیک، ملودیک", "Harmonic, melodic", MusicBlue),
            GridCardData(Screen.GMODES, "🎭", "مدهای کلیسایی", "Church Modes", "۷ مد اصلی", "7 Church modes", MusicPink),
            GridCardData(Screen.CHORDS, "🎸", "آکوردها", "Chords", "ماژور، مینور، کاسته", "Major, minor, dim", MusicGreen),
            GridCardData(Screen.QUIZ, "🧠", "آزمون", "Quiz", "۱۲ سؤال تستی", "12 Practice Qs", MusicRed)
        )

        val rowCount = (gridItems.size + 1) / 2
        for (i in 0 until rowCount) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val item1 = gridItems[i * 2]
                val item2 = if (i * 2 + 1 < gridItems.size) gridItems[i * 2 + 1] else null

                Box(modifier = Modifier.weight(1f)) {
                    DashboardCard(data = item1, lang = lang, isDone = visitedPages.contains(item1.screen)) {
                        onNavigate(item1.screen)
                    }
                }
                if (item2 != null) {
                    Box(modifier = Modifier.weight(1f)) {
                        DashboardCard(data = item2, lang = lang, isDone = visitedPages.contains(item2.screen)) {
                            onNavigate(item2.screen)
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Large CTA Card for Interactive Tools
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(MusicAccentGold.copy(alpha = 0.08f), MusicAccentPurple.copy(alpha = 0.05f))
                    )
                )
                .border(1.dp, MusicAccentGold.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
                .clickable { onNavigate(Screen.TOOLS_HUB) }
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🛠",
                    fontSize = 32.sp,
                    modifier = Modifier.padding(end = 12.dp)
                )

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = if (lang == AppLang.FA) Alignment.End else Alignment.Start
                ) {
                    Text(
                        text = if (lang == AppLang.FA) "ابزارهای تعاملی موسیقی" else "Interactive Musical Tools",
                        color = MusicAccentGold,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (lang == AppLang.FA) {
                            "دایره پنجم‌ها، آکوردهای گام، گام‌نماها، جدول کامل فواصل و ابزار پیشرفته مدها"
                        } else {
                            "Circle of Fifths, Scale Chords, Scale Viewer, Interval Tables & advanced Church Modes"
                        },
                        color = OnMusicMuted,
                        fontSize = 11.5.sp,
                        lineHeight = 16.sp,
                        textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (lang != AppLang.FA) {
                    Text(
                        text = "→",
                        color = MusicAccentGold,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                } else {
                    Text(
                        text = "←",
                        color = MusicAccentGold,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
            }
        }
    }
}

data class GridCardData(
    val screen: Screen,
    val icon: String,
    val titleFa: String,
    val titleEn: String,
    val subFa: String,
    val subEn: String,
    val color: Color
)

@Composable
fun DashboardCard(
    data: GridCardData,
    lang: AppLang,
    isDone: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp)) // rounded-3xl in HTML design
            .background(MusicSurfaceVariant) // bg-[#F3F0F5] in HTML design
            .border(0.5.dp, MusicBorder.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = if (lang == AppLang.FA) Alignment.End else Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Done indicator dot
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(RoundedCornerShape(50))
                        .background(if (isDone) MusicGreen else Color.Transparent)
                        .border(0.5.dp, if (isDone) Color.Transparent else MusicBorder, RoundedCornerShape(50))
                )

                // Emoji Icon bubble - styled as a white card with shadow-sm in HTML design
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp)) // rounded-2xl
                        .background(Color.White) // bg-white
                        .border(0.5.dp, MusicBorder.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = data.icon, fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Card Title
            Text(
                text = if (lang == AppLang.FA) data.titleFa else data.titleEn,
                color = OnMusicDarkBg,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Card Subtitle
            Text(
                text = if (lang == AppLang.FA) data.subFa else data.subEn,
                color = OnMusicMuted,
                fontSize = 11.sp,
                textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left
            )
        }
    }
}

// Canvas-drawn dynamically flowing sine-wave
@Composable
fun AnimatedWaveform(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "waveform")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val wavePath = Path()

        val points = 80
        val amplitude = height * 0.4f
        val frequency = 2f // Number of complete waves in banner

        for (i in 0..points) {
            val x = (i.toFloat() / points) * width
            val relativeX = i.toFloat() / points
            val angle = relativeX * frequency * 2 * Math.PI + phase
            val y = height / 2 + Math.sin(angle).toFloat() * amplitude

            if (i == 0) {
                wavePath.moveTo(x, y)
            } else {
                wavePath.lineTo(x, y)
            }
        }

        drawPath(
            path = wavePath,
            color = MusicAccentPurple,
            style = Stroke(
                width = 2.dp.toPx(),
                miter = Stroke.DefaultMiter
            )
        )
    }
}
