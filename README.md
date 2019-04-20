# Ju4Ja - Julia for Java
<strong>This project aims to make java programs easier to call julia functions.</strong>
<br/>
<p style="line-height:20px;">
Java, along with its bro languages such as Scala, really sucks at numerical computing. 
Projects like Spark, Breeze, ND4j, Common-math and Optimizer made great efforts to facilitate numerical computing in JVM ecosystem. 
However, the whole Java community actually lags extremely behind Python and Julia with regard to numerical computing and machine learning. 
Developers and scientists using Java and Scala have been struggling to push Java into such arena, but it ultimately turns out that Java lacks floating point accuracy, performance, as well as enthusiasm, towards mathematical computation.
This project try to build a bridge from Java to Julia, which is perfectly suited to create fast and accurate numerical applications.
</p>
<h3>Basic usage 1.1 - Call remote function in Julia from Java</h3>
<pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'宋体';font-size:12.0pt;">
<span style="color:#629755;font-style:italic;">/**<br></span><span style="color:#629755;font-style:italic;"> * Call function named as 'greetings' on Julia server<br></span><span style="color:#629755;font-style:italic;"> */<br></span><span style="color:#bbb529;">@Test<br></span><span style="color:#cc7832;">public void </span><span style="color:#ffc66d;">testRemoteGreeting</span>() {<br>    String ip = <span style="color:#6a8759;">"127.0.0.1"</span><span style="color:#cc7832;">; </span><span style="color:#808080;">//Ip address of Ju4ja server<br></span><span style="color:#808080;">    </span><span style="color:#cc7832;">int </span>port = <span style="color:#6897bb;">6996</span><span style="color:#cc7832;">;</span><span style="color:#808080;">//port of Ju4ja server<br></span><span style="color:#808080;">    //Build a Ju4ja client to call function in Julia<br></span><span style="color:#808080;">    </span>Ju4jaClient client = <span style="color:#cc7832;">new </span>Ju4jaClient(ip<span style="color:#cc7832;">, </span>port)<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    </span><span style="color:#808080;">//encapsulate arguments for remote function<br></span><span style="color:#808080;">    </span>Object[] as = {<span style="color:#6a8759;">"Zhang fei"</span>}<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    </span><span style="color:#808080;">//invocation to the remote function<br></span><span style="color:#808080;">    </span>JavaCallResult result = client.invokeFunction(<span style="color:#6a8759;">"greetings"</span><span style="color:#cc7832;">, </span><span style="color:#6a8759;">"Main"</span><span style="color:#cc7832;">, </span>as)<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    if </span>(result != <span style="color:#cc7832;">null</span>) {<br>        <span style="color:#808080;">//print out the result<br></span><span style="color:#808080;">        </span>System.<span style="color:#9876aa;font-style:italic;">out</span>.println(result.getResultStr())<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">        </span>System.<span style="color:#9876aa;font-style:italic;">out</span>.println(result.getStatus())<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    </span>}
<br>}
</pre>

<h3>Basic usage 1.2 - Code snippet in Julia to define the "greetings" function and start Ju4ja server</h3>

<div><div><pre style="background-color: rgb(43, 43, 43);"><font color="#a9b7c6" face="宋体"><span style="font-size: 16px;">#set the path where the modules are located
push!(LOAD_PATH, "./")

#A examle function ready to be called from java using Ju4ja
function greetings(name::String)
&nbsp;    str="Hi, $name"
&nbsp;    println(str)
&nbsp;    return str
end

#g=greetings("test")
#println(g)

#using Ju4ja and the example solver for linear programming
using Ju4ja
using RereDmlLpSolver

#start the Ju4ja server as a new coroutine
@async begin
&nbsp;   startServer()
end</span></font>
</pre></div></div><div><br></div>



<h3>Basic usage 2.1 - Call Julia function to solve a linear programming problem from Java</h3>

