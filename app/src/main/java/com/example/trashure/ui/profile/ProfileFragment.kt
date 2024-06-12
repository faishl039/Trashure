package com.example.trashure.ui.profile

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.trashure.R
import com.example.trashure.ViewModelFactory
import com.example.trashure.ui.login.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var textEmail: TextView
    private lateinit var btnLogout: Button
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textEmail = view.findViewById(R.id.email)
        btnLogout = view.findViewById(R.id.btn_logout)

        viewModel.getSession().observe(viewLifecycleOwner) { session ->
            if (session != null) {
                textEmail.text = session.accessToken
            }
        }

        progressDialog = ProgressDialog(requireContext()).apply {
            setTitle("Log out")
            setMessage("Please wait...")
            setCancelable(false)
        }

        viewModel.isLoggingOut.observe(viewLifecycleOwner) { isLoggingOut ->
            if (isLoggingOut) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        }

        viewModel.logoutComplete.observe(viewLifecycleOwner) { logoutComplete ->
            if (logoutComplete) {
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }

        btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

}
