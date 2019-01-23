package rnk.bb.rest.blank;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import rnk.bb.domain.blank.AbstractEntity;
import rnk.bb.domain.hotel.resource.Hotel;
import rnk.bb.domain.hotel.resource.Room;
import rnk.bb.util.json.JsonHelper;

import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.bind.JAXBException;
import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class AbstractController<T extends AbstractEntity, ID>  extends LazyDataModel<T> {
    protected abstract EntityManager entityManager();

    Map<String,Object> filter;

    private Class<T> entityClass() {
        @SuppressWarnings("unchecked")
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    private Class<ID> keyClass() {
        @SuppressWarnings("unchecked")
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<ID>) parameterizedType.getActualTypeArguments()[1];
    }

    public void init(Map<String, Object> filter){
        this.filter=filter;
        this.setRowCount(getEntitiesCount());
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

    public T findByLongId(Long id) {
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


    //LazyLoadModel implementation
    private CriteriaQuery<T> addPredicates(CriteriaBuilder cb){
        CriteriaQuery<T> q = cb.createQuery(entityClass());
        Root<T> root = q.from(entityClass());

        Iterator it=filter.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair=(Map.Entry)it.next();
            String field=(String)pair.getKey();
            Object value=pair.getValue();
            Predicate equal=cb.equal(root.get(field),value);
            q.where(equal);
        }

        return q;
    }

    private CriteriaQuery<Long> addLongPredicates(CriteriaBuilder cb){
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<T> root = q.from(entityClass());

        Iterator it=filter.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair=(Map.Entry)it.next();
            String field=(String)pair.getKey();
            Object value=pair.getValue();
            Predicate equal=cb.equal(root.get(field),value);
            q.where(equal);
        }

        return q;
    }

    public Integer getEntitiesCount(){
        CriteriaBuilder cb = this.entityManager().getCriteriaBuilder();
        CriteriaQuery<Long> q=addLongPredicates(cb);
        q.select(cb.count(q.getRoots().iterator().next()));

        Integer count=entityManager().createQuery(q).getSingleResult().intValue();
        return count;
    }

    private List<T> load(int first, int pageSize){
        CriteriaBuilder cb = this.entityManager().getCriteriaBuilder();
        CriteriaQuery<T> q=addPredicates(cb);

        return entityManager()
                .createQuery(q)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return load(first,pageSize);
    }

    public List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        return load(first,pageSize);
    }

    public T getRowData(String rowKey) {
        if (keyClass().equals(Long.class)) {
            return findByLongId(Long.valueOf(rowKey));
        }else{
            throw new UnsupportedOperationException("getRowData(String rowKey) must be implemented by %s when basic rowKey algorithm is not used [component=%s,view=%s].");
        }

    }

    public Object getRowKey(T object) {
        return object.getId();
    }



}
