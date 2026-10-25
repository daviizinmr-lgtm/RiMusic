package it.fast4x.rimusic.ui.components.themed

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import it.fast4x.rimusic.R
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
            .fillMaxWidth(0.95f)
            .padding(vertical = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            // Imagem Oficial do Mascote da Paçoca
            Image(
                painter = painterResource(R.drawable.pacoca_setup_icon),
                contentDescription = "Mascote PaçocaMusic",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(95.dp)
                    .padding(top = 4.dp, bottom = 4.dp)
            )

            BasicText(
                text = "PaçocaMusic",
                style = typography().xl.bold.color(colorPalette().accent)
            )

            // Badge de Créditos do Criador Davi C.
            Box(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(colorPalette().background2)
                    .border(1.dp, colorPalette().accent.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 5.dp)
            ) {
                BasicText(
                    text = "✨ Desenvolvido & Customizado por Davi C.",
                    style = typography().xxs.bold.color(colorPalette().accent)
                )
            }

            BasicText(
                text = "Streaming brasileiro livre, sem anúncios e com reprodução em segundo plano.",
                style = typography().xs.color(colorPalette().textSecondary),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Seção de Changelog & Patches Aplicados
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorPalette().background2)
                    .padding(12.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    BasicText(
                        text = "🚀 Melhorias & Patches Aplicados (v1.0)",
                        style = typography().xs.bold.color(colorPalette().text),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    ChangelogItem(
                        icon = "🎨",
                        title = "Nova Interface Estilo Spotify / YTM",
                        desc = "Dark AMOLED puro sem cores borradas, abas espaçadas e novo logo oficial."
                    )
                    ChangelogItem(
                        icon = "🎧",
                        title = "Segundo Plano & Tela Desligada",
                        desc = "Suas músicas tocam sem interrupção mesmo com o celular bloqueado."
                    )
                    ChangelogItem(
                        icon = "🇧🇷",
                        title = "100% Brasil em Primeiro Lugar",
                        desc = "Tendências, lançamentos e paradas musicais nacionais automáticas."
                    )
                    ChangelogItem(
                        icon = "⚡",
                        title = "Zero Anúncios & Cache Otimizado",
                        desc = "Músicas sem propagandas e carregamento veloz sem engasgos."
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Card Interativo para Desativar Otimização de Bateria
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorPalette().background1)
                    .border(1.dp, colorPalette().accent.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
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
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        BasicText(
                            text = "🔋 Otimização de Bateria (Tela Apagada)",
                            style = typography().xs.semiBold.color(colorPalette().accent)
                        )
                        BasicText(
                            text = "Toque para impedir que o Android pause a música após alguns minutos em segundo plano.",
                            style = typography().xxs.color(colorPalette().textSecondary)
                        )
                    }
                    BasicText(
                        text = "Configurar",
                        style = typography().xs.bold.color(colorPalette().accent),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Botão Começar a Curtir
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
private fun ChangelogItem(
    icon: String,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        BasicText(
            text = icon,
            style = typography().s,
            modifier = Modifier.padding(end = 8.dp, top = 1.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            BasicText(
                text = title,
                style = typography().xs.semiBold.color(colorPalette().text)
            )
            BasicText(
                text = desc,
                style = typography().xxs.color(colorPalette().textSecondary)
            )
        }
    }
}
