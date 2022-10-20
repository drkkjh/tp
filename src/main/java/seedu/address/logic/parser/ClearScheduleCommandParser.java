package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_OF_SCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEKDAY;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.schedule.ClearScheduleCommand;
import seedu.address.logic.commands.schedule.ViewScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.schedule.ScheduleContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new ClearScheduleCommand object
 */
public class ClearScheduleCommandParser implements Parser<ClearScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearScheduleCommand
     * and returns a ClearScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearScheduleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new ClearScheduleCommand();
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_OF_SCHEDULE);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ClearScheduleCommand.MESSAGE_USAGE));
        }

        ArrayList<ModuleCode> modulesToClear = new ArrayList<>();
        Set<String> modulesList = ParserUtil.parseModules(argMultimap.getAllValues(PREFIX_MODULE_OF_SCHEDULE));
        for(String moduleCode : modulesList) {
            modulesToClear.add(new ModuleCode(moduleCode));
        }

        return new ClearScheduleCommand(modulesToClear);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }



}
