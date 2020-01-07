# USB_LINK
  VERSION 1.0
  
  需要解决的问题---
  手机是服务端，pc是客户端。pc可以一直接收数据，但是在服务端，手机还不能一直接收数据，能一直接收，但是不能发送了，只能单向的。
  
  Phone  GetData  (一直)
  Phone  PostData  (事件按钮)
  
  Pc  GetData (一直)
  Pc  PostData (事件)
  
  VERSION 2.0
  
  1.主要是修改去掉接收和发送的按钮，接收用轮询，每3秒一次去接收，收到数据 发送消息给pc通知别发了，当收到消息status为1的时候，是通知手机端发送，启动发送的线程。
  
  2.Adapter item的数据变化 需要写个监听器，方便通知activity界面，或者提供一个接口，返回的真实mData数据接口。
  
  3.多重排序，需要一层一层的去排序，然后优先级逆序去执行Comparator
  
  VERSION 3.0
  
  PC 测试软件  HardWareSocketHost.exe  0 是pc 发数据到Android   1 是 pc 请求发送数据    2 pc请求删除数据
  需要 配置adb HardWareSocketHost.exe.config文件  value为你pc端的adb路径
  <add key="AdbPath" value = "C:\Users\tcb\AppData\Local\Android\Sdk\platform-tools"/>
