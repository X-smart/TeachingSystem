#7.3 Bean的概述
Spring通过Ioc容器控制bean，Ioc也可以管理不是标准bean的java类，
##1.bean的属性
1.scope 作用范围
2.name  bean的名字 采用驼峰命名法。
3.id  bean的名字 必须唯一，命名规范比name更加严格 Spring3.1之后被定义为String类型。采用峰命名法。
4. <alias/> 定义别名的标签 <alias name="fromName" alias="toName"/>

##2.实例化Bean
	1.通过反射构造出一个bean，等同于在java代码中new一个，需要默认空的构造方法（适用于大部分情况）
	2.通过调用工厂类中的static工厂方法来传建一个bean，需要自定义一个静态的实例化方法。
	3.使用实例的工厂方法实例化（非静态）
	调用一个已存在的bean的非静态方法来创建一个新的bean，class属性为空，且factory-bean属性为被调用的用来创建对象的包含工厂方法的bean

#7.4 依赖
##1.依赖注入
	一个bean完成工作可能要依赖其他的bean，Spring实例化这个bean 对象时会把它所依赖的bean一起实例化。（控制反转）
###依赖注入的方法：
（1）通过构造方法注入，由容器调用带参数的构造方法来完成，每一个参数代表一个依赖。

（2）通过setter方法注入，由容器在调用无参构造方法或无参静态工厂方法之后调用setter方法来实例化bean
###依赖注入的过程
	ApplicationContext被创建并初始化描述所有bean的配置元数据。配置元数据可以是XML、Java代码或注解。
	bean的依赖以属性、构造方法参数或静态工厂方法参数的形式表示。这些依赖在bean被实例化的时候就已经创建完成。
	每一个属性或构造方法参数都是将被设置的值的实际定义，或容器中另一个bean的引用。
	每一个值类型的属性或构造方法参数都会从特定的形式转化为它的实际类型。默认地，Spring可以把字符串形式的值转化为所有的内置类型，比如int, long, String, boolean，等等。
###Xml配置依赖注入示例
1. setter方法注入
<bean id="exampleBean" class="examples.ExampleBean">
    <!-- setter injection using the nested ref element -->
    <property name="beanOne">
        <ref bean="anotherExampleBean"/>
    </property>

    <!-- setter injection using the neater ref attribute -->
    <property name="beanTwo" ref="yetAnotherBean"/>
    <property name="integerProperty" value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>

2. 构造方法注入
<bean id="exampleBean" class="examples.ExampleBean">
    <!-- constructor injection using the nested ref element -->
    <constructor-arg>
        <ref bean="anotherExampleBean"/>
    </constructor-arg>

    <!-- constructor injection using the neater ref attribute -->
    <constructor-arg ref="yetAnotherBean"/>

    <constructor-arg type="int" value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>

3. 静态工厂方法注入 通过<constructor-arg/>元素为静态工厂方法提供参数，与实际使用构造方法时一样
<bean id="exampleBean" class="examples.ExampleBean" factory-method="createInstance">
    <constructor-arg ref="anotherExampleBean"/>
    <constructor-arg ref="yetAnotherBean"/>
    <constructor-arg value="1"/>
</bean>

<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>

