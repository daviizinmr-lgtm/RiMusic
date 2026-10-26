package it.fast4x.rimusic.ui.styling

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import it.fast4x.rimusic.enums.ColorPaletteMode
import it.fast4x.rimusic.enums.ColorPaletteName

@Immutable
data class ColorPalette(
    val background0: Color,
    val background1: Color,
    val background2: Color,
    val background3: Color,
    val background4: Color,
    val accent: Color,
    val onAccent: Color,
    val red: Color = Color(0xffef4444),
    val blue: Color = Color(0xff3b82f6),
    val text: Color,
    val textSecondary: Color,
    val textDisabled: Color,
    val isDark: Boolean,
    val iconButtonPlayer: Color,
) {
    companion object : Saver<ColorPalette, List<Any>> {
        override fun restore(value: List<Any>) = when (val accent = value[0] as Int) {
            0 -> DefaultDarkColorPalette
            1 -> DefaultLightColorPalette
            2 -> PureBlackColorPalette
            3 -> ModernBlackColorPalette
            else -> dynamicColorPaletteOf(
                FloatArray(3).apply { ColorUtils.colorToHSL(accent, this) },
                value[1] as Boolean
            )
        }

        override fun SaverScope.save(value: ColorPalette) =
            listOf(
                when {
                    value === DefaultDarkColorPalette -> 0
                    value === DefaultLightColorPalette -> 1
                    value === PureBlackColorPalette -> 2
                    value === ModernBlackColorPalette -> 3
                    else -> value.accent.toArgb()
                },
                value.isDark
            )
    }
}

// DARK AMOLED THEME WITH CRISP WHITE BUTTONS & HIGH CONTRAST (PAÇOCAMUSIC)
val DefaultDarkColorPalette = ColorPalette(
    background0 = Color(0xFF000000),      // Pure AMOLED Black
    background1 = Color(0xFF000000),      // Pitch Black
    background2 = Color(0xFF101014),      // Subtle dark card
    background3 = Color(0xFF1F1F24),      // Subtle border
    background4 = Color(0xFF28282E),
    text = Color(0xFFFFFFFF),             // Pure white text
    textSecondary = Color(0xFFA1A1AA),    // Modern zinc
    textDisabled = Color(0xFF52525B),
    iconButtonPlayer = Color(0xFFFFFFFF), // White player controls
    accent = Color(0xFFFFFFFF),           // Crisp white buttons
    onAccent = Color(0xFF000000),         // Black icon on white button
    isDark = true
)

val DefaultLightColorPalette = ColorPalette(
    background0 = Color(0xfffdfdfe),
    background1 = Color(0xfff8f8fc),
    background2 = Color(0xffeaeaf5),
    background3 = Color(0xffeaeafd),
    background4 = Color(0xffeaeafd),
    text = Color(0xff212121),
    textSecondary = Color(0xff656566),
    textDisabled = Color(0xff9d9d9d),
    iconButtonPlayer = Color(0xff212121),
    accent = Color(0xFF000000),
    onAccent = Color.White,
    isDark = false
)

val PureBlackColorPalette = ColorPalette(
    background0 = Color.Black,
    background1 = Color.Black,
    background2 = Color(0xFF0C0C0E),
    background3 = Color(0xFF18181C),
    background4 = Color(0xFF222228),
    text = Color.White,
    textSecondary = Color(0xFFA1A1AA),
    textDisabled = Color(0xFF52525B),
    iconButtonPlayer = Color.White,
    accent = Color.White,
    onAccent = Color.Black,
    isDark = true
)

val ModernBlackColorPalette = PureBlackColorPalette.copy(
    background2 = Color(0xFF111114),
    background3 = Color(0xFF1E1E24),
    accent = Color.White,
    onAccent = Color.Black
)

fun colorPaletteOf(
    colorPaletteName: ColorPaletteName,
    colorPaletteMode: ColorPaletteMode,
    isSystemInDarkMode: Boolean
): ColorPalette {
    return when (colorPaletteName) {
        ColorPaletteName.Default, ColorPaletteName.Dynamic,
        ColorPaletteName.MaterialYou, ColorPaletteName.Customized, ColorPaletteName.CustomColor -> when (colorPaletteMode) {
            ColorPaletteMode.Light -> DefaultLightColorPalette
            ColorPaletteMode.Dark, ColorPaletteMode.PitchBlack -> PureBlackColorPalette
            ColorPaletteMode.System -> when (isSystemInDarkMode) {
                true -> PureBlackColorPalette
                false -> DefaultLightColorPalette
            }
        }
        ColorPaletteName.PureBlack -> PureBlackColorPalette
        ColorPaletteName.ModernBlack -> ModernBlackColorPalette
    }
}

fun dynamicColorPaletteOf(bitmap: Bitmap, isDark: Boolean): ColorPalette? {
    if (isDark) return PureBlackColorPalette

    val palette = Palette
        .from(bitmap)
        .maximumColorCount(8)
        .generate()

    val hsl = palette.dominantSwatch?.hsl ?: return null

    return if (hsl[1] < 0.08) {
        val newHsl = palette.swatches
            .map(Palette.Swatch::getHsl)
            .sortedByDescending(FloatArray::component2)
            .find { it[1] != 0f }
            ?: hsl
        dynamicColorPaletteOf(newHsl, isDark)
    } else {
        dynamicColorPaletteOf(hsl, isDark)
    }
}

fun dynamicColorPaletteOf(hsl: FloatArray, isDark: Boolean): ColorPalette {
    if (isDark) return PureBlackColorPalette
    return colorPaletteOf(ColorPaletteName.Dynamic, ColorPaletteMode.Light, false)
}

fun dynamicColorPaletteOf(hsl: Hsl, isDark: Boolean): ColorPalette {
    if (isDark) return PureBlackColorPalette
    return colorPaletteOf(ColorPaletteName.Dynamic, ColorPaletteMode.Light, false)
}

fun dynamicColorPaletteOf(
    accentColor: Color,
    isDark: Boolean
) = dynamicColorPaletteOf(
    hsl = accentColor.hsl,
    isDark = isDark
)

inline val ColorPalette.collapsedPlayerProgressBar: Color
    get() = Color.White

inline val ColorPalette.favoritesIcon: Color
    get() = Color(0xFFF59E0B)

inline val ColorPalette.shimmer: Color
    get() = Color(0xff333333)

inline val ColorPalette.primaryButton: Color
    get() = if (this.isDark) Color.White else Color.Black

inline val ColorPalette.favoritesOverlay: Color
    get() = Color(0xFFF59E0B).copy(alpha = 0.35f)

inline val ColorPalette.overlay: Color
    get() = Color.Black.copy(alpha = 0.65f)

inline val ColorPalette.onOverlay: Color
    get() = Color.White

inline val ColorPalette.onOverlayShimmer: Color
    get() = Color(0xff444444)

inline val ColorPalette.applyPitchBlack: ColorPalette
    get() = PureBlackColorPalette
