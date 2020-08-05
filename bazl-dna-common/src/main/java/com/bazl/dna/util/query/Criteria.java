package com.bazl.dna.util.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.compress.utils.Lists;
import org.springframework.data.jpa.domain.Specification;

/***
 * <p>Description: 定义一个查询条件容器 </p>
 * @author lrm
 * @date 2018年9月27日
 */
public class Criteria<T> implements Specification<T>{  
	
	private static final long serialVersionUID = 1L;
	private List<Criterion> criterions = Lists.newArrayList();
  
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,  
            CriteriaBuilder builder) {  
        if (!criterions.isEmpty()) {  
            List<Predicate> predicates = Lists.newArrayList();
            for(Criterion c : criterions){  
                predicates.add(c.toPredicate(root, query,builder));  
            }
            query.distinct(true);
            // 将所有条件用 and 联合起来  
            if (!predicates.isEmpty()) {  
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));  
            }
        }  
        return builder.conjunction();  
    }  
   
    /***
     * 增加简单条件表达式 
     * @param criterion
     */
    public void add(Criterion criterion){  
        if(criterion!=null){  
            criterions.add(criterion);  
        }  
    }  
}
