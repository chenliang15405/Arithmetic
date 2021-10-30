/**
 * @author tangsong
 * @date 2021/2/6 20:25
 */
public class DCLSingleleton {

    private static volatile DCLSingleleton instance = null;

    private DCLSingleleton() {}

    public static DCLSingleleton getInstance() {
        if(instance == null) {
            synchronized (DCLSingleleton.class) {
                if(instance == null) {
                    instance = new DCLSingleleton();
                }
            }
        }
        return instance;
    }

}
