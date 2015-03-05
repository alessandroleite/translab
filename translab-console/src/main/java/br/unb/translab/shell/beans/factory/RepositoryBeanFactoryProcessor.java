package br.unb.translab.shell.beans.factory;

import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

import br.unb.translab.core.components.Repository;

public class RepositoryBeanFactoryProcessor implements BeanFactoryPostProcessor
{
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .addUrls(ClasspathHelper.forPackage("br.unb.translab"))
                .addScanners(new TypeElementsScanner(), new TypeAnnotationsScanner()));

        final Set<Class<?>> repositoryTypes = reflections.getTypesAnnotatedWith(Repository.class);

        for (Class<?> type : repositoryTypes)
        {
            AbstractBeanDefinition bean = BeanDefinitionBuilder.rootBeanDefinition(JdbiRepositoryFactoryBean.class)
                    .setScope(BeanDefinition.SCOPE_SINGLETON)
                    .addDependsOn("datasource")
                    .addConstructorArgReference("dbi")
                    .addConstructorArgValue(type)
                    .getBeanDefinition();

            final String beanName = type.getCanonicalName().substring(0, 1).toLowerCase().concat(type.getCanonicalName().substring(1));
            beanFactory.registerSingleton(beanName, bean);
        }
    }
}
