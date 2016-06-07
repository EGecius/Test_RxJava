package lt.gecius.test_rxjava;


import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func2;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class Activity2Presenter {

	CompositeSubscription compositeSubscription = new CompositeSubscription();

	public void onCreate() {
	}

	private void doDelaySameInterval() {
		Log.v("Eg:Activity2Presenter:29", "doDelaySameInterval");

		Observable.range(1, 4)
				.delay(3, TimeUnit.SECONDS)
				.subscribe(new Subscriber<Integer>() {
					@Override
					public void onCompleted() {
						Log.d("Eg:Activity2Presenter:34", "onCompleted ");
					}

					@Override
					public void onError(Throwable e) {
						Log.e("Eg:Activity2Presenter:39", "onError ");
					}

					@Override
					public void onNext(Integer integer) {
						Log.i("Eg:Activity2Presenter:44", "onNext integer " + integer);					}
				});
	}

	private void doCombineLatest() {

		EditText editText1 = null;
		EditText editText2 = null;


		Observable<CharSequence> textViewObservable1 = RxTextView.textChanges(editText1);
		Observable<CharSequence> textViewObservable2 = RxTextView.textChanges(editText2);


		Observable.combineLatest(textViewObservable1, textViewObservable2, new Func2<CharSequence, CharSequence, Object>() {
			@Override
			public Object call(CharSequence charSequence, CharSequence charSequence2) {
				return null;
			}
		}).subscribe();
	}


	private void doPublishSubject() {
		PublishSubject<String> subject = PublishSubject.create();

		Subscription subscription = subject.subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {
				Log.d("Eg:Activity2Presenter:27", "onCompleted ");
			}

			@Override
			public void onError(Throwable e) {
				Log.e("Eg:Activity2Presenter:32", "onError ");
			}

			@Override
			public void onNext(String string) {
				Log.i("Eg:Activity2Presenter:37", "onNext string " + string);
			}
		});

		compositeSubscription.add(subscription);


		Log.v("Eg:Activity2Presenter:42", "doPublishSubject about to call onNext");

		subject.onNext("Vienas");
		subject.onNext("Du");

		Log.v("Eg:Activity2Presenter:47", "doPublishSubject about to call onComlete");

		subject.onCompleted();

		Log.v("Eg:Activity2Presenter:51", "doPublishSubject about to call onNextAgain");

		subject.onNext("Trys");

		compositeSubscription.unsubscribe();

	}

	private void doError() {
		Log.v("Eg:Activity2Presenter:21", "doError");

		Observable.error(new Exception("Egis's error"))
				.doOnEach(new Action1<Notification<? super Object>>() {
					@Override
					public void call(Notification<? super Object> notification) {
						Object value = notification.getValue();
						Log.i("Eg:Activity2Presenter:28", "call value " + value);
					}
				})
				.subscribe(new Subscriber<Object>() {
					@Override
					public void onCompleted() {
						Log.d("Eg:Activity2Presenter:25", "onCompleted ");
					}

					@Override
					public void onError(Throwable e) {
						Log.e("Eg:Activity2Presenter:30", "onError ");
					}

					@Override
					public void onNext(Object o) {
						Log.v("Eg:Activity2Presenter:35", "onNext object " + o);
					}
				});
	}

	private void doTimer() {
		Log.v("Eg:Activity2Presenter:21", "doTimer");

		Observable<Long> timer = Observable.timer(3, TimeUnit.SECONDS);
		timer.subscribe(new Subscriber<Long>() {
			@Override
			public void onCompleted() {
				Log.v("Eg:Activity2Presenter:25", "onCompleted ");
			}

			@Override
			public void onError(Throwable e) {
				Log.e("Eg:Activity2Presenter:30", "onError ");
			}

			@Override
			public void onNext(Long aLong) {
				Log.v("Eg:Activity2Presenter:35", "onNext aLong " + aLong);
			}
		});
	}

	private void doZip() {
		Log.v("Eg:Activity2Presenter:21", "doZip");

		String[] strings = new String[] {"Vienas", "Du", "Trys", "Keturi"};
		Integer[] ints = new Integer[] {1, 2, 3};
		Observable<String> fromStrings = Observable.from(strings);
		Observable<Integer> fromInts = Observable.from(ints);

		Observable.zip(fromStrings, fromInts, new Func2<String, Integer, String>() {
			@Override
			public String call(String s, Integer integer) {
				String s1 = s + " " + integer;
//				Log.i("Eg:Activity2Presenter:31", "call s1 " + s1);

				return s1;
			}
		}).take(2)
				.subscribe(new Subscriber<String>() {
					@Override
					public void onCompleted() {
						Log.d("Eg:Activity2Presenter:38", "onCompleted ");
					}

					@Override
					public void onError(Throwable e) {
						Log.e("Eg:Activity2Presenter:43", "onError ");
					}

					@Override
					public void onNext(String s) {
						Log.i("Eg:Activity2Presenter:48", "onNext s " + s);
					}
				});


	}


	private void doMerge() {
		Observable<Integer> just0 = Observable.just(0);

		Observable<Long> intervalObs = Observable.interval(1, TimeUnit.SECONDS);
		Observable<Long> justLong = Observable.just( (long) -13);


		Observable<Integer> rangeObservable = Observable.range(5, 100).doOnNext(integer -> Log.v
				("Eg:Activity2Presenter:24",
				"in onNext integer " + integer));

//		rangeObservable.subscribe(new Action1<Integer>() {
//			@Override
//			public void call(Integer integer) {
//				Log.i("Eg:Activity2Presenter:27", "call integer " + integer);
//			}
//		});

		Observable.merge(intervalObs, justLong).subscribe(new Subscriber<Long>() {
			@Override
			public void onCompleted() {
				Log.d("Eg:Activity2Presenter:23", "onCompleted ");

			}

			@Override
			public void onError(Throwable e) {
				Log.e("Eg:Activity2Presenter:29", "onError ");
			}

			@Override
			public void onNext(Long integer) {
				Log.i("Eg:Activity2Presenter:34", "onNext integer " + integer);
			}
		});
	}

	private void doRangeAndSkip() {
		Observable.range(1, 4)
				.doOnEach(new Action1<Notification<? super Integer>>() {
					@Override
					public void call(Notification<? super Integer> notification) {
						Object value = notification.getValue();
						Log.i("Eg:Activity2Presenter:27", "in doOnEach value = " + value);

					}
				})
				.skip(2)
				.subscribe(new Subscriber<Integer>() {
					@Override
					public void onCompleted() {
						Log.v("Eg:Activity2Presenter:17", "onCompleted ");
					}

					@Override
					public void onError(Throwable e) {
						Log.i("Eg:Activity2Presenter:24", "onError e " + e);
					}

					@Override
					public void onNext(Integer integer) {
						Log.i("Eg:Activity2Presenter:25", "onNext integer " + integer);
					}
				});
	}

}
