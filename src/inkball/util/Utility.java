package inkball.util;

import java.util.List;
import java.util.Random;

public class Utility {
    public static <T> T getRandomElement(List<T> list) {
        if (list.size() == 0) return null;

        Random r = new Random();
        int randomIdx = r.nextInt(list.size());
        return list.get(randomIdx);
    }
}
