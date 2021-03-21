package de.heilsen.virtuallychallenging.workoutform

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
import de.heilsen.virtuallychallenging.R
import de.heilsen.virtuallychallenging.dashboard.DashboardAction
import de.heilsen.virtuallychallenging.dashboard.DashboardViewModel
import de.heilsen.virtuallychallenging.domain.model.Workout
import de.heilsen.virtuallychallenging.util.asLocalDate
import de.heilsen.virtuallychallenging.util.doAfterFocus
import de.heilsen.virtuallychallenging.util.show
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class WorkoutFormFragment : BottomSheetDialogFragment() {

    private val viewModel by activityViewModels<DashboardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_workout, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val distanceField = view.findViewById<EditText>(R.id.distance_field)
        val distanceContainer = view.findViewById<TextInputLayout>(R.id.distance_container)
        val dateField = view.findViewById<EditText>(R.id.date_field)
        val dateContainer = view.findViewById<TextInputLayout>(R.id.date_container)
        val addButton = view.findViewById<Button>(R.id.add_button)

        dateField.setText(Instant.now().asLocalDate())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
            .apply {
                addOnPositiveButtonClickListener { epochMilli ->
                    val localDate = Instant.ofEpochMilli(epochMilli).asLocalDate()
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

        dateField.setOnEditorActionListener { textView, actionId, keyEvent ->
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
        val distance = distanceField.text.toString().toFloatOrNull()
        if (distance == null) {
            distanceField.requestFocus()
            distanceContainer.error = getString(R.string.no_distance)
            return
        }
        val date = LocalDate.parse(dateField.text.toString(), DateTimeFormatter.ISO_LOCAL_DATE)
        val instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
        viewModel.dispatch(DashboardAction.AddWorkout(Workout(distance, instant)))
        dismiss()
    }
}