# android-commonlog
代码借鉴了：https://github.com/noveogroup/android-logger.git　去掉了对：org.slf4j　的依赖
＃lib 使用说明
1.LoggerManager 调用任何log方法前必须调用：LoggerManager．init　方法来初始化log配置信息,比如：
            try {
                LoggerManager.init(getAssets().open("android-logger.properties"));
            } catch (Exception e) {
                e.printStackTrace();
            }
２.　类：com.rain.lib.log.Log 和android系统中的Log的方法类似，只是参数不需要传tag参数
　　　类：com.rain.lib.log.Log.Logger　使用方法为：
　　  Logger logger = LoggerManager.getLogger(XX.class); logger.d("printf you log info");
# android-logger.properties　配置文件说说明
   注释掉default 为把默认的log信息的输出都屏蔽掉，但以logger开头规则除外。
   log输出匹配规则：  default=<level>:<tag>:<message head>#     
         如果default=kankan表示所有的log的tag标签为：kankan
                     <level>  level的值有：VERBOSE(2)，DEBUG(3)，INFO(4)，WARN(5)，ERROR(6)，ASSERT(7)
                              level的值为INFO时，其之后的：WARN,ERROR,ASSERT均会打印出来，之前的：VERBOSE,DEBUG任何信息不会被打印出来# 
                     <tag>    正规表达式：%logger表示,以类的路径为所有的Tag的标签，当然另起tag名字。如：default=VERBOSE:kankan
                     <message head>  在打印日记时，先打印 正规表达式：%date %caller%n 所表示的信息，再打印log内容。
                              其中：%date打印的时间信息，%caller 表示log信息被调用的方法，行数，包名的信息
         如：default=VERBOSE:%logger:%date %caller%n   表示：默认所有的log信息都会被打印，因为level为VERBOSE, 打印出来的信息的tag标签为：包名+类名。  打印的内容开头为：时间+ 打印代码所在方法的详细信息+回车+打印内容
         如：default=VERBOSE:   表示：把所有信息都打印出来，tag标签为：包名+类名
   #default=VERBOSE:%logger:%date %n:
   
   #下面表示：包com.XXX.caching下面的log输出的tag标签为－－image cache
   #logger.com.XXX.caching=VERBOSE:image cache
   ＃下面表示：包com.XXX.phone.tab和类com.XXX.phone.PersionalCenterFragment的log输出标签都为：包名＋类名
   #logger.com.XXX.phone.tab=VERBOSE:%logger:
   #logger.com.XXX.phone.PersionalCenterFragment=VERBOSE:%logger:
    
