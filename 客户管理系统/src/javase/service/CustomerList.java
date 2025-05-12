package javase.service;

import javase.domain.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerList {

    /*用于保存客户对象的集合*/
    private List<Customer> customers;

    /**
     * 用来初始化customer集合的构造器
     */
    public CustomerList() {
        customers = new ArrayList();
    }

    /**
     * 添加客户添加到集合中
     *
     * @param customer 客户对象
     */
    public boolean addCustomer(Customer customer) {
        customers.add(customer);
        return true;
    }

    /**
     * 删除指定索引位置上的客户
     *
     * @param index 指定的索引下标
     * @return：删除成功 false：删除失败
     */
    public boolean deleteCustomer(int index) {
        if (index < 0 || index >= customers.size()) {
            return false;
        }
        customers.remove(index);
        return true;
    }

    /**
     * 修改指定索引位置的信息
     *
     * @param index 指定的下标索引
     * @param cust  客户对象
     * @return true：修改成功 false：修改失败
     */
    public boolean replaceCustomer(int index, Customer cust) {
        if (index < 0 || index >= customers.size()) {
            return false;
        }
        customers.set(index, cust);
        return true;
    }

    /**
     * 获取客户所有信息
     *
     * @return 所有用户信息
     */
    public List<Customer> getAllCustomer() {
        return customers;
    }

    /**
     * 获取指定索引位置上的客户
     *
     * @param index 指定下标
     * @return 返回找到该元素 则返回 null
     */
    public Customer getCustomer(int index) {
        if (index < 0 || index >= customers.size()) {
            return null;
        }
        return customers.get(index);
    }

    /**
     * 获取存储的客户的数量
     *
     * @return 客户数量
     */
    public int getsize() {
        return customers.size();
    }

    /**
     * 模糊查询
     * @param text
     */
    public List<Customer> fuzzyQuery(List<Customer> listFQ,String text){
        if (text.length() >= 1){
            String str = ".*" + text + ".*";
            for (int i = 0; i < customers.size(); i++) {
                boolean matches = customers.get(i).getName().matches(str);
                if (matches){
                    listFQ.add(customers.get(i));
                }
            }
        }
        return listFQ;
    }
}