##2.bean的属性注入
###直接设置值
	1. <property/>的value属性可以为属性或构造方法参数指定人类可读的字符串形式的值
	2.
	* 名称空间p的属性注入的方式:Spring2.x 版本后提供的方式.导入p名称空间的约束。
		<beans xmlns="http://www.springframework.org/schema/beans"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	    xmlns:p="http://www.springframework.org/schema/p"
	    xsi:schemaLocation="http://www.springframework.org/schema/beans
	    http://www.springframework.org/schema/beans/spring-beans.xsd">

	    <bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource"
	        destroy-method="close"
	        p:driverClassName="com.mysql.jdbc.Driver"
	        p:url="jdbc:mysql://localhost:3306/mydb"
	        p:username="root"
	        p:password="masterkaoli
		</beans>
	* c命名空间是Spring3.1新引入的，允许使用内联属性配置构造方法的参数，而不用嵌套constructor-arg元素
		<beans xmlns="http://www.springframework.org/schema/beans"
		 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		 xmlns:c="http://www.springframework.org/schema/c"
		 xsi:schemaLocation="http://www.springframework.org/schema/beans
				http://www.springframework.org/schema/beans/spring-beans.xsd">
		<bean id="bar" class="x.y.Bar"/>
		<bean id="baz" class="x.y.Baz"/>
		<!-- traditional declaration -->
		<bean id="foo" class="x.y.Foo">
				<constructor-arg ref="bar"/>
				<constructor-arg ref="baz"/>
				<constructor-arg value="foo@bar.com"/>
		</bean>
	<!-- c-namespace declaration  c命名空间-->
	<bean id="foo" class="x.y.Foo" c:bar-ref="bar" c:baz-ref="baz" c:email="foo@bar.com"/>
</beans>

###引入其他Bean
1. <ref bean="someBean"/>
2.
* <!-- in the parent context -->
<bean id="accountService" class="com.foo.SimpleAccountService">
    <!-- insert dependencies as required as here -->
</bean>

* <!-- in the child (descendant) context -->
<bean id="accountService" <!-- bean name is the same as the parent bean -->
    class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="target">
        <ref parent="accountService"/> <!-- notice how we refer to the parent bean -->
    </property>
    <!-- insert other configuration and dependencies as required here -->
</bean>

###内部bean
* 在<property/>或<constructor-arg/>元素内部定义的bean就是所谓的内部bean。

###集合
<bean id="moreComplexObject" class="example.ComplexObject">
    <!-- results in a setAdminEmails(java.util.Properties) call -->
    <property name="adminEmails">
        <props>
            <prop key="administrator">administrator@example.org</prop>
            <prop key="support">support@example.org</prop>
            <prop key="development">development@example.org</prop>
        </props>
    </property>
    <!-- results in a setSomeList(java.util.List) call  list集合-->
    <property name="someList">
        <list>
            <value>a list element followed by a reference</value>
            <ref bean="myDataSource" />
        </list>
    </property>
    <!-- results in a setSomeMap(java.util.Map) call map集合-->
    <property name="someMap">
        <map>
            <entry key="an entry" value="just some string"/>
            <entry key ="a ref" value-ref="myDataSource"/>
        </map>
    </property>
    <!-- results in a setSomeSet(java.util.Set) call set集合-->
    <property name="someSet">
        <set>
            <value>just some string</value>
            <ref bean="myDataSource" />
        </set>
    </property>
</bean>
map的键值(set的值)：bean ref idref list set map props value null
map集合可以使用泛型。
###合成属性名
<bean id="foo" class="foo.Bar">
    <property name="fred.bob.sammy" value="123" />
</bean>
foo的fred属性和fred的bob属性在bean构造后必须不为null，否则将会抛出空指针异常。

##3.使用depends-on
	bean之间的依赖关系不是很强烈时用depends—on。
	* 对一个bean依赖时
	<bean id="beanOne" class="ExampleBean" depends-on="manager"/>
	* 对多个bean依赖时
	<bean id="beanOne" class="ExampleBean" depends-on="manager,accountDao">
    <property name="manager" ref="manager" />
	</bean>

##4.延迟加载
 延迟加载的bean会在第一次使用时才会实例化，而不是初始化容器的时候就被实例化。通过属性lazy-init控制
##5.自动装配
###自动装配的优点：
 自动装配将极大地减少指定属性或构造方法参数的需要。
 自动装配可以更新配置当你的对象进化时。
