package com.example.ucp2pam_141.ui.view.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeView(
    onDosenClick: () -> Unit,
    onMataKuliahClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // State for animation control
    var isVisible by remember { mutableStateOf(false) }

    // Trigger animation when the view is loaded
    LaunchedEffect(Unit) {
        isVisible = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            onBack = {}, // No back action in HomeView
            showBackButton = false,
            judul = "Home",

            )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Buttons with animations
            AnimatedButton(text = "Dosen", icon = Icons.Filled.Person, onClick = onDosenClick)
            AnimatedButton(text = "Matakuliah", icon = Icons.Filled.Edit, onClick = onMataKuliahClick)
        }
    }
}

@Composable
fun AnimatedButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }

    Button(
        onClick = onClick,

        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp) // Larger padding for a bigger button
            .height(90.dp) // Increase button height
            .fillMaxWidth(0.9f) // Make button wider
            .clickable { isPressed = !isPressed }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Left,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp) // Padding between icon and text
                    .size(50.dp), // Increased icon size
            )
            // Text
            Text(
                text = text,
                fontSize = 30.sp, // Increase font size for bigger text
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .animateScale(isPressed) // Adding animation to text
            )
        }
    }
}

@Composable
fun Modifier.animateScale(isPressed: Boolean): Modifier {
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1.0f,
        animationSpec = tween(durationMillis = 300)
    )
    return this.then(Modifier.scale(scale))
}