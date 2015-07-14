package carmera.io.carmera.utils;


import android.app.Application;
import android.util.Log;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.ObjectPersister;
import com.octo.android.robospice.persistence.ObjectPersisterFactory;
import com.octo.android.robospice.persistence.Persister;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.file.InFileObjectPersister;
import com.octo.android.robospice.persistence.memory.LruCacheStringObjectPersister;
import com.octo.android.robospice.persistence.springandroid.json.gson.GsonObjectPersister;
import com.octo.android.robospice.persistence.springandroid.json.jackson.JacksonObjectPersister;
import com.octo.android.robospice.persistence.springandroid.json.jackson.JacksonObjectPersisterFactory;

import java.util.ArrayList;
import java.util.List;

import carmera.io.carmera.models.GenerationData;
import carmera.io.carmera.models.Predictions;


public class InMemorySpiceService extends SpiceService {

    @Override
    public CacheManager createCacheManager(Application application) {
        CacheManager manager = new CacheManager();
        try {
            JacksonObjectPersister genDataPersister = new JacksonObjectPersister(application, GenerationData.class);
            GsonObjectPersister predictionPersister = new GsonObjectPersister(application, Predictions.class);
            manager.addPersister(genDataPersister);
            manager.addPersister(predictionPersister);
        } catch (CacheCreationException e) {
            Log.i("Create cache manager: ", e.getMessage());
        }
        LruCacheStringObjectPersister memoryPersister = new LruCacheStringObjectPersister(1024 * 1024);
        manager.addPersister(memoryPersister);
        return manager;
    }
}