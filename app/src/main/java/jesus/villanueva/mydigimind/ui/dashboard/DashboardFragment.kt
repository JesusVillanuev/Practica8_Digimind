package jesus.villanueva.mydigimind.ui.dashboard

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import jesus.villanueva.mydigimind.Carrito
import jesus.villanueva.mydigimind.R
import jesus.villanueva.mydigimind.Recordatorio
import jesus.villanueva.mydigimind.databinding.FragmentDashboardBinding
import jesus.villanueva.mydigimind.ui.home.HomeFragment.Companion.carrito

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val setTime: Button = root.findViewById(R.id.Registro)
        val mensaje: TextView = root.findViewById(R.id.txtNombreActividad)
        val hora: Button = root.findViewById(R.id.Time)
        val tiempo: TextView = root.findViewById(R.id.txtTiempo)

        val checkBoxes = listOf(
            root.findViewById<CheckBox>(R.id.cMonday) to "Monday",
            root.findViewById<CheckBox>(R.id.cTuesday) to "Tuesday",
            root.findViewById<CheckBox>(R.id.cWednesday) to "Wednesday",
            root.findViewById<CheckBox>(R.id.cThursday) to "Thursday",
            root.findViewById<CheckBox>(R.id.cFriday) to "Friday",
            root.findViewById<CheckBox>(R.id.cSaturday) to "Saturday",
            root.findViewById<CheckBox>(R.id.cSunday) to "Sunday"
        )

        hora.setOnClickListener {
            TimePickerDialog(requireContext(), { _, hourOfDay, minutes ->
                tiempo.text = String.format("%02d:%02d", hourOfDay, minutes)
            }, 0, 0, false).show()
        }

        setTime.setOnClickListener {
            val mensajes = mensaje.text.toString().trim()
            val tiempos = tiempo.text.toString().trim()
            val diasSeleccionados = checkBoxes.filter { it.first.isChecked }.map { it.second }

            if (mensajes.isNotEmpty() && tiempos.isNotEmpty() && diasSeleccionados.isNotEmpty()) {
                val dias = if (diasSeleccionados.size == 7) "Everyday" else diasSeleccionados.joinToString(", ")
                val recordatorio = Recordatorio(dias, tiempos, mensajes)


                carrito.agregar(recordatorio)

                mensaje.text = ""
                tiempo.text = ""
                checkBoxes.forEach { it.first.isChecked = false }

                Toast.makeText(context, "Recordatorio registrado", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Debes escribir un mensaje, seleccionar al menos un día y una hora", Toast.LENGTH_LONG).show()
            }
        }

        return root
    }
}

