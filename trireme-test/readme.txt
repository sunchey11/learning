1.require函数的执行步骤
module.js中的代码
Module.prototype.require = function(path) {
  assert(typeof path === 'string', 'path must be a string');
  assert(path, 'missing path');
  return Module._load(path, this);
};

2.
   Node10Implementation implements NodeImplementation 一个实现类代表一个node 版本
       定义了程序入口脚本名称和内置脚本
       主脚本名称:trireme
       内置脚本: 
            { "_debugger",             P + "node._debugger" },
            { "_linklist",             P + "node._linklist" },
            { "_stream_duplex",        P + "node._stream_duplex" },
            { "_stream_passthrough",   P + "node._stream_passthrough" },
            { "_stream_readable",      P + "node._stream_readable" },
            { "_stream_transform",     P + "node._stream_transform" },
            { "_stream_writable",      P + "node._stream_writable" },
            { "assert",                P + "node.assert" },
            { "cluster",               P + "node.cluster" },
            { "console",               P + "node.console" },
            { "constants",             P + "node.constants" },
            { "dgram",                 P + "node.dgram" },
            { "domain",                P + "node.domain" },
            { "events",                P + "node.events" },
            { "freelist",              P + "node.freelist" },
            { "fs",                    P + "node.fs" },
            { "node_http",             P + "node.http" },
            { "node_https",            P + "node.https" },
            { "module",                P + "node.module" },
            { "net",                   P + "node.net" },
            { "os",                    P + "node.os" },
            { "path",                  P + "node.path" },
            { "punycode",              P + "node.punycode" },
            { "querystring",           P + "node.querystring" },
            { "readline",              P + "node.readline" },
            { "stream",                P + "node.stream" },
            { "string_decoder",        P + "node.string_decoder" },
            { "sys",                   P + "node.sys" },
            { "timers",                P + "node.timers" },
            { "url",                   P + "node.url" },
            { "util",                  P + "node.util" },

            { "http",                   P + "trireme.adaptorhttp" },
            { "https",                  P + "trireme.adaptorhttps" },
            { "child_process",          P + "trireme.child_process" },
            { "crypto",                 P + "trireme.crypto" },
            { "trireme_metrics",        P + "trireme.trireme_metrics" },
            { "tls",                    P + "trireme.tls" },
            { "tty",                    P + "trireme.tty" },
            { "vm",                     P + "trireme.vm" },
            { "zlib",                   P + "trireme.zlib" }




3.RootModuleRegistry(NodeImplementation impl) 用来读取module,在ScriptRunner.runScript里被调用
  loadRoot方法流程:
     1.读取native module 实现了NodeModule接口,有三种module
        HashMap<String, NodeModule>         modules   即RegularModule
		                
		        Process,NativeModule,Buffer,DNS,
        
           
        HashMap<String, InternalNodeModule> internalModules
                OS,Evals,HTTPWrap,ModuleLoader,Constants,Crypto,UDPWrap,Natives,ProcessWrap,
                ZLib,HTTPParser,TriremeNativeSupport,CaresWrap,TimerWrap

        HashMap<String, NativeNodeModule>   nativeModules 为空
        
    2.读取main script
    3.读取 compiledModule,compiledModule是在Node10Implementation中定义的
      HashMap<String, Script>             compiledModules
      
    
4.ScriptRunner

       方法initializeModule("模块名称");//创建NodeModule实例,并调用registerExports方法得到export对象
       属性nativeModule,是一个NativeImpl对象,有下列方法
         require 给js调用
         exists  给js调用
         
       属性Process.ProcessImpl process
       binding(name)   调用runner获得module
       argv  命令行参数
       _eval
       env
       
       方法initGloble
       1.创建nativeModule,并放到缓存里 HashMap<String, NativeModule.ModuleImpl> moduleCache
       2.创建process对象
       3.将process对象放到scope中
                      在js对象中使用var NativeModule = process.binding('native_module')
                      就可以取得NativeModule
     

4.trireme与spring结合