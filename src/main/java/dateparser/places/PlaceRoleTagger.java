package dateparser.places;

import org.chasen.crfpp.Model;
import org.chasen.crfpp.Tagger;

import java.io.IOException;

/**
 * Created by nikolayivanov on 2/1/17.
 */
public class PlaceRoleTagger {

    private static final String MODEL_FILE = "src/main/resources/train/model";
    /**
     * Train using the sequences in a file.
     *
     * @param args
     *			Training file, model file.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        try {
            System.out.println(System.getProperty("java.library.path"));
            System.out.println(System.getenv("LD_LIBRARY_PATH"));
            System.loadLibrary("CRFPP");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Cannot load the example native code.\nMake sure your LD_LIBRARY_PATH contains \'.\'\n" + e);
            System.exit(1);
        }
        Model placeRoleModel = new Model(MODEL_FILE);
        Tagger placeRoleTagger = placeRoleModel.createTagger();
        System.out.println(placeRoleTagger.parse("en:place to en:place"));
    }
}
