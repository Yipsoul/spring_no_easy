### 一个星期之前闲着蛋疼想自己手写一个简单的spring，才发现是多么的不容易，在网上找了不少资料，也厚着脸皮问了不少大佬，还是非常不容易的写了一个简单的SpringIOC基于XML配置，注解的实在是太难了（主要还是自己太菜了），完全是知识的空白区，比如说spring的ASM啥的。。现在还在研究中。。
#### 贴上自己简单实现spring的架构图
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019052814371862.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY0OTA5MA==,size_16,color_FFFFFF,t_70)
 ### 项目结构如下，由于现在还没做完基于注解的IOC，有些类到时候可能还需要多更改一下。
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190528143832891.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY0OTA5MA==,size_16,color_FFFFFF,t_70)
 ### 我们都知道基于xml配置的ioc是利用了java的反射+工厂模式+配置文件。Spring在运行时，根据Spring的xml配置文件来动态的创建对象，和调用对象里的方法，控制权由对象本身转向容器。由容器根据配置文件去创建实例并创建各个实例之间的依赖关系 。但是看了spring源码才发现他的精妙之处。spring的5.x源码很难懂，还是自己内功修炼的不够好，自己参考了3.x和4.x的，学到了很多东西，也深深体会到了设计模式的用处，真的太优雅了。简单介绍下几个类：
 ## BeanFactory
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190528145425520.png)
##### 是一个获取bean工厂的接口，DeafaultBeanFactory为BeanFactory的默认实现。
---
 ## XmlBeanDefinitionReader
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/2019052814560915.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY0OTA5MA==,size_16,color_FFFFFF,t_70)
**专门负责用来读取xml配置文件**

-----
## BeanDefinitionRegistry
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190528145747776.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY0OTA5MA==,size_16,color_FFFFFF,t_70)
**读取完配置文件后，BeanDefinitionRegistry负责注册bean，DefaultSingletonBeanRegistry为默认实现**
 
 -----
 ## 当然还有很关键的BeanDefinition
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190528145913608.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY0OTA5MA==,size_16,color_FFFFFF,t_70)
 **这个接口十分关键，是存储每一个bean的属性定义，比如说该bean是否为单例、多例，是否有构造参数，是否依赖其他bean，GenericBeanDefinition为默认实现**

-----
### 以上就是我个人认为生产一个bean的比较核心的几个类把，spring把生产一个bean分为大概三个步骤，读取xml，注册bean，存到一个Map中。但是spring把这些步骤都分到不同的类和接口中，完美的实现了面向对象五个基本原则（SOLID）之单一职责原则（SRP）。
## ApplicationContext
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190528153209707.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTY0OTA5MA==,size_16,color_FFFFFF,t_70)
**ApplicationContext是BeanFactory的子接口。他们都可代表Spring容器，Spring容器是生成Bean实例的工厂，并且管理容器中的Bean。ApplicationContext是高级容器，比beanFactory扩展了更多的功能，比如说国际化，访问资源，如URL和文件，消息发送、响应机制，AOP。beanFactory是懒加载bean，等用户getBean的时候才会去注册相关bean，但是applicationContext是容器初始化的时候就已经注册好所有的bean，并放到容器中存储着，我这里就实现了访问资源ResourceLoader，其他的有兴趣的道友可以自己去实现。**

----
#### 目前已经实现了简单的xml配置的ioc，分别是set属性注入与构造器注入，由于生活的各种忙活，到今天才发现这篇博客没有写完，spring的ioc就实现到这里，以后有空会继续完善。
#### github地址 ： https://github.com/Yipsoul/spring_no_easy



