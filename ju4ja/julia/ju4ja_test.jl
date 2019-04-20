

#set the path where the modules are located
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
end
