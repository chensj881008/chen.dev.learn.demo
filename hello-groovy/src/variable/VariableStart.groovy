package variable

/*变量学习*/

// 在Groovy中 基本类型都是对象
int x = 10
double d = 3.14

println x.class
println d.class

def a = 1
def b = 3.14
def c = 'dddd'
println(a.class)
println(b.class)
println(c.class)
c = 1.2
println(c.class)

// 字符串
def name = 'This is String'
println(name.class)

def thupleName = '''This is a Tuple String'''
println(thupleName.class)

// 上述两种字符串定义方式的区别主要在于后一种可以定义格式

def all_name = '''
 David
 Tom
 Green
'''

println(all_name)


def double_name = "This common module"
println("double_name")
println(double_name.class)

println('GString')
def aname = 'demo'
def hello = "hello ${aname}"
println(hello)
println(hello.class)

// 在字符串中使用表达式
println('在字符串中使用表达式')
// 可扩展做任意的表达式
def sum = "The sum is of 2 add 3 equals ${ 2 + 3}"
println(sum)

// string gstring
println('String GString Convert')

static String echo(String message){
    return message
}

def result = echo(sum)
println(result)
/*========================= 字符串方法 ===============================================*/
// 字符串填充
// center 字符串填充，以当前字符串为中心，两边填充
// 第一个参数指定填充后字符串长度，第二个参数指定填充的内容，不填则为空格
def str = 'Groovy'
println str.center(8,'1')
// padLeft 字符串填充，从当前字符串左边填充 参数同center
println str.padLeft(8,'1')
// padLeft 字符串填充，从当前字符串右边填充
println str.padRight(8,'1')
// 字符串对比 可以使用方法compareTo，也可以使用操作符 > < =
def str2 = 'Hello'
// 比较的是两个字符串的Unicode编码
println(str > str2)

// 索引
// java 结果 G
println(str.getAt(0))
// groovy  结果 G
println(str[0])
// 传入范围 结果 Gr
println(str[0..1])

// 减法 结果 Groovy 因为没有包含指定的值
println(str.minus(str2))
println(str - str2)

str = "hello groovy"
str2 = "hello"
println("minus")
println("str:${str}")
println("str2:${str2}")
println("str - str2:${str - str2}")

// reverse 反转 倒序
println("String reverse:${str.reverse()}")

// 首字母大写 仅限于首字母
println("首字母大写:${str.capitalize()}")

// 判断是否是数字型字符串
println("String is Number String:${str.isNumber()}")
// 可以通过使用toInteger/toLong等转换为指定的数字类型
str = '12345'
println("String to Integet:${str.toInteger()}")