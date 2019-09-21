package variable

/**
 *
 * @author chensj(chensj@winning.com.cn)
 * @date 2019-09-21 14:34
 */

// 闭包定义
def closu = { println('This is Closure')}
// 闭包调用
closu.call()
closu()

// 闭包参数 箭头前为参数，后面为闭包体
def closure_param = { name -> println("Hello ${name}, welcome to groovy wrold!")}
// 调用
closure_param('aaaa')
closure_param.call('bbbb')
// 多参数
closure_param = { name,age -> println("Hello ${name}, age is ${age} ,welcome to groovy wrold!")}
// 调用
closure_param('aa',20)
// 隐式参数
closure_param = { println("Hello , ${it}")}
// 调用
closure_param('ccc')

closure_param =  { println("Hello , ${it}")}
closure_param();

println('闭包的返回值')
// 使用return获取返回值
closure_param = {name -> return "Hello , ${name} !"}
def result = closure_param('Groovy')
println(result)
// 不使用return
closure_param = {name -> println( "Hello , ${name} !")}
result = closure_param('Groovy')
println(result) // 返回null