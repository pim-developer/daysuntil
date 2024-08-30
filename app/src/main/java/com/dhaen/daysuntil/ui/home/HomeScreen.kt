package com.dhaen.daysuntil.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.dhaen.daysuntil.R
import com.dhaen.daysuntil.domain.countdownevent.CountDownEventModel
import com.dhaen.daysuntil.presentation.home.HomeViewModel
import com.dhaen.daysuntil.ui.common.GradientBackground
import com.dhaen.daysuntil.ui.common.ThreeDCard
import com.dhaen.daysuntil.ui.theme.DaysUntilTheme
import com.dhaen.daysuntil.ui.theme.errorContainerDark
import com.dhaen.daysuntil.ui.theme.green1
import com.dhaen.daysuntil.ui.theme.green2
import com.dhaen.daysuntil.ui.theme.green3
import com.dhaen.daysuntil.ui.theme.green4
import com.dhaen.daysuntil.ui.theme.onCustomColor1Dark
import com.dhaen.daysuntil.ui.theme.onCustomColor1DarkMediumContrast
import com.dhaen.daysuntil.ui.theme.onErrorDark
import com.dhaen.daysuntil.ui.theme.onPrimaryDark
import com.dhaen.daysuntil.ui.theme.onTertiaryContainerDark
import com.dhaen.daysuntil.ui.theme.outlineDark
import com.dhaen.daysuntil.ui.theme.primaryDark
import com.dhaen.daysuntil.ui.theme.primaryLightMediumContrast
import com.dhaen.daysuntil.ui.theme.red1
import com.dhaen.daysuntil.ui.theme.red2
import com.dhaen.daysuntil.ui.theme.red3
import com.dhaen.daysuntil.ui.theme.tertiaryContainerDark
import com.dhaen.daysuntil.ui.theme.tertiaryContainerDarkHighContrast
import com.dhaen.daysuntil.ui.theme.tertiaryContainerDarkMediumContrast
import com.dhaen.daysuntil.ui.theme.tertiaryContainerLight
import com.dhaen.daysuntil.utils.areDatesOnSameDay
import com.dhaen.daysuntil.utils.convertEpochSecondsToDate
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.days

