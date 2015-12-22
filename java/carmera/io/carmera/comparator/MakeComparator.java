package carmera.io.carmera.comparator;

import java.util.Comparator;

import carmera.io.carmera.models.queries.MakeQuery;

/**
 * Created by bski on 12/22/15.
 */
public class MakeComparator implements Comparator<MakeQuery> {
    @Override
    public int compare (MakeQuery m0, MakeQuery m1) {
        return m0.getMake().compareTo(m1.getMake());
    }
}
