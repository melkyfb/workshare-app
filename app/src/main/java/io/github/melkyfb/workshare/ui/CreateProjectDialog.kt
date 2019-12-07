package io.github.melkyfb.workshare.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import io.github.melkyfb.workshare.R
import io.github.melkyfb.workshare.data.model.LoggedInUser
import io.github.melkyfb.workshare.data.model.Project
import io.github.melkyfb.workshare.ui.project.ProjectFragment
import kotlinx.android.synthetic.main.fragment_create_project.view.*

class CreateProjectDialog : DialogFragment() {

    var edtName: EditText? = null
    var edtDescription: EditText? = null
    var btnSave: Button? = null

    var user: LoggedInUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var _view: View = getActivity()!!.getLayoutInflater().inflate(R.layout.fragment_create_project, null)

        this.edtName = _view.findViewById(R.id.edt_project_name)
        this.edtDescription = _view.findViewById(R.id.edt_project_description)
        this.btnSave = _view.findViewById(R.id.btn_project_save)

        var alert = AlertDialog.Builder(this.context)
        alert.setView(_view)

        var b = arguments as Bundle
        this.user = b.getSerializable("user") as LoggedInUser

        this.btnSave!!.setOnClickListener(View.OnClickListener {
            val name:String = this.edtName!!.text.toString()
            val description:String = this.edtDescription!!.text.toString()
            if (TextUtils.isEmpty(name)) {
                this.edtName!!.requestFocus()
                this.edtName!!.setError("Nome deve ser preenchido")
            } else {
                val p = Project(java.util.UUID.randomUUID().toString(), name, description, false, this.user!!)
                (parentFragment!!.childFragmentManager.primaryNavigationFragment as ProjectFragment).onCreateProject(p)
                dismiss()
            }
        })

        return alert.create()

    }

}
