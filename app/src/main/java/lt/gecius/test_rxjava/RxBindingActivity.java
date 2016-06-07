package lt.gecius.test_rxjava;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.egecius.test_rxjava.R;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/** Samples of RxBinding usage */
public class RxBindingActivity extends AppCompatActivity {

	private EditText emailEditText;
	private EditText passwordEditText;
	private Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rx_binding);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

		findViews();

//		RxView.clicks(fab).subscribe(aVoid -> {
//			Log.v("Eg:RxBindingActivity:36", "call");
//			Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//
//		});

//		printEvenNumbers();
//		printForEach();
//		groupBy();

//		iterateOverList();

		setLoginBtnToEnableWhenSufficientCharsEntered();

	}

	private void setLoginBtnToEnableWhenSufficientCharsEntered() {
		Observable<TextViewTextChangeEvent> emailChangeObservable  = RxTextView.textChangeEvents(emailEditText);
		Observable<TextViewTextChangeEvent> passwordChangeObservable  = RxTextView.textChangeEvents(passwordEditText);

		Observable.combineLatest(emailChangeObservable, passwordChangeObservable, (emailChangeEvent, passwordChangeEvent) -> {
			CharSequence email = emailChangeEvent.text();
			boolean emailCheck = email.length() > 3;


			CharSequence password = passwordChangeEvent.text();
			boolean passwordCheck = password.length() > 3;


			return emailCheck && passwordCheck;
		}).subscribe(enable -> {
			loginButton.setEnabled(enable);
		});
	}

	private void findViews() {
		emailEditText = (EditText) findViewById(R.id.email);
		passwordEditText = (EditText) findViewById(R.id.password);
		loginButton = (Button) findViewById(R.id.login_btn);
	}

	private void iterateOverList() {
		Observable.just(getList())
				.flatMap(new Func1<List<String>, Observable<? extends String>>() {
					@Override
					public Observable<? extends String> call(List<String> list) {
						return Observable.from(list);
					}
				})
				.subscribe(new Subscriber<String>() {
					@Override
					public void onCompleted() {
						Log.v("Eg:RxBindingActivity:57", "onCompleted");
					}

					@Override
					public void onError(Throwable e) {
						Log.e("Eg:RxBindingActivity:62", "onError");
					}

					@Override
					public void onNext(String s) {
						Log.i("Eg:RxBindingActivity:67", "onNext " + s);
					}
				});

	}

	private List<String> getList() {
		List<String> list = new ArrayList<>();
		list.add("Egis");
		list.add("Tomas");
		list.add("Andrius");
		return list;
	}

	private void groupBy() {
		Observable
				.just(1, 2, 3, 4, 5)
				.groupBy(integer -> integer % 2 == 0)
				.subscribe(grouped -> {
					grouped.toList().subscribe(integers -> {
						Log.v("Eg:", integers + " (Even: " + grouped.getKey() + ")");
					});
				});

		// [1, 3, 5] (Even: false)
		// [2, 4] (Even: true)

	}

	private void printForEach() {
		Log.v("Eg:RxBindingActivity:39", "printForEach");

		Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
				.forEach(new Action1<Integer>() {
					@Override
					public void call(Integer integer) {
						Log.v("Eg:RxBindingActivity:43", "call integer " + integer);
					}
				});
	}

	private void printEvenNumbers() {
		Log.v("Eg:RxBindingActivity:37", "printEvenNumbers");

		Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
				.filter(integer -> integer % 2 == 0)
				.subscribe(integer1 -> Log.v("Eg:RxBindingActivity:41", "printEvenNumbers integer1 " + integer1));
	}

}
