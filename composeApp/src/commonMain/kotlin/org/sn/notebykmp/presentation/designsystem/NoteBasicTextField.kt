package org.sn.notebykmp.presentation.designsystem

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NoteBasicTextField(
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    value: TextFieldValue,
    height: Dp = 54.dp,
    borderWidth: Dp = 1.dp,
    borderColor: Color = Color.Unspecified,
    borderShape: Shape = RoundedCornerShape(size = 5.dp),
    updateTextValue: (TextFieldValue) -> Unit,
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = Color.DarkGray
    ),
    contentAlignment: Alignment = Alignment.TopStart,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Column(modifier = modifier.height(height)) {
        label?.let { it() }
        BasicTextField(
            modifier = modifier.fillMaxSize(),
            readOnly = readOnly,
            value = value,
            onValueChange = {
                updateTextValue(it)
            },
            visualTransformation = visualTransformation,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            textStyle = textStyle,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = borderWidth,
                            color = borderColor,
                            shape = borderShape
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = contentAlignment // Center the content vertically
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        leadingIcon?.let { it() }
                        Box(
                            modifier = Modifier
                                .weight(1f),
                        ) {
                            if (value.text.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    style = LocalTextStyle.current.copy(
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.outline
                                    ),
                                )
                            }
                            innerTextField()
                        }
                        trailingIcon?.let { it() }
                    }
                }
            }
        )
    }
}
