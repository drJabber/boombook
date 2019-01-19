package rnk.bb.rest.blank;

import rnk.bb.domain.blank.AbstractEntity;
import rnk.bb.util.json.JsonHelper;

import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.xml.bind.JAXBException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class AbstractController<T extends AbstractEntity, ID> {
    protected abstract EntityManager entityManager();

    private Class<T> entityClass() {
        @SuppressWarnings("unchecked")
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    public List<T> findAll() {

        CriteriaBuilder cb = this.entityManager().getCriteriaBuilder();

        CriteriaQuery<T> q = cb.createQuery(entityClass());
        Root<T> c = q.from(entityClass());

        return entityManager().createQuery(q).getResultList();
    }

    public T unmarshal(JsonObject object) throws JAXBException {
        return JsonHelper.unmarshal(object, entityClass());
    }

    public String marshal(Object object){
        return JsonHelper.marshal(object);
    }

    public T save(T entity) {
        if (entity.getId() == null) {
            entityManager().persist(entity);

            return entity;
        } else {
            return entityManager().merge(entity);
        }
    }

    public T findById(ID id) {
        return entityManager().find(entityClass(), id);
    }

    public void delete(T entity) {
        T _entity = entityManager().merge(entity);
        entityManager().remove(_entity);
    }

    public void deleteById(ID id) {
        T _entity = findById(id);
        entityManager().remove(_entity);
    }

    public Stream<T> stream() {
        CriteriaBuilder cb = this.entityManager().getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(entityClass());
        Root<T> c = q.from(entityClass());

        return entityManager().createQuery(q).getResultStream();
    }

    public Optional<T> findOptionalById(ID id) {
        return Optional.ofNullable(findById(id));
    }

}
