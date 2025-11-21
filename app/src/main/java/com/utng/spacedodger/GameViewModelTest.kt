package com.utng.spacedodger

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import com.utng.spacedodger.viewmodel.GameViewModel
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * GameViewModelTest contiene las pruebas unitarias para GameViewModel.
 *
 * Las pruebas unitarias son como experimentos científicos:
 * - Preparas el escenario (setup)
 * - Realizas una acción (act)
 * - Verificas el resultado (assert)
 *
 * Cada prueba debe ser:
 * - Independiente (no depende de otras pruebas)
 * - Repetible (siempre da el mismo resultado)
 * - Rápida (se ejecuta en milisegundos)
 */
@ExperimentalCoroutinesApi
class GameViewModelTest {

    // ViewModel que vamos a probar
    private lateinit var viewModel: GameViewModel

    // Dispatcher de prueba para controlar el tiempo en las coroutines
    private val testDispatcher = StandardTestDispatcher()

    /**
     * @Before se ejecuta ANTES de cada prueba.
     * Es como preparar el laboratorio antes de un experimento.
     */
    @Before
    fun setup() {
        // Configurar el dispatcher de prueba para las coroutines
        Dispatchers.setMain(testDispatcher)

        // Crear una nueva instancia del ViewModel
        viewModel = GameViewModel()
    }

    /**
     * @After se ejecuta DESPUÉS de cada prueba.
     * Es como limpiar el laboratorio después del experimento.
     */
    @After
    fun tearDown() {
        // Restaurar el dispatcher original
        Dispatchers.resetMain()
    }

    /**
     * PRUEBA 1: Verificar que el juego inicia con valores correctos
     *
     * Esta prueba verifica que cuando se crea el ViewModel,
     * el estado inicial del juego sea el esperado.
     *
     * Es como verificar que un coche nuevo tenga:
     * - Tanque lleno
     * - Kilometraje en 0
     * - Todas las luces funcionando
     */
    @Test
    fun `el juego debe iniciar con valores por defecto correctos`() {
        // ARRANGE (Preparar)
        // Ya tenemos el viewModel creado en setup()

        // ACT (Actuar)
        val estadoInicial = viewModel.gameState.value

        // ASSERT (Verificar)
        // Verificar posición del jugador
        assertEquals(
            "La posición X inicial debe ser 0.5 (centro)",
            0.5f,
            estadoInicial.playerX,
            0.01f // Delta para comparación de floats
        )

        assertEquals(
            "La posición Y inicial debe ser 0.8 (parte inferior)",
            0.8f,
            estadoInicial.playerY,
            0.01f
        )

        // Verificar puntuación inicial
        assertEquals(
            "La puntuación inicial debe ser 0",
            0,
            estadoInicial.score
        )

        // Verificar nivel inicial
        assertEquals(
            "El nivel inicial debe ser 1",
            1,
            estadoInicial.level
        )

        // Verificar que no hay asteroides al inicio
        assertTrue(
            "No debe haber asteroides al inicio",
            estadoInicial.asteroids.isEmpty()
        )

        // Verificar que el juego no está pausado ni terminado
        assertFalse(
            "El juego no debe estar pausado al inicio",
            estadoInicial.isPaused
        )

        assertFalse(
            "El juego no debe estar terminado al inicio",
            estadoInicial.isGameOver
        )

        println("✅ Prueba 1 pasada: Estado inicial correcto")
    }

    /**
     * PRUEBA 2: Verificar que el jugador se mueve correctamente
     *
     * Esta prueba verifica que cuando llamamos a movePlayer(),
     * el jugador cambia su posición correctamente.
     *
     * Es como verificar que el volante de un coche
     * efectivamente cambia la dirección del vehículo.
     */
    @Test
    fun `movePlayer debe actualizar la posicion X del jugador`() {
        // ARRANGE (Preparar)
        val nuevaPosicionX = 0.3f

        // ACT (Actuar)
        viewModel.movePlayer(nuevaPosicionX)
        val estadoDespuesDeMoverse = viewModel.gameState.value

        // ASSERT (Verificar)
        assertEquals(
            "La posición X debe actualizarse a 0.3",
            nuevaPosicionX,
            estadoDespuesDeMoverse.playerX,
            0.01f
        )

        println("✅ Prueba 2 pasada: Movimiento del jugador funciona")
    }

