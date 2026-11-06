package com.daniela.misrecordatorios
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
data class Recordatorio(
    val id: Int,
    val titulo: String,
    val descripcion: String?,
    val categoria: Categoria,
    val prioridad: Prioridad,
    val estado: Estado
) {
    init {
        require(titulo.isNotBlank()) {
            "El título no puede estar vacío"
        }
        require(id > 0) {
            "El ID debe ser mayor que cero"
        }
    }
}
sealed class Prioridad {
    data object Alta : Prioridad()
    data object Media : Prioridad()
    data object Baja : Prioridad()
}

sealed class Estado {
    data object Pendiente : Estado()
    data object Completado : Estado()
}

data class Categoria(
    val nombre: String
)
val recordatorios = listOf(
    Recordatorio(
        1,
        "Estudiar Kotlin",
        "Repasar la materia",
        Categoria("Estudios"),
        Prioridad.Alta,
        Estado.Pendiente
    ),
    Recordatorio(
        2,
        "Comprar materiales",
        null,
        Categoria("Personal"),
        Prioridad.Media,
        Estado.Completado
    ),
    Recordatorio(
        3,
        "Revisar tareas",
        "Organizar pendientes",
        Categoria("Trabajo"),
        Prioridad.Baja,
        Estado.Pendiente
    )
)
val categorias = recordatorios
    .map { it.categoria }
    .toSet()
val coloresCategorias = mapOf(
    "Estudios" to "Morado",
    "Personal" to "Azul",
    "Trabajo" to "Verde"
)
val pendientes = recordatorios.filter {
    it.estado == Estado.Pendiente
}

val prioridadAlta = recordatorios.filter {
    it.prioridad == Prioridad.Alta
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        "Mis Recordatorios",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                items(recordatorios) { recordatorio ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("Título: ${recordatorio.titulo}", fontSize = 20.sp)
                            Text("Categoría: ${recordatorio.categoria.nombre}", fontSize = 18.sp)
                            Text(
                                "Prioridad: ${recordatorio.prioridad.javaClass.simpleName}",
                                fontSize = 18.sp
                            )
                            Text(
                                "Estado: ${recordatorio.estado.javaClass.simpleName}",
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
