package br.unb.translab.shell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import static com.google.common.base.Strings.*;
import static com.google.common.base.Preconditions.*;

@Component
public class DataLoadCommand implements CommandMarker
{
    @Autowired
    private ApplicationContext context;

    @CliCommand(help = "Load all the supporting data, which includes: ", value = {})
    public String loadAllSupportingData(@CliOption(help = "Absolute path to the directory with the files", key = { "wd", "working-dir" }) String path)
    {
        if (!isNullOrEmpty(path))
        {
            
        }
        
        return null;
    }
}
