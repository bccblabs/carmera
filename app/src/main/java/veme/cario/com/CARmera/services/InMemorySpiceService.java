package veme.cario.com.CARmera.services;

import android.app.Application;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.memory.LruCacheStringObjectPersister;

/**
 * Created by bski on 11/25/14.
 */
public class InMemorySpiceService extends SpiceService {

    private static final int CACHE_SZ = 4096*4096;

    @Override
    public CacheManager createCacheManager(Application application) {
        CacheManager manager = new CacheManager();
        LruCacheStringObjectPersister memoryPersister = new LruCacheStringObjectPersister(CACHE_SZ);
        manager.addPersister(memoryPersister);
        return manager;
    }

}
