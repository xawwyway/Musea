package com.example.ui.screens

enum class AppLang { FA, EN }

enum class Screen {
    HOME,
    ALTERATION,
    NOTES,
    METER,
    SOUND,
    MAJOR,
    MINOR,
    PENTATONIC,
    GMODES,
    KEYSIG,
    DEGREES,
    CHORDS,
    INTERVALS,
    POLYPHONY,
    QUIZ,
    TOOLS_HUB,
    TOOL_CHORDS,
    TOOL_SCALES,
    TOOL_INTERVALS,
    TOOL_MODES,
    TOOL_CIRCLE
}

data class ScreenInfo(
    val titleFa: String,
    val titleEn: String,
    val badgeFa: String,
    val badgeEn: String
)

val SCREEN_INFO_MAP = mapOf(
    Screen.HOME to ScreenInfo("خانه", "Home", "شروع", "Start"),
    Screen.ALTERATION to ScreenInfo("آلتراسیون", "Alteration", "پایه", "Basics"),
    Screen.NOTES to ScreenInfo("انواع نت‌ها", "Note Types", "پایه", "Basics"),
    Screen.METER to ScreenInfo("میزان و ریتم", "Meter & Rhythm", "پایه", "Basics"),
    Screen.SOUND to ScreenInfo("صدای موسیقیایی", "Musical Sound", "پایه", "Basics"),
    Screen.MAJOR to ScreenInfo("گام ماژور", "Major Scale", "گام", "Scales"),
    Screen.MINOR to ScreenInfo("گام مینور", "Minor Scale", "گام", "Scales"),
    Screen.PENTATONIC to ScreenInfo("پنتاتونیک و بلوز", "Pentatonic & Blues", "گام", "Scales"),
    Screen.GMODES to ScreenInfo("مدهای کلیسایی", "Church Modes", "گام", "Scales"),
    Screen.KEYSIG to ScreenInfo("دیزها و بمل‌ها", "Key Signatures", "گام", "Scales"),
    Screen.DEGREES to ScreenInfo("درجات گام", "Scale Degrees", "هارمونی", "Harmony"),
    Screen.CHORDS to ScreenInfo("ساختار آکورد", "Chord Structure", "هارمونی", "Harmony"),
    Screen.INTERVALS to ScreenInfo("فواصل", "Consonant Intervals", "هارمونی", "Harmony"),
    Screen.POLYPHONY to ScreenInfo("انواع موسیقی", "Music Types", "هارمونی", "Harmony"),
    Screen.QUIZ to ScreenInfo("آزمون", "Quiz", "تمرین", "Practice"),
    Screen.TOOLS_HUB to ScreenInfo("همه ابزارها", "All Tools", "ابزار", "Tools"),
    Screen.TOOL_CHORDS to ScreenInfo("آکوردهای گام", "Scale Chords", "ابزار", "Tools"),
    Screen.TOOL_SCALES to ScreenInfo("گام‌نماها", "Scale Viewer", "ابزار", "Tools"),
    Screen.TOOL_INTERVALS to ScreenInfo("جدول فواصل", "Interval Table", "ابزار", "Tools"),
    Screen.TOOL_MODES to ScreenInfo("ابزار مدها", "Modes Tool", "ابزار", "Tools"),
    Screen.TOOL_CIRCLE to ScreenInfo("دایره پنجم‌ها", "Circle of Fifths", "ابزار", "Tools")
)

// Content models
sealed class LessonBlock {
    data class TextBlock(val textFa: String, val textEn: String) : LessonBlock()
    data class HighlightBlock(val textFa: String, val textEn: String) : LessonBlock()
    data class HighlightPointsBlock(val pointsFa: List<String>, val pointsEn: List<String>) : LessonBlock()
    data class TableBlock(val headersFa: List<String>, val headersEn: List<String>, val rows: List<List<String>>) : LessonBlock()
    data class PatternBlock(val intervals: List<String>) : LessonBlock() // Whole, Half, 1.5, etc.
}

data class Lesson(
    val screen: Screen,
    val introFa: String,
    val introEn: String,
    val blocks: List<LessonBlock>
)

// Raw data for Chords Tool
val MAJOR_CHORDS_MAP = mapOf(
    "C" to listOf("C", "Dm", "Em", "F", "G", "Am", "B°"),
    "C♯" to listOf("C♯", "D♯m", "E♯m", "F♯", "G♯", "A♯m", "B♯°"),
    "D♭" to listOf("D♭", "E♭m", "Fm", "G♭", "A♭", "B♭m", "C°"),
    "D" to listOf("D", "Em", "F♯m", "G", "A", "Bm", "C♯°"),
    "E♭" to listOf("E♭", "Fm", "Gm", "A♭", "B♭", "Cm", "D°"),
    "E" to listOf("E", "F♯m", "G♯m", "A", "B", "C♯m", "D♯°"),
    "F" to listOf("F", "Gm", "Am", "B♭", "C", "Dm", "E°"),
    "F♯" to listOf("F♯", "G♯m", "A♯m", "B", "C♯", "D♯m", "E♯°"),
    "G♭" to listOf("G♭", "A♭m", "B♭m", "C♭", "D♭", "E♭m", "F°"),
    "G" to listOf("G", "Am", "Bm", "C", "D", "Em", "F♯°"),
    "A♭" to listOf("A♭", "B♭m", "Cm", "D♭", "E♭", "Fm", "G°"),
    "A" to listOf("A", "Bm", "C♯m", "D", "E", "F♯m", "G♯°"),
    "B♭" to listOf("B♭", "Cm", "Dm", "E♭", "F", "Gm", "A°"),
    "B" to listOf("B", "C♯m", "D♯m", "E", "F♯", "G♯m", "A♯°")
)

val MINOR_CHORDS_MAP = mapOf(
    "Cm" to listOf("Cm", "D°", "E♭", "Fm", "Gm", "A♭", "B♭"),
    "C♯m" to listOf("C♯m", "D♯°", "E", "F♯m", "G♯m", "A", "B"),
    "Dm" to listOf("Dm", "E°", "F", "Gm", "Am", "B♭", "C"),
    "D♯m" to listOf("D♯m", "E♯°", "F♯", "G♯m", "A♯m", "B", "C♯"),
    "Em" to listOf("Em", "F♯°", "G", "Am", "Bm", "C", "D"),
    "Fm" to listOf("Fm", "G°", "A♭", "B♭m", "Cm", "D♭", "E♭"),
    "F♯m" to listOf("F♯m", "G♯°", "A", "Bm", "C♯m", "D", "E"),
    "Gm" to listOf("Gm", "A°", "B♭", "Cm", "Dm", "E♭", "F"),
    "G♯m" to listOf("G♯m", "A♯°", "B", "C♯m", "D♯m", "E", "F♯"),
    "Am" to listOf("Am", "B°", "C", "Dm", "Em", "F", "G"),
    "A♯m" to listOf("A♯m", "B♯°", "C♯", "D♯m", "E♯m", "F♯", "G♯"),
    "Bm" to listOf("Bm", "C♯°", "D", "Em", "F♯m", "G", "A")
)

