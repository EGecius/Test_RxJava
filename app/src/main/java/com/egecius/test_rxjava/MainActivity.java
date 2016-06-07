package com.egecius.test_rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


//		observableStandard.subscribe(subscriber);
////
//		observableJust.subscribe(subscriber);

//		observableJust.subscribe(new Action1<String>() {
//			@Override
//			public void call(String s) {
//				Log.v("Eg:MainActivity:30",  "call " + s);
//			}
//		});
//
//		observableJust.subscribe(new Subscriber<String>() {
//			@Override
//			public void onCompleted() {
//				Log.i("Eg:MainActivity:39", "Subscriber onCompleted");
//			}
//
//			@Override
//			public void onError(Throwable e) {
//				Log.i("Eg:MainActivity:43", "Subscriber onError");
//			}
//
//			@Override
//			public void onNext(String s) {
//				Log.i("Eg:MainActivity:49", "Subscriber onNext " + s);
//			}
//		});
//
//		observableJust.subscribe(onNextAction);
//
//		Observable.just("chained")
//				.subscribe(new Action1<String>() {
//					@Override
//					public void call(String s) {
//						Log.i("Eg:MA:27", "printing: " + s);
//					}
//				});

//
//		Observable.just("Chained with map")
//				.map(new Func1<String, String>() {
//					@Override
//					public String call(String s) {
//						return s + " -Egis";
//					}
//				})
//				.subscribe(new Action1<String>() {
//					@Override
//					public void call(String s) {
//						Log.i("Eg:MA:27", "printing: " + s);
//					}
//				});

//		callWithTransformingMaps();


		callFlatMap1();

//		flatMapsCombined();

//		callSimpleObservable();

//		callJust();

//		callFrom();

//		callFlatMap();

	}

	private void callFlatMap() {

		Observable.just(getList())
				.flatMap(new Func1<ArrayList<String>, Observable<String>>() {
					@Override
					public Observable<String> call(ArrayList<String> list) {
						return Observable.from(list);
					}
				})
				.flatMap(new Func1<String, Observable<String>>() {
					@Override
					public Observable<String> call(String s) {
						return Observable.just(s + " [amended]");
					}
				})
				.doOnNext(new Action1<String>() {
					@Override
					public void call(String s) {

					}
				})
				.subscribe(new Action1<String>() {
					@Override
					public void call(String s) {
						Log.v("Eg:MA:80", "in call = " + s);
					}
				});
	}

	private Observable<List<String>> getQueryResultsAsObservable() {
		ArrayList<String> list = getList();

		return Observable.just(list);
	}

	private void callFrom() {

		Observable.from(getList())
				.map(s -> s + " [in map]")
				.subscribe(s -> Log.i("Eg:MA:74", "in Action1 call " + s));

	}



	private ArrayList<String> getList() {
		ArrayList<String> list = new ArrayList<>();
		list.add("VIENAS");
		list.add("DU");
		list.add("TRYS");

		return list;
	}

	private void callJust() {

		Observable<String> observable = Observable.just("from just");

		Subscriber<String> subscriber = new Subscriber<String>() {
			@Override
			public void onCompleted() {
				Log.v("Eg:MA:87", "in subscriber onCompleted");

			}

			@Override
			public void onError(Throwable e) {
				Log.v("Eg:MA:87", "in subscriber onError");

			}

			@Override
			public void onNext(String s) {
				Log.v("Eg:MA:87", "in subscriber onNext: " + s);
			}
		};

		observable.subscribe(subscriber);
	}

	private void callSimpleObservable() {

		Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				subscriber.onNext("in OnSubscribe");
				subscriber.onCompleted();

			}
		});

		Subscriber<String> subscriber = new Subscriber<String>() {
			@Override
			public void onCompleted() {
				Log.v("Eg:MA:87", "in subscriber onCompleted");

			}

			@Override
			public void onError(Throwable e) {
				Log.v("Eg:MA:87", "in subscriber onError");

			}

			@Override
			public void onNext(String s) {
				Log.v("Eg:MA:87", "in subscriber onNext");
			}
		};

		observable.subscribe(subscriber);

	}

	private void flatMapsCombined() {
		query("query")
				.flatMap(urls -> Observable.from(urls))
				.flatMap(new Func1<String, Observable<String>>() {
					@Override
					public Observable<String> call(String url) {
						return getTitle(url);
					}
				})
//				.doOnNext(new Action1<String>() {
//					@Override
//					public void call(String s) {
//						Log.i("Eg:MA:74", "in doOnNext with " + s);
//					}
//				})
				.take(1)
				.filter(s -> !s.equals("-1- query[in getTitle]"))
				.subscribe(title -> Log.v("Eg:MA:65", "printing " + title));
	}



	private void callFlatMap1() {
		query("Hello, world!")
				.flatMap(new Func1<List<String>, Observable<String>>() {
					@Override
					public Observable<String> call(List<String> urls) {
						return Observable.from(urls);
					}
				})
				.map(new Func1<String, Object>() {
					@Override
					public String call(String s) {
						return s + " [in map]";
					}
				})
				.subscribe(new Action1<Object>() {
					@Override
					public void call(Object url) {
						Log.v("Eg:MA:57", "printing " + url);
					}
				});
	}

	Observable<String> getTitle(String url) {
		return Observable.just(url + "[in getTitle]");
	}



	private void queryForLoop() {
		query("Egis query")
				.subscribe(urls -> {
					for (String url : urls) {
						Log.v("Eg:MA:57", "printing " + url);
					}
				});
	}

	private void callWithTransformingMaps() {
		Observable.just("Chained with map transformation")
				.map(new Func1<String, Integer>() {
					@Override
					public Integer call(String s) {
						return s.hashCode();
					}
				})
				.map(new Func1<Integer, String>() {
					@Override
					public String call(Integer integer) {
						return integer.toString();
					}
				})
				.subscribe(s -> {
					Log.i("Eg:MA:27", "printing: " + s);
				});
	}

	Observable<List<String>> query(String text) {

		Observable.OnSubscribe<List<String>> onSubscribe = new Observable.OnSubscribe<List<String>>() {
			@Override
			public void call(Subscriber<? super List<String>> subscriber) {

				subscriber.onNext(getFakeQueryList(text));
				subscriber.onCompleted();
			}

			private ArrayList<String>  getFakeQueryList(String text) {
				ArrayList<String> list = new ArrayList<>();
				list.add("-1- " + text);
				list.add("-2- " + text);
				list.add("-3- " + text);
				return list;
			}
		};

		return  Observable.create(onSubscribe);

	}

	Observable<String> observableStandard = Observable.create(new Observable.OnSubscribe<String>() {
		@Override
		public void call(Subscriber<? super String> subscriber) {
			subscriber.onNext("observableStandard");
			subscriber.onCompleted();

		}
	});

	Observable<String> observableJust = Observable.just("observableJust");

	Subscriber<String> subscriber = new Subscriber<String>() {
		@Override
		public void onCompleted() {

		}

		@Override
		public void onError(Throwable e) {

		}

		@Override
		public void onNext(String string) {
			Log.v("Eg:MA", "subscriber.onNext | " + string);

		}
	};

	Action1<String> onNextAction = new Action1<String>() {
		@Override
		public void call(String string) {
			Log.d("Eg:MA:56", "onNextAction | " + string);
		}
	};



}
