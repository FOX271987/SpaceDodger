package com.utng.spacedodger.data

/**
 * GameState representa el estado completo del juego en un momento dado.
 * Es como una fotografía instantánea de todo lo que está pasando en el juego.
 *
 * @property playerX Posición horizontal del jugador (0.0 = izquierda, 1.0 = derecha)
 * @property playerY Posición vertical del jugador (0.0 = arriba, 1.0 = abajo)
 * @property asteroids Lista de todos los asteroides actualmente en pantalla
 * @property score Puntuación actual del jugador
 * @property isGameOver Indica si el juego ha terminado (colisión detectada)
 * @property isPaused Indica si el juego está pausado
 * @property highScore Puntuación más alta alcanzada
 * @property level Nivel actual de dificultad
 */
data class GameState(
    val playerX: Float = 0.5f,
    val playerY: Float = 0.8f,
    val asteroids: List<Asteroid> = emptyList(),
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val isPaused: Boolean = false,
    val highScore: Int = 0,
    val level: Int = 1
)

/**
 * Asteroid representa un asteroide individual en el juego.
 * Cada asteroide tiene su propia posición y velocidad.
 *
 * @property id Identificador único del asteroide (importante para Compose)
 * @property x Posición horizontal (0.0 a 1.0)
 * @property y Posición vertical (0.0 a 1.0)
 * @property speed Velocidad de caída (cuánto se mueve en cada frame)
 * @property size Tamaño relativo del asteroide (0.5 a 1.5)
 */
data class Asteroid(
    val id: Int,
    val x: Float,
    val y: Float,
    val speed: Float,
    val size: Float = 1.0f
)

/**
 * Star representa una estrella en el fondo animado.
 *
 * @property x Posición horizontal
 * @property y Posición vertical
 * @property size Tamaño de la estrella (1.0 a 3.0 píxeles)
 * @property speed Velocidad de movimiento del parallax
 */
data class Star(
    val x: Float,
    val y: Float,
    val size: Float,
    val speed: Float
)