val ROMANS_LIST = listOf("I", "II", "III", "IV", "V", "VI", "VII")

// Raw data for Scales Tool
data class ScaleItem(val name: String, val notes: String)
data class ScaleGroup(val titleFa: String, val titleEn: String, val scales: List<ScaleItem>)

val SCALES_DATA_LIST = listOf(
    ScaleGroup(
        "گام‌های ماژور", "Major Scales", listOf(
            ScaleItem("C", "C - D - E - F - G - A - B"),
            ScaleItem("C♯", "C♯ - D♯ - F - F♯ - G♯ - A♯ - C"),
            ScaleItem("D", "D - E - F♯ - G - A - B - C♯"),
            ScaleItem("D♯", "D♯ - F - G - G♯ - A♯ - C - D"),
            ScaleItem("E", "E - F♯ - G♯ - A - B - C♯ - D♯"),
            ScaleItem("F", "F - G - A - A♯ - C - D - E"),
            ScaleItem("F♯", "F♯ - G♯ - A♯ - B - C♯ - D♯ - F"),
            ScaleItem("G", "G - A - B - C - D - E - F♯"),
            ScaleItem("G♯", "G♯ - A♯ - C - C♯ - D♯ - F - G"),
            ScaleItem("A", "A - B - C♯ - D - E - F♯ - G♯"),
            ScaleItem("A♯", "A♯ - C - D - D♯ - F - G - A"),
            ScaleItem("B", "B - C♯ - D♯ - E - F♯ - G♯ - A♯")
        )
    ),
    ScaleGroup(
        "گام‌های مینور", "Minor Scales", listOf(
            ScaleItem("Cm", "C - D - D♯ - F - G - G♯ - A♯"),
            ScaleItem("C♯m", "C♯ - D♯ - E - F♯ - G♯ - A - B"),
            ScaleItem("Dm", "D - E - F - G - A - A♯ - C"),
            ScaleItem("D♯m", "D♯ - E - F♯ - G♯ - A♯ - B - C♯"),
            ScaleItem("Em", "E - F♯ - G - A - B - C - D"),
            ScaleItem("Fm", "F - G - G♯ - A♯ - C - C♯ - D♯"),
            ScaleItem("F♯m", "F♯ - G♯ - A - B - C♯ - D - E"),
            ScaleItem("Gm", "G - A - A♯ - C - D - D♯ - F"),
            ScaleItem("G♯m", "G♯ - A♯ - B - C♯ - D♯ - E - F♯"),
            ScaleItem("Am", "A - B - C - D - E - F - G"),
            ScaleItem("A♯m", "A♯ - C - C♯ - D♯ - F - F♯ - G♯"),
            ScaleItem("Bm", "B - C♯ - D - E - F♯ - G - A")
        )
    )
)

// Raw data for Modes Tool
data class ModeData(
    val id: String,
    val name: String,
    val num: String,
    val charFa: String,
    val charEn: String,
    val formula: String,
    val example: String,
    val descFa: String,
    val descEn: String,
    val maj: List<Int>,
    val min: List<Int>,
    val dim: List<Int>
)

val MODES_DATA_LIST = listOf(
    ModeData(
        "ionian", "Ionian", "I", "= ماژور — شاد", "= Major — Bright", "W-W-H-W-W-W-H", "C: C-D-E-F-G-A-B",
        "مد Ionian همان گام ماژور طبیعی است. صدایی شاد و روشن دارد و در موسیقی پاپ، راک و کلاسیک رایج است.",
        "The Ionian mode is the natural major scale. It sounds bright, triumphant and happy; widely used in pop, rock and classical music.",
        listOf(1, 4, 5), listOf(2, 3, 6), listOf(7)
    ),
    ModeData(
        "dorian", "Dorian", "II", "مینور + ۶م بزرگ", "Minor + Major 6th", "W-H-W-W-W-H-W", "D: D-E-F-G-A-B-C",
        "مد Dorian مینور با ششم بزرگ است. صدایی جازی و فولکلور دارد و در موسیقی فیلم، جاز و فولک زیاد استفاده می‌شود.",
        "The Dorian mode is a minor scale with a major 6th. It has a jazzy, sophisticated or soulful flavor; prominent in jazz, folk, and movie soundtracks.",
        listOf(3, 4, 7), listOf(1, 2, 5), listOf(6)
    ),
    ModeData(
        "phrygian", "Phrygian", "III", "دوم کوچک — اگزوتیک", "Minor + Flat 2nd — Exotic", "H-W-W-W-H-W-W", "E: E-F-G-A-B-C-D",
        "مد Phrygian با دوم کوچک طعمی اسپانیایی و اگزوتیک دارد. در فلامنکو، موسیقی شرقی و ژانر متال بسیار رایج است.",
        "The Phrygian mode is a minor scale with a flat 2nd. It has an exotic, dark, Spanish flamenco vibe; highly popular in flamenco, Middle Eastern music, and heavy metal.",
        listOf(2, 3, 6), listOf(1, 4, 7), listOf(5)
    ),
    ModeData(
        "lydian", "Lydian", "IV", "چهارم افزوده — رویایی", "Major + Sharp 4th — Dreamy", "W-W-W-H-W-W-H", "F: F-G-A-B-C-D-E",
        "مد Lydian با چهارم افزوده صدایی فضایی و رویایی دارد. در موسیقی متن فیلم‌های تخیلی و جاز کاربرد فراوانی دارد.",
        "The Lydian mode is a major scale with a sharp 4th. It sounds ethereal, mystical, and sci-fi dreamy; heavily favored by film composers and jazz fusion artists.",
        listOf(1, 4, 5), listOf(2, 3, 6), listOf(7)
    ),
    ModeData(
        "mixolydian", "Mixolydian", "V", "هفتم کوچک — بلوزی", "Major + Flat 7th — Bluesy", "W-W-H-W-W-H-W", "G: G-A-B-C-D-E-F",
        "مد Mixolydian با هفتم کوچک صدایی صمیمی و بلوزی دارد. یکی از پرکاربردترین مدها در بلوز، کلاسیک راک و موسیقی فولک است.",
        "The Mixolydian mode is a major scale with a flat 7th. It has a friendly, laid-back, bluesy/classic rock sound; fundamental in blues, country, and rock & roll.",
        listOf(1, 4, 5), listOf(2, 5, 6), listOf(3)
    ),
    ModeData(
        "aeolian", "Aeolian", "VI", "= مینور طبیعی", "= Natural Minor", "W-H-W-W-H-W-W", "A: A-B-C-D-E-F-G",
        "مد Aeolian همان گام مینور طبیعی است. صدایی ملانکولیک، غمگین و عمیق دارد و در انواع سبک‌ها استفاده می‌شود.",
        "The Aeolian mode is the natural minor scale. It is emotional, melancholic, and tragic; used extensively across all genres.",
        listOf(3, 6, 7), listOf(1, 4, 5), listOf(2)
    ),
    ModeData(
        "locrian", "Locrian", "VII", "پنجم کاسته — ناپایدار", "Diminished 5th — Tense/Unstable", "H-W-W-H-W-W-W", "B: B-C-D-E-F-G-A",
        "مد Locrian با پنجم کاسته و دوم کوچک ناپایدارترین مد است. صدایی بسیار تاریک و پر تنش دارد و در سبک‌های تجربی و متال به کار می‌رود.",
        "The Locrian mode features a diminished 5th. It is extremely tense, unresolved, and dark; rarely heard in commercial pop but prized in experimental music and technical metal.",
        listOf(2, 5, 6), listOf(3, 4, 7), listOf(1)
    )
)

