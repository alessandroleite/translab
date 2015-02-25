package br.unb.translab.core.domain.repository;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import br.unb.translab.core.domain.JustificationType;
import static org.assertj.core.api.Assertions.*;

public class JustificationRepositoryTest extends RepositoryTestSupport
{
    private JustificationRepository repository;

    @Before
    @Override
    public void setUp()
    {
        super.setUp();
        this.repository = openRepository(JustificationRepository.class);
    }
    
    
    @Test
    public void insertOneJustification()
    {
        JustificationType jt = new JustificationType().setAcronym("TP").setDescription("Technical Problems");
        Integer id = this.repository.insert(jt);
        
        assertThat(id).isNotNull();
        assertThat(id > 0).isTrue();
        
        jt.setId(id);
        
        Optional<JustificationType> jt2 = this.repository.findByAcronym("TP");
        
        assertThat(jt2.isPresent()).isTrue();
        assertThat(jt.getId()).isEqualTo(jt2.get().getId());
        assertThat(jt.getAcronym()).isEqualTo(jt2.get().getAcronym());
        assertThat(jt.getDescription()).isEqualTo(jt2.get().getDescription());
        
        List<JustificationType> all = this.repository.listAll();
        
        assertThat(all.size() == 1).isTrue();
        assertThat(all.get(0)).isEqualTo(jt2.get());
    }
}
