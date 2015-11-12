package carmera.io.carmera.predicates;

import android.support.annotation.Nullable;

import org.parceler.guava.base.Predicate;


/**
 * Created by bski on 11/10/15.
 */
public class RatingsPredicate implements Predicate<String> {
    @Override
    public boolean apply(@Nullable String s) {
        return s.contains("comfort") || s.contains("interior") || s.contains("exterior") ||
                s.contains("build") || s.contains("reliabi") || s.contains("fun");
    }

    @Override
    public boolean equals (Object o) {
        return false;
    }

}
