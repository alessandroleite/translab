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
package br.unb.translab.core.domain.repository;

import io.dohko.jdbi.BigIntegerArgumentFactory;
import io.dohko.jdbi.JodaTimeArgumentFactory;
import io.dohko.jdbi.OptionalArgumentFactory;
import io.dohko.jdbi.OptionalContainerFactory;
import io.dohko.jdbi.args.JodaDateTimeMapper;
import io.dohko.jdbi.util.BigIntegerMapper;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.After;
import org.junit.Before;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

public class RepositoryTestSupport
{
    protected static final transient Logger LOGGER = LoggerFactory.getLogger(RepositoryTestSupport.class.getName());
    
    protected JdbcConnectionPool h2Config;
    protected DBI dbi;

    {
        h2Config = JdbcConnectionPool.create("jdbc:h2:mem:" + UUID.randomUUID(), "sa", "sa");
        dbi = new DBI(h2Config);

        dbi.registerArgumentFactory(new JodaTimeArgumentFactory());
        dbi.registerArgumentFactory(new OptionalArgumentFactory());
        dbi.registerArgumentFactory(new BigIntegerArgumentFactory());

        dbi.registerContainerFactory(new OptionalContainerFactory());
        dbi.registerMapper(new JodaDateTimeMapper());
        dbi.registerMapper(new BigIntegerMapper());
    }

    @Before
    public void setUp() throws Exception
    {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .addUrls(ClasspathHelper.forPackage("br.unb.translab"))
                    .addScanners(new ResourcesScanner()));

        final List<String> scripts = Lists.newArrayList(reflections.getResources(new Predicate<String>()
        {
            @Override
            public boolean apply(@Nullable String input)
            {
                return input != null && input.endsWith(".sql");
            }
        }));
        
        Collections.sort(scripts);
        
        try(Handle handle = dbi.open())
        {
            for (String script: scripts)
            {
                LOGGER.debug("Executing the database script [{}]", script);
                handle.execute(script);
            }
        }
    }
    
    public <T> T openRepository(Class<T> klass)
    {
        if (dbi != null)
        {
            return dbi.open(klass);
        }
        return null;
    }

    @After
    public void tearDown() throws Exception
    {
        if (dbi != null)
        {
            this.h2Config.dispose();
        }
        
        dbi = null;
    }
}
