# Ju4Ja - Julia for Java
<strong>This project aims to makes java programs easier to call julia functions.</strong>
<br/>
<p style="line-height:20px;">
Java, along with its bro languages such as Scala, really sucks at numerical computing. 
Projects like Spark, Breeze, ND4j, Common-math and Optimizer made great efforts to facilitate numerical computing in JVM ecosystem. 
However, the whole Java community actually lags extremely behind Python and Julia with regard to numerical computing and machine learning. 
Developers and scientists using Java and Scala have been struggling to push Java into such arena, but it ultimately turns out that Java lacks floating point accuracy, performance, as well as enthusiasm, towards mathematical computation.
So, this project try to build a bridge from Java to Julia, which is perfectly suited to create fast and accurate numerical applications.
</p>
<h3>Basic usage 1 - Call remote function in Julia from Java</h3>
<pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'宋体';font-size:12.0pt;"><pre style="font-family: 宋体; font-size: 12pt;"><span style="color:#629755;font-style:italic;">/**<br></span><span style="color:#629755;font-style:italic;"> * Call function named as 'greetings' on Julia server<br></span><span style="color:#629755;font-style:italic;"> */<br></span><span style="color:#bbb529;">@Test<br></span><span style="color:#cc7832;">public void </span><span style="color:#ffc66d;">testRemoteGreeting</span>() {<br>    String ip = <span style="color:#6a8759;">"127.0.0.1"</span><span style="color:#cc7832;">; </span><span style="color:#808080;">//Ip address of Ju4ja server<br></span><span style="color:#808080;">    </span><span style="color:#cc7832;">int </span>port = <span style="color:#6897bb;">6996</span><span style="color:#cc7832;">;</span><span style="color:#808080;">//port of Ju4ja server<br></span><span style="color:#808080;">    //Build a Ju4ja client to call function in Julia<br></span><span style="color:#808080;">    </span>Ju4jaClient client = <span style="color:#cc7832;">new </span>Ju4jaClient(ip<span style="color:#cc7832;">, </span>port)<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    </span><span style="color:#808080;">//encapsulate arguments for remote function<br></span><span style="color:#808080;">    </span>Object[] as = {<span style="color:#6a8759;">"Zhang fei"</span>}<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    </span><span style="color:#808080;">//invocation to the remote function<br></span><span style="color:#808080;">    </span>JavaCallResult result = client.invokeFunction(<span style="color:#6a8759;">"greetings"</span><span style="color:#cc7832;">, </span><span style="color:#6a8759;">"Main"</span><span style="color:#cc7832;">, </span>as)<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    if </span>(result != <span style="color:#cc7832;">null</span>) {<br>        <span style="color:#808080;">//print out the result<br></span><span style="color:#808080;">        </span>System.<span style="color:#9876aa;font-style:italic;">out</span>.println(result.getResultStr())<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">        </span>System.<span style="color:#9876aa;font-style:italic;">out</span>.println(result.getStatus())<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    </span>}<br>}</pre></pre>

<h3>Basic usage 1 - Code snippet in Julia to define the "greetings" function and start Ju4ja server</h3>

<pre style="background-color: rgb(43, 43, 43);"><pre><font color="#629755" face="宋体"><span style="font-size: 16px;"><i>#set the path where the modules are located
push!(LOAD_PATH, "./")

#A examle function ready to be called from java using Ju4ja
function greetings(name::String)
    str="Hi, $name"
    println(str)
    return str
end

# g=greetings("test")
# println(g)

#using Ju4ja and the example solver for linear programming
using Ju4ja
using RereDmlLpSolver

#start the Ju4ja server as a new coroutine
@async begin
    startServer()
end</i></span></font><span style="color: rgb(169, 183, 198); font-family: 宋体; font-size: 12pt; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: normal;">
</span></pre><div style="color: rgb(169, 183, 198); font-family: 宋体; font-size: 12pt; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: normal;"><br></div></pre>


<h4>
More examples can be found here: <a href="https://github.com/lteb2002/ju4ja/tree/master/ju4ja/java/com/reremouse/ju4ja/example">Examples</a>
</h4>
