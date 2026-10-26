package it.fast4x.rimusic.ui.screens.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import it.fast4x.rimusic.R
import it.fast4x.rimusic.colorPalette
import it.fast4x.rimusic.enums.NavigationBarPosition
import it.fast4x.rimusic.extensions.contributors.ShowDevelopers
import it.fast4x.rimusic.extensions.contributors.ShowTranslators
import it.fast4x.rimusic.extensions.contributors.countDevelopers
import it.fast4x.rimusic.extensions.contributors.countTranslators
import it.fast4x.rimusic.typography
import it.fast4x.rimusic.ui.components.themed.HeaderWithIcon
import it.fast4x.rimusic.ui.components.themed.Title
import it.fast4x.rimusic.ui.styling.Dimensions
import it.fast4x.rimusic.utils.bold
import it.fast4x.rimusic.utils.color
import it.fast4x.rimusic.utils.getVersionName
import it.fast4x.rimusic.utils.secondary
import it.fast4x.rimusic.utils.semiBold

@ExperimentalAnimationApi
@Composable
fun About() {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .background(colorPalette().background0)
            .fillMaxHeight()
            .fillMaxWidth(
                if (NavigationBarPosition.Right.isCurrent())
                    Dimensions.contentWidthRightBar
                else
                    1f
            )
            .verticalScroll(rememberScrollState())
    ) {
        HeaderWithIcon(
            title = stringResource(R.string.about),
            iconId = R.drawable.information,
            enabled = false,
            showIcon = true,
            modifier = Modifier,
            onClick = {}
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            BasicText(
                text = "PaçocaMusic",
                style = typography().xl.bold.color(colorPalette().text)
            )
            BasicText(
                text = "Versão ${getVersionName()} (Build Oficial)",
                style = typography().xs.secondary
            )

            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(colorPalette().background2)
                    .border(1.dp, colorPalette().accent.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                BasicText(
                    text = "Criado e customizado por Davi C.",
                    style = typography().xs.bold.color(colorPalette().accent)
                )
            }
        }

        SettingsGroupSpacer()

        SettingsEntryGroupText(title = "Sobre o Projeto")

        SettingsEntry(
            title = "Código-fonte no GitHub",
            text = "Repositório oficial PaçocaMusic",
            onClick = {
                uriHandler.openUri("https://github.com/daviizinmr-lgtm/RiMusic")
            }
        )

        SettingsEntry(
            title = "Reportar um Bug ou Problema",
            text = "Abrir ticket no repositório de Davi C.",
            onClick = {
                uriHandler.openUri("https://github.com/daviizinmr-lgtm/RiMusic/issues")
            }
        )

        SettingsEntry(
            title = "Sugerir Nova Função ou Melhoria",
            text = "Deixe sua ideia para as próximas atualizações",
            onClick = {
                uriHandler.openUri("https://github.com/daviizinmr-lgtm/RiMusic/issues")
            }
        )

        SettingsGroupSpacer()

        Title(
            title = stringResource(R.string.contributors)
        )

        SettingsEntryGroupText(title = "${ countTranslators() } " + stringResource(R.string.translators))
        SettingsDescription(text = stringResource(R.string.in_alphabetical_order))
        ShowTranslators()

        SettingsGroupSpacer()

        SettingsEntryGroupText(title = "${ countDevelopers() } " + "Desenvolvedores & Colaboradores")
        SettingsDescription(text = stringResource(R.string.in_alphabetical_order))
        ShowDevelopers()

        SettingsGroupSpacer(
            modifier = Modifier.height(Dimensions.bottomSpacer)
        )
    }
}
