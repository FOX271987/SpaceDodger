package com.utng.spacedodger

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import com.utng.spacedodger.viewmodel.GameViewModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

@ExperimentalCoroutinesApi
class GameViewModelTest {

    private lateinit var viewModel: GameViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = GameViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun elJuegoDebeIniciarConValoresPorDefectoCorrectos() {
        val estadoInicial = viewModel.gameState.value

        assertEquals(0.5f, estadoInicial.playerX, 0.01f)
        assertEquals(0.8f, estadoInicial.playerY, 0.01f)
        assertEquals(0, estadoInicial.score)
        assertEquals(1, estadoInicial.level)
        assertTrue(estadoInicial.asteroids.isEmpty())
        assertFalse(estadoInicial.isPaused)
        assertFalse(estadoInicial.isGameOver)
    }

    @Test
    fun movePlayerDebeActualizarLaPosicionXDelJugador() {
        val nuevaPosicionX = 0.3f

        viewModel.movePlayer(nuevaPosicionX)
        val estadoDespuesDeMoverse = viewModel.gameState.value

        assertEquals(nuevaPosicionX, estadoDespuesDeMoverse.playerX, 0.01f)
    }

    @Test
    fun togglePauseDebeAlternarElEstadoDePausa() {
        val estadoInicial = viewModel.gameState.value
        assertFalse(estadoInicial.isPaused)

        viewModel.togglePause()
        val estadoDespuesDePrimerapausa = viewModel.gameState.value
        assertTrue(estadoDespuesDePrimerapausa.isPaused)

        viewModel.togglePause()
        val estadoDespuesDeSegundaPausa = viewModel.gameState.value
        assertFalse(estadoDespuesDeSegundaPausa.isPaused)
    }
}