<pre style="background-color:#2b2b2b;color:#a9b7c6;font-family:'宋体';font-size:12.0pt;">
<span style="color:#629755;font-style:italic;">/**<br></span><span style="color:#629755;font-style:italic;"> * call Julia server to solve a linear programming problem<br></span><span style="color:#629755;font-style:italic;"> * The function is named as 'solveDmlLp'<br></span><span style="color:#629755;font-style:italic;"> */<br></span><span style="color:#bbb529;">@Test<br></span><span style="color:#cc7832;">public void </span><span style="color:#ffc66d;">testRemoteLinearProgramming</span>() {<br>    <span style="color:#629755;font-style:italic;">/**<br></span><span style="color:#629755;font-style:italic;">     * min c'x<br></span><span style="color:#629755;font-style:italic;">     * s.t. Ax &gt;= b, x&gt;=0<br></span><span style="color:#629755;font-style:italic;">     */<br></span><span style="color:#629755;font-style:italic;">    </span><span style="color:#cc7832;">double</span>[] c = {-<span style="color:#6897bb;">3</span><span style="color:#cc7832;">, </span>-<span style="color:#6897bb;">1</span><span style="color:#cc7832;">, </span>-<span style="color:#6897bb;">2</span>}<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    double</span>[][] A = {<br>            {-<span style="color:#6897bb;">1.0</span><span style="color:#cc7832;">, </span>-<span style="color:#6897bb;">1.0</span><span style="color:#cc7832;">, </span>-<span style="color:#6897bb;">3.0</span>}<span style="color:#cc7832;">,<br></span><span style="color:#cc7832;">            </span>{-<span style="color:#6897bb;">2.0</span><span style="color:#cc7832;">, </span>-<span style="color:#6897bb;">2.0</span><span style="color:#cc7832;">, </span>-<span style="color:#6897bb;">5.0</span>}<span style="color:#cc7832;">,<br></span><span style="color:#cc7832;">            </span>{-<span style="color:#6897bb;">4.0</span><span style="color:#cc7832;">, </span>-<span style="color:#6897bb;">1.0</span><span style="color:#cc7832;">, </span>-<span style="color:#6897bb;">2.0</span>}<br>    }<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    double</span>[] b = {-<span style="color:#6897bb;">30</span><span style="color:#cc7832;">, </span>-<span style="color:#6897bb;">24</span><span style="color:#cc7832;">, </span>-<span style="color:#6897bb;">36</span>}<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    </span>String ip = <span style="color:#6a8759;">"127.0.0.1"</span><span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    int </span>port = <span style="color:#6897bb;">6996</span><span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    </span>Ju4jaClient client = <span style="color:#cc7832;">new </span>Ju4jaClient(ip<span style="color:#cc7832;">, </span>port)<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    </span>Object[] as = {c<span style="color:#cc7832;">, </span>A<span style="color:#cc7832;">, </span>b}<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    </span>JavaCallResult result = client.invokeFunction(<span style="color:#6a8759;">"solveDmlLp"</span><span style="color:#cc7832;">, </span><span style="color:#6a8759;">"RereDmlLpSolver"</span><span style="color:#cc7832;">, </span>as)<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">    </span><span style="color:#808080;">//System.out.println(result);<br></span><span style="color:#808080;">    </span><span style="color:#cc7832;">if </span>(result != <span style="color:#cc7832;">null</span>) {<br>        System.<span style="color:#9876aa;font-style:italic;">out</span>.println(result.getResultStr())<span style="color:#cc7832;">;<br></span><span style="color:#cc7832;">        </span><span style="color:#808080;">//System.out.println(result.getStatus());<br></span><span style="color:#808080;">    </span>}
<br>}
</pre>

<h3>Basic usage 2.2 - Code snippet in Julia defines "solveDmlLp" function to solve linear programming problem</h3>

<div><div><pre style="background-color: rgb(43, 43, 43);"><font color="#a9b7c6" face="宋体"><span style="font-size: 16px;">

#This module works as a example of solving linear programming problems for Ju4ja
module RereDmlLpSolver
using JuMP
using GLPK
export solveDmlLp

  #solve linear programming
&nbsp;  function solveDmlLp(c::Vector,A::Matrix,b::Vector,ifReg::Bool=true)
&nbsp;&nbsp;    model = JuMP.Model(with_optimizer(GLPK.Optimizer))
&nbsp;&nbsp;    #println(length(c))
&nbsp;&nbsp;    @variable(model, x[i=1:length(c)])
&nbsp;&nbsp;    @constraint(model, con, A * x .&gt;= b)
&nbsp;&nbsp;    @constraint(model, con0, x .&gt;= 0)
&nbsp;&nbsp;    @objective(model, Min, c'*x )
&nbsp;&nbsp;    status = optimize!(model)
&nbsp;&nbsp;    result=JuMP.value.(x)
&nbsp;&nbsp;    obj=JuMP.objective_value(model)
&nbsp;&nbsp;    println("x = ", result)
&nbsp;&nbsp;    println("Objective value: ", obj)
&nbsp;&nbsp;    return result
&nbsp;  end
end</span></font><span style="color: rgb(169, 183, 198); font-family: 宋体; font-size: 12pt;">
</span></pre></div></div><div><br></div>

Ju4Ja builds socket communications between Java (client) and Julia (Server), automatically transforms the numeric (float, vector, matrix) arguments of Java to Julia format, 
invokes Julia functions, and finally returns the results to Java caller.

<h4>
More examples can be found here: <a href="https://github.com/lteb2002/ju4ja/tree/master/ju4ja/java/com/reremouse/ju4ja/example">Examples</a>
</h4>