@Composable
fun HomeScreenRoute(setHomeScreenFABButtonOnClick: (() -> Unit) -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()

    val countDownEventsFeedState = viewModel.countDownEventsFeedState.collectAsState()

    HomeScreen(
        setAddNewCountDownEventFABButtonClick = setHomeScreenFABButtonOnClick,
        countDownEventsFeed = countDownEventsFeedState.value,
        editCountDownEventState = viewModel.editCountDownEventState.value,
        onViewCountDownEventClick = { idHexString: String, name: String, dateEpochSecondsUTC: Long ->
            viewModel.onEvent(
                HomeViewModel.HomeViewModelEvent.ViewCountDownEvent(
                    idHexString = idHexString,
                    name = name,
                    dateEpochSecondsUTC = dateEpochSecondsUTC
                )
            )
        },
        onResetResetEditCountDownEventState = {
            viewModel.onEvent(HomeViewModel.HomeViewModelEvent.ResetEditCountDownEventState)
        },
        onNameChange = {
            viewModel.onEvent(HomeViewModel.HomeViewModelEvent.ChangeCountDownEventName(it))
        },
        onDeleteClick = {
            viewModel.onEvent(HomeViewModel.HomeViewModelEvent.DeleteCountDownEvent)
        },
        onSaveClick = {
            viewModel.onEvent(HomeViewModel.HomeViewModelEvent.SaveChanges(it))
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    setAddNewCountDownEventFABButtonClick: (() -> Unit) -> Unit,
    countDownEventsFeed: List<CountDownEventModel>,
    editCountDownEventState: HomeViewModel.EditCountDownEventState,
    onViewCountDownEventClick: (idHexString: String, name: String, dateEpochSecondsUTC: Long) -> Unit,
    onResetResetEditCountDownEventState: () -> Unit,
    onNameChange: (name: String) -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: (dateTimeUTCMillis: Long) -> Unit,
) {
    val isNewEventDialogVisible = remember {
        mutableStateOf(false)
    }

    val haptic = LocalHapticFeedback.current

    LaunchedEffect(Unit) {
        setAddNewCountDownEventFABButtonClick {
            isNewEventDialogVisible.value = true
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }

    val color1 = MaterialTheme.colorScheme.background
    val color2 = MaterialTheme.colorScheme.tertiaryContainer
    val largeRadialGradient = object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val biggerDimension = maxOf(size.height, size.width)
            return RadialGradientShader(
                colors = listOf(color1, color2),
                center = size.center,
                radius = biggerDimension / 2f,
                colorStops = listOf(0f, 0.95f)
            )
        }
    }

    GradientBackground {
        Column(
            Modifier
                .fillMaxSize()
//            .background(largeRadialGradient)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {

            Spacer(modifier = Modifier.size(36.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.app_name_display_large),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            if (countDownEventsFeed.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .offset(y = -(38.dp + 24.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.no_events_planned_label_text),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                /* no-op */
            }

            Spacer(modifier = Modifier.size(24.dp))

            // Single time source to sync all countdowns
            var currentEpochSecondsUTC by remember { mutableLongStateOf(System.currentTimeMillis() / 1000) }

            // LaunchedEffect to update the current time every second
            LaunchedEffect(Unit) {
                while (true) {
                    delay(1000L)
                    currentEpochSecondsUTC = System.currentTimeMillis() / 1000
                }
            }

            // TODO: add more attractive backgrounds
            val headerBackgrounds = listOf(
                R.drawable.blurry_gradient_haikei,
                R.drawable.blurry_gradient_haikei_2,
                R.drawable.blurry_gradient_haikei_3
            )

            LazyColumn(
                Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                itemsIndexed(
                    items = countDownEventsFeed,
                    key = { _, item -> item.idHexString }) { i, event ->

                    val formattedDateTime = if (i - 1 >= 0 &&
                        areDatesOnSameDay(
                            countDownEventsFeed[i - 1].dateEpochSecondsUTC,
                            event.dateEpochSecondsUTC
                        )
                    ) {
                        ""
                    } else {
                        convertEpochSecondsToDate(event.dateEpochSecondsUTC)
                    }

                    // Calculate remaining time for each event based on the synced time source
                    val remainingTime = event.dateEpochSecondsUTC - currentEpochSecondsUTC

                    // consecutive cards have a different background
                    val headerBackgroundIndex = i % headerBackgrounds.size

                    CountDownEventBlock(
                        modifier = Modifier.animateItemPlacement(),
                        headerBackground = headerBackgrounds[headerBackgroundIndex],
                        formattedDateTime = formattedDateTime,
                        heading = event.name,
                        emoji = null,
                        onEditClick = {
                            onViewCountDownEventClick(
                                event.idHexString,
                                event.name,
                                event.dateEpochSecondsUTC
                            )
                            isNewEventDialogVisible.value = true
                        },
                        remainingTimeEpochSecondsUTC = remainingTime
                    )

                    if (
                        i + 1 < countDownEventsFeed.size
                    ) {
                        if (areDatesOnSameDay(
                                countDownEventsFeed[i + 1].dateEpochSecondsUTC,
                                event.dateEpochSecondsUTC
                            )
                        ) {
                            /* no-op */
                        } else {
                            Spacer(modifier = Modifier.size(20.dp))
                        }
                    } else {
                        Spacer(modifier = Modifier.size(20.dp))
                    }

                }

            }

            if (isNewEventDialogVisible.value) {
                NewCountDownEventDialog(
                    onDismissRequest = {
                        onResetResetEditCountDownEventState()
                        isNewEventDialogVisible.value = false
                    },
                    editCountDownEventState = editCountDownEventState,
                    onNameChange = onNameChange,
                    onDeleteClick = onDeleteClick,
                    onSaveClick = onSaveClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCountDownEventDialog(
    onDismissRequest: () -> Unit,
    editCountDownEventState: HomeViewModel.EditCountDownEventState,
    onNameChange: (name: String) -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: (dateTimeUTCMillis: Long) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val isNameErrorVisible = remember {
        mutableStateOf(false)
    }
    val isDateTimeErrorVisible = remember {
        mutableStateOf(false)
    }

    BasicAlertDialog(
        onDismissRequest = {
            isNameErrorVisible.value = false
            isDateTimeErrorVisible.value = false
            onDismissRequest()
        },
        modifier = Modifier
            .width(392.dp)
            .padding(8.dp),
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        ElevatedCard {
            Column(
                Modifier
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NewCountDownEventDialogHeader(
                    editCountDownEventState = editCountDownEventState,
                    onDismissRequest = {
                        isNameErrorVisible.value = false
                        isDateTimeErrorVisible.value = false
                        onDismissRequest()
                    }
                )

                Spacer(modifier = Modifier.size(32.dp))

                val context = LocalContext.current
                val textFieldFocus = remember { FocusRequester() }

                LaunchedEffect(key1 = context) {
                    if (editCountDownEventState.idHexString == null) {
                        // only request focus when user clicks new event
                        textFieldFocus.requestFocus()
                    }
                }

                TextField(
                    modifier = Modifier.focusRequester(focusRequester = textFieldFocus),
                    value = editCountDownEventState.name.orEmpty(),
                    onValueChange = {
                        if (isNameErrorVisible.value) {
                            isNameErrorVisible.value = false
                        }
                        onNameChange(it)
                    },
                    colors = TextFieldDefaults.colors()
                        .copy(
                            unfocusedTextColor = primaryDark,
                            focusedTextColor = primaryDark,
                            focusedContainerColor = onPrimaryDark,
                            unfocusedContainerColor = onPrimaryDark,
                            unfocusedPlaceholderColor = outlineDark,
                            focusedPlaceholderColor = outlineDark,
                            cursorColor = MaterialTheme.colorScheme.tertiaryContainer,
                            unfocusedIndicatorColor = tertiaryContainerLight,
                            focusedIndicatorColor = tertiaryContainerLight
                            // TODO: implement more suitable colors
                        ),
                    maxLines = 2,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    supportingText = {
                        if (isNameErrorVisible.value) {
                            Text(
                                text = stringResource(id = R.string.countdown_name_textfield_error_text),
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.labelSmall
                            )
                        } else {
                            /* no-op */
                        }
                    },
                    textStyle = MaterialTheme.typography.titleLarge,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.text_field_countdown_event_name_placeholder_text),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    },
                    isError = isNameErrorVisible.value
                )

                Spacer(modifier = Modifier.size(16.dp))

                val initialSelectedDateMillis =
                    if (editCountDownEventState.dateEpochSecondsUTC != null) {
                        editCountDownEventState.dateEpochSecondsUTC!! * 1000L
                    } else {
                        System.currentTimeMillis() + 1.days.inWholeMilliseconds
                    }

                val dateFormatter = remember { DatePickerDefaults.dateFormatter() }
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = initialSelectedDateMillis,
                    initialDisplayMode = DisplayMode.Picker
                )
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    if (this.maxWidth <= 340.dp) {
                        // TODO: use a modal to display date picker if width too small
                        datePickerState.displayMode = DisplayMode.Input
                    }

                    DatePicker(
                        colors = DatePickerDefaults.colors()
                            .copy(dividerColor = tertiaryContainerLight),
                        modifier = Modifier.fillMaxWidth(),
                        state = datePickerState,
                        dateFormatter = dateFormatter,
                        headline = {
//                        see DatePickerDefaults
                            val locale = context.resources.configuration.locales.get(0)
                            val formattedDate = dateFormatter.formatDate(
                                dateMillis = datePickerState.selectedDateMillis,
                                locale = locale
                            )

                            Text(
                                text = if (formattedDate.toString() == "null") "…" else "$formattedDate",
                                modifier = Modifier
                                    .padding(start = 24.dp, end = 12.dp, bottom = 12.dp)
                                    .semantics {
                                        liveRegion = LiveRegionMode.Polite
                                        contentDescription = "$formattedDate"
                                    },
                                maxLines = 1,
                                style = MaterialTheme.typography.titleLarge.copy(fontSize = 38.sp),
                            )
                        }
                    )
                }

                if (isDateTimeErrorVisible.value) {
                    if (datePickerState.selectedDateMillis != null) {
                        isDateTimeErrorVisible.value = false
                    }
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp, bottom = 16.dp)
                            .align(Alignment.Start),
                        text = stringResource(id = R.string.countdown_date_textfield_error_text),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                    )
                } else {
                    /* no-op */
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.width(146.dp),
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            isNameErrorVisible.value = false
                            isDateTimeErrorVisible.value = false
                            onDeleteClick()
                            onDismissRequest()
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete_24px),
                            contentDescription = stringResource(id = R.string.delete_dialog_button_title),
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            text = stringResource(id = R.string.delete_dialog_button_title),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Button(
                        modifier = Modifier.width(146.dp),
                        colors = ButtonDefaults.buttonColors()
                            .copy(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)

                            isNameErrorVisible.value =
                                editCountDownEventState.name.isNullOrEmpty()
                            isDateTimeErrorVisible.value =
                                datePickerState.selectedDateMillis == null

                            if (!isDateTimeErrorVisible.value && !isNameErrorVisible.value) {
                                onSaveClick(datePickerState.selectedDateMillis!!)
                                onDismissRequest()
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.save_24px),
                            contentDescription = stringResource(id = R.string.delete_dialog_button_title),
                            tint = MaterialTheme.colorScheme.onTertiaryContainer
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Text(
                            text = stringResource(id = R.string.save_dialog_button_title),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }

        }
    }
}

@Composable
private fun NewCountDownEventDialogHeader(
    modifier: Modifier = Modifier,
    editCountDownEventState: HomeViewModel.EditCountDownEventState,
    onDismissRequest: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = if (editCountDownEventState.idHexString == null) stringResource(id = R.string.new_countdown_event_title_dialog) else
                stringResource(id = R.string.edit_countdown_event_title_dialog),
            style = MaterialTheme.typography.displayMedium,
        )

        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 16.dp, y = (-16).dp),
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onDismissRequest()
            }) {
            Icon(
                painter = painterResource(id = R.drawable.close_24px),
                contentDescription = stringResource(
                    id = R.string.content_description_close_dialog_button
                ),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
private fun CountDownEventBlock(
    modifier: Modifier = Modifier,
    @DrawableRes headerBackground: Int,
    formattedDateTime: String,
    heading: String,
    emoji: String?,
    onEditClick: () -> Unit,
    remainingTimeEpochSecondsUTC: Long,
) {
    // Convert remaining time to days, hours, minutes, and seconds
    val days = TimeUnit.SECONDS.toDays(remainingTimeEpochSecondsUTC)
    val hours = TimeUnit.SECONDS.toHours(remainingTimeEpochSecondsUTC) % 24
    val minutes = TimeUnit.SECONDS.toMinutes(remainingTimeEpochSecondsUTC) % 60
    val seconds = remainingTimeEpochSecondsUTC % 60

    Column(
        modifier = modifier
            .widthIn(max = 480.dp)
            .heightIn(max = 168.dp)
    ) {
        if (formattedDateTime.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(start = 8.dp, bottom = 1.dp),
                text = "$formattedDateTime",
                style = MaterialTheme.typography.labelSmall.copy(fontStyle = FontStyle.Italic),
                color = MaterialTheme.colorScheme.onBackground
            )
        } else {
            VerticalDivider(
                modifier = Modifier
                    .height(16.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .offset(y = 6.dp),
                thickness = 2.dp,
                color = primaryLightMediumContrast// MaterialTheme.colorScheme.onTertiaryContainer
            )
        }


        var daysRowModifier: Modifier = Modifier
            .fillMaxWidth()
        var textStyle = MaterialTheme.typography.titleMedium
        var threeDCardShadowColor = Brush.horizontalGradient(
            colors = listOf(onCustomColor1DarkMediumContrast, onCustomColor1DarkMediumContrast)
        )

        val daysText = if (days <= -1L) {
            textStyle = MaterialTheme.typography.titleMedium.copy(
                brush = Brush.horizontalGradient(
                    colors = listOf(red1, errorContainerDark)
                ),
                fontStyle = FontStyle.Italic
            )
            threeDCardShadowColor = Brush.horizontalGradient(
                colors = listOf(errorContainerDark, red1)
            )
            "$days ${stringResource(id = R.string.days_appended_label)}"
        } else if (remainingTimeEpochSecondsUTC < 0) {
            textStyle = MaterialTheme.typography.titleMedium.copy(
                brush = Brush.horizontalGradient(
                    colors = listOf(green4, green1)
                ),
                fontStyle = FontStyle.Italic
            )
            threeDCardShadowColor = Brush.horizontalGradient(
                colors = listOf(green1, green4)
            )
            stringResource(id = R.string.started_countdown_text)
        } else if (days == 0L) {
            textStyle = MaterialTheme.typography.titleMedium.copy(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        tertiaryContainerDarkHighContrast,
                        tertiaryContainerDarkMediumContrast
                    )
                ),
                fontStyle = FontStyle.Italic
            )
            threeDCardShadowColor = Brush.horizontalGradient(
                colors = listOf(
                    tertiaryContainerDarkMediumContrast,
                    tertiaryContainerDarkHighContrast
                )
            )
            stringResource(id = R.string.less_than_one_day_countdown_text)
        } else {
            "$days ${stringResource(id = R.string.days_appended_label)}"
        }

        ThreeDCard(shadowColor = threeDCardShadowColor) {

            ElevatedCard {
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surface)
                        .fillMaxSize()
                        .padding(bottom = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    CountDownEventCardHeader(
                        heading = heading,
                        headerBackground = headerBackground,
                        onEditClick = onEditClick
                    )

                    if (days <= -1L) {
                        MaterialTheme.typography.titleMedium.copy(color = red2)
                    } else if (remainingTimeEpochSecondsUTC < 0) {
                        // STARTED
                        MaterialTheme.typography.titleMedium.copy(
                            color = onTertiaryContainerDark,
                            fontStyle = FontStyle.Italic
                        )
                    } else if (days == 0L) {
                        // SOON
                        MaterialTheme.typography.titleMedium.copy(
                            color = green3,
                            fontStyle = FontStyle.Italic
                        )
                    } else {
                        MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                    }

                    Row(
                        modifier = daysRowModifier
                            .padding(top = 14.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = daysText,
                            style = textStyle,
                        )
                    }

                    // TODO: fix padding

                    Spacer(modifier = Modifier.size(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$hours ${stringResource(id = R.string.hours_appended_label)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "$minutes ${stringResource(id = R.string.minutes_appended_label)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "$seconds ${stringResource(id = R.string.seconds_appended_label)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
private fun CountDownEventCardHeader(
    modifier: Modifier = Modifier,
    @DrawableRes headerBackground: Int,
    heading: String,
    onEditClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp)
            .paint(
                painter = painterResource(id = headerBackground),
                contentScale = ContentScale.FillBounds
            )
            .padding(8.dp),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .width(262.5.dp)
//                .height(27.5.dp)
            ,
            text = heading,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.inversePrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        IconButton(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(22.dp)
                .offset(x = (-6).dp, y = (-6).dp),
            onClick = onEditClick
        ) {
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(id = R.drawable.edit_24px),
                contentDescription = stringResource(
                    id = R.string.content_description_icon_edit_event_countdown,
                    heading
                ),
                tint = MaterialTheme.colorScheme.primaryContainer
            )
        }
    }
}


@Composable
private fun DividerCircle(modifier: Modifier = Modifier) {
    val color = primaryLightMediumContrast
    Canvas(
        modifier = modifier.size(8.dp),
        onDraw = {
            drawCircle(color = color)
        }
    )
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    DaysUntilTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                HomeScreen(
                    setAddNewCountDownEventFABButtonClick = {},
                    countDownEventsFeed = listOf(
                        CountDownEventModel("1", "event 1", 0L),
                        CountDownEventModel("2", "event 2", System.currentTimeMillis() / 1000 - 1),
                        CountDownEventModel(
                            "3",
                            "event 3",
                            System.currentTimeMillis() / 1000 + 1
                        ),
                        CountDownEventModel(
                            "4",
                            "event 4",
                            System.currentTimeMillis() / 1000 + 1.days.inWholeSeconds
                        ),
                    ),
                    editCountDownEventState = HomeViewModel.EditCountDownEventState(),
                    onResetResetEditCountDownEventState = {},
                    onViewCountDownEventClick = { _, _, _ -> },
                    onNameChange = {},
                    onDeleteClick = {},
                    onSaveClick = {}
                )

            }
        }
    }
}

//@PreviewScreenSizes
@Preview
@Composable
private fun PreviewNewEventDialog() {
    DaysUntilTheme {
        Surface {

            val color1 = MaterialTheme.colorScheme.background
            val color2 = MaterialTheme.colorScheme.tertiaryContainer
            val largeRadialGradient = object : ShaderBrush() {
                override fun createShader(size: Size): Shader {
                    val biggerDimension = maxOf(size.height, size.width)
                    return RadialGradientShader(
                        colors = listOf(color1, color2),
                        center = size.center,
                        radius = biggerDimension / 2f,
                        colorStops = listOf(0f, 0.95f)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(largeRadialGradient)
            ) {


                NewCountDownEventDialog(
                    onDismissRequest = { },
                    editCountDownEventState = HomeViewModel.EditCountDownEventState(),
                    onNameChange = {},
                    onDeleteClick = { }) {

                }
            }
        }
    }
}


@Preview(widthDp = 360, heightDp = 600)
@Composable
private fun PreviewNewEventModalDatePickerDialog() {
    DaysUntilTheme {
        Surface {
            val color1 = MaterialTheme.colorScheme.background
            val color2 = MaterialTheme.colorScheme.tertiaryContainer
            val largeRadialGradient = object : ShaderBrush() {
                override fun createShader(size: Size): Shader {
                    val biggerDimension = maxOf(size.height, size.width)
                    return RadialGradientShader(
                        colors = listOf(color1, color2),
                        center = size.center,
                        radius = biggerDimension / 2f,
                        colorStops = listOf(0f, 0.95f)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .width(140.dp)
                    .height(100.dp)
                    .background(largeRadialGradient)
            ) {
                NewCountDownEventDialog(
                    onDismissRequest = { },
                    editCountDownEventState = HomeViewModel.EditCountDownEventState(),
                    onNameChange = {},
                    onDeleteClick = { }) {
                }
            }
        }
    }
}