// Raw data for Circle of Fifths
data class CircleItem(
    val maj: String,
    val min: String,
    val majKey: String,
    val minKey: String,
    val sigCount: Int,
    val sigType: String, // "♯", "♭", or ""
    val accidentals: List<String>
)

val CIRCLE_DATA_LIST = listOf(
    CircleItem("C", "Am", "C", "Am", 0, "", emptyList()),
    CircleItem("G", "Em", "G", "Em", 1, "♯", listOf("F♯")),
    CircleItem("D", "Bm", "D", "Bm", 2, "♯", listOf("F♯", "C♯")),
    CircleItem("A", "F♯m", "A", "F♯m", 3, "♯", listOf("F♯", "C♯", "G♯")),
    CircleItem("E", "C♯m", "E", "C♯m", 4, "♯", listOf("F♯", "C♯", "G♯", "D♯")),
    CircleItem("B", "G♯m", "B", "G♯m", 5, "♯", listOf("F♯", "C♯", "G♯", "D♯", "A♯")),
    CircleItem("F♯ / G♭", "D♯m", "F♯", "D♯m", 6, "♯", listOf("F♯", "C♯", "G♯", "D♯", "A♯", "E♯")),
    CircleItem("D♭", "B♭m", "D♭", "B♭m", 5, "♭", listOf("B♭", "E♭", "A♭", "D♭", "G♭")),
    CircleItem("A♭", "Fm", "A♭", "Fm", 4, "♭", listOf("B♭", "E♭", "A♭", "D♭")),
    CircleItem("E♭", "Cm", "E♭", "Cm", 3, "♭", listOf("B♭", "E♭", "A♭")),
    CircleItem("B♭", "Gm", "B♭", "Gm", 2, "♭", listOf("B♭", "E♭")),
    CircleItem("F", "Dm", "F", "Dm", 1, "♭", listOf("B♭"))
)

// Quiz questions
data class QuizQuestion(
    val qFa: String,
    val qEn: String,
    val optsFa: List<String>,
    val optsEn: List<String>,
    val ans: Int,
    val expFa: String,
    val expEn: String
)

