# 数据库设计文档

**数据库名：** bikescreen

**文档版本：** 1.0.0

**文档描述：** 数据库设计文档生成

| 表名                  | 说明       |
| :---: | :---: |
| [bike](#bike) | 单车 |
| [bikeorder](#bikeorder) | 单车订单 |
| [income](#income) | 收益 |
| [invert](#invert) | 单车投放 |
| [menu](#menu) | 菜单 |
| [notice](#notice) | 公告通知 |
| [platform](#platform) | 平台 |
| [recovery](#recovery) | 回收 |
| [user](#user) | 用户 |

**表名：** <a id="bike">bike</a>

**说明：** 单车

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 单车id  |
|  2   | name |   varchar   | 255 |   0    |    Y     |  N   |       | 单车名称  |
|  3   | platform |   int   | 10 |   0    |    Y     |  N   |       | 单车平台  |
|  4   | first_price |   decimal   | 11 |   2    |    Y     |  N   |       | 起步价（元）  |
|  5   | first_distance |   int   | 10 |   0    |    Y     |  N   |       | 起步时长(分钟）  |
|  6   | price |   decimal   | 11 |   2    |    Y     |  N   |       | 计时价(元/分钟）  |
|  7   | image |   varchar   | 255 |   0    |    Y     |  N   |       | 单车图片  |
|  8   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  9   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |
|  10   | remark |   varchar   | 255 |   0    |    Y     |  N   |       | 介绍  |
|  11   | count |   int   | 10 |   0    |    Y     |  N   |   0    | 单车投放总数  |

**表名：** <a id="bikeorder">bikeorder</a>

**说明：** 单车订单

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 订单id  |
|  2   | order_code |   varchar   | 255 |   0    |    Y     |  N   |       | 订单编号  |
|  3   | user |   int   | 10 |   0    |    Y     |  N   |       | 下单用户  |
|  4   | bike |   int   | 10 |   0    |    Y     |  N   |       | 使用单车  |
|  5   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 开始使用时间  |
|  6   | money |   decimal   | 12 |   2    |    Y     |  N   |       | 订单金额  |
|  7   | status |   int   | 10 |   0    |    Y     |  N   |   0    | 订单状态，0已下单，未确认完成，1，已完成确认，未支付，2已支付  |
|  8   | finish_time |   datetime   | 19 |   0    |    Y     |  N   |       | 结束单车使用时间  |
|  9   | use_time |   int   | 10 |   0    |    Y     |  N   |   0    | 使用时长  |
|  10   | pay_time |   datetime   | 19 |   0    |    Y     |  N   |       | 支付时间  |

**表名：** <a id="income">income</a>

**说明：** 收益

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 收益id  |
|  2   | user |   int   | 10 |   0    |    Y     |  N   |       | 用户  |
|  3   | bike |   int   | 10 |   0    |    Y     |  N   |       | 单车  |
|  4   | bikeorder |   int   | 10 |   0    |    Y     |  N   |       | 订单  |
|  5   | money |   decimal   | 12 |   2    |    Y     |  N   |       | 金额  |
|  6   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 收益时间  |

**表名：** <a id="invert">invert</a>

**说明：** 单车投放

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 投放id  |
|  2   | bike |   int   | 10 |   0    |    Y     |  N   |       | 投放单车  |
|  3   | count |   int   | 10 |   0    |    Y     |  N   |       | 投放量  |
|  4   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 投放时间  |
|  5   | address |   varchar   | 255 |   0    |    Y     |  N   |       | 投放地址  |
|  6   | province |   varchar   | 255 |   0    |    Y     |  N   |       | 投放省份  |
|  7   | city |   varchar   | 255 |   0    |    Y     |  N   |       | 投放城市  |
|  8   | lon |   decimal   | 11 |   7    |    Y     |  N   |       | 经度  |
|  9   | lat |   decimal   | 11 |   7    |    Y     |  N   |       | 纬度  |

**表名：** <a id="menu">menu</a>

**说明：** 菜单

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 菜单ID  |
|  2   | menuCode |   varchar   | 8 |   0    |    Y     |  N   |       | 菜单编码  |
|  3   | menuName |   varchar   | 16 |   0    |    Y     |  N   |       | 菜单名字  |
|  4   | menuLevel |   varchar   | 2 |   0    |    Y     |  N   |       | 菜单级别  |
|  5   | menuParentCode |   varchar   | 8 |   0    |    Y     |  N   |       | 菜单的父code  |
|  6   | menuClick |   varchar   | 16 |   0    |    Y     |  N   |       | 点击触发的函数  |
|  7   | menuRight |   varchar   | 255 |   0    |    Y     |  N   |       | 权限0超级管理员，1表示管理员，2表示普通用户，可以用逗号组合使用  |
|  8   | menuComponent |   varchar   | 200 |   0    |    Y     |  N   |       | 菜单主键  |
|  9   | menuIcon |   varchar   | 100 |   0    |    Y     |  N   |       | 菜单图标  |

**表名：** <a id="notice">notice</a>

**说明：** 公告通知

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 公告id  |
|  2   | title |   varchar   | 255 |   0    |    Y     |  N   |       | 公告标题  |
|  3   | content |   varchar   | 255 |   0    |    Y     |  N   |       | 公告内容  |
|  4   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 发布时间  |

**表名：** <a id="platform">platform</a>

**说明：** 平台

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 主键  |
|  2   | name |   varchar   | 100 |   0    |    N     |  N   |       | 分类名  |
|  3   | remark |   varchar   | 1000 |   0    |    Y     |  N   |       | 备注  |
|  4   | create_time |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    | 创建时间  |
|  5   | property |   int   | 10 |   0    |    Y     |  N   |       | 资产，单位/万亿元  |

**表名：** <a id="recovery">recovery</a>

**说明：** 回收

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 回收id  |
|  2   | bike |   int   | 10 |   0    |    Y     |  N   |       | 回收单车  |
|  3   | count |   int   | 10 |   0    |    Y     |  N   |       | 回收量  |
|  4   | create_time |   datetime   | 19 |   0    |    Y     |  N   |       | 回收时间  |
|  5   | address |   varchar   | 255 |   0    |    Y     |  N   |       | 回收地址  |
|  6   | province |   varchar   | 255 |   0    |    Y     |  N   |       | 回收省份  |
|  7   | city |   varchar   | 255 |   0    |    Y     |  N   |       | 回收城市  |
|  8   | lon |   decimal   | 11 |   7    |    Y     |  N   |       | 经度  |
|  9   | lat |   decimal   | 11 |   7    |    Y     |  N   |       | 纬度  |

**表名：** <a id="user">user</a>

**说明：** 用户

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 主键  |
|  2   | name |   varchar   | 100 |   0    |    N     |  N   |       | 名字  |
|  3   | password |   varchar   | 20 |   0    |    N     |  N   |       | 密码  |
|  4   | telephone |   varchar   | 20 |   0    |    Y     |  N   |       | 电话  |
|  5   | age |   int   | 10 |   0    |    Y     |  N   |       | 年龄  |
|  6   | sex |   int   | 10 |   0    |    N     |  N   |   1    | 性别  |
|  7   | role |   int   | 10 |   0    |    N     |  N   |   2    | 角色0超级管理员，1管理员，2用户  |
|  8   | status |   int   | 10 |   0    |    N     |  N   |   1    | 是否有效，1正常，0注销  |
|  9   | avatar |   varchar   | 255 |   0    |    Y     |  N   |   default    | 头像  |
|  10   | create_time |   datetime   | 19 |   0    |    N     |  N   |   CURRENT_TIMESTAMP    | 注册时间  |
|  11   | update_time |   datetime   | 19 |   0    |    Y     |  N   |       | 修改时间  |
