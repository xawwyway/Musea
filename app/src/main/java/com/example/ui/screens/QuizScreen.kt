package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.delay

@Composable
fun QuizScreen(
    lang: AppLang,
    onScoreEarned: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentQuestionIdx by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var selectedOptionIdx by remember { mutableStateOf<Int?>(null) }
    var isAnswered by remember { mutableStateOf(false) }
    var showQuizFinished by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    LaunchedEffect(showQuizFinished) {
        if (showQuizFinished) {
            onScoreEarned(score * 10)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (!showQuizFinished) {
            val q = QUIZ_QUESTIONS[currentQuestionIdx]

            // Question Box header
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MusicSurface),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MusicBorder)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = if (lang == AppLang.FA) Alignment.End else Alignment.Start
                ) {
                    Text(
                        text = if (lang == AppLang.FA) "سؤال ${currentQuestionIdx + 1} از ${QUIZ_QUESTIONS.size}" else "Question ${currentQuestionIdx + 1} of ${QUIZ_QUESTIONS.size}",
                        color = OnMusicMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = if (lang == AppLang.FA) q.qFa else q.qEn,
                        color = OnMusicDarkBg,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 24.sp,
                        textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Options List
            val options = if (lang == AppLang.FA) q.optsFa else q.optsEn
            val optionMarks = if (lang == AppLang.FA) listOf("الف", "ب", "پ", "ت") else listOf("A", "B", "C", "D")

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                options.forEachIndexed { idx, option ->
                    val isSelected = selectedOptionIdx == idx
                    val isCorrectIdx = idx == q.ans

                    val itemColor = when {
                        !isAnswered -> if (isSelected) MusicAccentPurple else MusicBorder
                        isCorrectIdx -> MusicGreen
                        isSelected -> MusicRed
                        else -> MusicBorder
                    }

                    val bgColor = when {
                        !isAnswered -> if (isSelected) MusicAccentPurple.copy(alpha = 0.08f) else MusicSurface
                        isCorrectIdx -> MusicGreen.copy(alpha = 0.08f)
                        isSelected -> MusicRed.copy(alpha = 0.08f)
                        else -> MusicSurface
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(bgColor)
                            .border(1.dp, itemColor, RoundedCornerShape(12.dp))
                            .clickable(enabled = !isAnswered) {
                                selectedOptionIdx = idx
                                isAnswered = true
                                if (idx == q.ans) {
                                    score++
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (lang == AppLang.FA) Arrangement.End else Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (lang != AppLang.FA) {
                                OptionMarkBox(mark = optionMarks[idx], color = itemColor, active = isSelected)
                                Spacer(modifier = Modifier.width(12.dp))
                            }

                            Text(
                                text = option,
                                color = OnMusicDarkBg,
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left,
                                modifier = Modifier.weight(1f)
                            )

                            if (lang == AppLang.FA) {
                                Spacer(modifier = Modifier.width(12.dp))
                                OptionMarkBox(mark = optionMarks[idx], color = itemColor, active = isSelected)
                            }
                        }
                    }
                }
            }

            // Feedback Explanatory Box
            AnimatedVisibility(
                visible = isAnswered,
                enter = fadeIn() + expandVertically()
            ) {
                val isCorrectAnswer = selectedOptionIdx == q.ans
                val feedbackText = if (lang == AppLang.FA) q.expFa else q.expEn
                val feedbackBg = if (isCorrectAnswer) MusicGreen.copy(alpha = 0.08f) else MusicRed.copy(alpha = 0.08f)
                val feedbackStroke = if (isCorrectAnswer) MusicGreen else MusicRed

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(feedbackBg)
                        .border(1.dp, feedbackStroke.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = if (lang == AppLang.FA) Alignment.End else Alignment.Start
                    ) {
                        Text(
                            text = if (isCorrectAnswer) {
                                if (lang == AppLang.FA) "✅ پاسخ صحیح است!" else "✅ Correct!"
                            } else {
                                if (lang == AppLang.FA) "❌ پاسخ نادرست است." else "❌ Incorrect."
                            },
                            color = if (isCorrectAnswer) MusicGreen else MusicRed,
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = feedbackText,
                            color = OnMusicDarkBg,
                            fontSize = 12.5.sp,
                            lineHeight = 20.sp,
                            textAlign = if (lang == AppLang.FA) TextAlign.Right else TextAlign.Left
                        )
                    }
                }
            }

            // Next Button
            AnimatedVisibility(
                visible = isAnswered,
                enter = fadeIn() + slideInVertically(initialOffsetY = { 40 })
            ) {
                Button(
                    onClick = {
                        if (currentQuestionIdx < QUIZ_QUESTIONS.size - 1) {
                            currentQuestionIdx++
                            selectedOptionIdx = null
                            isAnswered = false
                        } else {
                            showQuizFinished = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MusicAccentPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = if (lang == AppLang.FA) "سؤال بعدی ←" else "Next Question →",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            // Quiz Completed Screen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(MusicSurface, MusicDarkBg)
                        )
                    )
                    .border(1.dp, MusicBorder, RoundedCornerShape(20.dp))
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "🎉", fontSize = 54.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = if (lang == AppLang.FA) "آفرین! آزمون به پایان رسید" else "Outstanding! Quiz Completed",
                        color = MusicAccentGold,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val scorePercentage = Math.round((score.toFloat() / QUIZ_QUESTIONS.size) * 100)
                    Text(
                        text = if (lang == AppLang.FA) {
                            "امتیاز نهایی شما: $score از ${QUIZ_QUESTIONS.size} ($scorePercentage%)"
                        } else {
                            "Your Score: $score / ${QUIZ_QUESTIONS.size} ($scorePercentage%)"
                        },
                        color = OnMusicDarkBg,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (lang == AppLang.FA) {
                            "هر پاسخ صحیح ۱۰۰ امتیاز به رتبه کل شما اضافه کرد!"
                        } else {
                            "Each correct answer added 100 stars to your global rank!"
                        },
                        color = OnMusicMuted,
                        fontSize = 11.5.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            currentQuestionIdx = 0
                            score = 0
                            selectedOptionIdx = null
                            isAnswered = false
                            showQuizFinished = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MusicAccentGold, contentColor = Color(0xFF121212)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(44.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Retry")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (lang == AppLang.FA) "تلاش مجدد 🔄" else "Retry 🔄",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OptionMarkBox(mark: String, color: Color, active: Boolean) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(if (active) color else MusicSurfaceVariant)
            .border(1.dp, color.copy(alpha = 0.5f), RoundedCornerShape(6.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = mark,
            color = if (active) Color.White else OnMusicMuted,
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}
