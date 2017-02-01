package dateparser.web;

import dateparser.GeneralParser;
import dateparser.GeneralParserResult;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.IOException;

/**
 * Created by nikolayivanov on 2/1/17.
 */

@Path("/tokenExtractor")
public class TokenExtractor {
    @POST
    @Path("/extract")
    public GeneralParserResult extract(NaturalLanguageInput nlInput) throws IOException {
        return GeneralParser.parse(nlInput.getText());
    }
}
