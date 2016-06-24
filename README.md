

# 机票管理系统


**开发工具**：Android Studio， Genymotion，Ubuntu
**API 版本**：Android SDK 23
**数据库**：SQLite3

## 实现功能

1. 	创建航空公司表、航班表等信息。

2.	每个航班信息的输入。

3.	每个航班的坐位信息的输入；

4.	当旅客进行机票预定时，输入旅客基本信息，系统为旅客安排航班；

5.	旅客在飞机起飞前一天凭取票通知交款取票；

6.	旅客能够退订机票；

7.	能够查询每个航班的预定情况、计算航班的满座率。
#### 入口
 ![这里写图片描述](http://img.blog.csdn.net/20160624094419952)
#### 航班管理  
 ![这里写图片描述](http://img.blog.csdn.net/20160624094442499)
 
#### 机票管理
 ![这里写图片描述](http://img.blog.csdn.net/20160624094503671)
## 数据库设计

### 表关系
![这里写图片描述](http://img.blog.csdn.net/20160624103348505)

### 参照数据库范式进行设计：
#### 1. 第一范式：数据库表中所有字段都是单一属性的，不可再分的。
**数据库中所有表都是二维表**
#### 2. 第二范式：数据库中不存在任意非关键字段对候选关键字段的部分函数依赖
**所有单关键字段的表都满足第二范式**
#### 3.第三范式：不存在非关键字段对任意候选关键字段的传递函数依赖

1. 航班表   
(航班编号 PK integer ,航空公司，起点，终点，起飞时间，票价)

```
CREATE TABLE flights (
    _id       INTEGER  PRIMARY KEY  AUTOINCREMENT
                                NOT NULL,
    flight_company  TEXT        NOT NULL,
    flight_starting TEXT        NOT NULL,
    flight_ending   TEXT        NOT NULL,
    flight_time     DATETIME    NOT NULL,
    flight_price    REAL (2)    NOT NULL
);

```
2. 旅客表   
(旅客账号 PK，机票编号 FK)

```
CREATE TABLE guest (
    guest_name TEXT PRIMARY KEY
                    UNIQUE
                    NOT NULL,
    guest_pwd  TEXT NOT NULL
);

```

3. 机票表   
(机票编号 PK, 座位号,航班编号 FK,座位信息)


```
CREATE TABLE ticket (
    _id INTEGER PRIMARY KEY AUTOINCREMENT
                      NOT NULL,
    flight_id INTEGER REFERENCES flights (flight_id) 
                      NOT NULL,
    seat_id   INTEGER UNIQUE
                      NOT NULL,
    seat_info TEXT    NOT NULL,
    is_pick Boolean NOE NULL
);

```
4. 订单表


```
CREATE TABLE orders (
    _id  INTEGER PRIMARY KEY AUTOINCREMENT
                       NOT NULL,
    guset_name TEXT    REFERENCES guest (guest_name) 
                       NOT NULL,
    ticket_id  INTEGER REFERENCES ticket (ticket_id) 
                       NOT NULL,
    is_paying boolean NOT NULL
                       
);

```

## 下载
http://pre.im/tikcet
