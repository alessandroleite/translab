package br.unb.translab.shell.beans.factory;

import static java.util.Objects.requireNonNull;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.FactoryBean;

public class JdbiRepositoryFactoryBean<T> implements FactoryBean<T>
{
    private final Class<T> _repositoryClass;
    
    private final org.skife.jdbi.v2.DBI _dbi;
    
    public JdbiRepositoryFactoryBean(@Nonnull org.skife.jdbi.v2.DBI dbi, @Nonnull Class<T> repositoryClazz)
    {
        this._dbi = requireNonNull(dbi);
        this._repositoryClass = requireNonNull(repositoryClazz);
    }

    @Override
    public T getObject() throws Exception
    {
        return _dbi.open(_repositoryClass);
    }

    @Override
    public Class<?> getObjectType()
    {
        return _repositoryClass;
    }

    @Override
    public boolean isSingleton()
    {
        return true;
    }
}
