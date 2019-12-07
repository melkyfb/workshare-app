package io.github.melkyfb.workshare.ui

import android.app.AlertDialog
import android.app.Dialog
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import io.github.melkyfb.workshare.R
import io.github.melkyfb.workshare.data.model.File
import io.github.melkyfb.workshare.data.model.LoggedInUser
import io.github.melkyfb.workshare.data.model.Project
import io.github.melkyfb.workshare.ui.project.ProjectFragment
import java.util.*



class CreateFileDialog : DialogFragment() {

    var edtFileName: EditText? = null
    var edtFileDescription: EditText? = null
    var edtFileVersion: EditText? = null
    var btnFileSave: Button? = null
    var fileUri: Uri? = null
    var project: Project? = null
    var user: LoggedInUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var _view: View =
            getActivity()!!.getLayoutInflater().inflate(R.layout.fragment_create_fileversion, null)

        this.edtFileName = _view.findViewById(R.id.edt_file_name)
        this.edtFileDescription = _view.findViewById(R.id.edt_file_description)
        this.edtFileVersion = _view.findViewById(R.id.edt_file_version)
        this.btnFileSave = _view.findViewById(R.id.btn_save_file)

        this.edtFileVersion!!.setText(System.currentTimeMillis().toString())

        var alert = AlertDialog.Builder(this.context)
        alert.setView(_view)

        var b = arguments as Bundle
        this.fileUri = b.getParcelable("file") as Uri?
        this.project = b.getSerializable("project") as Project?
        this.user = b.getSerializable("user") as LoggedInUser?

        var file: java.io.File = java.io.File(this.fileUri!!.path)
        this.edtFileName!!.setText(file.name)

        this.btnFileSave!!.setOnClickListener(View.OnClickListener {
            val name: String = this.edtFileName!!.text.toString()
            val description: String = this.edtFileDescription!!.text.toString()
            val version: String = this.edtFileVersion!!.text.toString()
            if (TextUtils.isEmpty(name)) {
                this.edtFileName!!.requestFocus()
                this.edtFileName!!.setError("Nome deve ser preenchido")
            } else {
                val f = File(UUID.randomUUID().toString(), name, this.fileUri.toString(), version, description, this.project!!, this.user!!)
                (activity as ProjectViewActivity).setResponseFile(f)
                dismiss()
            }
        })

        return alert.create()
    }
}