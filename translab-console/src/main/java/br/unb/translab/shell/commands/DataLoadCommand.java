/**
 *     Copyright (C) 2015  the original author or authors.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License,
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
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
