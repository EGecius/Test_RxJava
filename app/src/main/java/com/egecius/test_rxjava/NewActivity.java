package com.egecius.test_rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;


public class NewActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

//		callSimpleOnSubscribe();
//		callObservableJust();

//		callWithMap();
//		callWithFrom();
//		callWithFlatMap();

		callWithScan();
	}

	private void callWithScan() {
		Observable.from(getList())
				.map(new Func1<String, Integer>() {
					@Override
					public Integer call(String s) {
						Log.v("Eg:NewActivity:42", "call s " + s);
						return 1;
					}
				})
				.scan(new Func2<Integer, Integer, Integer>() {
					@Override
					public Integer call(Integer integer, Integer integer2) {
						Log.v("Eg:NewActivity:48", "call integer " + integer);
						Log.v("Eg:NewActivity:49", "call integer2 " + integer2);
						int sum = integer + integer2;

						Log.v("Eg:NewActivity:53", "call sum " + sum);
						return sum;
					}
				})
				.subscribe();
	}

	private void callWithFlatMap() {
		Log.v("Eg:NewActivity:34", "callWithFlatMap");
		Subscription subscription = Observable.just(getList()).flatMap(new Func1<ArrayList<String>, Observable<String>>() {
			@Override
			public Observable<String> call(ArrayList<String> list) {
				return Observable.from(list);
			}
		}).take(1).filter(new Func1<String, Boolean>() {
			@Override
			public Boolean call(String s) {
				return s.contains("DU");
			}
		}).map(new Func1<String, String>() {
			@Override
			public String call(String s) {
				Log.e("Eg:NewActivity.map:45", "call ");
				return s + " amended in map";
			}
		}).doOnNext(new Action1<String>() {
			@Override
			public void call(String s) {
				Log.w("Eg:NewActivity:57", "doOnNext ");

				s = s + " [ADDED IN doOnNext]";
			}
		}).subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {
				Log.v("Eg:NewActivity:50", "Subscriber.onCompleted");

			}

			@Override
			public void onError(Throwable e) {
				Log.v("Eg:NewActivity:56", "Subscriber.onError");

			}

			@Override
			public void onNext(String s) {
				Log.v("Eg:NewActivity:62", "Subscriber.onNext s ->" + s);

			}
		});
	}

	private void callSimpleOnSubscribe() {
		Log.v("Eg:NewActivity:25", "callSimpleOnSubscribe");
		Observable<String> stringObservable = Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				Log.v("Eg:NewActivity:28", "OnSubscribe call ");
			}
		});

		stringObservable.subscribe();
		stringObservable.subscribe();
	}

	private void callObservableJust() {
		Log.v("Eg:NewActivity:33", "callObservableJust");

		Observable<String> justObservable = Observable.just("arg passed to 'just'");

		justObservable.subscribe(new Action1<String>() {
			@Override
			public void call(String s) {
				Log.v("Eg:NewActivity:48", "Action1.call ");
			}
		});

	}

	private void callWithMap() {
		Log.v("Eg:NewActivity:29", "callWithMap");

		Observable<String> observable = Observable.just("5");

		observable.map(new Func1<String, Integer>() {
			@Override
			public Integer call(String s) {
				Log.v("Eg:NewActivity:64", "call s " + s);

				return Integer.valueOf(s);
			}
		});
	}


	private void callWithFrom() {
		Log.v("Eg:NewActivity:95", "callWithFrom");
		Observable.from(getList())
				.map(new Func1<String, String>() {
					@Override
					public String call(String s) {
						Log.i("Eg:NewActivity:101", "call s -> " + s);
						return s + " Egis";
					}
				})
				.subscribe(new Subscriber<String>() {
					@Override
					public void onCompleted() {
						Log.i("Eg:NewActivity:108", "onCompleted");

					}

					@Override
					public void onError(Throwable e) {
						Log.e("Eg:NewActivity:114", "onError");

					}

					@Override
					public void onNext(String s) {
						Log.w("Eg:NewActivity:120", "onNext s -> " + s);
					}
				});
	}

	private ArrayList<String> getList() {
		Log.d("Eg:NewActivity:102", "getList");
		ArrayList<String> list = new ArrayList<>();
		list.add("VIENAS");
		list.add("DU");
		list.add("TRYS");

		return list;
	}

}
