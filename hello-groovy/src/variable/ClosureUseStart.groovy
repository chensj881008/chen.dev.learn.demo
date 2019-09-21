package variable

/**
 * 闭包的使用
 * @author chensj(chensj@winning.com.cn)
 * @date 2019-09-21 15:17
 */
//闭包使用
//- 与基本类型的集合使用
int x = 10
// upto 用来求num的阶乘
static int fab(int num){
    int result = 1;
    1.upto(num,{n -> result *= n})
    return result;
}
def result = fab(x)
println(result)
// downto 用来求num的阶乘
static int fab2(int num){
    int result = 1;
    num.downto(1){
        n ->  result *=n
    }
    return result
}
result = fab2(x)
println(result)
// times 方法，不能用来获取阶乘，源码中index从0开始
static int count(num){
    int result = 0
    num.times {
        n -> result += n
    }
    return result
}

result = count(x)
println(result)

//- 与String结合使用

//- 与数据结构结合使用

//- 与文件等结合使用
