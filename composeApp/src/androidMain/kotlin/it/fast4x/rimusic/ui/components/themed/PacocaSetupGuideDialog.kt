package it.fast4x.rimusic.ui.components.themed

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import it.fast4x.rimusic.colorPalette
import it.fast4x.rimusic.typography
import it.fast4x.rimusic.utils.bold
import it.fast4x.rimusic.utils.color
import it.fast4x.rimusic.utils.semiBold

@Composable
fun PacocaSetupGuideDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    DefaultDialog(
        onDismiss = onDismiss,
        modifier = Modifier
            .fillMaxWidth(0.94f)
            .padding(vertical = 12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            // Emblema Superior do PaçocaMusic
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(colorPalette().accent.copy(alpha = 0.18f))
                    .border(2.dp, colorPalette().accent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                BasicText(
                    text = "🥜",
                    style = typography().xxl.bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            BasicText(
                text = "Bem-vindo ao PaçocaMusic!",
                style = typography().l.bold.color(colorPalette().accent)
            )

            Spacer(modifier = Modifier.height(4.dp))

            BasicText(
                text = "Seu streaming brasileiro de alta fidelidade, 100% livre, sem anúncios e com reprodução em segundo plano.",
                style = typography().xs.color(colorPalette().textSecondary),
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Destaque 1: Tema Dark
            SetupGuideItem(
                emoji = "🌙",
                title = "Tema Dark AMOLED",
                description = "Visual escuro com detalhes em caramelo paçoca, garantindo máxima economia de bateria e conforto visual."
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Destaque 2: Segundo Plano & Tela Desligada
            SetupGuideItem(
                emoji = "🎧",
                title = "Segundo Plano & Tela Desligada",
                description = "Suas músicas continuam tocando normalmente quando você sai do app ou desliga a tela do celular."
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Card interativo para desativar otimização de bateria
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorPalette().background2)
                    .clickable {
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
                                val isIgnoring = powerManager?.isIgnoringBatteryOptimizations(context.packageName) ?: false
                                if (!isIgnoring) {
                                    val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                                        data = Uri.parse("package:${context.packageName}")
                                    }
                                    context.startActivity(intent)
                                } else {
                                    val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
                                    context.startActivity(intent)
                                }
                            }
                        } catch (_: Exception) {
                            try {
                                context.startActivity(Intent(Settings.ACTION_SETTINGS))
                            } catch (_: Exception) {}
                        }
                    }
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        BasicText(
                            text = "🔋 Otimização de Bateria",
                            style = typography().xs.semiBold.color(colorPalette().accent)
                        )
                        BasicText(
                            text = "Toque aqui para desativar restrições do Android e evitar pausas com a tela apagada.",
                            style = typography().xxs.color(colorPalette().textSecondary)
                        )
                    }
                    BasicText(
                        text = "Ajustar",
                        style = typography().xs.bold.color(colorPalette().accent),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Destaque 3: Brasil em Primeiro Lugar
            SetupGuideItem(
                emoji = "🇧🇷",
                title = "Conteúdo Nacional Integrado",
                description = "Músicas em alta, sucessos e tendências do Brasil carregadas automaticamente na sua tela inicial."
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Botão Concluir Setup
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(colorPalette().accent)
                    .clickable { onDismiss() }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                BasicText(
                    text = "Bora Ouvir Música! 🚀",
                    style = typography().s.bold.color(colorPalette().onAccent)
                )
            }
        }
    }
}

@Composable
private fun SetupGuideItem(
    emoji: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        BasicText(
            text = emoji,
            style = typography().m,
            modifier = Modifier.padding(end = 12.dp, top = 2.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            BasicText(
                text = title,
                style = typography().xs.semiBold.color(colorPalette().text)
            )
            BasicText(
                text = description,
                style = typography().xxs.color(colorPalette().textSecondary)
            )
        }
    }
}
