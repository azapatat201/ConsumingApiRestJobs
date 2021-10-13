package com.example.consumingmyfirstapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentResultListener

class FilterDialogFragment:DialogFragment(){
    lateinit var btCacancel:Button
    lateinit var btAceptar:Button
    lateinit var rbSearchFilter:RadioButton
    lateinit var rbCategoryFilter:RadioButton
    lateinit var rbCompanyFilter:RadioButton
    lateinit var rbLimiteRegistro:RadioButton
    lateinit var etLimit:EditText
    lateinit var txtLabelLimit:TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.filter_fragment_dialog, container, false)

        btCacancel = rootView.findViewById(R.id.btCancel)
        btAceptar = rootView.findViewById(R.id.btAcept)
        rbSearchFilter = rootView.findViewById(R.id.rbSearchFilter)
        rbCategoryFilter = rootView.findViewById(R.id.rbCategoryFilter)
        rbCompanyFilter = rootView.findViewById(R.id.rbCompanyFilter)
        rbLimiteRegistro = rootView.findViewById(R.id.rbLimiteRegistro)
        etLimit = rootView.findViewById(R.id.etLimit)
        txtLabelLimit = rootView.findViewById(R.id.txtLabelLimit)

        if(!rbLimiteRegistro.isChecked){
            txtLabelLimit.visibility = View.GONE
            etLimit.visibility = View.GONE
        }

        parentFragmentManager.setFragmentResultListener("keySent", this,
            FragmentResultListener { requestKey, result ->
                var rbSelect = result.get("rbSelect")
                var etLimitMv = result.get("limit")

                when(rbSelect){
                    "Por cantidad registros" -> {
                        rbLimiteRegistro.isChecked = true
                        txtLabelLimit.visibility = View.VISIBLE
                        etLimit.visibility = View.VISIBLE
                    }
                    "Por Categoría" -> rbCategoryFilter.isChecked = true
                    "Por Compañia" -> rbCompanyFilter.isChecked = true
                    else -> rbSearchFilter.isChecked = true
                }
                etLimit.setText(etLimitMv.toString())

                //Toast.makeText(context, "Frag Recibimos: $rbSelect y el limite es $etLimitMv", Toast.LENGTH_LONG).show()
            })

        btCacancel.setOnClickListener{
            dismiss()
        }

        btAceptar.setOnClickListener {
            val rgFiltro = rootView.findViewById<RadioGroup>(R.id.rgFilter).checkedRadioButtonId
            val rbSelect = rootView.findViewById<RadioButton>(rgFiltro)

            val selectItem = rbSelect.text.toString()

            val bundle: Bundle = Bundle()

            var limit = "0"
            if(!etLimit.text.isNullOrEmpty()) limit = etLimit.text.toString()

            bundle.putString("rbSelect", selectItem)
            bundle.putString("limit", limit)
            parentFragmentManager.setFragmentResult("key", bundle)

            //Toast.makeText(context, "Filtro seleccionado $selectItem", Toast.LENGTH_SHORT).show()

            dismiss()
        }

        rbLimiteRegistro.setOnClickListener { setVisibilityLimit(true) }
        rbSearchFilter.setOnClickListener { setVisibilityLimit(false) }
        rbCategoryFilter.setOnClickListener { setVisibilityLimit(false) }
        rbCompanyFilter.setOnClickListener { setVisibilityLimit(false) }

        return rootView
    }

    fun setVisibilityLimit(bool:Boolean){
        if(bool){
            txtLabelLimit.visibility = View.VISIBLE
            etLimit.visibility = View.VISIBLE
        }
        else{
            txtLabelLimit.visibility = View.GONE
            etLimit.visibility = View.GONE
        }

    }
}