Geospatial 地理位置

* geoadd [key] [longitude] [latitude] [city] 添加地理位置
* geopos [key] [city] 获取城市的经纬度
* geodist [key] [city1] [city2] [m] 获取两个位置的直线距离，[m] 单位
* georadius [key] [longitude] [latitude] [radius] [m] 获取以给定的经纬度为中心，找出某个半径内的元素
* georadiusbymember [key] [city] [radius] [m] 找出位于指定元素周围的其他元素！ 

Hyperloglog基数（不重复的元素，可以接受误差）

* pfadd [key] [value1] [value2] 创建一组元素
* pfcount [key] 统计基数数量
* pfmerge [key3] [key1] [key2] 合并两组到第三组

Bitmap位存储

* setbit [key] [index] [0|1] 设置位值
* getbit [key] [index] 获取位值
* bitcount [key]  统计为1的值







