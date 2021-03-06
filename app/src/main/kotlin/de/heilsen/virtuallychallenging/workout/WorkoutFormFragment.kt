package de.heilsen.virtuallychallenging.workout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import de.heilsen.compat.edittext.EditTextCompat.fixDigitsKeyListenerLocale
import de.heilsen.compat.edittext.localeCompat
import de.heilsen.virtuallychallenging.R
import de.heilsen.virtuallychallenging.dashboard.DashboardAction
import de.heilsen.virtuallychallenging.dashboard.DashboardViewModel
import de.heilsen.virtuallychallenging.util.doAfterFocus
import de.heilsen.virtuallychallenging.util.show
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class WorkoutFormFragment : BottomSheetDialogFragment() {

    private val viewModel by activityViewModels<DashboardViewModel>()

    private val decimalFormat by lazy {
        val locale = requireContext().resources.configuration.localeCompat
        DecimalFormat.getInstance(locale)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_workout, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val distanceField = view.findViewById<EditText>(R.id.distance_field)
        distanceField.fixDigitsKeyListenerLocale()
        val distanceContainer = view.findViewById<TextInputLayout>(R.id.distance_container)
        val dateField = view.findViewById<EditText>(R.id.date_field)
        val dateContainer = view.findViewById<TextInputLayout>(R.id.date_container)
        val addButton = view.findViewById<Button>(R.id.add_button)

        dateField.setText(Instant.now().toLocalDateString())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
            .apply {
                addOnPositiveButtonClickListener { epochMilli ->
                    val localDate = Instant.ofEpochMilli(epochMilli).toLocalDateString()
                    dateField.setText(localDate)
                }
            }

        dateContainer.setEndIconOnClickListener {
            datePicker.show(parentFragmentManager)
        }
        addButton.setOnClickListener {
            action(distanceField, distanceContainer, dateField)
        }
        distanceField.doAfterTextChanged { distanceContainer.isErrorEnabled = false }
        distanceField.doAfterFocus { distanceContainer.isErrorEnabled = false }

        dateField.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId != EditorInfo.IME_ACTION_DONE) return@setOnEditorActionListener false
            val imm: InputMethodManager =
                textView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(textView.windowToken, 0)
            action(distanceField, distanceContainer, dateField)
            true
        }
    }

    private fun action(
        distanceField: EditText,
        distanceContainer: TextInputLayout,
        dateField: EditText
    ) {
        val distance: Number? = decimalFormat.parseOrNull(distanceField.text.toString())
        if (distance == null) {
            distanceField.requestFocus()
            distanceContainer.error = getString(R.string.distance_missing)
            return
        }
        val localDate = dateField.text.asLocalDateTime()
        viewModel.dispatch(DashboardAction.AddWorkout(WorkoutModel(distance, localDate)))
        dismiss()
    }
}

private fun NumberFormat.parseOrNull(string: String): Number? {
    return try {
        parse(string)
    } catch (e: Exception) {
        null
    }
}

fun Instant.toLocalDateString(): String {
    return atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
        ?: ""
}

fun CharSequence.asLocalDateTime(): LocalDateTime {
    return LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay()
}