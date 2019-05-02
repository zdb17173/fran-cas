package org.fran.microservice.cas.authserver.dao.po;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fran on 2016/7/10.
 */
public class QueryCriteria {

    private List<Criteria> criteria = new ArrayList<>();
    private String group;
    private String limit;
    private String order;

    public void addCriterion(Criteria criterion){
        this.criteria.add(criterion);
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public List<Criteria> getCriteria() {
        return criteria;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public static class Criteria {
        private String condition;
        private Object value;
        private boolean listValue;
        private boolean hasValue;

        public Criteria(String condition, Object value) {
            this.condition = condition;
            this.value = value;
        }

        public Criteria setHasValue(boolean hasValue) {
            this.hasValue = hasValue;
            return this;
        }

        public boolean isHasValue() {
            return hasValue;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public boolean isListValue() {
            return listValue;
        }

        public Criteria setListValue(boolean listValue) {
            this.listValue = listValue;
            return this;
        }
    }
}