val QUIZ_QUESTIONS = listOf(
    QuizQuestion(
        "الگوی فواصل گام ماژور کدام است؟",
        "What is the pattern of intervals for a major scale?",
        listOf("پرده-پرده-نیم-پرده-پرده-پرده-نیم", "پرده-نیم-پرده-پرده-نیم-پرده-پرده", "پرده-نیم-پرده-پرده-نیم-یک‌و‌نیم-نیم", "نیم-پرده-پرده-پرده-نیم-پرده-پرده"),
        listOf("W-W-H-W-W-W-H", "W-H-W-W-H-W-W", "W-H-W-W-H-1.5-H", "H-W-W-W-H-W-W"),
        0,
        "الگوی گام ماژور: پرده-پرده-نیم-پرده-پرده-پرده-نیم (W-W-H-W-W-W-H) است.",
        "The Major Scale pattern is Whole-Whole-Half-Whole-Whole-Whole-Half (W-W-H-W-W-W-H)."
    ),
    QuizQuestion(
        "♯ (دیز) نت را چه تغییری می‌دهد؟",
        "What does a sharp (♯) sign do to a note?",
        listOf("یک پرده بالا می‌برد", "نیم پرده بالا می‌برد", "نیم پرده پایین می‌آورد", "یک پرده پایین می‌آورد"),
        listOf("Raises it by a whole step", "Raises it by a half step", "Lowers it by a half step", "Lowers it by a whole step"),
        1,
        "دیز نت را نیم پرده بالا می‌برد. بمل (♭) نت را نیم پرده پایین می‌آورد.",
        "A sharp raises a note by a half step. A bemol (flat) lowers it by a half step."
    ),
    QuizQuestion(
        "آکورد ماژور از کدام ترکیب ساخته می‌شود؟",
        "What is the construction of a Major Triad?",
        listOf("سوم کوچک + سوم بزرگ", "سوم بزرگ + سوم کوچک", "سوم کوچک + سوم کوچک", "سوم بزرگ + سوم بزرگ"),
        listOf("Minor 3rd + Major 3rd", "Major 3rd + Minor 3rd", "Minor 3rd + Minor 3rd", "Major 3rd + Major 3rd"),
        1,
        "آکورد ماژور = سوم بزرگ (پایه تا سوم) + سوم کوچک (سوم تا پنجم). آکورد مینور برعکس است.",
        "A major chord consists of a Major 3rd at the bottom and a Minor 3rd on top. Minor chord is the opposite."
    ),
    QuizQuestion(
        "در گام ماژور، آکوردهای روی درجات I، IV، V چه نوعی هستند؟",
        "In a major scale, what types of chords are built on degrees I, IV, and V?",
        listOf("مینور", "ماژور", "کاسته (Diminished)", "افزوده (Augmented)"),
        listOf("Minor", "Major", "Diminished", "Augmented"),
        1,
        "در گام ماژور: درجات I, IV, V ماژور هستند؛ II, III, VI مینور و VII کاسته (dim) است.",
        "In a major scale, triads on I, IV, and V are Major; II, III, and VI are Minor; VII is Diminished."
    ),
    QuizQuestion(
        "گام مینور نسبی هر گام ماژور از کدام درجه آغاز می‌شود؟",
        "The relative minor of any major scale starts on which degree?",
        listOf("درجه چهارم", "درجه پنجم", "درجه ششم", "درجه هفتم"),
        listOf("4th degree", "5th degree", "6th degree", "7th degree"),
        2,
        "مینور نسبی از درجه ششم گام ماژور آغاز می‌شود، که دقیقاً ۳ نیم‌پرده پایین‌تر از پایه ماژور است.",
        "The relative minor starts on the 6th degree of the major scale, which is 3 semitones below the major root."
    ),
    QuizQuestion(
        "تفاوت اصلی گام مینور هارمونیک با مینور طبیعی در چیست؟",
        "What is the main difference between Harmonic Minor and Natural Minor?",
        listOf("نت سوم دیز (نیم‌پرده بالا) می‌شود", "نت ششم بمل می‌شود", "نت هفتم دیز (نیم‌پرده بالا) می‌شود", "نت پنجم دیز می‌شود"),
        listOf("The 3rd note is raised", "The 6th note is lowered", "The 7th note (leading tone) is raised", "The 5th note is raised"),
        2,
        "در مینور هارمونیک نسبت به مینور طبیعی، نت هفتم (محسوس) نیم‌پرده بالا برده می‌شود تا کشش حرکتی به پایه ایجاد شود.",
        "In harmonic minor, the 7th scale degree is raised by a half step to create a leading tone towards the tonic."
    ),
    QuizQuestion(
        "درجه پنجم (V) گام چه نام دارد؟",
        "What is the name of the 5th degree (V) of a scale?",
        listOf("تونیک (پایه)", "دومینانت (نمایان)", "ساب‌دومینانت (زیرنمایان)", "لیدینگ‌تون (محسوس)"),
        listOf("Tonic", "Dominant", "Subdominant", "Leading Tone"),
        1,
        "درجه V گام را نمایان یا Dominant می‌نامند که قوی‌ترین کشش هارمونیک را به تونیک دارد.",
        "The 5th degree is called Dominant. It possesses the strongest harmonic pull back to the Tonic."
    ),
    QuizQuestion(
        "انارمونیک (Enharmonic) به چه معناست؟",
        "What does Enharmonic mean in music?",
        listOf("نت‌های هم‌نام با فرکانس متفاوت", "نت‌های با اسامی متفاوت ولی صدای یکسان (هم‌صدا)", "نت‌هایی که دقیقاً یک پرده فاصله دارند", "نت‌هایی با ارزش زمانی برابر"),
        listOf("Same name with different pitch", "Different spelling but same pitch (homologous)", "Notes spaced exactly a whole tone apart", "Notes of identical duration"),
        1,
        "انارمونیک یعنی دو نت با نام‌های متفاوت ولی صدای یکسان، مانند C♯ و D♭ روی کلاویه پیانو.",
        "Enharmonic notes sound identical but have different letter names (such as C♯ and D♭)."
    ),
    QuizQuestion(
        "در کسر میزان، عدد مخرج نشان‌دهنده چیست؟",
        "In a time signature, what does the bottom number represent?",
        listOf("تعداد ضرب‌ها در هر میزان", "شکل نت هر ضرب (ارزش زمانی)", "تمپو قطعه", "تعداد میزان‌ها"),
        listOf("Number of beats per measure", "The note value of each beat", "The tempo indicator", "The total count of measures"),
        1,
        "صورت کسر نشان‌دهنده تعداد ضرب‌ها در هر میزان، و مخرج نشان‌دهنده شکل نت هر ضرب است (مثلاً ۴ برای نت سیاه).",
        "The top number is the beats per bar; the bottom is the note division representing the beat unit (e.g., 4 = quarter note)."
    ),
    QuizQuestion(
        "مد Dorian با گام مینور طبیعی چه تفاوتی دارد؟",
        "How does the Dorian mode differ from the Natural Minor scale?",
        listOf("دارای درجه دوم بالاتر (بزرگ) است", "دارای درجه ششم بالاتر (بزرگ) است", "دارای درجه سوم بالاتر (بزرگ) است", "دارای درجه هفتم بالاتر (بزرگ) است"),
        listOf("It has a raised 2nd degree", "It has a raised 6th degree (major 6th)", "It has a raised 3rd degree", "It has a raised 7th degree"),
        1,
        "مد Dorian نسبت به گام مینور طبیعی (مد Aeolian)، دارای درجه ششم بزرگ (نیم‌پرده بالاتر) است.",
        "The Dorian mode is a minor scale with a raised 6th scale degree (major 6th instead of minor 6th)."
    ),
    QuizQuestion(
        "کدام‌یک از فواصل زیر جزء مطبوع‌ترین (خوشایندترین) فواصل هم‌صدا به شمار می‌رود؟",
        "Which of the following is considered the most consonant interval?",
        listOf("سوم بزرگ", "پنجم درست", "یکم درست (هم‌صدا)", "ششم بزرگ"),
        listOf("Major 3rd", "Perfect 5th", "Perfect Unison (Octave/Unison)", "Major 6th"),
        2,
        "یکم درست و هشتم درست (اکتاو) به علت سادگی نسبت فرکانس (۱:۱ و ۲:۱) مطبوع‌ترین فواصل فیزیکی هستند.",
        "Perfect Unison and Perfect Octave, due to simplest mathematical frequency ratios (1:1 and 2:1), are the most consonant intervals."
    ),
    QuizQuestion(
        "الگوی فاصله گام پنتاتونیک مینور چیست؟",
        "What is the pattern of intervals for the Minor Pentatonic scale?",
        listOf("۱ - ۱ - ۱.۵ - ۱ - ۱.5", "۱.۵ - ۱ - ۱ - ۱.۵ - ۱", "۱ - ۰.۵ - ۱ - ۱ - ۱.۵", "۰.۵ - ۱ - ۱ - ۱ - ۱.۵"),
        listOf("1 - 1 - 1.5 - 1 - 1.5", "1.5 - 1 - 1 - 1.5 - 1", "1 - 0.5 - 1 - 1 - 1.5", "0.5 - 1 - 1 - 1 - 1.5"),
        1,
        "پنتاتونیک مینور دارای ۵ نت است و الگوی فواصل آن از ریشه: ۱.۵ پرده - ۱ - ۱ - ۱.۵ - ۱ پرده است.",
        "The Minor Pentatonic scale has 5 notes and its interval formula is: 1.5 steps - 1 - 1 - 1.5 - 1."
    )
)

