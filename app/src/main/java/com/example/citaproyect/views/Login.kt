import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.example.citaproyect.ApiService
import com.example.citaproyect.AppDatabase
import com.example.citaproyect.R
import com.example.citaproyect.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
@Composable
fun Login(navController: NavController) {
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Inicializamos la base de datos
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "app-database"
    ).build()

    val serviceDao = db.ServiceDao() // Obtener el ServiceDao


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.darkMidnightBlue))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Botón para crear usuario
            Button(
                onClick = {
                    navController.navigate("LoginCreateAcccount") // Cambia esta ruta según tu navegación
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                enabled = !isLoading // Deshabilitar el botón mientras se procesa
            ) {
                Text(text = "Crear Cuenta", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para loguearse (y también insertar el usuario)
            Button(
                onClick = {
                    navController.navigate("LoginSesion") // Cambia esta ruta según tu navegación
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                enabled = !isLoading // Deshabilitar el botón mientras se procesa
            ) {
                Text(text = "Iniciar Sesión", fontSize = 18.sp)
            }
        }
    }
}