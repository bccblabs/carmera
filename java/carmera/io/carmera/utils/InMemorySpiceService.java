package carmera.io.carmera.utils;


import android.app.Application;
import android.util.Log;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.memory.LruCacheStringObjectPersister;
import com.octo.android.robospice.persistence.springandroid.json.jackson.JacksonObjectPersister;

import carmera.io.carmera.models.Listings;
import carmera.io.carmera.models.StyleData;


public class InMemorySpiceService extends SpiceService {

    @Override
    public CacheManager createCacheManager(Application application) {
        CacheManager manager = new CacheManager();
        try {
            JacksonObjectPersister genDataPersister = new JacksonObjectPersister(application, StyleData.class);
            JacksonObjectPersister listingDataPersister = new JacksonObjectPersister(application, Listings.class);
            manager.addPersister(genDataPersister);
            manager.addPersister(listingDataPersister);
        } catch (CacheCreationException e) {
            Log.i("Create cache manager: ", e.getMessage());
        }
        LruCacheStringObjectPersister memoryPersister = new LruCacheStringObjectPersister(128 * 128);
        manager.addPersister(memoryPersister);
        return manager;
    }
}
