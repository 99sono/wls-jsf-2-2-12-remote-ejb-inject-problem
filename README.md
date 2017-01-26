# Verify that in weblogic 12.2.1.2 @EJB injection points have problems in class hierarchies

In this sample application, we can visit the index.xhtml page and press a button that causes a @RequestScoped bean
to print out the state of injection of itself and its parent class.
What we see is that on the implementation class all fields are properly injected.
In the parent class, however, we have a bug.
We see that for the parent clas @Local ejb injeciton points and normall WELD CDI beans are injected properly.
But the @Remote EJB injection points are only properly injected if the field name is not repeated in the child class.
This is clearly a bug, as injection resolution cannot be different for remote EJBs specifically. IN Wildfly the full bean hiearhcy
is fully injected.
What we expect to see in the  log file is as follows:


```
####<Jan 26, 2017, 12:53:00,497 PM CET> <Info> <Deployer> <SCHB7M-000260> <AdminServer> <[STANDBY] ExecuteThread: '5' for queue: 'weblogic.kernel.Default (self-tuning)'> <<WLS Kernel>> <> <cb7c3106-ff66-4c4f-8e2c-3a096634cd43-00000016> <1485431580497> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-149074> <Successfully completed deployment task: [Deployer:149026]deploy application primefaces-60 on AdminServer..> 
####<Jan 26, 2017, 12:53:24,684 PM CET> <Info> <web.DummyWebBean> <SCHB7M-000260> <AdminServer> <[ACTIVE] ExecuteThread: '2' for queue: 'weblogic.kernel.Default (self-tuning)'> <<anonymous>> <> <cb7c3106-ff66-4c4f-8e2c-3a096634cd43-00000017> <1485431604684> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-000000> <DummyWebBean -  bean constructed. > 
####<Jan 26, 2017, 12:53:24,689 PM CET> <Info> <web.DummyWebBean> <SCHB7M-000260> <AdminServer> <[ACTIVE] ExecuteThread: '2' for queue: 'weblogic.kernel.Default (self-tuning)'> <<anonymous>> <> <cb7c3106-ff66-4c4f-8e2c-3a096634cd43-00000017> <1485431604689> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-000000> <
----------------------------------------------------------------> 
####<Jan 26, 2017, 12:53:24,690 PM CET> <Info> <web.DummyWebBean> <SCHB7M-000260> <AdminServer> <[ACTIVE] ExecuteThread: '2' for queue: 'weblogic.kernel.Default (self-tuning)'> <<anonymous>> <> <cb7c3106-ff66-4c4f-8e2c-3a096634cd43-00000017> <1485431604690> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-000000> <FIRST - We log the injection state of collaborators in the implementation class:> 
####<Jan 26, 2017, 12:53:24,692 PM CET> <Info> <web.DummyWebBean> <SCHB7M-000260> <AdminServer> <[ACTIVE] ExecuteThread: '2' for queue: 'weblogic.kernel.Default (self-tuning)'> <<anonymous>> <> <cb7c3106-ff66-4c4f-8e2c-3a096634cd43-00000017> <1485431604692> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-000000> <web.DummyWebBean ---- INJECTION STATE IS:
{
basicService=service.BasicServiceImpl@571f0bd6, 
dummyBaseFacade=ejb.DummyBaseFacadeImpl_pyj1nk_DummyBaseFacadeImpl@695f5ff1, 
dummyBaseFacadeLocal=ejb.DummyBaseFacadeImpl_pyj1nk_DummyBaseFacadeLocalImpl@736f1a9c}> 
####<Jan 26, 2017, 12:53:24,694 PM CET> <Info> <web.DummyWebBean> <SCHB7M-000260> <AdminServer> <[ACTIVE] ExecuteThread: '2' for queue: 'weblogic.kernel.Default (self-tuning)'> <<anonymous>> <> <cb7c3106-ff66-4c4f-8e2c-3a096634cd43-00000017> <1485431604694> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-000000> <





> 
####<Jan 26, 2017, 12:53:24,695 PM CET> <Info> <web.DummyWebBean> <SCHB7M-000260> <AdminServer> <[ACTIVE] ExecuteThread: '2' for queue: 'weblogic.kernel.Default (self-tuning)'> <<anonymous>> <> <cb7c3106-ff66-4c4f-8e2c-3a096634cd43-00000017> <1485431604695> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-000000> <SECOND - We log the injection state of collaborators in the parent class:> 
####<Jan 26, 2017, 12:53:24,696 PM CET> <Info> <web.AbstractWebBean> <SCHB7M-000260> <AdminServer> <[ACTIVE] ExecuteThread: '2' for queue: 'weblogic.kernel.Default (self-tuning)'> <<anonymous>> <> <cb7c3106-ff66-4c4f-8e2c-3a096634cd43-00000017> <1485431604696> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-000000> <web.AbstractWebBean ---- INJECTION STATE IS:
{
basicService=service.BasicServiceImpl@571f0bd6, 
basicServiceNameThatWillNeverExistInChildClass=service.BasicServiceImpl@571f0bd6, 
dummyBaseFacade=null,  <-------------- THIS HERE IS THE BUG. THis a @EJB injection field that not being injected  web.AbstractWebBean but that was properly injected into  web.DummyWebBean
dummyBaseFacadeLocal=ejb.DummyBaseFacadeImpl_pyj1nk_DummyBaseFacadeLocalImpl@736f1a9c, 
dummyBaseFacadeLocalNameThatWillNeverExistInChildClass=ejb.DummyBaseFacadeImpl_pyj1nk_DummyBaseFacadeLocalImpl@736f1a9c, 
dummyBaseFacadeNameThatWillNeverExistInChildClass=ejb.DummyBaseFacadeImpl_pyj1nk_DummyBaseFacadeImpl@695f5ff1}> 
####<Jan 26, 2017, 12:53:24,718 PM CET> <Info> <web.DummyWebBean> <SCHB7M-000260> <AdminServer> <[ACTIVE] ExecuteThread: '2' for queue: 'weblogic.kernel.Default (self-tuning)'> <<anonymous>> <> <cb7c3106-ff66-4c4f-8e2c-3a096634cd43-00000017> <1485431604718> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-000000> <AbstractWebBean - Request scoped bean destroyed. > 


```

REPRODUCE:
(1) Build the war file
(2) Deploy it 
(3) Visit http://localhost:7001/wls-remote-ejb-inject-bugy-1.0.0-SANPSHOT/
(4) Click the log to server button 
Check the log file.

NOTE:
- In wildfly and weblogic 12.1.2  all fields are properly injected.

PATCHES - on vanila 12.2.1.2 to reproduce issue:
22754279;One-off
21663638;One-off
19795066;One-off
19632480;One-off
19154304;One-off
19030178;One-off
19002423;One-off
18905788;One-off

