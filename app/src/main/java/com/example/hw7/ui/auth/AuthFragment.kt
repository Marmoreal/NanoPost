package com.example.hw7.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hw7.R
import com.example.hw7.data.model.CheckUsernameResultApi
import com.example.hw7.databinding.FragmentAuthBinding
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.haveTokenAndUserId()) {
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToFeedFragment())
        }

        binding.password.isVisible = false

        binding.tiLogin.doAfterTextChanged {
            viewModel.onUsernameChanged(it?.toString() ?: "")
        }
        binding.tiPassword.doAfterTextChanged {
            viewModel.onPasswordChanged(it?.toString() ?: "")
        }

        binding.btnContinue.setOnClickListener {
            viewModel.onContinueClicked()
        }

        binding.tiLogin.actionListener(viewModel)
        binding.tiPassword.actionListener(viewModel)

        viewModel.navigateLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToFeedFragment())
        }

        viewModel.exceptionLiveData.observe(viewLifecycleOwner) {
            binding.password.error = getString(R.string.password_is_incorrect)
        }

        viewModel.validateUsernameLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ValidateUsernameResult.TooShort -> {
                    binding.login.error = getString(R.string.username_must_be_longer_than_3_characters)
                }
                ValidateUsernameResult.TooLong -> {
                    binding.login.error = getString(R.string.username_must_shorter_than_16_characters)
                }
                ValidateUsernameResult.InvalidCharacters -> {
                    binding.login.error = getString(R.string.username_contains_invalid_characters)
                }
                else -> {
                    throw IllegalStateException("Something went wrong")
                }
            }
        }

        viewModel.validatePasswordLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ValidatePasswordResult.TooShort -> {
                    binding.password.error = getString(R.string.password_must_be_longer_than_3_characters)
                }
                ValidatePasswordResult.TooLong -> {
                    binding.password.error = getString(R.string.password_must_be_shorter_than_16_characters)
                }
                else -> {
                    binding.password.error = null
                }
            }
        }

        viewModel.checkUsernameApiLiveData.observe(viewLifecycleOwner) {
            when (it.result) {
                CheckUsernameResultApi.TooShort -> {
                    binding.login.error = getString(R.string.password_must_be_longer_than_3_characters)
                }
                CheckUsernameResultApi.TooLong -> {
                    binding.login.error = getString(R.string.username_must_shorter_than_16_characters)
                }
                CheckUsernameResultApi.InvalidCharacters -> {
                    binding.login.error = getString(R.string.username_contains_invalid_characters)
                }
                CheckUsernameResultApi.Taken -> {
                    binding.login.error = null
                    binding.password.isVisible = true
                    binding.login.isEnabled = false
                }
                CheckUsernameResultApi.Free -> {
                    binding.login.error = null
                    binding.password.isVisible = true
                    binding.login.isEnabled = false
                }
            }
        }
    }
}

fun TextInputEditText.actionListener(viewModel: AuthViewModel) {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            viewModel.onContinueClicked()
            true
        } else false
    }
}