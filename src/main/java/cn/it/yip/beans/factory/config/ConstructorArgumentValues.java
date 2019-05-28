package cn.it.yip.beans.factory.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 构造参数值类
 *
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-22 17:05
 **/
public class ConstructorArgumentValues {
    private List<ConstructorArgumentValues.ValueHolder> genericArgumentValues = new ArrayList<>();
    /**
     * 添加参数
     *
     * @param value
     */
    public void addArgumentValue(Object value) {
        this.genericArgumentValues.add(new ValueHolder(value));
    }
    /**
     * 添加参数
     *
     * @param value
     */
    public void addArgumentValue(ValueHolder valueHolder) {
        this.genericArgumentValues.add(valueHolder);
    }
    /**
     * 获取该类的所有构造参数
     *
     * @return
     */
    public List<ConstructorArgumentValues.ValueHolder> getArgumentValues() {
        return Collections.unmodifiableList(this.genericArgumentValues);
    }


    public int getSize() {
        return this.genericArgumentValues.size();
    }

    public boolean isEmpty() {
        return this.genericArgumentValues.isEmpty();

    }

    public void clear() {
        this.genericArgumentValues.clear();
    }

    /**
     * 封装参数类型
     */
    public static class ValueHolder {
        private Object value;
        private String name;
        private String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ValueHolder(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
