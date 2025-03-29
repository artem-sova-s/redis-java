package command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;

// class is dedicad for parsing the input stream
// validation of the command correctness
// returns CommandContext
public class CommandParser {
    private static final Logger log = LoggerFactory.getLogger(CommandParser.class);

    public CommandContext parse(final String clientMessage, final OutputStream outputStream) throws IOException {
        try {

            if (clientMessage == null || clientMessage.trim().isEmpty()) {
                return new CommandContext(outputStream);
            }

            log.info("Parsing command: " + clientMessage);

            String[] tokens = clientMessage.trim().split("\\s+");
            String command = tokens[0];
            List<String> args = Arrays.asList(tokens).subList(1, tokens.length);

            // creation of the CommandContext object with parsed data
            return new CommandContext(command, outputStream, args);
        } catch (Exception e) {
            log.error("Error reading command line", e);
            throw e;
        }
    }
}
