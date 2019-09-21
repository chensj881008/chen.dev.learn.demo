package variable

/*
 *
 * @author chensj(chensj@winning.com.cn)
 * @date 2019-09-21 14:01
 *
 */
// switch 支持任意类型的类型匹配
def x = 1.23
def result
switch (x) {
    case 'foo':
        result = 'found foo'
        break
    case 'bar':
        result = 'found bar'
        break
    case [1,2,3, 'list']:  // 列表
        result = 'found list'
        break
    case 12..30: // 范围
        result = 'in range'
        break
    case Integer:
        result = 'Integer'
        break
    case BigDecimal:
        result = 'BigDecimal'
        break
    default:
        result = 'default'
        break
}
println(result)

// 对范围的for循环
def sum = 0
for ( i in 0..9) {
    sum +=i
}
println(sum)
/*对list的循环*/
sum = 0
for(i in [1,2,3,4,5,6,7,8,9]){
    sum += i
}
println(sum)
/*对map循环*/
sum = 0
for (item in ['d1':1,'d2':2,'d3':3,'d4':4,'d5':5]){
    sum += item.value
}
println(sum)