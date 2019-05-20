package org.atomspace.learning.petspringangular.repository;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.List;

/**
 * Created by sergey.derevyanko on 19.05.2019.
 */
@NoRepositoryBean
public class ExtendedRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements ExtendedRepository<T, ID>{

    EntityManager entityManager;

    public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<T> findByAttributeContainsText(String attributeName, String text) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery(getDomainClass());
        Root<T> root = criteriaQuery.from(getDomainClass());
        criteriaQuery.select(root).where(builder.like(root.get(attributeName), "%"+text+"%"));
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    @Transactional
    public T updateWith(T with, ID id) {
        T persisted = entityManager.find(getDomainClass(), id);
        if(persisted != null ){
            BeanWrapper sourceBean = new BeanWrapperImpl(with);
            BeanWrapper destBean = new BeanWrapperImpl(persisted);
            PropertyDescriptor[] propertyDescriptors = sourceBean.getPropertyDescriptors();
            for(PropertyDescriptor propertyDescriptor: propertyDescriptors){
                Object pv = sourceBean.getPropertyValue(propertyDescriptor.getName());
                if(pv != null && destBean.isWritableProperty(propertyDescriptor.getName())){
                    destBean.setPropertyValue(propertyDescriptor.getName(), pv);
                }
            }
            entityManager.persist(persisted);
        }
        return persisted;
    }
}