    /**
     * PRUEBA 3: Verificar que el sistema de pausa funciona
     *
     * Esta prueba verifica que togglePause() alterna
     * correctamente entre pausado y no pausado.
     *
     * Es como verificar que el botón de pausa de un reproductor
     * efectivamente pausa y reanuda la música.
     */
    @Test
    fun `togglePause debe alternar el estado de pausa`() {
        // ARRANGE (Preparar)
        val estadoInicial = viewModel.gameState.value

        // Verificar que inicialmente NO está pausado
        assertFalse(
            "Inicialmente el juego no debe estar pausado",
            estadoInicial.isPaused
        )

        // ACT 1 (Actuar - Primera pausa)
        viewModel.togglePause()
        val estadoDespuesDePrimerapausa = viewModel.gameState.value

        // ASSERT 1 (Verificar primera pausa)
        assertTrue(
            "Después de togglePause(), el juego debe estar pausado",
            estadoDespuesDePrimerapausa.isPaused
        )

        // ACT 2 (Actuar - Segunda pausa)
        viewModel.togglePause()
        val estadoDespuesDeSegundaPausa = viewModel.gameState.value

        // ASSERT 2 (Verificar que se reanudó)
        assertFalse(
            "Después del segundo togglePause(), el juego debe estar reanudado",
            estadoDespuesDeSegundaPausa.isPaused
        )

        println("✅ Prueba 3 pasada: Sistema de pausa funciona correctamente")
    }

    /**
     * PRUEBA BONUS: Verificar que movePlayer limita los valores
     *
     * Esta prueba verifica que el jugador no pueda salirse
     * de los límites de la pantalla (0.0 a 1.0).
     *
     * Es como verificar que un tren no se salga de las vías.
     */
    @Test
    fun `movePlayer debe limitar la posicion entre 0 y 1`() {
        // ARRANGE & ACT 1: Intentar mover fuera del límite izquierdo
        viewModel.movePlayer(-0.5f) // Valor negativo (inválido)
        val estadoLimiteIzquierdo = viewModel.gameState.value

        // ASSERT 1: Debe estar en 0.0 (límite izquierdo)
        assertEquals(
            "La posición no debe ser menor a 0.0",
            0.0f,
            estadoLimiteIzquierdo.playerX,
            0.01f
        )

        // ARRANGE & ACT 2: Intentar mover fuera del límite derecho
        viewModel.movePlayer(1.5f) // Valor mayor a 1 (inválido)
        val estadoLimiteDerecho = viewModel.gameState.value

        // ASSERT 2: Debe estar en 1.0 (límite derecho)
        assertEquals(
            "La posición no debe ser mayor a 1.0",
            1.0f,
            estadoLimiteDerecho.playerX,
            0.01f
        )

        println("✅ Prueba Bonus pasada: Los límites funcionan correctamente")
    }
}

/**
 * CONCEPTOS CLAVE DE TESTING:
 *
 * 1. AAA Pattern (Arrange-Act-Assert):
 *    - Arrange: Preparar el escenario
 *    - Act: Ejecutar la acción a probar
 *    - Assert: Verificar el resultado
 *
 * 2. Nombres descriptivos:
 *    Los nombres de las pruebas deben explicar QUÉ se prueba
 *    Usamos backticks ` para nombres largos y descriptivos
 *
 * 3. Assertions comunes:
 *    - assertEquals(): Verifica que dos valores sean iguales
 *    - assertTrue(): Verifica que algo sea verdadero
 *    - assertFalse(): Verifica que algo sea falso
 *    - assertNotNull(): Verifica que algo no sea null
 *
 * 4. Delta en floats:
 *    Los números float pueden tener pequeñas imprecisiones
 *    Por eso usamos un "delta" (0.01f) para comparaciones
 *
 * 5. Independencia:
 *    Cada prueba debe funcionar sola, sin depender de otras
 *    Por eso usamos @Before para resetear el estado
 */