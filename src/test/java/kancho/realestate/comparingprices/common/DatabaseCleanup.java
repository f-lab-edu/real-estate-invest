package kancho.realestate.comparingprices.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.CaseFormat;

/**
 * 통합테스트, 인수테스트를 진행할때는 서버와 클라이언트의 스레드가 분리되어 @Transactional로 데이터 격리성을 제어할 수 없다.
 * DatabaseCleanup을 이용하여 매 테스트 마다 데이터를 삭제해주고 auto increment로 증가시킨 시퀀스를 다시 1로 변경한다.
 */

@Service
@ActiveProfiles("test")
public class DatabaseCleanup implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
            .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
            .map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()))
            .collect(Collectors.toList());
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            initAutoIncrementIdx(tableName);
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private void initAutoIncrementIdx(String tableName) {
        entityManager.createNativeQuery(
            "ALTER TABLE " + tableName + " ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }
}
