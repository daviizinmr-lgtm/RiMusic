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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF0C0C0E))
                .border(1.dp, Color(0xFF222228), RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Mascote Paçoca
                Image(
                    painter = painterResource(R.drawable.pacoca_setup_icon),
                    contentDescription = "Mascote PaçocaMusic",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(90.dp)
                        .padding(bottom = 6.dp)
                )

                BasicText(
                    text = "PaçocaMusic",
                    style = typography().xl.bold.color(Color.White)
                )

                // Badge de Créditos do Criador Davi C.
                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF18181C))
                        .border(1.dp, Color.White.copy(alpha = 0.35f), RoundedCornerShape(18.dp))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    BasicText(
                        text = "Desenvolvido & Customizado por Davi C.",
                        style = typography().xxs.bold.color(Color.White)
                    )
                }

                BasicText(
                    text = "Streaming de alta fidelidade, 100% livre e sem anúncios.",
                    style = typography().xs.color(Color(0xFFA1A1AA)),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Changelog & Patches de Melhoria
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF141418))
                        .border(1.dp, Color(0xFF24242C), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        BasicText(
                            text = "Melhorias & Patches Aplicados",
                            style = typography().xs.bold.color(Color.White),
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        ChangelogItem(
                            icon = "•",
                            title = "Interface Dark AMOLED + Botões Brancos",
                            desc = "Preto profundo que economiza bateria com botões de alto contraste."
                        )
                        ChangelogItem(
                            icon = "•",
                            title = "Correção de Playback & Estabilidade",
                            desc = "Patch aplicado contra fechamentos ao iniciar faixas e notificações."
                        )
                        ChangelogItem(
                            icon = "•",
                            title = "Segundo Plano & Tela Desligada",
                            desc = "Áudio contínuo sem pausas mesmo com a tela bloqueada."
                        )
                        ChangelogItem(
                            icon = "•",
                            title = "100% Brasil em Destaque",
                            desc = "Lançamentos e rankings nacionais carregados automaticamente."
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Bypass de Otimização de Bateria
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF141418))
                        .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(14.dp))
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
                        .padding(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            BasicText(
                                text = "Otimização de Bateria",
                                style = typography().xs.bold.color(Color.White)
                            )
                            BasicText(
                                text = "Toque aqui para desativar a suspensão do app com a tela apagada.",
                                style = typography().xxs.color(Color(0xFFA1A1AA))
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF26262E))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            BasicText(
                                text = "Ativar",
                                style = typography().xxs.bold.color(Color.White)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Botão Branco Moderno (Stark White Button)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White)
                        .clickable { onDismiss() }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BasicText(
                        text = "Começar a Curtir",
                        style = typography().s.bold.color(Color.Black)
                    )
                }
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
            style = typography().s.color(Color.White),
            modifier = Modifier.padding(end = 8.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            BasicText(
                text = title,
                style = typography().xs.bold.color(Color.White)
            )
            BasicText(
                text = desc,
                style = typography().xxs.color(Color(0xFFA1A1AA))
            )
        }
    }
}
