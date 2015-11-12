package carmera.io.carmera.predicates;

import android.support.annotation.Nullable;

import org.parceler.guava.base.Predicate;


/**
 * Created by bski on 11/10/15.
 */
public class CostsPredicate implements Predicate<String> {
    @Override
    public boolean apply(@Nullable String s) {
        return s.contains("main") || s.contains("insur") || s.contains("repairs") ||
                s.contains("depre") || s.contains("maint");
    }
    @Override
    public boolean equals(Object o) {
        return false;
    }
}
