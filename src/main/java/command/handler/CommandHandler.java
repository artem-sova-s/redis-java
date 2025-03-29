package command.handler;

import java.io.IOException;

// CommandContext import
import command.CommandContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for implementing commands in a Redis-like server.
 * <p>
 * This class follows the Pipeline (or Filter Chain) pattern, where each handler:
 * - Processes the input command (e.g., validation, execution, logging, etc.)
 * - Optionally passes the context to the next handler in the chain
 * <p>
 * To implement a specific command or behavior, extend this class and override
 * the {@code process(CommandContext ctx)} method.
 * <p>
 * This design allows for clean separation of concerns, improved extensibility,
 * and easy composition of complex command-processing flows.
 */
public abstract class CommandHandler {
    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    protected CommandHandler next;

    public CommandHandler(final CommandHandler next) {
        this.next = next;
    }

    public CommandHandler() {
        this.next = null;
    }

    public void handle(final CommandContext context) {
        try {
            if (this.canHandle(context.getCommand())) {
                process(context);
                return;
            }

            // if no next command, then exit
            if (next == null) {
                return;
            }

            this.next.handle(context);

        } catch (IOException e) {
            log.error("Failed to write to the output stream" + e.getMessage());
        }
    }

    protected abstract boolean canHandle(String command);

    protected abstract void process(final CommandContext context) throws IOException;
}
