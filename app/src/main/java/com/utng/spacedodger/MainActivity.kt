package com.utng.spacedodger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.utng.spacedodger.ui.screens.GameScreen
import com.utng.spacedodger.ui.theme.SpaceDodgerTheme

/**
 * MainActivity es el punto de entrada de nuestra aplicación.
 *
 * Es como la puerta principal de una casa: todo empieza aquí.
 * Android llama a esta Activity cuando el usuario abre la app.
 *
 * ComponentActivity es una Activity que tiene soporte para Jetpack Compose.
 * Es la versión moderna y mejorada de Activity.
 */
class MainActivity : ComponentActivity() {

    /**
     * onCreate se llama cuando la Activity es creada.
     * Es como el constructor de una clase, pero para Activities.
     * libs
     * Aquí configuramos toda la interfaz de usuario.
     *
     * @param savedInstanceState Estado guardado de una ejecución anterior
     *                          (null si es la primera vez)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent define el contenido de la pantalla usando Compose
        // Es como decir "esto es lo que el usuario verá"
        setContent {
            // SpaceDodgerTheme aplica el tema de colores de nuestra app
            SpaceDodgerTheme {
                // Surface es un contenedor con color de fondo
                // Es como un lienzo sobre el que pintamos
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // GameScreen es nuestra pantalla principal del juego
                    GameScreen()
                }
            }
        }
    }
}

/**
 * NOTA EDUCATIVA: Ciclo de vida de una Activity
 *
 * Las Activities tienen un ciclo de vida con varios estados:
 *
 * 1. onCreate(): Se crea la Activity (llamado una vez)
 * 2. onStart(): La Activity se vuelve visible
 * 3. onResume(): La Activity está en primer plano y el usuario puede interactuar
 * 4. onPause(): Otra Activity está tomando el primer plano
 * 5. onStop(): La Activity ya no es visible
 * 6. onDestroy(): La Activity está siendo destruida
 *
 * Es como las etapas de un negocio:
 * - onCreate = Construir el local
 * - onStart = Abrir las puertas
 * - onResume = Empezar a atender clientes
 * - onPause = Cerrar temporalmente para almorzar
 * - onStop = Cerrar al final del día
 * - onDestroy = Cerrar el negocio permanentemente
 *
 * En nuestro juego, solo necesitamos onCreate porque
 * el ViewModel maneja automáticamente el resto del ciclo de vida.
 */

/**
 * NOTA EDUCATIVA: ¿Qué es Jetpack Compose?
 *
 * Jetpack Compose es el kit de herramientas moderno de Android para crear UI.
 *
 * ANTES (XML + Java/Kotlin):
 * - Definías la UI en archivos XML separados
 * - Escribías código para encontrar y modificar vistas
 * - Muchas líneas de código para cosas simples
 * - Difícil de mantener y probar
 *
 * AHORA (Compose + Kotlin):
 * - Todo en Kotlin, un solo lenguaje
 * - UI declarativa: describes QUÉ quieres, no CÓMO hacerlo
 * - Menos código, más claro
 * - Fácil de mantener y probar
 * - Animaciones y transiciones más simples
 *
 * Es como comparar:
 * - Dar instrucciones paso a paso para llegar a un lugar
 * vs
 * - Simplemente decir la dirección y dejar que el GPS lo maneje
 */

