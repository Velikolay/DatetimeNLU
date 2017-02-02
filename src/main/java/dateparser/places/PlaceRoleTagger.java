package dateparser.places;

import org.chasen.crfpp.Tagger;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

/**
 * Created by nikolayivanov on 2/1/17.
 */
public class PlaceRoleTagger {

    private static final String MODEL_FILE = System.getProperty("user.dir") + "/src/main/resources/train/model";
    private Tagger tagger;

    static {
        try {
            System.load(System.getProperty("user.dir") + "/lib/libCRFPP.so");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Cannot load the libCRFPP native code\n" + e);
        }
    }

    public PlaceRoleTagger() {
        this.tagger = new Tagger("-m " + MODEL_FILE + " -v 3 -n2");
    }

    synchronized public List<String> tagTokens(List<String> tokens) {
        List<String> tags = new ArrayList<>();
        for (String token: tokens) {
            tagger.add(token);
        }
        if (tagger.parse()) {
            for (int i = 0; i < tagger.size(); ++i) {
                for (int j = 0; j < tagger.xsize(); ++j) {
                    System.out.print(tagger.x(i, j) + "\t");
                }
                tags.add(tagger.y2(i));
                System.out.print(tagger.y2(i) + "\t");
                System.out.print("\n");
            }
        }

        return tags;
    }

    public static void main(String[] args) throws IOException {
        PlaceRoleTagger roleTagger = new PlaceRoleTagger();
        List<String> tokens = new ArrayList<String>();
        tokens.add("en:place");
        tokens.add("to");
        tokens.add("en:place");
        System.out.println(roleTagger.tagTokens(tokens));
    }
}
