package jwf.debugport.internal.sqlite.commands;

import android.app.Application;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.regex.Pattern;

import jwf.debugport.annotations.Command;
import jwf.debugport.internal.Utils;
import jwf.debugport.internal.sqlite.SQLiteClientConnection;

/**
 * Created by jason on 5/9/16.
 */
@Command.Help(
        format = "SHOW DATABASES;",
        value = "Show all available databases for the app, including temporary databases.",
        group = Command.GROUP_SQL_INSPECTION
)
public class ShowDatabasesCommand extends SQLiteCommand {
    private static final Pattern command = Pattern.compile("show\\s+databases", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    @Override
    public boolean isCommandString(String candidate) {
        return command.matcher(candidate).matches();
    }

    @Override
    public void execute(SQLiteClientConnection connection, PrintWriter out, String commandString) {
        Application app = connection.getApp();
        String[] databaseList = app.databaseList();
        Arrays.sort(databaseList);
        Object[][] data = new Object[databaseList.length][1];
        for (int i = 0; i < databaseList.length; i++) {
            data[i][0] = databaseList[i];
        }
        Utils.printTable(out, new String[] { "Database" }, data);
    }
}
