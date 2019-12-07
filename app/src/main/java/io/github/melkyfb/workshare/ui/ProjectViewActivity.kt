package io.github.melkyfb.workshare.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import io.github.melkyfb.workshare.R
import io.github.melkyfb.workshare.data.model.Project
import android.os.Build
import io.github.melkyfb.workshare.data.model.File
import android.content.ActivityNotFoundException
import android.app.AlertDialog
import io.github.melkyfb.workshare.data.model.LoggedInUser


class ProjectViewActivity : AppCompatActivity() {

    val CHOOSE_FILE_ACTION  = 1

    var txvNameProject: TextView? = null
    var txvDescriptionProject: TextView? = null
    var txvColaboratorProject: TextView? = null
    var btnUpload: ImageButton? = null
    var btnDownload: ImageButton? = null
    var txvFileName: TextView? = null
    var txvFileVersao: TextView? = null
    var txvFileDescricao: TextView? = null
    var txvFileColaborador: TextView? = null
    var project: Project? = null
    var file: File? = null
    var user: LoggedInUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_view)
        this.txvNameProject = this.findViewById(R.id.txv_nome_projeto)
        this.txvDescriptionProject = this.findViewById(R.id.txv_descricao_projeto)
        this.txvColaboratorProject = this.findViewById(R.id.txv_colaborador_projeto)
        this.btnUpload = this.findViewById(R.id.btn_upload)
        this. btnDownload = this.findViewById(R.id.btn_download)
        this.txvFileName = this.findViewById(R.id.txv_file_name)
        this.txvFileVersao = this.findViewById(R.id.txv_file_versao)
        this.txvFileDescricao = this.findViewById(R.id.txv_file_descricao)
        this.txvFileColaborador = this.findViewById(R.id.txv_file_colaborador)
        val intent = this.intent
        val bundle = intent.getBundleExtra("bundle")
        this.project = bundle.getSerializable("project") as Project
        this.user = bundle.getSerializable("user") as LoggedInUser
        this.txvNameProject!!.setText(this.project!!.name)
        this.txvDescriptionProject!!.setText(this.project!!.description)
        this.txvColaboratorProject!!.setText(this.project!!.ownerUser.displayName)
        this.btnUpload!!.setOnClickListener(View.OnClickListener {
            callChooseFile()
        })
        this.btnDownload!!.setOnClickListener({
            downloadFile(this.file!!)
        })
    }

    fun downloadFile(file: File?){
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(file!!.path))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            var alertBuilder = AlertDialog.Builder(this)
            alertBuilder.setTitle("Falha!")
                .setMessage("Você não possui aplicação para abrir este tipo de arquivo.")
                .setCancelable(true)
                .setPositiveButton("Ok", { dialog, id ->
                    dialog.dismiss()
                })
            var dialog = alertBuilder.create()
            dialog.show()

        }
    }

    fun callChooseFile() {
        Toast.makeText(this, "Escolha um Arquivo", Toast.LENGTH_LONG).show()
        val mimeTypes = arrayOf(
            "application/msword", // .doc
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .docx
            "application/vnd.ms-excel", // .xls
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" // .xlsx
        )

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
            if (mimeTypes.size > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            }
        } else {
            var mimeTypesStr = ""
            for (mimeType in mimeTypes) {
                mimeTypesStr += "$mimeType|"
            }
            intent.type = mimeTypesStr.substring(0, mimeTypesStr.length - 1)
        }

        startActivityForResult(Intent.createChooser(intent, "Escolha um Arquivo"), CHOOSE_FILE_ACTION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_FILE_ACTION && resultCode == Activity.RESULT_OK) {
            createFileFromUri(data?.data)
        }
    }

    fun createFileFromUri(uri: Uri?){
        var createFileDialog = CreateFileDialog()
        var b = Bundle()
        b.putParcelable("file", uri)
        b.putSerializable("project", this.project)
        b.putSerializable("user", this.user)
        createFileDialog.setArguments(b)
        createFileDialog.show(supportFragmentManager, "create_file_dialog")
    }

    public fun setResponseFile(file: File){
        this.file = file
        this.txvFileName!!.setText(file.name)
        this.txvFileVersao!!.setText(file.version)
        this.txvFileDescricao!!.setText(file.description)
        this.txvFileColaborador!!.setText(file.user.displayName)
        this.btnDownload!!.setClickable(true)
    }
}
