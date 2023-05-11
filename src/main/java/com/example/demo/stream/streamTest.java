package com.example.demo.stream;



import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Boolean.parseBoolean;

/**
 *  Stream流接口中定义了许多对于集合的操作方法，总的来说可以分为两大类：中间操作和终端操作：
 *  1中间操作：会返回一个流，通过这种方式可以将多个中间操作连接起来，形成一个调用链，从而转换为另外 一个流。除非调用链后存在一个终端操作，否则中间操作对流不会进行任何结果处理。
 *  2终端操作：会返回一个具体的结果，如boolean、list、integer等。
 *
 */
public class streamTest {

    public static void main(String[] args) {
        List<Employee> list = new ArrayList<>();
        Employee ceo = new Employee("张三", 56, "男", 50000.42D, "浙江杭州", "浙江杭州", "ceo", null);
        Employee manager1 = new Employee("李四", 47, "女", 20000.7D, "浙江宁波", "浙江宁波", "经理", ceo);
        Employee manager2 = new Employee("王五", 45, "男", 24000.5D, "浙江金华", "浙江金华", "经理", ceo);
        Employee employee1 = new Employee("麻六", 27, "女", 7000.6D, "浙江宁波", "广东广州", "售前", manager1);
        Employee employee2 = new Employee("孙七", 28, "男", 8000.8D, "浙江宁波", "广东深圳", "售后", manager1);
        Employee employee3 = new Employee("赵八", 27, "女", 9500.2D, "浙江杭州", "云南昆明", "售前", manager2);
        Employee employee4 = new Employee("钱九", 26, "男", 9000.0D, "浙江杭州", "云南玉溪", "售后", manager2);
        list.add(ceo);
        list.add(manager1);
        list.add(manager2);
        list.add(employee1);
        list.add(employee2);
        list.add(employee3);
        list.add(employee4);

        //filter,中间操作，检验出name为张三的员工信息并打印出来
        list.stream().filter(employee -> "张三".equals(employee.getName())).forEach(employee -> System.out.println(employee.toString()));
        System.out.println("====================================");

        //map，中间操作,取出所有的员工姓名并打印
        list.stream().map(employee ->{ return employee.getName();}).forEach(name->{System.out.print(name+"\t");});
        System.out.println("\n====================================");

        //flatMap,中间操作
        List<Employee> newList = new ArrayList<>();
        newList.add(new Employee("新人", 18, "女", 5000.7D, "浙江金华", "浙江金华", "售前", null));
        //Stream.of()，将多个元素组合为一个元素进行操作,,也可以手写内容初始化为1个流,具体百度，但当前两个list这样打印出来是两组东西
        //Stream.of(list,newList).forEach(employee -> System.out.println(employee.toString()));
        System.out.println("====================================");
        //flatMap此操作才将两个list合并在了一起，后续想做去重，数据检验，map都可以
        Stream.of(list, newList).flatMap(employee  -> employee.stream()).forEach(employee -> System.out.println(employee.getName()));
        //Stream.of(list, newList).flatMap(employee  -> employee.stream().filter(ss -> "女".equals(ss.getSex()))).forEach(employee -> System.out.println(employee.getName()));
        System.out.println("====================================");


        //中间操作 limit  获取前几个员工的数据并打印出来(超出数组个数不会报错，索引开始为1，写负数会报错)
        list.stream().limit(1).forEach(employee -> System.out.print(employee.getName()));
        System.out.println("\n====================================");

        //中间操作-skip 跳过几个员工数据并打印后续所有人的信息(超出数组个数不会报错，索引开始为1，写负数会报错)
        list.stream().skip(5).forEach(employee -> System.out.print(employee.getName()));
        System.out.println("\n====================================");

        //中间操作-distinct  去除数组内的重复数据，太简单这内容就不写了
        System.out.println("\n====================================");
        //中间操作-sorted   排序 低到高  顺序调换则e2的工资减去e1的工资
        list.stream().sorted((e1,e2)->(int)(e1.getSalary()- e2.getSalary())).forEach(employee -> System.out.print(employee.getName()+"\t"));
        System.out.println("\n====================================");

        //中间操作-peek 打印公司员工的名字和职位,peek的官方定位是为了debug使用的，他和map功能有些类似，不同的是 map需要有返回值，且返回值可以被stream流接纳，而peek不需要有返回值 。
        list.stream().peek(employee -> System.out.print(employee.getName() + ":")).forEach(employee -> System.out.println(employee.getPosition()));
        System.out.println("\n====================================");

        //中间操作-collect-Collectors.groupingBy
        //把公司员工按照职位分组，collectGroup的key为position值，value为对象中position和key相同的集合
        Map<String, List<Employee>> collectGroup = list.stream().collect(Collectors.groupingBy(employee -> employee.getPosition()));
        System.out.println(collectGroup.toString());
        System.out.println("\n====================================");
        //中间操作-collect-Collectors.counting() 等价于 list.stream().count()=list.size()
        Long collectCount = list.stream().collect(Collectors.counting());
        System.out.println("\n====================================");
        //中间操作-collect-Collectors.toList(); 此操作把流重新转换为List
        System.out.println("\n====================================");
        //中间操作-collect-Collectors.toSet()转换成set，舍弃重复值(类型转换)
        Set<Employee> collectSet = list.stream().collect(Collectors.toSet());
        System.out.println(collectSet.toString());
        //中间操作-collect-Collectors.toMap()根据传入的属性获取对应属性的key和value
        //employee ->{ return employee.getName();}等价于Employee::getName
        Map<String,Double> collectMap=list.stream().collect(Collectors.toMap(Employee::getName,Employee::getSalary));
        System.out.println(collectMap.toString());
        System.out.println("\n====================================");
        //中间操作-collect-Collectors.averagingDouble()计算list中属性为salary的平均值
        Double collectDouble=list.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        //中间操作-collect-Collectors.averagingInt()计算list中属性为age的平均值,其实就是不同类型的变种
        //Double  collectInt=list.stream().collect(Collectors.averagingInt(Employee::getAge));
        System.out.println("\n====================================");

        //中间操作-collect-Collectors.mapping()相当于list.stream().map()，收集name属性
        //中间操作-collect-Collectors.joining()每次收集一次name都在后面加上,最后一次没有
        String collectMapping = list.stream().collect(Collectors.mapping(Employee::getName, Collectors.joining(",")));
        System.out.println(collectMapping);
        System.out.println("\n====================================");

        //中间操作-collect-Collectors.maxBy()求最大值,相当于list.stream().max()
        Employee employeeMax = list.stream().collect(Collectors.maxBy((a,b)->a.getAge()-b.getAge())).get();
        //employeeMax的age属性为56
        System.out.println(employeeMax.getAge());
        System.out.println("\n====================================");
        //中间操作-collect-Collectors.minBy()求最小值,相当于list.stream().min()   (和上述差不多，不写了)
        //中间操作-collect-reducing和list.stream().reduce()方法类似, 这里可以多加判断条件，更灵活
        Employee employeReducing=list.stream().collect(Collectors.reducing((a,b)->(a.getSalary()>b.getSalary()?a:b))).get();
        System.out.println(employeReducing.toString());
        System.out.println("\n====================================");


        //终端操作-foreach 打印内容，太简单这内容就不写了
        System.out.println("\n====================================");
        //终端操作-findFirst 效果等同于list.get(0)
        //Employee newEmployee=list.stream().findFirst().get();
        System.out.println("\n====================================");
        //终端操作-findAny 查找任意一个,大多数情况只会匹配第一个
        //在并行流的情况下会随机取一个元素   （不写了）parallelStream
        //这个方法在使用了filter筛选若没有结果后会报错，orElse()是设置找不到数据后的默认值，替换.get()
        //Employee newEmployee2=list.stream().findAny().get();
        //System.out.println(newEmployee2.toString());
        System.out.println("\n====================================");
        //终端操作-anyMatch，只要有符合条件的就返回true,默认为false
        //boolean anyMatch = list.stream().anyMatch(employee -> 27 == employee.getAge());
        //终端操作-allMatch，必须都符合条件的才返回true，默认为true
        //boolean allMatch = list.stream().allMatch(employee -> 27 == employee.getAge());
        //终端操作-noneMatch，必须全部不匹配才返回true，默认为true
        //boolean noneMatch = list.stream().noneMatch(employee -> 27 == employee.getAge());
        System.out.println("\n====================================");


        //终端操作-reduce 所有字符拼接，数字计算,数字比大小
        Double reduce=list.stream().map(Employee::getSalary).reduce((a,b)->{return a+b;}).get();
        //employee ->{ return employee.getSalary();}等价于Employee::getSalary
        System.out.println("\n====================================");

        //终端操作-count，等同于list.size()
        long count=list.stream().count();
        //终端操作-min  查找对应属性里最小的一个值
        Employee minAge =list.stream().min((a,b)-> (a.getAge()-b.getAge())).get();
        int minAgeInt=minAge.getAge();
        //终端操作-max  查找对应属性里最大的一个值
        Employee maxAge =list.stream().max((a,b)-> (a.getAge()-b.getAge())).get();
        int maxAgeInt=maxAge.getAge();


    }
}
