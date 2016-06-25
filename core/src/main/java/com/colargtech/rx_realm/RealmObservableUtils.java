package com.colargtech.rx_realm;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;

/**
 * Utils class to use realm in a reactive way
 *
 * @author gdfesta
 */
public class RealmObservableUtils {
    public static <T> Observable<T> createObservableWithRealm(final ActionWithRealm<T> action, final RealmConfiguration configuration) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                Realm realm = Realm.getInstance(configuration);
                subscriber.onNext(action.call(realm));
                subscriber.onCompleted();
                realm.close();
            }
        });
    }

    public static <T extends RealmObject> Observable<T> createRealObjectObservableFromRealmResultsWithRealm(final ActionWithRealm<RealmResults<T>> action, final RealmConfiguration configuration) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                Realm realm = Realm.getInstance(configuration);
                RealmResults<T> realmResults = action.call(realm);
                for (T realmObject : realmResults) {
                    subscriber.onNext(realmObject);
                }
                subscriber.onCompleted();
                realm.close();
            }
        });
    }
}
