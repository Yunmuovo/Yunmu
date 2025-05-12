package javase.view;

import javase.domain.Admin;
import javase.domain.Customer;
import javase.service.CustomerList;
import javase.util.CMUtility;

import java.util.ArrayList;
import java.util.List;

public class CustomerView {

    private CustomerList customerList = new CustomerList();
    private Admin admin;

    public CustomerView() {
        Customer cust = new Customer("张三", '男', 30, "010-56253825", "abc@email.com");
        customerList.addCustomer(cust);
    }


    /**
     * 开始界面
     */
    public void enterMainMenu() {

        boolean isFalg = false;
        while (!isFalg) {
            System.out.println("\n-----------------客户信息管理软件登录界面-----------------\n");
            if (!loginAdmin()) {
                System.out.println("账户或密码错误！");
                continue;
            }
            isFalg = true;//登录成功
            while (isFalg) {
                System.out.println("\n-----------------客户信息管理软件-----------------\n");
                System.out.println("                 1 添 加 客 户");
                System.out.println("                 2 修 改 客 户");
                System.out.println("                 3 删 除 客 户");
                System.out.println("                 4 查 询 客 户");
                System.out.println("                 5 客 户 列 表");
                System.out.println("                 6 退       出\n");
                System.out.print("    请选择(1-6)：");

                char menu = CMUtility.readMenuSelection();
                switch (menu) {
                    case '1':
                        addNewCustomer();
                        break;
                    case '2':
                        modifyCustomer();
                        break;
                    case '3':
                        deleteCustomer();
                        break;
                    case '4':
                        listFuzzyQuery();
                        break;
                    case '5':
                        listAllCustomer();
                        break;
                    case '6':
                        System.out.print("    确认退出？(Y/N)");
                        char isExit = CMUtility.readConfirmSelection();
                        if (isExit == 'Y') {
                            System.exit(0);
                        }
                        break;
                }
            }
        }
    }

    /**
     * 管理员登录
     *
     * @return
     */
    public boolean loginAdmin() {
        admin = new Admin();
        System.out.print("账户：");
        String name = CMUtility.readString(10);
        System.out.print("密码：");
        String password = CMUtility.readString(10);
        if (name.equals(admin.getNAME()) && password.equals(admin.getPassword())) return true;
        else return false;
    }

    /**
     * 添加客户的操作
     */
    private void addNewCustomer() {
        System.out.println("---------------------添加客户---------------------");
        System.out.print("姓名：");
        String name = CMUtility.readString(10);//名字不超过几十个字符
        System.out.print("性别：");
        char sex = CMUtility.readChar();
        System.out.print("年龄：");
        int age = CMUtility.readInt();
        System.out.print("手机：");
        String phone = CMUtility.readString(13);
        System.out.print("电子邮箱：");
        String email = CMUtility.readString(30);

        //将上述数据封装到对象中
        Customer customer = new Customer(name, sex, age, phone, email);
        boolean isSuccess = customerList.addCustomer(customer);
        if (isSuccess) {
            System.out.println("---------------------添加完成---------------------");
        } else {
            System.out.println("----------------记录已满,无法添加-----------------");
        }
    }

    /**
     * 修改客户的操作
     */
    private void modifyCustomer() {

        Customer cust;
        int number;
        System.out.println("---------------------修改客户---------------------");
        for (; ; ) {
            System.out.print("请选择待修改客户编号(-1退出)：");
            number = CMUtility.readInt();
            if (number == -1) {
                return;
            }
            cust = customerList.getCustomer(number - 1);
            if (cust == null) {
                System.out.println("无法找到指定客户！");
            } else {//找到对应客户
                break;
            }
        }

        //修改客户信息
        System.out.println("姓名（" + cust.getName() + ")：");
        String name = CMUtility.readString(10, cust.getName());

        System.out.println("性别（" + cust.getSex() + ")：");
        char sex = CMUtility.readChar(cust.getSex());

        System.out.println("年龄（" + cust.getAge() + ")：");
        int age = CMUtility.readInt(cust.getAge());

        System.out.println("手机（" + cust.getPhone() + ")：");
        String phone = CMUtility.readString(13, cust.getPhone());

        System.out.println("电子邮箱（" + cust.getEmail() + ")：");
        String email = CMUtility.readString(30, cust.getEmail());

        cust.setName(name);
        cust.setSex(sex);
        cust.setAge(age);
        cust.setPhone(phone);
        cust.setEmail(email);
        boolean isReplace = customerList.replaceCustomer(number - 1, cust);
        if (isReplace) {
            System.out.println("---------------------修改完成---------------------");
        } else {
            System.out.println("----------无法找到指定客户,修改失败--------------");
        }
    }

    /**
     * 删除客户的操作
     */
    private void deleteCustomer() {
        int number;
        System.out.println("---------------------删除客户---------------------");
        for (; ; ) {
            System.out.print("请选择待删除客户编号(-1退出)：");
            number = CMUtility.readInt();
            if (number == -1) {
                return;
            }
            Customer customer = customerList.getCustomer(number - 1);
            if (customer == null) {
                System.out.println("无法找到指定客户！");
            } else {
                break;
            }
        }
        //找到指定客户
        System.out.print("确认是否删除(Y/N)：");
        char isDelete = CMUtility.readConfirmSelection();
        if (isDelete == 'Y') {
            boolean deleteSuccess = customerList.deleteCustomer(number - 1);
            if (deleteSuccess) {
                System.out.println("---------------------删除完成---------------------");
            } else {
                System.out.println("----------无法找到指定客户,删除失败--------------");
            }
        }
    }

    /**
     * 显示客户列表的操作
     */
    private void listAllCustomer() {
        System.out.println("---------------------------客户列表---------------------------");
        if (customerList.getsize() == 0) {
            System.out.println("没有客户记录！");
        } else {
            System.out.println("编号\t姓名\t性别\t年龄\t\t电话\t\t邮箱");
            List<Customer> custs = customerList.getAllCustomer();
            for (int i = 0; i < custs.size(); i++) {
                Customer cust = custs.get(i);
                System.out.println((i + 1) + "\t\t" + cust.getName() + "\t " + cust.getSex() + "\t\t"
                        + cust.getAge() + "\t\t" + cust.getPhone() + "\t" + cust.getEmail());
            }
        }
        System.out.println("-------------------------客户列表完成-------------------------");

    }

    /**
     * 模糊查询记录
     */
    public void listFuzzyQuery(){
        System.out.print("指定姓名任意字符查询：");
        String text = CMUtility.readString(10);
        List<Customer> listFQ = new ArrayList<>();
        List<Customer> customer = customerList.fuzzyQuery(listFQ,text);
        System.out.println("---------------------------客户列表---------------------------");
        if (customer.size() == 0){
            System.out.println("没有该客户的记录！");
        }else {
            for (int i = 0; i < customer.size(); i++) {
                Customer cust = customer.get(i);
                System.out.println((i + 1) + "\t\t" + cust.getName() + "\t " + cust.getSex() + "\t\t"
                        + cust.getAge() + "\t\t" + cust.getPhone() + "\t" + cust.getEmail());
            }
        }
        System.out.println("-------------------------客户列表完成-------------------------");
    }
    public static void main(String[] args) {
        CustomerView customerView = new CustomerView();
        customerView.enterMainMenu();
    }
}
