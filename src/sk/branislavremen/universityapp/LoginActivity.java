package sk.branislavremen.universityapp;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	protected EditText usernameEditText;
	protected EditText passwordEditText;
	protected Button loginButton;

	protected TextView signUpTextView;
	protected TextView resetPasswordTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_login);

		signUpTextView = (TextView) findViewById(R.id.signUpText);
		resetPasswordTextView = (TextView) findViewById(R.id.resetPasswordText);
		usernameEditText = (EditText) findViewById(R.id.usernameField);
		passwordEditText = (EditText) findViewById(R.id.passwordField);
		loginButton = (Button) findViewById(R.id.loginButton);

		signUpTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						SignUpActivity.class);
				startActivity(intent);
			}
		});
		
		resetPasswordTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,
						PasswordResetActivity.class);
				startActivity(intent);
			}
		});

		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = usernameEditText.getText().toString();
				String password = passwordEditText.getText().toString();

				username = username.trim();
				password = password.trim();

				if (username.isEmpty() || password.isEmpty()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							LoginActivity.this);
					builder.setMessage(R.string.login_error_message)
							.setTitle(R.string.login_error_title)
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				} else {
					setProgressBarIndeterminateVisibility(true);

					ParseUser.logInInBackground(username, password,
							new LogInCallback() {
								@Override
								public void done(ParseUser user,
										ParseException e) {
									setProgressBarIndeterminateVisibility(false);

									if (e == null) {
										// Success!
										Intent intent = new Intent(
												LoginActivity.this,
												MainActivity.class);
										intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
										startActivity(intent);
									} else {
										// Fail
										AlertDialog.Builder builder = new AlertDialog.Builder(
												LoginActivity.this);
										builder.setMessage(e.getMessage())
												.setTitle(
														R.string.login_error_title)
												.setPositiveButton(
														android.R.string.ok,
														null);
										AlertDialog dialog = builder.create();
										dialog.show();
									}
								}
							});
				}
			}
		});
	}

	
}
