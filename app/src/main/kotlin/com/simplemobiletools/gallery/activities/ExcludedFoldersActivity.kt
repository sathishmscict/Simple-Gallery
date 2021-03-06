package com.simplemobiletools.gallery.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.simplemobiletools.commons.dialogs.FilePickerDialog
import com.simplemobiletools.commons.extensions.beVisibleIf
import com.simplemobiletools.commons.interfaces.RefreshRecyclerViewListener
import com.simplemobiletools.gallery.R
import com.simplemobiletools.gallery.adapters.ManageFoldersAdapter
import com.simplemobiletools.gallery.extensions.config
import kotlinx.android.synthetic.main.activity_manage_folders.*

class ExcludedFoldersActivity : SimpleActivity(), RefreshRecyclerViewListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_folders)
        updateExcludedFolders()
    }

    private fun updateExcludedFolders() {
        val folders = ArrayList<String>()
        config.excludedFolders.mapTo(folders, { it })
        manage_folders_placeholder.text = getString(R.string.excluded_activity_placeholder)
        manage_folders_placeholder.beVisibleIf(folders.isEmpty())
        manage_folders_placeholder.setTextColor(config.textColor)

        val adapter = ManageFoldersAdapter(this, folders, true, this, manage_folders_list) {}
        adapter.setupDragListener(true)
        manage_folders_list.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_excluded_folders, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_folder -> addExcludedFolder()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun refreshItems() {
        updateExcludedFolders()
    }

    private fun addExcludedFolder() {
        FilePickerDialog(this, pickFile = false, showHidden = config.shouldShowHidden) {
            config.addExcludedFolder(it)
            updateExcludedFolders()
        }
    }
}
