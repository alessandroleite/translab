package br.unb.translab.shell.beans.factory;

import io.dohko.jdbi.JodaTimeArgumentFactory;
import io.dohko.jdbi.OptionalArgumentFactory;
import io.dohko.jdbi.OptionalContainerFactory;
import io.dohko.jdbi.args.JodaDateTimeMapper;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.spring.DBIFactoryBean;

public class DBIFactoryBean2 extends DBIFactoryBean
{
    @Override
    public Object getObject() throws Exception
    {
        final DBI dbi = DBI.class.cast(super.getObject());

        dbi.registerArgumentFactory(new JodaTimeArgumentFactory());
        dbi.registerArgumentFactory(new OptionalArgumentFactory());
        dbi.registerContainerFactory(new OptionalContainerFactory());

        dbi.registerMapper(new JodaDateTimeMapper());

        return dbi;
    }
}