###自动装配的局限性和缺点：
 在property和constructor-arg上显式设置的依赖总是覆盖自动装配。而且，不能自动装配所谓的简单属性，如基本类型、String和Classes（也包括简单属性的数组）。这是设计层面的局限性。
 自动装配没有显式装配精确。如上表所述，尽管Spring很小心地避免了模棱两可的猜测，但其管理的对象之间的关系并不会被明确地记录。
 从Spring容器生成文档的工具可能找不到装配信息。
 容器中的多个bean定义可能会匹配setter方法或构造方法参数指定的类型。对于数组、集合或Map，这未必是个问题。但是，对于那些需要单个值的依赖，这种歧义并不能随意解决。如果没有各自不同的bean定义，将会抛出异常。
###避免bean自动装配
在单个bean上，可以避免自动装配。在xml形式中，设置<bean>元素的autowire-candidate属性为false，容器就不会让这个bean进入自动装配的架构中（包括注解形式的配置，比如@Autowired）。
也可以通过定义bean名称的匹配模式避免自动装配。顶级元素<beans>的属性default-autowire-candidates可以接受一个或多个模式。例如，为了限制以Repository结尾的bean自动装配，提供一个值Repository*即可。如果需要提供多个模式，请以逗号分隔。bean定义上的autowire-candidate**属性优先级要更高一点，对于这类bean，匹配模式将不起作用。

##6.方法注入

###查找方法注入：
 为了能使动态的子类有效，被继承的类不能是final，且被重写的方法也不能是final。
 单元测试一个具有抽象方法的类时，需要手动继承此类并重写其抽象方法。
 组件扫描的具体方法也需要具体类。
 一项关键限制是查找方法不能使用工厂方法和配置类中的@Bean方法，因为容器不会在运行时创建一个子类及其实例。
 最后，方法注入的目标对象不能被序列化。

#7.11 使用JSR 330标准注解

##1.使用@Inject和@Named依赖注入
 与@Autowired一样，可以在字段级别、方法级别或构造参数级别使用@Inject。另外，也可以定义注入点为Provider，以便按需访问短作用域的bean或通过调用Provider.get()延迟访问其它的bean。

##2.@Named：与@Component注解等价

 与@Component不同的是，JSR-330的@Named注解不能组合成其它的注解，因此，如果需要构建自定义的注解，请使用Spring的注解。

#7.12 基于Java的容器配置

##1.@Bean和@Configuration

 @Bean注解表示一个方法将会实例化、配置并初始化一个对象，且这个对象会被Spring容器管理。这就像在XML中<beans/>元素中<bean/>元素一样。@Bean注解可用于任何Spring的@Component注解的类中，但大部分都只用于@Configuration注解的类中。

##2.使用AnnotationConfigApplicationContext实例化Spring容器

 简单的构造方法：与使用ClassPathXmlApplicationContext注入XML文件一样，可以使用AnnotationConfigApplicationContext注入@Configuration类。

  使用scan(String…)扫描组件、使用AnnotationConfigWebApplicationContext支持web应用、使用register(Class）

##3.使用@Bean注解
 @Bean是方法级别的注解，它与XML中的<bean/>类似，同样地，也支持<bean/>的一些属性，比如，init-method, destroy-method, autowring和name。

 声明bean：只要在方法上简单的加上@Bean注解就可以定义一个bean了，这样就在ApplicationContext中注册了一个类型为方法返回值的bean。

 Bean之间的依赖：@Bean注解的方法可以有任意个参数用于描述这个bean的依赖关系。比如，如果TransferService需要一个AccountRepository，我们可以通过方法参数实现这种依赖注入。

 接收生命周期回调

 指定bean的作用域：使用@Scope注解 、@Scope和scoped-proxy

##4.组合的Java配置
 使用@Import注解、在导入的@Bean定义上注入依赖、有条件地包含@Configuration类或@Bean方法

##5.绑定Java与XML配置
 XML为主使用@Configuration类 、以@Configuration类为主使用@ImportResource引入XML 