// Complete Course Content list
val LESSONS_LIST = listOf(
    Lesson(
        Screen.ALTERATION,
        "بالا و پایین بردن نت‌ها به میزان نیم پرده را تغییرات کروماتیک یا آلتراسیون می‌نامند.",
        "Altering notes up or down by a semitone is called alteration or chromatic inflection.",
        listOf(
            LessonBlock.HighlightBlock(
                "تعریف آلتراسیون: بالا و پایین بردن نت‌ها به میزان نیم پرده را آلتراسیون می‌نامند.",
                "Definition: Alteration refers to raising or lowering a pitch by a semitone to introduce harmonic variety."
            ),
            LessonBlock.HighlightPointsBlock(
                listOf(
                    "♯ دیز (Sharp) — نت را نیم پرده بالا می‌برد",
                    "♭ بمل (Flat/Bemol) — نت را نیم پرده پایین می‌برد",
                    "♮ بکار (Natural) — اثر دیز یا بمل را خنثی می‌کند"
                ),
                listOf(
                    "♯ Sharp — Raises the note by a half step (semitone)",
                    "♭ Flat — Lowers the note by a half step (semitone)",
                    "♮ Natural — Cancels out a previous sharp or flat"
                )
            ),
            LessonBlock.HighlightBlock(
                "انارمونیک (Enharmonic): نت‌های هم‌صدا با نام‌های متفاوت. به عنوان مثال، نت C♯ و D♭ روی پیانو یک کلاویه مشترک را فشار می‌دهند ولی در تئوری موسیقی کاربردهای متفاوتی دارند.",
                "Enharmonic Notes: Notes that sound identical but are spelled differently. For example, C♯ and D♭ share the same physical piano key but serve distinct harmonic functions."
            ),
            LessonBlock.TableBlock(
                listOf("نوع نیم‌پرده", "توضیح", "مثال"),
                listOf("Semitone Type", "Explanation", "Example"),
                listOf(
                    listOf("دیاتونیک (Diatonic)", "بین دو نت غیر همنام", "E → F  یا  C → D♭"),
                    listOf("کروماتیک (Chromatic)", "بین دو نت همنام با علامت تغییردهنده", "C → C♯  یا  A → A♭")
                )
            )
        )
    ),
    Lesson(
        Screen.NOTES,
        "آشنایی با نمادهای زمانی نت‌ها و ارزش‌های نسبی آن‌ها در میزان، خطوط اتصال و نقاط تمدید زمانی.",
        "Learn about note durations, visual symbols, rests, dots of addition, and ties.",
        listOf(
            LessonBlock.TableBlock(
                listOf("نام نت", "نماد", "ارزش زمانی نسبی (ضرب)"),
                listOf("Note Name", "Symbol", "Relative Value (Beats)"),
                listOf(
                    listOf("نت گرد (Whole Note)", "𝅝", "۴ ضرب"),
                    listOf("نت سفید (Half Note)", "𝅗𝅥", "۲ ضرب"),
                    listOf("نت سیاه (Quarter Note)", "𝅘𝅥", "۱ ضرب"),
                    listOf("نت چنگ (Eighth Note)", "𝅘𝅥𝅮", "نصف ضرب (۰.۵)"),
                    listOf("نت دولاچنگ (Sixteenth Note)", "𝅘𝅥𝅯", "یک چهارم ضرب (۰.۲۵)")
                )
            ),
            LessonBlock.HighlightBlock(
                "سکوت‌ها (Rests): هر نت موسیقی یک معادل سکوت دارد که نشان‌دهنده مدت زمان خاموشی و عدم نوازندگی با همان طول زمانی است. مثلاً سکوت گرد (𝄻) و سکوت سفید (𝄼).",
                "Rests: Every musical note has an equivalent rest representing a duration of silence. E.g., Whole Rest (𝄻) and Half Rest (𝄼)."
            ),
            LessonBlock.HighlightPointsBlock(
                listOf(
                    "نقطه اول در سمت راست نت: نصف ارزش زمانی نت را به آن اضافه می‌کند.",
                    "نقطه دوم: نصف ارزش نقطه اول (یک‌چهارم نت اصلی) را اضافه می‌کند."
                ),
                listOf(
                    "First Dot: Adds half the original value of the note to its duration.",
                    "Second Dot: Adds half of the first dot's value (one-quarter of the original note)."
                )
            ),
            LessonBlock.TableBlock(
                listOf("نوع خط تمدید", "نام لاتین", "توضیح"),
                listOf("Extension Line", "Latin/English Name", "Explanation"),
                listOf(
                    listOf("خط اتحاد", "Tie", "دو نت همنام را به هم وصل کرده و ارزش زمانی آن‌ها را جمع می‌کند."),
                    listOf("خط اتصال", "Slur", "دو نت غیر همنام را به هم وصل می‌کند تا به صورت نرم و بدون وقفه (لگاتو) نواخته شوند.")
                )
            )
        )
    ),
    Lesson(
        Screen.METER,
        "میزان‌ها حامل موسیقی را به بخش‌های زمانی مساوی تقسیم می‌کنند و ریتم قطعه را تعیین می‌کنند.",
        "Measures divide the musical timeline into structured beat divisions, establishing the rhythm.",
        listOf(
            LessonBlock.HighlightBlock(
                "کسر میزان (Time Signature): دو عدد عمودی در ابتدای حامل. عدد بالا نشان‌دهنده تعداد ضرب در هر میزان و عدد پایین نشان‌دهنده ارزش زمانی هر ضرب است (مخرج ۴ یعنی سیاه، مخرج ۸ یعنی چنگ).",
                "Time Signature: Written at the beginning of a staff. The top number indicates beats per measure, while the bottom indicates the note value of each beat (e.g., 4 = quarter note, 8 = eighth note)."
            ),
            LessonBlock.TableBlock(
                listOf("دسته‌بندی میزان", "مثال‌ها", "ویژگی بارز"),
                listOf("Meter Classification", "Examples", "Key Characteristic"),
                listOf(
                    listOf("ساده دوتایی", "2/4 - 2/2", "میزان ۲ ضربی، تقسیم‌پذیری طبیعی هر ضرب به ۲ بخش"),
                    listOf("ساده سه‌تایی", "3/4 - 3/8", "میزان ۳ ضربی ساده"),
                    listOf("ترکیبی", "6/8 - 9/8 - 12/8", "ضرب‌های نقطه‌دار (مثل سیاه نقطه‌دار) که به ۳ تقسیم می‌شوند"),
                    listOf("لنگ (مختلط)", "5/8 - 7/8", "ضرب‌های نامتقارن، ترکیبی از گروه‌های ۲تایی و ۳تایی")
                )
            )
        )
    ),
    Lesson(
        Screen.SOUND,
        "صدا حاصل ارتعاش ماده است و در فیزیک موسیقی با چهار مشخصه فیزیکی اصلی شناخته می‌شود.",
        "Sound is created by air pressure waves. In music, it is classified by four essential properties.",
        listOf(
            LessonBlock.HighlightPointsBlock(
                listOf(
                    "۱. زیر و بمی (کوک / Pitch): بسامد ارتعاش که مشخص می‌کند صدا چقدر بم یا زیر است.",
                    "۲. کشش (دیرند / Duration): مدت زمان تداوم ارتعاش صدا.",
                    "۳. شدت (نیرو / Dynamics / Velocity): دامنه موج صدا که تعیین‌کننده بلندی و پویایی صدا است.",
                    "۴. طنین (رنگ صدا / Timbre): شکل موج ارتعاشی که باعث تفکیک صدای ویولن، پیانو یا حنجره انسان می‌شود."
                ),
                listOf(
                    "1. Pitch: The frequency of vibration, determining how high or low a note sounds.",
                    "2. Duration: The length of time the sound waves persist.",
                    "3. Dynamics / Velocity: The amplitude of the wave, determining volume and energy.",
                    "4. Timbre / Tone Color: The harmonic profile, allowing us to tell a violin from a piano."
                )
            )
        )
    ),
    Lesson(
        Screen.MAJOR,
        "گام ماژور روشن‌ترین، متداول‌ترین و پایه‌ای‌ترین الگوی موسیقی تونال غربی است.",
        "The major scale is the foundational formula of western tonal harmony, producing a bright, stable sound.",
        listOf(
            LessonBlock.HighlightBlock(
                "الگوی فواصل گام ماژور طبیعی: از نت پایه آغاز شده و دارای فواصل زیر است:",
                "Formula of the Major Scale: Starting from the root pitch, it ascends with these steps:"
            ),
            LessonBlock.PatternBlock(listOf("Whole", "Whole", "Half", "Whole", "Whole", "Whole", "Half")),
            LessonBlock.HighlightBlock(
                "گام مینور نسبی (Relative Minor): هر گام ماژور یک گام مینور همزاد دارد که از درجه ششم آن آغاز می‌شود و علائم سرکلید کاملاً یکسانی دارد.",
                "Relative Minor: Every major scale shares its key signature with a relative minor scale starting on the 6th degree."
            ),
            LessonBlock.HighlightPointsBlock(
                listOf(
                    "۳ نیم‌پرده پایین‌تر از پایه ماژور = پایه گام مینور نسبی (C ماژور → A مینور)",
                    "افزودن دیزها در دایره پنجم‌ها با حرکت به سمت پنجم‌های درست صعودی (V)"
                ),
                listOf(
                    "3 semitones below the major root = relative minor root (C Major → A Minor)",
                    "Sharps accumulate by moving clockwise to the 5th scale degree (V)"
                )
            )
        )
    ),
    Lesson(
        Screen.MINOR,
        "سه نوع گام مینور وجود دارد: طبیعی، هارمونیک و ملودیک که هر کدام بیان حسی متفاوتی دارند.",
        "There are three variants of the minor scale: Natural, Harmonic, and Melodic, each carrying unique emotional weight.",
        listOf(
            LessonBlock.HighlightBlock(
                "۱. مینور طبیعی (Natural Minor / Aeolian): گامی با فواصل غمگین و باستانی.",
                "1. Natural Minor: Built strictly on the shared relative major notes, with a melancholic feel."
            ),
            LessonBlock.PatternBlock(listOf("Whole", "Half", "Whole", "Whole", "Half", "Whole", "Whole")),
            LessonBlock.HighlightBlock(
                "۲. مینور هارمونیک (Harmonic Minor): نت هفتم نیم‌پرده دیز می‌شود تا محسوس (Leading Tone) ایجاد شود.",
                "2. Harmonic Minor: The 7th note is raised by a half step to create a leading tone, forming an augmented 2nd interval between degree 6 and 7."
            ),
            LessonBlock.PatternBlock(listOf("Whole", "Half", "Whole", "Whole", "Half", "1.5 (Aug)", "Half")),
            LessonBlock.HighlightBlock(
                "۳. مینور ملودیک (Melodic Minor): در صعود درجات ۶ و ۷ دیز می‌شوند و در فرود به حالت طبیعی باز می‌گردند.",
                "3. Melodic Minor: Sharps degrees 6 and 7 on the way up, but reverts to natural minor on the way down."
            ),
            LessonBlock.PatternBlock(listOf("Whole", "Half", "Whole", "Whole", "Whole", "Whole", "Half")),
            LessonBlock.TableBlock(
                listOf("تبدیل از ماژور موازی", "درجات تغییر یافته نسبت به ماژور هم‌نام"),
                listOf("Parallel Major Conversion", "Altered Degrees compared to Parallel Major"),
                listOf(
                    listOf("به مینور طبیعی", "بمل کردن درجات ۳، ۶ و ۷"),
                    listOf("به مینور هارمونیک", "بمل کردن درجات ۳ و ۶")
                )
            )
        )
    ),
    Lesson(
        Screen.PENTATONIC,
        "گام‌های پنج‌نتی در تمامی فرهنگ‌های موسیقی باستان یافت می‌شوند و صدایی طبیعی و حل‌نشده دارند.",
        "Five-note scales bypass tense half-steps, delivering an organic, highly melodic and universally pleasing sound.",
        listOf(
            LessonBlock.HighlightBlock(
                "پنتاتونیک ماژور: حذف درجات ۴ و ۷ از گام ماژور طبیعی (بدون نیم پرده). الگو:",
                "Major Pentatonic: Formed by omitting the 4th and 7th degrees of the major scale (no half-steps). Formula:"
            ),
            LessonBlock.PatternBlock(listOf("1 (Whole)", "1 (Whole)", "1.5 (Whole+Half)", "1 (Whole)", "1.5 (Whole+Half)")),
            LessonBlock.HighlightBlock(
                "پنتاتونیک مینور: حذف درجات ۲ و ۶ از گام مینور طبیعی. سنگ بنای موسیقی بلوز، راک و سولو نوازی. الگو:",
                "Minor Pentatonic: Formed by omitting the 2nd and 6th degrees of natural minor. The cornerstone of blues and rock. Formula:"
            ),
            LessonBlock.PatternBlock(listOf("1.5 (Whole+Half)", "1 (Whole)", "1 (Whole)", "1.5 (Whole+Half)", "1 (Whole)")),
            LessonBlock.HighlightBlock(
                "گام‌های بلوز (Blues Scales): با اضافه کردن یک 'نت آبی' (Blue Note) یا کروماتیک میانی به گام پنتاتونیک به دست می‌آیند. در بلوز مینور، نت افزوده پنجم کاسته (♭5) بین درجه ۳ و ۴ پنتاتونیک است.",
                "Blues Scales: Created by adding a chromatic 'blue note' to the pentatonic scale. In minor blues, a flat 5th (♭5) is added to the minor pentatonic."
            )
        )
    ),
    Lesson(
        Screen.GMODES,
        "هفت مد کلیسایی (Church Modes) سیستم‌های مدالی هستند که از درجات مختلف گام ماژور ساخته می‌شوند.",
        "Church modes are modal scales derived by starting the major scale formula on different starting degrees.",
        listOf(
            LessonBlock.HighlightPointsBlock(
                listOf(
                    "I. Ionian (ماژور طبیعی) — شاداب و سرزنده",
                    "II. Dorian (مینور با درجه ۶ بزرگ) — جازی و فولک",
                    "III. Phrygian (مینور با درجه ۲ بمل) — اگزوتیک و اسپانیایی",
                    "IV. Lydian (ماژور با درجه ۴ دیز) — رویایی و فضایی",
                    "V. Mixolydian (ماژور با درجه ۷ بمل) — بلوزی و کلاسیک راک",
                    "VI. Aeolian (مینور طبیعی) — ملانکولیک و حزن‌انگیز",
                    "VII. Locrian (کاسته با درجه ۵ بمل) — بسیار تاریک و ناپایدار"
                ),
                listOf(
                    "I. Ionian (Natural Major) — Happy and resolved",
                    "II. Dorian (Minor + Major 6th) — Soulful, jazzy, and medieval",
                    "III. Phrygian (Minor + Flat 2nd) — Exotic, Spanish flamenco",
                    "IV. Lydian (Major + Sharp 4th) — Dreamy, spacey, cinematic",
                    "V. Mixolydian (Major + Flat 7th) — Rock, country, and bluesy",
                    "VI. Aeolian (Natural Minor) — Sad, dark, and romantic",
                    "VII. Locrian (Diminished + Flat 5th) — Unstable, tense, and heavy"
                )
            )
        )
    ),
    Lesson(
        Screen.KEYSIG,
        "آرمیچر یا علائم سرکلید، برای حفظ انسجام گام و عدم نیاز به نوشتن علائم مکرر روی حامل نوشته می‌شوند.",
        "Key signatures are written at the start of the staff to establish the scale without repeating accidentals.",
        listOf(
            LessonBlock.HighlightBlock(
                "ترتیب افزودن دیزها (Sharps Order): با فاصله پنجم صعودی از فا:",
                "The Order of Sharps (♯): Progressing in perfect fifths starting from F:"
            ),
            LessonBlock.HighlightBlock(
                "Fa (فا) - Do (دو) - Sol (سل) - Re (ر) - La (لا) - Mi (می) - Si (سی)",
                "F - C - G - D - A - E - B"
            ),
            LessonBlock.HighlightBlock(
                "قانون طلایی دیزها: آخرین دیز را پیدا کنید؛ نیم پرده بالاتر از آن، نام گام ماژور است.",
                "Sharp Rule: Find the last sharp on the staff; a half step above it is the root of the major key."
            ),
            LessonBlock.HighlightBlock(
                "ترتیب افزودن بمل‌ها (Flats Order): عکس ترتیب دیزها، با فاصله چهارم صعودی از سی:",
                "The Order of Flats (♭): Reverse of the sharps order, progressing in perfect fourths starting from B:"
            ),
            LessonBlock.HighlightBlock(
                "Si (سی) - Mi (می) - La (لا) - Re (ر) - Sol (سل) - Do (دو) - Fa (فا)",
                "B - E - A - D - G - C - F"
            ),
            LessonBlock.HighlightBlock(
                "قانون طلایی بمل‌ها: بمل ماقبل آخر روی خطوط سرکلید، دقیقاً نام گام ماژور است (اگر فقط یک بمل یعنی Si وجود دارد، گام F ماژور است).",
                "Flat Rule: The second-to-last flat on the staff is the root of the major key (if there is only one flat B♭, the key is F major)."
            )
        )
    ),
    Lesson(
        Screen.DEGREES,
        "هر درجه از گام نقش هارمونیک متفاوتی ایفا می‌کند. سه درجه تونال و چهار درجه مدال داریم.",
        "Each scale degree represents a specific harmonic function. They are divided into Tonal and Modal degrees.",
        listOf(
            LessonBlock.HighlightPointsBlock(
                listOf(
                    "I - Tonic (پایه): مرکز ثقل و حل نهایی قطعه.",
                    "II - Supertonic (روپایه): درجه رابط.",
                    "III - Mediant (میانی): رنگ گام (ماژور/مینور بودن).",
                    "IV - Subdominant (زیرنمایان): کشش ملایم.",
                    "V - Dominant (نمایان): قوی‌ترین کشش حرکتی به سمت پایه.",
                    "VI - Submediant (رونمایان): درجه کمکی میانی.",
                    "VII - Leading Tone (محسوس): نیم‌پرده فاصله با پایه با کشش شدید حل."
                ),
                listOf(
                    "I - Tonic: Home key, final resolution, gravitational center.",
                    "II - Supertonic: Important pre-dominant transition chord.",
                    "III - Mediant: Midpoint, heavily defines major/minor color.",
                    "IV - Subdominant: Moderate tension, pulling away from Tonic.",
                    "V - Dominant: Strongest harmonic tension, resolves directly to Tonic.",
                    "VI - Submediant: Helper modal degree, lower mediant.",
                    "VII - Leading Tone: Extremely tense, half step below Tonic, demands resolution."
                )
            ),
            LessonBlock.TableBlock(
                listOf("نوع درجه", "درجات گام", "ویژگی"),
                listOf("Degree Type", "Scale Degrees", "Characteristic"),
                listOf(
                    listOf("تونال (Tonal)", "I, IV, V", "ثابت و مقتدر، ستون‌های ساختاری تنالیته"),
                    listOf("مدال (Modal)", "III, VI, VII", "تعیین‌کننده رنگ مدال و حسی گام")
                )
            )
        )
    ),
    Lesson(
        Screen.CHORDS,
        "آکورد به اجرای همزمان سه یا چند نت گفته می‌شود. آکوردهای سه‌نتی (Triads) پایه هارمونی هستند.",
        "A chord is the simultaneous sounding of three or more notes. Triads are the building blocks of harmony.",
        listOf(
            LessonBlock.HighlightBlock(
                "ساخت Triad: با اضافه کردن فواصل سوم و پنجم بالای نت ریشه ساخته می‌شود:",
                "Triad Formula: Built by adding a third and a fifth above the root note:"
            ),
            LessonBlock.HighlightPointsBlock(
                listOf(
                    "ریشه (Root) — نت مبدا و نام‌گذاری آکورد",
                    "سوم (Third) — تعیین‌کننده ماژور یا مینور بودن (سوم بزرگ یا کوچک)",
                    "پنجم (Fifth) — استحکام‌بخش آکورد (پنجم درست، کاسته یا افزوده)"
                ),
                listOf(
                    "Root — The base note which gives the chord its name",
                    "Third — Tells if the chord is major or minor (major/minor third interval)",
                    "Fifth — Provides stability (perfect, diminished, or augmented fifth interval)"
                )
            ),
            LessonBlock.TableBlock(
                listOf("نوع آکورد", "سوم اول (ریشه تا ۳)", "سوم دوم (۳ تا ۵)", "فاصله ریشه تا ۵"),
                listOf("Chord Type", "First Third (Root-3)", "Second Third (3-5)", "Root to 5th Interval"),
                listOf(
                    listOf("ماژور (Major)", "سوم بزرگ", "سوم کوچک", "پنجم درست"),
                    listOf("مینور (Minor)", "سوم کوچک", "سوم بزرگ", "پنجم درست"),
                    listOf("کاسته (Diminished / °)", "سوم کوچک", "سوم کوچک", "پنجم کاسته"),
                    listOf("افزوده (Augmented / +)", "سوم بزرگ", "سوم بزرگ", "پنجم افزوده")
                )
            ),
            LessonBlock.TableBlock(
                listOf("درجات گام ماژور", "نوع آکورد دیاتونیک مربوطه"),
                listOf("Major Scale Degrees", "Diatonic Chord Type"),
                listOf(
                    listOf("I, IV, V", "آکورد ماژور (بزرگ)"),
                    listOf("II, III, VI", "آکورد مینور (کوچک)"),
                    listOf("VII", "آکورد کاسته (Diminished)")
                )
            ),
            LessonBlock.TableBlock(
                listOf("درجات گام مینور طبیعی", "نوع آکورد دیاتونیک مربوطه"),
                listOf("Natural Minor Scale Degrees", "Diatonic Chord Type"),
                listOf(
                    listOf("I, IV, V", "آکورد مینور (کوچک)"),
                    listOf("III, VI, VII", "آکورد ماژور (بزرگ)"),
                    listOf("II", "آکورد کاسته (Diminished)")
                )
            )
        )
    ),
    Lesson(
        Screen.INTERVALS,
        "فواصل نشان‌دهنده تفاوت فرکانس بین دو نت هستند و به دو دسته مطبوع و نامطبوع تقسیم می‌شوند.",
        "Intervals measure the pitch distance between two notes, classified as consonant or dissonant.",
        listOf(
            LessonBlock.HighlightBlock(
                "فواصل مطبوع (Consonant): فواصلی پایدار و آرامش‌بخش که نیازی به حل ندارند.",
                "Consonant Intervals: Stable, pleasing combinations of pitches that do not require resolution."
            ),
            LessonBlock.HighlightPointsBlock(
                listOf(
                    "مطبوع کامل (Perfect): یکم درست (هم‌صدا)، هشتم درست (اکتاو)، پنجم درست و چهارم درست.",
                    "مطبوع ناقص (Imperfect): سوم بزرگ و کوچک، ششم بزرگ و کوچک."
                ),
                listOf(
                    "Perfect Consonance: Unison, Octave, Perfect 5th, Perfect 4th.",
                    "Imperfect Consonance: Major/Minor 3rd, Major/Minor 6th."
                )
            ),
            LessonBlock.HighlightBlock(
                "فواصل نامطبوع (Dissonant): فواصلی پر تنش و تند که گوش تمایل دارد آن‌ها را به سمت فواصل مطبوع حل کند.",
                "Dissonant Intervals: Unstable, harsh, or highly active intervals that create musical tension."
            ),
            LessonBlock.HighlightPointsBlock(
                listOf(
                    "دوم بزرگ و کوچک، هفتم بزرگ و کوچک.",
                    "فاصله تریتون (Tritone) یا چهارم افزوده / پنجم کاسته (شیطان در موسیقی!)"
                ),
                listOf(
                    "Major/Minor 2nd, Major/Minor 7th.",
                    "The Tritone (Augmented 4th / Diminished 5th) — historically labeled 'the devil in music'."
                )
            )
        )
    ),
    Lesson(
        Screen.POLYPHONY,
        "بافت موسیقی نشان‌دهنده چگونگی ترکیب خطوط ملودی و هارمونی با یکدیگر است.",
        "Texture describes how melodic, harmonic, and rhythmic elements are combined in a composition.",
        listOf(
            LessonBlock.TableBlock(
                listOf("بافت موسیقی", "تعریف", "مثال تاریخی / کاربردی"),
                listOf("Texture Type", "Definition", "Historical / Practical Example"),
                listOf(
                    listOf("تک‌صدایی (Monophonic)", "فقط یک خط ملودی تک و بدون هارمونی یا همراهی ساز.", "آوازهای گریگوری کلیسا، تکنوازی نی"),
                    listOf("هم‌بافتی (Homophonic)", "یک ملودی اصلی با آکوردها و هارمونی پشتیبان.", "بیشتر موسیقی‌های پاپ امروزی، سرودها"),
                    listOf("چندصدایی (Polyphonic)", "دو یا چند خط ملودی کاملاً مستقل که همزمان اجرا می‌شوند.", "فوگ‌های باخ، موسیقی کرال رنسانس")
                )
            ),
            LessonBlock.HighlightBlock(
                "تاریخچه بافت: چندصدایی از قرون وسطی با تکنیک 'ارگانم' (Organum) آغاز شد و در عصر باروک با کنترپوان باخ به اوج کمال خود رسید.",
                "History: Polyphony originated in the Middle Ages through 'Organum' and reached its absolute peak in the Baroque era with Bach's counterpoint."
            )
        )
    )
)
