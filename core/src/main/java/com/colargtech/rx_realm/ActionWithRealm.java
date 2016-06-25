package com.colargtech.rx_realm;

import io.realm.Realm;

/**
 * @author gdfesta
 */
public interface ActionWithRealm<T> {
    T call(Realm realm);
}
