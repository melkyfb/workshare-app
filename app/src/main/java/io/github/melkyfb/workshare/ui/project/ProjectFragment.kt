package io.github.melkyfb.workshare.ui.project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.melkyfb.workshare.R
import io.github.melkyfb.workshare.data.model.LoggedInUser
import io.github.melkyfb.workshare.data.model.Project
import io.github.melkyfb.workshare.ui.CreateProjectDialog
import io.github.melkyfb.workshare.ui.ProjectViewActivity

class ProjectFragment : Fragment() {

    private lateinit var projectViewModel: ProjectViewModel

    var llProject: LinearLayout? = null

    var user: LoggedInUser? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        projectViewModel =
            ViewModelProviders.of(this).get(ProjectViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_project, container, false)
        llProject = root.findViewById(R.id.linear_layout_project)
        val textView: TextView = root.findViewById(R.id.text_empty_project)
        projectViewModel.text.observe(this, Observer {
            textView.text = it
        })
        val buttonAdd: ImageButton = root.findViewById(R.id.button_add_project)
        buttonAdd.setOnClickListener(View.OnClickListener {
            createProjectAlert()
        })

        var b = arguments as Bundle
        this.user = b.getSerializable("user") as LoggedInUser

        return root
    }

    private fun createProjectAlert() {
        var createProjectDialog = CreateProjectDialog()
        var b = bundleOf("user" to this.user)
        createProjectDialog.setArguments(b)
        createProjectDialog.show(fragmentManager, "create_project_dialog")
    }

    private fun createProjectView(project: Project): Button {
        val button = Button(this.context)
        button.text = project.name
        button.setOnClickListener({
            callProjectIntent(project)
        })
        return button
    }

    private fun callProjectIntent(project: Project) {
        val i = Intent(this.context, ProjectViewActivity::class.java)
        val b = Bundle()
        b.putSerializable("project", project)
        b.putSerializable("user", this.user)
        i.putExtra("bundle", b)
        startActivity(i)
    }

    public fun onCreateProject(project: Project) {
        val b = createProjectView(project)
        llProject!!.addView(b)
    }
}